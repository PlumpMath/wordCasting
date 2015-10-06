package wordcasting.model;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Model for model-view-controller
 */
public class Model {
  public Model() throws IOException {
    listeners = new ArrayList<>();

    mapper = new ObjectMapper();
    mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    mapper.registerModule(new Jdk7Module());
    mapper.registerModule(new ParameterNamesModule());

    Collection<WordSpell> data =
      mapper.readValue(new File("target/classes/spells.json"),
                       new TypeReference<List<WordSpell>>(){});

    spells = new TreeMap<String, WordSpell>();
    spellList = new TreeSet<String>();
    for (WordSpell spell: data) {
      spells.put(spell.getName(), spell);
      spellList.add(spell.getName());
    }
  }

  /**
   * Get the map from the names of the spells to the spells.
   */
  public Map<String, WordSpell> getSpells() {
    return spells;
  }

  /**
   * Get the names of the spells in the current spell list.
   */
  public Collection<String> getSpellList() {
    return spellList;
  }

  /**
   * Set the selected spells
   */
  public void setSpellList(Collection<String> newSpells) {
    int oldNumSpells = spellList.size();
    spellList.clear();
    spellList.addAll(newSpells);

    fireModelChangedEvent(spellList.size(),
                          oldNumSpells);
  }

  public void addModelListener(ModelListener l) {
    if (!listeners.contains(l)) listeners.add(l);
  }

  private void fireModelChangedEvent(int numSpells,
                                     int oldNumSpells) {
    for (ModelListener l: listeners)
      l.modelChanged(new ModelEvent(this, numSpells, oldNumSpells));
  }

  /**
   * Save the current spell list to disk
   * @param name The name of the file.  The appropriate suffix is added if it is
   * missing.
   * @throws IOException if an error occurs writing the file
   */
  public void saveSpellList(String name) throws IOException {
    if (!name.endsWith("." + SPELL_LIST_EXTENSION)) {
      name += "." + SPELL_LIST_EXTENSION;
    }

    try (Writer writer = new BufferedWriter(new FileWriter(name)))
        {
          mapper.writeValue(writer, spellList);
        }
  }

  /**
   * Load the current spell list to disk
   * @param name The name of the file.  The appropriate suffix is added if it is
   * missing.
   * @throws IOException if an error occurs writing the file.  If this happens,
   * the spell list is unchanged.
   */
  public void loadSpellList(String name) throws IOException {
    List<String> list =
      mapper.readValue(new File(name), new TypeReference<List<String>>(){});

    // Do this after reading the data, in case the read throws an exceptions.
    int oldNumSpells = spellList.size();
    spellList.clear();
    spellList.addAll(list);

    fireModelChangedEvent(spellList.size(), oldNumSpells);

  }
  private final Collection<ModelListener> listeners;
  private final Map<String, WordSpell> spells;
  private final Collection<String> spellList;
  private final ObjectMapper mapper;

  /** Extension of files that hold a spell list */
  public final static String SPELL_LIST_EXTENSION = "spellList";
}


