package ui;

import model.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents the action to be taken when the user wants to see statistics of a car
class ShowStatsAction extends AbstractAction {

    private final CarSimGUI carSimGUI;
    private final JTextField styleField;
    private final JTextField carNameField;
    private final JButton btn;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    ShowStatsAction(CarSimGUI carSimGUI) {
        super("Show Statistics Of A Car");
        this.carSimGUI = carSimGUI;
        btn = new JButton("Enter");
        btn.setActionCommand("EnterButton");
        btn.addActionListener(this);
        btn.setVisible(true);

        styleField = new JTextField();
        styleField.setVisible(true);
        carNameField = new JTextField();
        carNameField.setVisible(true);
    }

    // MODIFIES: carSimGUI.displayPanel, carSimGUI.controlDisplayPanel
    // EFFECTS: performs the action necessary to see statistics of a car
    @Override
    public void actionPerformed(ActionEvent evt) {
        carSimGUI.displayPanel.removeAll();
        carSimGUI.displayPanel.revalidate();
        carSimGUI.displayPanel.repaint();

        carSimGUI.displayPanel.setLayout(new GridLayout(20, 1));
        carSimGUI.displayPanel.add(new JLabel("Please select a style by typing the name:"));
        carSimGUI.displayPanel.add(styleField);
        carSimGUI.displayPanel.add(new JLabel("Please select a car by typing the name:"));
        carSimGUI.displayPanel.add(carNameField);
        carSimGUI.displayPanel.add(btn);

        if (evt.getActionCommand().equals("EnterButton")) {
            Car carToShowStats = carSimGUI.findCar(styleField.getText().toLowerCase(),
                    carNameField.getText().toLowerCase());

            if (carToShowStats == null) {
                carSimGUI.displayPanel.add(new JLabel("Car to show statistics could not be found..."));
            } else {
                carSimGUI.displayPanel.add(new JLabel("Car Model: " + carToShowStats.getModel()));
                carSimGUI.displayPanel.add(new JLabel("Car Weight: " + carToShowStats.getWeight()));
                carSimGUI.displayPanel.add(new JLabel("Car Engine Force: " + carToShowStats.getEngineForce()));
                carSimGUI.displayPanel.add(new JLabel("Car Drag Area: " + carToShowStats.getDragArea()));
            }
        }

        carSimGUI.controlDisplayPanel.add(carSimGUI.displayPanel, BorderLayout.CENTER);
        carSimGUI.controlDisplayPanel.revalidate();
    }
}
