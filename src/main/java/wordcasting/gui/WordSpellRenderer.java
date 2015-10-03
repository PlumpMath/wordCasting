package wordcasting.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import wordcasting.model.WordSpell.Group;
import wordcasting.model.WordSpell.Target;
import wordcasting.model.WordSpell;


public class WordSpellRenderer
  extends DefaultTableCellRenderer {

  public WordSpellRenderer() {
    colors = new EnumMap<Group, Color>(Group.class);

    int numValues = Group.values().length;
    float base = 0.6f;
    float scale = 0.4f / numValues;
    for (Group group: Group.values()) {
      int index = group.ordinal();
      float red = base + index * scale;
      float green = base + ((3 * index) % numValues) * scale;
      float blue = base + ((9 * index) % numValues) * scale;
      colors.put(group, new Color(red, green, blue));
    }
  }


  @Override
  public Component getTableCellRendererComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus,
                                                 int row,
                                                 int column)
  {
    WordSpell spell = (WordSpell) value;
    WordSpellColumns which = WordSpellColumns.values()[column];
    String text = which.getDisplayValue(spell);
    setText(text);
    setBackground(getSpellColor(spell));
    setToolTipText(spell.getDescription());
    return this;
  }

  public Color getSpellColor(WordSpell spell) {
    return colors.get(spell.getGroup());
  }

  private final Map<WordSpell.Group, Color> colors;
}
