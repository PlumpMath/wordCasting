package wordcasting.gui;

import java.util.Collection;
import javax.swing.RowFilter;
import wordcasting.model.Model;
import wordcasting.model.WordSpell;

class WordSpellRowFilter extends RowFilter<WordCastingTableModel, Integer>
{
  public WordSpellRowFilter(Model model) {
    this.model = model;

   }

  @Override
    public boolean include(RowFilter.Entry<? extends WordCastingTableModel,
                           ? extends Integer> entry) {
    WordSpell spell = (WordSpell) entry.getValue(0);
    return model.getSpellList().contains(spell.getName());
  }

  private final Model model;
}
