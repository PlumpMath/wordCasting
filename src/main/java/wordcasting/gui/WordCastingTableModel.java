package wordcasting.gui;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import wordcasting.model.Model;
import wordcasting.model.WordSpell;

class WordCastingTableModel extends AbstractTableModel {
  WordCastingTableModel(Model model) {
    spells = sortSpells(model.getSpells().values());
  }

  private List<WordSpell> sortSpells(Collection<WordSpell> spells) {
    List<WordSpell> data = new ArrayList<WordSpell>(spells);

    Collections.sort(data, (first, second) -> {
        int delta = first.getLevel() - second.getLevel();
        if (delta == 0) {
          return first.getName().compareTo(second.getName());
        } else {
          return delta;
        }
      });

    return data;
  }

  @Override
  public int getRowCount() {
    return spells.size();
  }


  @Override
  public int getColumnCount() {
    return WordSpellColumns.values().length;
  }

  @Override
  public Object getValueAt(int row, int column) {
    return spells.get(row);
  }

  @Override
  public String getColumnName(int column) {
    return WordSpellColumns.values()[column].toString();
  }


  private final List<WordSpell> spells;
}
