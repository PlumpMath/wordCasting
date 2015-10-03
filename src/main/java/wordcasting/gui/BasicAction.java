package wordcasting.gui;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * Action implementation with more useful functionality
 */
public abstract class BasicAction extends AbstractAction {
  public BasicAction(String name) {
    super(name);
  }

  public void acceleratorKey(char key) {
    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(key));
  }
}
