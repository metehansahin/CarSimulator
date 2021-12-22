package ui;

import model.Car;
import model.Style;
import model.StyleList;

import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Car Simulator Application
// This class references code from these repos:
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class CarSim {
    private static final String JSON_STORE = "./data/styleList.json";
    private Scanner input;
    private StyleList styleList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Car Simulator application
    public CarSim() throws FileNotFoundException {
        input = new Scanner(System.in);
        runCarSim();
    }

    // MODIFIES: this
    // EFFECTS:  processes user input
    private void runCarSim() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            System.out.println("\n\nWelcome to the main menu of the Car Simulator");
            displayMenu();
            command = input.next().toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS:  processes user command
    private void processCommand(String command) {
        if (command.equals("b")) {
            createNewStyle();
        } else if (command.equals("v")) {
            viewStyles();
        } else if (command.equals("c")) {
            addNewCar();
        } else if (command.equals("l")) {
            viewCars();
        } else if (command.equals("s")) {
            modifyCarStats();
        } else if (command.equals("m")) {
            showCarStats();
        } else if (command.equals("x")) {
            simulateCar();
        } else if (command.equals("a")) {
            saveStyleList();
        } else if (command.equals("z")) {
            loadStyleList();
        } else {
            System.out.println("Selection not valid, please try again...");
        }
    }

    // MODIFIES: this
    // EFFECTS:  initializes input and style list
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        styleList = new StyleList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> create a new car style");
        System.out.println("\tv -> view a list of all the car styles");
        System.out.println("\tc -> select a car style and add a new car");
        System.out.println("\tl -> select a car style and view a list of all the cars in that style");
        System.out.println("\ts -> modify statistics for a car");
        System.out.println("\tm -> show statistics of a car");
        System.out.println("\tx -> go to the simulation");
        System.out.println("\ta -> save styles and cars to file");
        System.out.println("\tz -> load styles and cars from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS:  creates a new style
    private void createNewStyle() {
        System.out.println("\nPlease enter the name of the style (SUV, Sedan, Coupe, etc.): ");
        String styleName = input.next().toLowerCase();
        Style newStyle = new Style(styleName);
        this.styleList.addStyle(newStyle);
        System.out.println("The new style has been added to the list of styles!");
    }

    // EFFECTS: if the style list is empty:
    //              prints a message to user indicating style list is empty
    //          else:
    //              prints all the styles currently available
    private void viewStyles() {
        ArrayList<Style> styleList = this.styleList.getStyles();

        if (styleList.isEmpty()) {
            System.out.println("\nPlease create a new style first.");
        } else {
            System.out.println("\nA list of all the styles:");
            for (Style style : styleList) {
                System.out.println("\t" + style.getStyleName());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds a new car to the specified style
    private void addNewCar() {
        ArrayList<Style> styleList = this.styleList.getStyles();

        String styleName = selectStyle();

        System.out.println("\nPlease enter the model of the car:");
        String carModel = input.next().toLowerCase();
        System.out.println("\nPlease enter the weight (kg) of the car:");
        Double carWeight = input.nextDouble();
        System.out.println("\nPlease enter the engine force (N) of the car:");
        Double carEngineForce = input.nextDouble();
        System.out.println("\nPlease enter the drag area (m^2) of the car:");
        Double carDragArea = input.nextDouble();

        Car newCar = createCar(carModel, carWeight, carEngineForce, carDragArea);

        for (Style style : styleList) {
            if (styleName.equals(style.getStyleName())) {
                style.addCar(newCar);
                System.out.println("\nCar successfully added to the corresponding style!\n");
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:  if car list corresponding to the chosen style is empty:
    //              prints a message to user indicating the style is empty
    //           else:
    //              prints all the cars in that style
    private void viewCars() {
        ArrayList<Style> styleList = this.styleList.getStyles();

        String styleName = selectStyle();

        for (Style style : styleList) {
            if (styleName.equals(style.getStyleName())) {
                ArrayList<Car> carList  = style.getCars();
                if (carList.isEmpty()) {
                    System.out.println("\nThere are no cars corresponding to this style...");
                } else {
                    System.out.println("\nA list of all cars corresponding to the style:");
                    for (Car car : carList) {
                        System.out.println("\t" + car.getModel());
                    }
                }
                break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:  changes statistics of the chosen car corresponding to the given input
    private void modifyCarStats() {
        Car carToModify = findCar();

        System.out.println("\nPlease enter the new model of the car:");
        String carModel = input.next().toLowerCase();
        System.out.println("\nPlease enter the new weight (kg) of the car:");
        Double carWeight = input.nextDouble();
        System.out.println("\nPlease enter the new engine force (N) of the car:");
        Double carEngineForce = input.nextDouble();
        System.out.println("\nPlease enter the new drag area (m^2) of the car:");
        Double carDragArea = input.nextDouble();

        carToModify.addStats(carModel, carWeight, carEngineForce, carDragArea);

        System.out.println("\nCar statistics updated!");
    }

    // EFFECTS: prints the statistics for the chosen car
    private void showCarStats() {
        Car carToShowStats = findCar();

        System.out.println("\nCar Model: " + carToShowStats.getModel());
        System.out.println("Car Weight: " + carToShowStats.getWeight());
        System.out.println("Car Engine Force: " + carToShowStats.getEngineForce());
        System.out.println("Car Drag Area: " + carToShowStats.getDragArea());
    }

    // MODIFIES: this
    // EFFECTS:  simulates the chosen car and prints out velocity, acceleration, distance travelled,
    //           and time passed regarding that the time interval between calculations is 1 second
    private void simulateCar() {
        boolean keepSimulating = true;
        String simulationCommand;

        Car carToSimulate = findCar();

        System.out.println("\nHold enter to simulate, enter e to stop the simulation!");

        System.out.println("\nCar Info:");
        System.out.println("\tCar Model: " + carToSimulate.getModel());
        System.out.println("\tCar Weight: " + carToSimulate.getWeight());
        System.out.println("\tCar Engine Force: " + carToSimulate.getEngineForce());
        System.out.println("\tCar Drag Area: " + carToSimulate.getDragArea());

        System.out.println("\nVelocity (m/s) | Acceleration (m/s^2) | Distance (m) | Time (s)");

        while (keepSimulating) {
            simulationCommand = input.next().toLowerCase();

            if (simulationCommand.equals("e")) {
                keepSimulating = false;
            }

            carToSimulate.calculateData(1);
            System.out.printf("\n%4.4f (m/s) | %4.4f (m/s^2) | %4.4f (m) | %4.4f (s)",
                    carToSimulate.getVelocity(),
                    carToSimulate.getAcceleration(),
                    carToSimulate.getDistance(),
                    carToSimulate.getTime());
        }
    }

    // EFFECTS: saves the style list to file
    private void saveStyleList() {
        System.out.println("\nYou will lose your previously saved cars and styles (if any)"
                + " if you save the new ones.");
        System.out.println("We suggest you load them first and enter the"
                + " new cars and styles again.");
        System.out.println("\nDo you want to continue?");
        System.out.println("Press y to continue, n to stop");

        String inputForSaving = input.next().toLowerCase();

        if (inputForSaving.equals("y")) {
            try {
                jsonWriter.open();
                jsonWriter.write(this.styleList);
                jsonWriter.close();
                System.out.println("\nSaved styles and cars to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads style list from file
    private void loadStyleList() {
        System.out.println("\nYou will lose your current cars and styles (if any) "
                + "if you load the old ones.");
        System.out.println("\nDo you want to continue?");
        System.out.println("Press y to continue, n to stop");

        String inputForLoading = input.next().toLowerCase();

        if (inputForLoading.equals("y")) {
            try {
                this.styleList = jsonReader.read();
                System.out.println("\nLoaded styles and cars from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("\nUnable to read from file: " + JSON_STORE);
            }
        }
    }

    // REQUIRES: carWeight > 0.0, carEngineForce > 0.0, carDragArea > 0.0, carModel has at least one char
    // MODIFIES: this
    // EFFECTS:  creates a new car
    private Car createCar(String carModel, double carWeight, double carEngineForce, double carDragArea) {
        Car newCar = new Car(carModel, carWeight, carEngineForce, carDragArea);
        return newCar;
    }


    // REQUIRES: style to select already exists in style list
    // MODIFIES: this
    // EFFECTS:  returns the name of the style
    private String selectStyle() {
        System.out.println("\nPlease select a style by typing the name:");
        String styleName = input.next().toLowerCase();
        return styleName;
    }

    // REQUIRES: car to find already exists in style
    // MODIFIES: this
    // EFFECTS:  finds a car from a specific style and returns it
    public Car findCar() {
        ArrayList<Style> styleList = this.styleList.getStyles();
        Car carToFind = null;

        String styleName = selectStyle();

        outer: for (Style style : styleList) {
            if (styleName.equals(style.getStyleName())) {
                ArrayList<Car> carList = style.getCars();

                System.out.println("\nPlease select a car by typing the name:");
                String carName = input.next().toLowerCase();

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
}