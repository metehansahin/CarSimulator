package ui;

import model.Car;
import model.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// Represents the action to be taken when the user wants to add a car
class AddCarAction extends AbstractAction {
    private final CarSimGUI carSimGUI;
    private final JTextField modelField;
    private final JTextField styleField;
    private final JTextField weightField;
    private final JTextField engineForceField;
    private final JTextField dragAreaField;
    private final JButton btn;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    AddCarAction(CarSimGUI carSimGUI) {
        super("Add A Car");
        this.carSimGUI = carSimGUI;

        btn = new JButton("Enter");
        btn.setActionCommand("EnterButton");
        btn.addActionListener(this);
        btn.setVisible(true);

        styleField = new JTextField();
        styleField.setVisible(true);
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
    // EFFECTS: performs the action necessary to add a car
    @Override
    public void actionPerformed(ActionEvent evt) {
        ArrayList<Style> styleListAction = carSimGUI.styleList.getStyles();
        setUpDisplayPanel();

        if (evt.getActionCommand().equals("EnterButton")) {
            if (!carSimGUI.styleIsInStyleList(styleListAction, styleField)) {
                carSimGUI.displayPanel.add(new JLabel("Please enter a valid style or add one..."));
            } else {
                Car newCar = carSimGUI.createCar(modelField.getText().toLowerCase(),
                        Double.parseDouble(weightField.getText()),
                        Double.parseDouble(engineForceField.getText()),
                        Double.parseDouble(dragAreaField.getText()));
                for (Style style : styleListAction) {
                    if (styleField.getText().toLowerCase().equals(style.getStyleName())) {
                        style.addCar(newCar);
                        carSimGUI.displayPanel.add(new JLabel("Car successfully added to the corresponding style!"));
                        break;
                    }
                }
            }
        }
        carSimGUI.controlDisplayPanel.add(carSimGUI.displayPanel, BorderLayout.CENTER);
        carSimGUI.controlDisplayPanel.revalidate();
    }

    // MODIFIES: carSimGUI.displayPanel
    // EFFECTS: sets up the display panel for add car action
    private void setUpDisplayPanel() {
        carSimGUI.displayPanel.removeAll();
        carSimGUI.displayPanel.revalidate();
        carSimGUI.displayPanel.repaint();

        carSimGUI.displayPanel.setLayout(new GridLayout(20, 1));
        carSimGUI.displayPanel.add(new JLabel("Please select a style by typing the name:"));
        carSimGUI.displayPanel.add(styleField);
        carSimGUI.displayPanel.add(new JLabel("Please enter the model of the car:"));
        carSimGUI.displayPanel.add(modelField);
        carSimGUI.displayPanel.add(new JLabel("Please enter the weight (kg) of the car:"));
        carSimGUI.displayPanel.add(weightField);
        carSimGUI.displayPanel.add(new JLabel("Please enter the engine force (N) of the car:"));
        carSimGUI.displayPanel.add(engineForceField);
        carSimGUI.displayPanel.add(new JLabel("Please enter the drag area (m^2) of the car:"));
        carSimGUI.displayPanel.add(dragAreaField);
        carSimGUI.displayPanel.add(btn);
    }
}
