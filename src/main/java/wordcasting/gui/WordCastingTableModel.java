package wordcasting.gui;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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
    mapper.registerModule(new ParameterNamesModule());

    data = mapper.readValue(new File("target/classes/spells.json"),
                            new TypeReference<List<WordSpell>>(){});
    System.out.println("read " + data);


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
