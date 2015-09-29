package wordcasting.gui;

import wordcasting.model.WordSpell;

import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

class WordCastingTableModel extends AbstractTableModel {
  WordCastingTableModel() {
    data =
      Arrays.asList(
          new WordSpell("Accelerate",
                        2,
                        "1 round/level",
                        Group.TIME),
          new WordSpell("Cramp",
                        0,
                        "1 round",
                        Group.PAIN));

  }

  @Override
  public int getRowCount() {
    return data.size();
  }


  @Override
  public int getColumnCount() {
    return 3;
  }

  @Override
  public Object getValueAt(int row, int column) {
    WordSpell spell = data.get(row);
    switch (column) {
    case 0: return spell.getName();
    case 1: return spell.getLevel();
    case 2: return spell.getDuration();
    default:
      throw new IllegalArgumentException("Bad column number: " + column);
    }
  }

  @Override
  public String getColumnName(int column) {
    switch (column) {
    case 0: return "Name";
    case 1: return "Level";
    case 2: return "Duration";
    default:
      throw new IllegalArgumentException("Bad column number: " + column);
    }
  }

  private final List<WordSpell> data;
}
