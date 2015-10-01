package wordcasting.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import wordcasting.model.Model;

public class Gui {
  private final JFrame frame;

  public Gui(Model model) throws IOException {
    frame = new JFrame("Wordcasting");
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

    frame.add(new JScrollPane(table), BorderLayout.CENTER);

    TableRowSorter<WordCastingTableModel> sorter =
      new WordSpellTableRowSorter(model, tableModel);

    sorter.setRowFilter(new WordSpellRowFilter(model));
    table.setRowSorter(sorter);

    frame.add(new SelectSpellsButton(model, frame),
              BorderLayout.PAGE_END);
  }

  public void show() {
    frame.pack();
    frame.setVisible(true);
  }
}
