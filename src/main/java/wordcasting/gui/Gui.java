package wordcasting.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wordcasting.model.Model;

public class Gui {
  private final JFrame frame;
  private final Model model;
  private JFileChooser spellListChooser;

  private final static String SPELL_LIST_DIRECTORY = "spellListDirectory";


  private final static Logger logger =
    LoggerFactory.getLogger(Gui.class);


  public Gui(Model model) throws IOException {
    frame = new JFrame("Wordcasting");
    this.model = model;
    SwingUtilities.invokeLater(() -> {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    TableCellRenderer renderer = new WordSpellRenderer();
    WordCastingTableModel tableModel = new WordCastingTableModel(model);
    JTable table = new JTable(tableModel) {
        @Override
        public TableCellRenderer getCellRenderer(int r, int c) {
          return renderer;
        }};

    for (WordSpellColumns column: WordSpellColumns.values()) {
      table.getColumnModel().getColumn(column.ordinal()).setPreferredWidth(column.getWidth());
    }

    model.addModelListener((evt) -> {
        if (evt.getNumSpells() > evt.getOldNumSpells()) {
          tableModel.fireTableRowsInserted(0, evt.getNumSpells()-1);
        } else {
          tableModel.fireTableRowsDeleted(0, evt.getOldNumSpells()-1);
        }});

    frame.add(new JScrollPane(table), BorderLayout.CENTER);

    TableRowSorter<WordCastingTableModel> sorter =
      new WordSpellTableRowSorter(tableModel);

    sorter.setRowFilter(new WordSpellRowFilter(model));
    table.setRowSorter(sorter);

    frame.add(new SelectSpellsButton(model, frame),
              BorderLayout.PAGE_END);

    frame.setJMenuBar(makeMenuBar());
      });
  }

  private JMenuBar makeMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    // File menu
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('f');
    menuBar.add(fileMenu);

    fileMenu.add(new BasicAction("Save Spell list") {
        { acceleratorKey('s'); }
        @Override public void actionPerformed(ActionEvent evt) {
          saveSpellList();
        }});

    fileMenu.add(new BasicAction("Load Spell list") {
        { acceleratorKey('l'); }
        @Override public void actionPerformed(ActionEvent evt) {
          loadSpellList();
        }});

    fileMenu.addSeparator();
    fileMenu.add(new BasicAction("Exit") {
        { acceleratorKey('x'); }
        @Override public void actionPerformed(ActionEvent evt) {
          if (JOptionPane.showConfirmDialog(frame,
                                            "Really exit?",
                                            "Confirm Exit",
                                            JOptionPane.YES_NO_OPTION) ==
              JOptionPane.YES_OPTION) {
            System.exit(0);
          }}});

    return menuBar;
  }

  private JFileChooser getSpellListChooser() {
    if (null == spellListChooser) {
      spellListChooser = new JFileChooser(getPreferenceOrElse(SPELL_LIST_DIRECTORY, "."));
      FileNameExtensionFilter filter =
        new FileNameExtensionFilter("Spell Lists", Model.SPELL_LIST_EXTENSION);
      spellListChooser.setFileFilter(filter);
    }
    return spellListChooser;
  }


  private void saveSpellList() {
    JFileChooser chooser = getSpellListChooser();
    int returnVal = chooser.showSaveDialog(frame);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      String name = chooser.getCurrentDirectory() +
        File.separator +
        chooser.getSelectedFile().getName();
      try {
        model.saveSpellList(name);
        setPreference(SPELL_LIST_DIRECTORY,
                      chooser.getCurrentDirectory().getAbsolutePath());
      } catch (IOException exc) {
        exc.printStackTrace();
        JOptionPane.showMessageDialog(frame,
                                      new Object[] { "Unable to save spell list",
                                                     exc.getMessage()
                                      },
                                      "Unable to save",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void loadSpellList() {
    JFileChooser chooser = getSpellListChooser();
    int returnVal = chooser.showOpenDialog(frame);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      String name = chooser.getCurrentDirectory() +
        File.separator +
        chooser.getSelectedFile().getName();
      try {
        model.loadSpellList(name);
        setPreference(SPELL_LIST_DIRECTORY,
                      chooser.getCurrentDirectory().getAbsolutePath());
      } catch (IOException exc) {
        exc.printStackTrace();
        JOptionPane.showMessageDialog(frame,
                                      new Object[] { "Unable to load spell list",
                                                     exc.getMessage()
                                      },
                                      "Unable to load",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Get a stored preference
   * @param key String to look up value
   * @param fallback Default value if there is no value stored
   */
  private String getPreferenceOrElse(String key,
                                     String fallback) {
    Preferences prefs = Preferences.userNodeForPackage(getClass());
    String result = prefs.get(key, null);
    if (null == result) {
      logger.info("Key {} does not exist in user preferences");
      prefs = Preferences.systemNodeForPackage(getClass());
      result = prefs.get(key, fallback);
    }
    return result;
  }

  /**
   * Set a stored preference
   * @param key String to look up value
   * @param value Associated value
   */
  private void setPreference(String key,
                             String value) {
    Preferences prefs = Preferences.userNodeForPackage(getClass());
    prefs.put(key, value);
  }

  public void show() {
    SwingUtilities.invokeLater(() -> {
        frame.pack();
        frame.setVisible(true);
      });
  }
}
