package ui;

import model.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents the action to be taken when the user wants to modify statistics of a car
class ModifyStatsAction extends AbstractAction {
    private final CarSimGUI carSimGUI;
    private final JTextField modelField;
    private final JTextField styleField;
    private final JTextField carNameField;
    private final JTextField weightField;
    private final JTextField engineForceField;
    private final JTextField dragAreaField;
    private final JButton btn;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    ModifyStatsAction(CarSimGUI carSimGUI) {
        super("Modify Statistics For A Car");
        this.carSimGUI = carSimGUI;

        btn = new JButton("Enter");
        btn.setActionCommand("EnterButton");
        btn.addActionListener(this);
        btn.setVisible(true);

        styleField = new JTextField();
        styleField.setVisible(true);
        carNameField = new JTextField();
        carNameField.setVisible(true);
        modelField = new JTextField();
        modelField.setVisible(true);
        weightField = new JTextField();
        weightField.setVisible(true);
        engineForceField = new JTextField();
        engineForceField.setVisible(true);
        dragAreaField = new JTextField();
        dragAreaField.setVisible(true);
    }

    // MODIFIES: carSimGUI.displayPanel, carSimGUI.controlDisplayPanel
    // EFFECTS: performs the action necessary to modify statistics of a car
    @Override
    public void actionPerformed(ActionEvent evt) {
        setUpDisplayPanel();

        if (evt.getActionCommand().equals("EnterButton")) {
            Car carToModify = carSimGUI.findCar(styleField.getText().toLowerCase(),
                    carNameField.getText().toLowerCase());
            if (carToModify == null) {
                carSimGUI.displayPanel.add(new JLabel("Car to modify could not be found..."));
            } else {
                carToModify.addStats(modelField.getText().toLowerCase(),
                        Double.parseDouble(weightField.getText()),
                        Double.parseDouble(engineForceField.getText()),
                        Double.parseDouble(dragAreaField.getText()));

                carSimGUI.displayPanel.add(new JLabel("Car statistics updated!"));
            }
        }
        carSimGUI.controlDisplayPanel.add(carSimGUI.displayPanel, BorderLayout.CENTER);
        carSimGUI.controlDisplayPanel.revalidate();
    }

    // MODIFIES: carSimGUI.displayPanel
    // EFFECTS: sets up the display panel for modify statistics action
    private void setUpDisplayPanel() {
        carSimGUI.displayPanel.removeAll();
        carSimGUI.displayPanel.revalidate();
        carSimGUI.displayPanel.repaint();

        carSimGUI.displayPanel.setLayout(new GridLayout(20, 1));
        carSimGUI.displayPanel.add(new JLabel("Please select a style by typing the name:"));
        carSimGUI.displayPanel.add(styleField);
        carSimGUI.displayPanel.add(new JLabel("Please select a car by typing the name:"));
        carSimGUI.displayPanel.add(carNameField);
        carSimGUI.displayPanel.add(new JLabel("Please enter the new model of the car:"));
        carSimGUI.displayPanel.add(modelField);
        carSimGUI.displayPanel.add(new JLabel("Please enter the new weight (kg) of the car:"));
        carSimGUI.displayPanel.add(weightField);
        carSimGUI.displayPanel.add(new JLabel("Please enter the new engine force (N) of the car:"));
        carSimGUI.displayPanel.add(engineForceField);
        carSimGUI.displayPanel.add(new JLabel("Please enter the new drag area (m^2) of the car:"));
        carSimGUI.displayPanel.add(dragAreaField);
        carSimGUI.displayPanel.add(btn);
    }
}
