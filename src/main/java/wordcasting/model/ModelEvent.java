package wordcasting.model;

import java.util.EventObject;

public class ModelEvent extends EventObject {
  ModelEvent(Object source) {
    super(source);
  }
}
