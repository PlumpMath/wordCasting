package wordcasting.gui;

import java.util.Comparator;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import wordcasting.model.Model;
import wordcasting.model.WordSpell;

class WordSpellTableRowSorter extends TableRowSorter<WordCastingTableModel> {
  WordSpellTableRowSorter(WordCastingTableModel tableModel) {
    super(tableModel);

    for (WordSpellColumns column: WordSpellColumns.values()) {
      this.setComparator(column.ordinal(),
                         (WordSpell first, WordSpell second) ->
                         column.getDisplayValue(first).compareTo(column.getDisplayValue(second)));
                         // new Comparator<WordSpell>() {
                         //   @Override public int compare(WordSpell first, WordSpell second) {
                         //     return column.getDisplayValue(first).compareTo(column.getDisplayValue(second));
                         //   }});
    }

  }
}

