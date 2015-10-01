package wordcasting.gui;

import java.util.logging.Level;
import wordcasting.model.WordSpell.Target;
import wordcasting.model.WordSpell;

public enum WordSpellColumns {
  Level {
    @Override public String getDisplayValue(WordSpell spell) {
      return Integer.toString(spell.getLevel());
    }
    @Override public int getWidth() { return 10; }

  },
  Name {
    @Override public String getDisplayValue(WordSpell spell) {
      return spell.getName();
    }
    @Override public int getWidth() { return 40; }
  },
  Duration {
    @Override public String getDisplayValue(WordSpell spell) {
      return spell.getDuration();
    }
    @Override public int getWidth() { return 40; }
  },
  Targets {
    @Override public String getDisplayValue(WordSpell spell) {
      if (spell.getTarget().size() == Target.values().length) {
        return "Any";
      } else {
        return spell.getTarget().toString();
      }
    }
    @Override public int getWidth() { return 40; }
  };

  public abstract String getDisplayValue(WordSpell spell);
  public abstract int getWidth();
}

