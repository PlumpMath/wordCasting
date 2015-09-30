package wordcasting.gui;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class Gui {
  private final JFrame frame;

  public Gui() throws IOException {
    frame = new JFrame("Wordcasting");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    TableCellRenderer renderer = new WordSpellRenderer();
    JTable table = new JTable(new WordCastingTableModel()) {
        @Override
        public TableCellRenderer getCellRenderer(int r, int c) {
          System.out.println("Calling getCellRenderer()");
          return renderer;
        }};

    frame.add(new JScrollPane(table));
  }

  public void show() {
    frame.pack();
    frame.setVisible(true);
  }
}
