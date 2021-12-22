package ui;

import model.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the action to be taken when the user wants to use the simulation
class SimulateAction extends AbstractAction {

    private final CarSimGUI carSimGUI;
    private final JTextField styleField;
    private final JTextField carNameField;
    private final JButton btn;
    private final JButton btn2;
    private final JLabel simDataLabel;
    private Timer timer;
    private Car carToSimulate;

    // MODIFIES: this
    // EFFECTS: constructs labels and fields necessary for displayPanel
    SimulateAction(CarSimGUI carSimGUI) {
        super("SIMULATE A CAR");
        this.carSimGUI = carSimGUI;

        btn = new JButton("Enter");
        btn.setActionCommand("EnterButton");
        btn.addActionListener(this);
        btn.setVisible(true);

        btn2 = new JButton("Press This To Stop Simulation");
        btn2.setActionCommand("StopSimulationButton");
        btn2.addActionListener(this);
        btn2.setVisible(true);

        styleField = new JTextField();
        styleField.setVisible(true);
        carNameField = new JTextField();
        carNameField.setVisible(true);

        simDataLabel = new JLabel();
        simDataLabel.setVisible(true);
    }

    // MODIFIES: carSimGUI.simulationDisplayPanel
    // EFFECTS: performs the action necessary to simulate a car
    @Override
    public void actionPerformed(ActionEvent evt) {
        setUpDisplayPanel();

        if (evt.getActionCommand().equals("EnterButton")) {
            carToSimulate = carSimGUI.findCar(styleField.getText().toLowerCase(),
                    carNameField.getText().toLowerCase());
            preCheckCarStatus();

            if (carToSimulate == null) {
                carSimGUI.simulationDisplayPanel.add(new JLabel("Car to simulate could not be found..."));
            } else {
                simulateCar();
            }
        }
        checkToStopSimulation(evt);
    }

    // MODIFIES: this
    // EFFECTS: Repeatedly prints out statistical values
    private void simulateCar() {
        int delay = 1000;
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carToSimulate.calculateData(1);
                String velString = String.format(" %4.4f (m/s) | ", carToSimulate.getVelocity());
                String accString = String.format("%4.4f (m/s^2) | ", carToSimulate.getAcceleration());
                String disString = String.format("%4.4f (m) | ", carToSimulate.getDistance());
                String timeString = String.format("%4.4f (s) ", carToSimulate.getTime());
                simDataLabel.setText(velString + accString + disString + timeString);
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.start();
    }

    // MODIFIES: carSimGUI.simulationDisplayPanel
    // EFFECTS: Adds labels necessary to display car statistics
    private void preCheckCarStatus() {
        carSimGUI.simulationDisplayPanel.add(new JLabel("Velocity (m/s) | Acceleration (m/s^2) "
                + "| Distance (m) "
                + "| Time (s)"));
        carSimGUI.simulationDisplayPanel.add(simDataLabel);
        carSimGUI.simulationDisplayPanel.add(btn2);
    }

    // MODIFIES: carSimGUI.simulationDisplayPanel
    // EFFECTS: sets up the display panel for simulate action
    private void setUpDisplayPanel() {
        carSimGUI.simulationDisplayPanel.removeAll();
        carSimGUI.simulationDisplayPanel.revalidate();
        carSimGUI.simulationDisplayPanel.repaint();

        carSimGUI.simulationDisplayPanel.setLayout(new GridLayout(9, 1));
        carSimGUI.simulationDisplayPanel.add(new JLabel("Please select a style by typing the name:"));
        carSimGUI.simulationDisplayPanel.add(styleField);
        carSimGUI.simulationDisplayPanel.add(new JLabel("Please select a car by typing the name:"));
        carSimGUI.simulationDisplayPanel.add(carNameField);
        carSimGUI.simulationDisplayPanel.add(btn);
    }

    // MODIFIES: this, carSimGUI.simulationPanel, carSimGUI.simulationDisplayPanel
    // EFFECTS:
    public void checkToStopSimulation(ActionEvent evt) {
        if (evt.getActionCommand().equals("StopSimulationButton")) {
            carToSimulate.resetData();
            timer.stop();
            timer.setRepeats(false);
        }
        carSimGUI.simulationPanel.add(carSimGUI.simulationDisplayPanel, BorderLayout.CENTER);
        carSimGUI.simulationPanel.revalidate();
    }
}
