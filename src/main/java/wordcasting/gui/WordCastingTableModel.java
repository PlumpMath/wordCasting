package wordcasting.gui;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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
    Collections.sort(data, (first, second) -> {
        int delta = first.getLevel() - second.getLevel();
        if (delta == 0) {
          return first.getName().compareTo(second.getName());
        } else {
          return delta;
        }
      });


    System.out.println("read " + data);


  }

  @Override
  public int getRowCount() {
    return data.size();
  }


  @Override
  public int getColumnCount() {
    return 4;
  }

  @Override
  public Object getValueAt(int row, int column) {
    return data.get(row);
  }

  @Override
  public String getColumnName(int column) {
    switch (column) {
    case 0: return "Level";
    case 1: return "Name";
    case 2: return "Duration";
    case 3: return "Targets";
    default:
      throw new IllegalArgumentException("Bad column number: " + column);
    }
  }


  private final List<WordSpell> data;
}
