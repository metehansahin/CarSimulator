package ui;

import model.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// Represents the action to be taken when the user wants to create a new style
class CreateStyleAction extends AbstractAction {
    private final CarSimGUI carSimGUI;
    private final JTextField field;
    private final JButton btn;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    public CreateStyleAction(CarSimGUI carSimGUI) {
        super("Create A Style");
        this.carSimGUI = carSimGUI;
        field = new JTextField(15);
        field.setVisible(true);
        btn = new JButton("Enter");
        btn.setActionCommand("EnterButton");
        btn.addActionListener(this);
    }

    // MODIFIES: this, carSimGUI.displayPanel, carSimGUI.controlDisplayPanel
    // EFFECTS: performs the action necessary to create a style
    @Override
    public void actionPerformed(ActionEvent evt) {
        setUpDisplayPanel();

        if (evt.getActionCommand().equals("EnterButton")) {
            int count = 0;
            ArrayList<Style> styles = carSimGUI.styleList.getStyles();
            String styleName = field.getText().toLowerCase();
            Style newStyle = new Style(styleName);

            for (Style style : styles) {
                if (style.getStyleName().equals(styleName)) {
                    count++;
                }
            }

            if (count > 0) {
                carSimGUI.displayPanel.add(new JLabel("The style is already added!"));
            } else {
                carSimGUI.styleList.addStyle(newStyle);
                carSimGUI.displayPanel.add(new JLabel("The new style has been added to the list of styles!"));
            }
        }
        carSimGUI.controlDisplayPanel.add(carSimGUI.displayPanel, BorderLayout.CENTER);
        carSimGUI.controlDisplayPanel.revalidate();
    }

    // MODIFIES: carSimGUI.displayPanel
    // EFFECTS: sets up the display panel for create a style action
    private void setUpDisplayPanel() {
        carSimGUI.displayPanel.removeAll();
        carSimGUI.displayPanel.revalidate();
        carSimGUI.displayPanel.repaint();

        carSimGUI.displayPanel.setLayout(new GridLayout(20, 1));
        carSimGUI.displayPanel.add(new JLabel("Please enter the name of the style:"));
        carSimGUI.displayPanel.add(field);
        carSimGUI.displayPanel.add(btn);
    }
}
