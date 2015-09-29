package wordcasting.model;

public class WordSpell {
  public enum Group {
    ACID,
    ALIGNMENT,
    ARMOR,
    BODY,
    CHANGE,
    COLD,
    COMMAND,
    CONCEALING,
    DESTRUCTION,
    DIVINATION,
    ELECTRICITY,
    FIRE,
    FLIGHT,
    GRAVITY,
    HEALING,
    ILLUSION,
    LANGUAGE,
    POWER,
    SONIC,
    TELEPORTATION,
    TIME,
    WALL,
    WEATHER,
    WOUNDING,
  }
  public WordSpell(String name,
                   int level,
                   String duration,
                   Group group) {
    this.name = name;
    this.level = level;
    this.duration = duration;
    this.group = group;
  }


  /**
   * Gets the value of group
   *
   * @return the value of group
   */
  public final Group getGroup() {
    return this.group;
  }

  /**
   * Gets the value of name
   *
   * @return the value of name
   */
  public final String getName() {
    return this.name;
  }

  /**
   * Gets the value of duration
   *
   * @return the value of duration
   */
  public final String getDuration() {
    return this.duration;
  }

  /**
   * Gets the value of level
   *
   * @return the value of level
   */
  public final int getLevel() {
    return this.level;
  }

  private final String name;
  private final String duration;
  private final int level;
  private final Group group;
}



