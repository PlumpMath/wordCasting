package wordcasting.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk7.Jdk7Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.File;
import java.io.IOException;
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

    ObjectMapper mapper = new ObjectMapper();
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
    spellList.clear();
    spellList.addAll(newSpells);
    fireModelChangedEvent();
  }

  public void addModelListener(ModelListener l) {
    if (!listeners.contains(l)) listeners.add(l);
  }

  private void fireModelChangedEvent() {
    for (ModelListener l: listeners)
      l.modelChanged(new ModelEvent(this));
  }

  private final Collection<ModelListener> listeners;
  private final Map<String, WordSpell> spells;
  private final Collection<String> spellList;
}

