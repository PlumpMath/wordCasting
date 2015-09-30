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
    String text;
    switch (column) {
    case 0: text = Integer.toString(spell.getLevel()); break;
    case 1: text = spell.getName(); break;
    case 2: text = spell.getDuration(); break;
    case 3:
      if (spell.getTarget().size() == Target.values().length) {
        text = "Any";
      } else {
        text = spell.getTarget().toString();
      }
      break;

    default:
      throw new IllegalArgumentException("Unknown column: " + column);
    }
    setText(text);
    setBackground(colors.get(spell.getGroup()));
    setToolTipText(spell.getDescription());
    System.out.println("Renderer: " + this);
    return this;
  }

  private final Map<WordSpell.Group, Color> colors;
}
