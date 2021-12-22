package ui;

import model.Car;
import model.Event;
import model.EventLog;
import model.Style;
import model.StyleList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// Represents application's main window frame
// This class references code from these repos:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class CarSimGUI extends JFrame {
    private final JDesktopPane desktop;
    protected JInternalFrame controlDisplayPanel;
    private JInternalFrame controlPanel;
    private JInternalFrame actionPanel;
    protected JInternalFrame saveAndLoadImagePanel;
    protected JInternalFrame simulationPanel;
    protected final JPanel displayPanel;
    protected final JPanel simulationDisplayPanel;
    protected final JPanel saveAndLoadImageDisplayPanel;
    protected JLabel saveLoadLabel;
    protected JLabel loadingImageLabel;
    protected JLabel savingImageLabel;
    private JLabel jlabel;
    private static final String JSON_STORE = "./data/styleList.json";
    protected StyleList styleList;
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: sets up button and display panels for simulation, images, and modifications
    public CarSimGUI() {
        styleList = new StyleList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());

        setUpPanels();

        setContentPane(desktop);
        setTitle("Car Simulator");
        setSize(800, 600);

        addButtonPanelForControlPanel();
        addButtonPanelForActionPanel();

        displayPanel = setUpJPanel(20,1);
        simulationDisplayPanel = setUpJPanel(10,1);
        saveAndLoadImageDisplayPanel = setUpJPanel(1,1);

        loadingImageLabel = setUpImageLabels("./data/loadingImage2.JPEG", 130, 120);
        savingImageLabel = setUpImageLabels("./data/savingImage.JPEG", 100, 100);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
        setResizable(false);
        eventLogger();

    }

    private void eventLogger() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                printLog(EventLog.getInstance());
                EventLog.getInstance().clear();
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up panels for control, action, control display, save and load and simulation
    private void setUpPanels() {
        controlPanel = setJInternalFrame("");
        actionPanel = setJInternalFrame("");
        controlDisplayPanel = setJInternalFrame("");
        saveAndLoadImagePanel = setJInternalFrame("");
        simulationPanel = setJInternalFrame("SIMULATION");

        controlPanel.pack();
        controlPanel.setVisible(true);
        controlPanel.setSize(445,132);
        desktop.add(controlPanel);

        actionPanel.pack();
        actionPanel.setVisible(true);
        actionPanel.setLocation(0, 132);
        actionPanel.setSize(178,162);
        desktop.add(actionPanel);

        setUpInternalFrames(controlDisplayPanel, 430, 0, 370, 560);
        setUpInternalFrames(simulationPanel, 0, 293, 445, 266);
        setUpInternalFrames(saveAndLoadImagePanel, 158, 132, 288, 162);
    }

    // MODIFIES: this
    // EFFECTS: sets up button panel for controls
    private void addButtonPanelForControlPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,2));
        buttonPanel.add(new JButton(new CreateStyleAction(this)));
        buttonPanel.add(new JButton(new ViewStylesAction(this)));
        buttonPanel.add(new JButton(new AddCarAction(this)));
        buttonPanel.add(new JButton(new ViewCarsAction(this)));
        buttonPanel.add(new JButton(new ModifyStatsAction(this)));
        buttonPanel.add(new JButton(new ShowStatsAction(this)));

        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: sets up button panel for load, save, and simulate
    private void addButtonPanelForActionPanel() {
        JPanel buttonPanel = new JPanel();
        saveLoadLabel = new JLabel("");
        buttonPanel.setLayout(new GridLayout(4,1));
        buttonPanel.add(new JButton(new LoadAction(this)));
        buttonPanel.add(new JButton(new SaveAction(this)));
        buttonPanel.add(saveLoadLabel);
        buttonPanel.add(new JButton(new SimulateAction(this)));
        actionPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: sets up a JPanel given number of rows and columns
    public JPanel setUpJPanel(int rows, int cols) {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(new GridLayout(rows,cols));
        return jpanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up a JInternalFrame
    private JInternalFrame setJInternalFrame(String name) {
        final JInternalFrame jInternalFrame;
        jInternalFrame = new JInternalFrame(name, false, false, false, false);
        jInternalFrame.setLayout(new BorderLayout());
        return jInternalFrame;
    }

    // MODIFIES: this
    // EFFECTS: sets up image labels for the main application and returns it
    private JLabel setUpImageLabels(String fileName, int width, int height) {
        try {
            jlabel = new JLabel(new ImageIcon(ImageIO.read(new File(fileName))
                    .getScaledInstance(width, height, Image.SCALE_FAST)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jlabel.setVisible(true);
        return jlabel;
    }

    // MODIFIES: this
    // EFFECTS: sets up internal frames for the main application
    private void setUpInternalFrames(JInternalFrame panel, int x, int y, int width, int height) {
        panel.pack();
        panel.setVisible(true);
        panel.setLocation(x, y);
        panel.setSize(width,height);
        desktop.add(panel);
    }

    // REQUIRES: carWeight > 0.0, carEngineForce > 0.0, carDragArea > 0.0, carModel has at least one char
    // MODIFIES: this
    // EFFECTS:  creates a new car
    public Car createCar(String carModel, double carWeight, double carEngineForce, double carDragArea) {
        Car newCar = new Car(carModel, carWeight, carEngineForce, carDragArea);
        return newCar;
    }

    // REQUIRES: car to find already exists in style
    // MODIFIES: this
    // EFFECTS:  finds a car from a specific style and returns it
    public Car findCar(String styleName, String carName) {
        ArrayList<Style> styleList = this.styleList.getStyles();
        Car carToFind = null;

        outer: for (Style style : styleList) {
            if (styleName.equals(style.getStyleName())) {
                ArrayList<Car> carList = style.getCars();
                for (Car car : carList) {
                    if (carName.equals(car.getModel())) {
                        carToFind = car;
                        break outer;
                    }
                }
            }
        }
        return carToFind;
    }

    // EFFECTS: check if style is in style list
    public boolean styleIsInStyleList(ArrayList<Style> styleListAction, JTextField styleField) {
        int count = 0;
        for (Style style : styleListAction) {
            if (style.getStyleName().equals(styleField.getText().toLowerCase())) {
                count++;
            }
        }
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    // EFFECTS: represents action to be taken when user clicks desktop
    //          to switch focus. (Needed for key handling.)
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            CarSimGUI.this.requestFocusInWindow();
        }
    }

    // EFFECTS: centres the main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // EFFECTS: prints out all the events that have been logged
    public void printLog(EventLog el) {
        for (Event event : el) {
            System.out.println(event.getDescription());
        }
    }

    // EFFECTS: starts the application
    public static void main(String[] args) {
        new CarSimGUI();
    }
}