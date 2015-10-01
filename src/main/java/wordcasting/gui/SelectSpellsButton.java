package wordcasting.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import wordcasting.model.Model;
import wordcasting.model.WordSpell;

class SelectSpellsButton extends JButton {
  SelectSpellsButton(Model model, JFrame owner) {
    super("Select Spells");

    Collection<String> selectedSpells = new HashSet<>();
    JDialog dialog = new JDialog(owner, "Select Spells");
    JPanel panel = new JPanel(new GridLayout(8, 0));
    dialog.add(panel, BorderLayout.CENTER);

    Collection<JCheckBox> checkboxes = new ArrayList<>();
    for (WordSpell spell: model.getSpells().values()) {
      JCheckBox check = new JCheckBox(spell.getName());
      check.addActionListener((evt) -> {
          if (check.isSelected()) {
            selectedSpells.add(spell.getName());
          } else {
            selectedSpells.remove(spell.getName());
          }
        });
      if (model.getSpellList().contains(spell.getName())) {
        // Use doClick instead of setSelected to trigger the action
        check.doClick(0);
      }
      checkboxes.add(check);
      panel.add(check);
    }

    Box controlPanel = Box.createHorizontalBox();
    dialog.add(controlPanel, BorderLayout.PAGE_END);

    JButton selectAll = new JButton("Select All");
    selectAll.addActionListener((evt) ->
            checkboxes.stream().
            filter((check) -> ! check.isSelected()).
            forEach((check) -> check.doClick(0)));


    JButton clearAll = new JButton("Clear All");
    clearAll.addActionListener((evt) ->
            checkboxes.stream().
            filter((check) -> check.isSelected()).
            forEach((check) -> check.doClick(0)));

    JButton cancel = new JButton("Cancel");
    cancel.addActionListener((evt) -> dialog.setVisible(false));
    JButton ok = new JButton("Ok");
    ok.addActionListener((evt) -> {
        model.setSpellList(selectedSpells);
        dialog.setVisible(false);
      });


    controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    controlPanel.add(clearAll);
    controlPanel.add(Box.createHorizontalStrut(20));
    controlPanel.add(selectAll);
    controlPanel.add(Box.createHorizontalGlue());
    controlPanel.add(cancel);
    controlPanel.add(Box.createHorizontalStrut(20));
    controlPanel.add(ok);

    dialog.pack();

    addActionListener((event) -> dialog.setVisible(true));
  }

}

