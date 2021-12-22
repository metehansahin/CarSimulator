package ui;

import java.io.FileNotFoundException;

// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class Main {
    public static void main(String[] args) {
        try {
            new CarSim();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
