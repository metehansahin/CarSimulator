package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a style having a list of cars corresponding to it
public class Style implements Writable {
    private ArrayList<Car> carList;  // list of all the cars
    private String styleName;        // name of the style

    /*
     * EFFECTS: creates a new ArrayList that contains data of type Car
     *          assigns name of the style to the corresponding field
     */
    public Style(String styleName) {
        carList = new ArrayList<Car>();
        this.styleName = styleName;
        EventLog.getInstance().logEvent(new Event("New style, " + this.styleName
                + ", has been created."));
    }

    /*
     * MODIFIES: this
     * EFFECTS:  if car is not in the style:
     *              adds car to the carList for the specific style
     */
    public void addCar(Car car) {
        if (!carList.contains(car)) {
            this.carList.add(car);
            EventLog.getInstance().logEvent(new Event(car.getModel()
                    + " has been added to " + this.styleName + "."));
        }
    }

    /*
     * EFFECTS: return the list of all the cars within the car style
     */
    public ArrayList getCars() {
        return this.carList;
    }

    /*
     * EFFECTS: return the name of the car style
     */
    public String getStyleName() {
        return this.styleName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", styleName);
        json.put("cars", carsToJson());
        return json;
    }

    // EFFECTS: returns cars in this style as a JSON array
    private JSONArray carsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Car c : carList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
