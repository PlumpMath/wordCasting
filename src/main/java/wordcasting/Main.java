package wordcasting;

import java.io.IOException;
import wordcasting.gui.Gui;
import wordcasting.model.Model;

public class Main {
  private final Gui gui;

  public Main() throws IOException {
    Model model = new Model();
    gui = new Gui(model);
  }

  public void run() {
    gui.show();
  }


  public static void main(String[] args) {
    try {
      new Main().run();
    } catch (Throwable exc) {
      System.out.println("Unable to create GUI");
      exc.printStackTrace();
    }
  }
}
