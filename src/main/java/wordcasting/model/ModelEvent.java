package wordcasting.model;

import java.util.EventObject;

public class ModelEvent extends EventObject {
  ModelEvent(Object source,
             int numSpells,
             int oldNumSpells) {
    super(source);
    this.numSpells = numSpells;
    this.oldNumSpells = oldNumSpells;
  }


  /**
   * Gets the value of numSpells
   *
   * @return the value of numSpells
   */
  public final int getNumSpells() {
    return this.numSpells;
  }

  /**
   * Gets the value of oldNumSpells
   *
   * @return the value of oldNumSpells
   */
  public final int getOldNumSpells() {
    return this.oldNumSpells;
  }

  private final int numSpells;
  private final int oldNumSpells;

}
