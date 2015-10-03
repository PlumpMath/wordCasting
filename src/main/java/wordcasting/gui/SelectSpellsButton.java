package wordcasting.gui;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import wordcasting.model.Model;
import wordcasting.model.WordSpell;

class SelectSpellsButton extends JButton {
  SelectSpellsButton(Model model, JFrame owner) {
    super("Select Spells");

    Collection<String> selectedSpells = new HashSet<>();
    JDialog dialog = new JDialog(owner, "Select Spells");
    JComponent panel = Box.createVerticalBox(); // new JPanel(new GridLayout(8, 0));
    dialog.add(panel, BorderLayout.CENTER);

    // Groups spells by level
    ListMultimap<Integer, WordSpell> spellsByLevel =
       ArrayListMultimap.<Integer, WordSpell>create();
    model.getSpells().values().stream().
      forEachOrdered((spell) -> spellsByLevel.put(spell.getLevel(), spell));

    // Add them to the gui
    WordSpellRenderer renderer = new WordSpellRenderer();
    Collection<JCheckBox> checkboxes = new ArrayList<>();
    for(Map.Entry<Integer, Collection<WordSpell>> entry:
          spellsByLevel.asMap().entrySet()) {
      // Wrap the boxes after adding 8 spells.
      JComponent checksPanel = new JPanel(new GridLayout(0, 8));// new JPanel(new WrapLayout());
      panel.add(checksPanel);
      checksPanel.setBorder(BorderFactory.createTitledBorder("Level " + entry.getKey()));

      for (WordSpell spell: entry.getValue()) {
        JCheckBox check = new JCheckBox(spell.getName());
        check.setToolTipText(spell.getDescription());
        check.setBackground(renderer.getSpellColor(spell));
        checksPanel.add(check);
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
      }
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

