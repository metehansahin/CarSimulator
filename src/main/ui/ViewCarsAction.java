package ui;

import model.Car;
import model.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// Represents the action to be taken when the user wants to view cars
class ViewCarsAction extends AbstractAction {
    private final CarSimGUI carSimGUI;
    private final JTextField styleField;
    private final JButton btn;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    ViewCarsAction(CarSimGUI carSimGUI) {
        super("View Cars");
        this.carSimGUI = carSimGUI;

        styleField = new JTextField();
        styleField.setVisible(true);

        btn = new JButton("Enter");
        btn.setActionCommand("EnterButton");
        btn.addActionListener(this);
        btn.setVisible(true);
    }

    // MODIFIES: carSimGUI.displayPanel, carSimGUI.controlDisplayPanel
    // EFFECTS: performs the action necessary to view cars
    @Override
    public void actionPerformed(ActionEvent evt) {
        ArrayList<Style> styleListAction = carSimGUI.styleList.getStyles();

        setUpDisplayPanel();

        if (evt.getActionCommand().equals("EnterButton")) {
            if (!carSimGUI.styleIsInStyleList(styleListAction, styleField)) {
                carSimGUI.displayPanel.add(new JLabel("Please enter a valid style or add one..."));
            } else {
                displayCars(styleListAction);
            }
        }
        carSimGUI.controlDisplayPanel.add(carSimGUI.displayPanel, BorderLayout.CENTER);
        carSimGUI.controlDisplayPanel.revalidate();
    }

    // MODIFIES: carSimGUI.displayPanel
    // EFFECTS: displays all the cars corresponding to the given style
    private void displayCars(ArrayList<Style> styleListAction) {
        for (Style style : styleListAction) {
            if (styleField.getText().toLowerCase().equals(style.getStyleName())) {
                ArrayList<Car> carList = style.getCars();
                if (!carList.isEmpty() && carSimGUI.styleIsInStyleList(styleListAction, styleField)) {
                    carSimGUI.displayPanel.add(new JLabel("A list of all cars corresponding to the style:"));
                    for (Car car : carList) {
                        carSimGUI.displayPanel.add(new JLabel(car.getModel()));
                    }
                } else if (carList.isEmpty()) {
                    carSimGUI.displayPanel.add(new JLabel("There are no cars corresponding to this style..."));
                }
                break;
            }
        }
    }

    // MODIFIES: carSimGUI.displayPanel
    // EFFECTS: sets up the display panel for view cars action
    private void setUpDisplayPanel() {
        carSimGUI.displayPanel.removeAll();
        carSimGUI.displayPanel.revalidate();
        carSimGUI.displayPanel.repaint();

        carSimGUI.displayPanel.setLayout(new GridLayout(20, 1));
        carSimGUI.displayPanel.add(new JLabel("Please select a style by typing the name:"));
        carSimGUI.displayPanel.add(styleField);
        carSimGUI.displayPanel.add(btn);
    }
}
