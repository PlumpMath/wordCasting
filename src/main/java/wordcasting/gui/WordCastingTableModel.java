package wordcasting.gui;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import wordcasting.model.WordSpell;

class WordCastingTableModel extends AbstractTableModel {
  WordCastingTableModel() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jdk7Module());

    List<WordSpell> tmpdata =
      Arrays.asList(
          new WordSpell("Accelerate",
                        2,
                        "1 round/level",
                        WordSpell.Group.TIME),
          new WordSpell("Cramp",
                        0,
                        "1 round",
                        WordSpell.Group.PAIN));
    System.out.println("Would write");
    mapper.writeValue(System.out, tmpdata);

    data = mapper.readValue(new File("target/classes/spells.json"), List.class);
    System.out.println("read " + data);

    // data =
    //   Arrays.asList(
    //       new WordSpell("Accelerate",
    //                     2,
    //                     "1 round/level",
    //                     Group.TIME),
    //       new WordSpell("Cramp",
    //                     0,
    //                     "1 round",
    //                     Group.PAIN));

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
