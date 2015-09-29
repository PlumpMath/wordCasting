package wordcasting;

import wordcasting.gui.Gui;

public class Main {
  private final Gui gui;

  public Main() {
    gui = new Gui();
  }

  public void run() {
    gui.show();
  }


  public static void main(String[] args) {
    new Main().run();
  }
}
