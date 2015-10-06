package wordcasting.model;

import java.lang.annotation.Target;
import java.util.EnumSet;
import wordcasting.model.WordSpell.Group;

public class WordSpell {
  public enum Group {
    ACID,
    ALIGNMENT,
    ANIMAL,
    ARMOR,
    BINDING,
    BODY,
    BOLSTER,
    CHANGE,
    COLD,
    COMMAND,
    CONCEALING,
    DEATH,
    DESTRUCTION,
    DETECTION,
    DISPELLING,
    DIVINATION,
    ELECTRICITY,
    FIRE,
    FEAR,
    FORCE,
    FLIGHT,
    GRAVITY,
    HEALING,
    ILLUSION,
    ILLUMINATION,
    LANGUAGE,
    LIFE,
    PAIN,
    POWER,
    SONIC,
    SUMMONING,
    TELEPORTATION,
    TIME,
    WALL,
    WEATHER,
    WOUNDING,

  }

  public enum Target {
    SELECTED,
    PERSONAL,
    BARRIER,
    CONE,
    LINE,
    BURST;

    @Override
    public String toString() {
      return name().charAt(0) +
        name().substring(1).toLowerCase();
    }
  }

  public WordSpell(String name,
                   int level,
                   String duration,
                   Group group,
                   String description,
                   EnumSet<Target> target) {
    this.name = name;
    this.level = level;
    this.duration = duration;
    this.group = group;
    this.description = description;
    this.target = target.isEmpty()? EnumSet.allOf(Target.class): target;
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

  /**
   * Gets the value of description
   *
   * @return the value of description
   */
  public final String getDescription() {
    return this.description;
  }

  public final EnumSet<Target> getTarget() {
    return target;
  }

  private final String name;
  private final String duration;
  private final int level;
  private final Group group;
  private final String description;
  private final EnumSet<Target> target;
}



