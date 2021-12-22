package persistence;

import model.Car;
import model.Style;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkStyle(String name, ArrayList<Car> carList, Style style) {
        assertEquals(name, style.getStyleName());
        assertEquals(carList, style.getCars());
    }

    protected void checkCar(String model, double weight, double engineForce, double dragArea, Car car) {
        assertEquals(model, car.getModel());
        assertEquals(weight, car.getWeight());
        assertEquals(engineForce, car.getEngineForce());
        assertEquals(dragArea, car.getDragArea());
    }
}
