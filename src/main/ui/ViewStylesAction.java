package ui;

import model.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// Represents the action to be taken when the user wants to view styles
class ViewStylesAction extends AbstractAction {
    private final CarSimGUI carSimGUI;
    private final JLabel viewStylesLabel;
    private final JButton btn;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    ViewStylesAction(CarSimGUI carSimGUI) {
        super("View Styles");
        this.carSimGUI = carSimGUI;

        btn = new JButton("Press This To View Styles");
        btn.setActionCommand("EnterButton");
        btn.addActionListener(this);
        btn.setVisible(true);

        viewStylesLabel = new JLabel();
        viewStylesLabel.setVisible(true);
    }

    // MODIFIES: carSimGUI.displayPanel, carSimGUI.controlDisplayPanel
    // EFFECTS: performs the action necessary to view styles
    public void actionPerformed(ActionEvent evt) {
        ArrayList<Style> styleListAction = carSimGUI.styleList.getStyles();

        carSimGUI.displayPanel.removeAll();
        carSimGUI.displayPanel.revalidate();
        carSimGUI.displayPanel.repaint();

        carSimGUI.displayPanel.add(btn);
        carSimGUI.displayPanel.add(viewStylesLabel);
        viewStylesLabel.setText("");

        if (evt.getActionCommand().equals("EnterButton")) {
            if (styleListAction.isEmpty()) {
                viewStylesLabel.setText("Please create a new style first.");
            } else {
                viewStylesLabel.setText("A list of all the styles:");
                DefaultListModel listModel = new DefaultListModel();
                for (Style style : styleListAction) {
                    carSimGUI.displayPanel.add(new JLabel(style.getStyleName()));
                }
            }
        }
        carSimGUI.controlDisplayPanel.add(carSimGUI.displayPanel, BorderLayout.CENTER);
        carSimGUI.controlDisplayPanel.revalidate();
    }
}
