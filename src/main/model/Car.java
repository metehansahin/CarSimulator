package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a car having a model, weight, engine force, drag area
// velocity, acceleration, drag force, distance travelled and time passed
public class Car implements Writable {
    public static final double AIR_DENSITY = 1.225;

    private String model;                   // model of the car
    private double weight;                  // weight of the car (kg)
    private double engineForce;             // engine force of the car (N)
    private double dragArea;                // drag area of the car (m^2)

    private double velocity;                // velocity of the car (m/s)
    private double acceleration;            // acceleration of the car (m/s^2)
    protected double dragForce;             // drag force of the car (N)
    private double distanceToReport;        // distance travelled at certain time (m)
    protected double distance0;             // initial distance (m)
    protected double distance1;             // secondary distance (m)
    private double time;                    // time passed (s)

    /*
     * REQUIRES: model has a non-zero length
     *           weight, engineForce and dragArea are non-zero double values
     * EFFECTS:  model is set to model field, similarly, weight is set
     *           to weight field, engineForce is set to engineForce field,
     *           and dragArea is set to dragArea field
     */
    public Car(String model, double weight, double engineForce, double dragArea) {
        this.model = model;
        this.weight = weight;
        this.engineForce = engineForce;
        this.dragArea = dragArea;
        EventLog.getInstance().logEvent(new Event("New car, " + this.getModel()
                + ", has been created."));
    }

    public String getModel() {
        return this.model;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getEngineForce() {
        return this.engineForce;
    }

    public double getDragArea() {
        return this.dragArea;
    }

    public double getVelocity() {
        return this.velocity;
    }

    public double getAcceleration() {
        return this.acceleration;
    }

    public double getDistance() {
        return this.distanceToReport;
    }

    public double getTime() {
        return this.time;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  model is set to model field, similarly, weight is set
     *           to weight field, engineForce is set to engineForce field,
     *           and dragArea is set to dragArea field
     *           if model has zero length:
     *              do not modify model
     *           if weight <= 0:
     *              do not modify weight
     *           if engineForce <= 0:
     *              do not modify engineForce
     *           if dragArea <= 0:
     *              do not modify dragArea
     */
    public void addStats(String model, double weight, double engineForce, double dragArea) {
        // used for changing stats of a car after instantiation
        if (model != "") {
            this.model = model;
        }
        if (weight > 0) {
            this.weight = weight;
        }
        if (engineForce > 0) {
            this.engineForce = engineForce;
        }
        if (dragArea > 0) {
            this.dragArea = dragArea;
        }
        EventLog.getInstance().logEvent(new Event("Statistics changed for: "
                + this.getModel() + "."));
    }

    /*
     * REQUIRES: timeInterval > 0
     * MODIFIES: this
     * EFFECTS:  drag force, acceleration, velocity, and distance travelled is calculated
     *           and respective fields are updated
     */
    public void calculateData(double timeInterval) {
        this.dragForce = (AIR_DENSITY * this.dragArea * this.velocity * this.velocity) / 2;
        this.acceleration = calculateAcceleration(engineForce - dragForce, weight);
        this.velocity = calculateVelocity(this.velocity, this.acceleration, timeInterval);
        this.distance1 = calculatePosition(distance0, this.velocity, timeInterval);
        this.time += timeInterval;
        this.distance0 = this.distance1;
        this.distanceToReport = this.distance1;
    }

    /*
     * REQUIRES: force >= 0
     * EFFECTS:  return the acceleration of the car
     */
    public double calculateAcceleration(double force, double weight) {
        return (force / weight);
    }

    /*
     * REQUIRES: initialVelocity >= 0, acceleration >= 0
     * EFFECTS:  return the velocity of the car
     */
    public double calculateVelocity(double initialVelocity, double acceleration, double timeInterval) {
        return (acceleration * timeInterval + initialVelocity);
    }

    /*
     * REQUIRES: initialPosition >= 0, velocity >= 0
     * EFFECTS:  return the position of the car
     */
    public double calculatePosition(double initialPosition, double velocity, double timeInterval) {
        return (velocity * timeInterval + initialPosition);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  resets the data for drag force, acceleration, velocity, and distance travelled
     *           corresponding fields are assigned to the value 0
     */
    public void resetData() {
        this.dragForce = 0;
        this.acceleration = 0;
        this.velocity = 0;
        this.distance1 = 0;
        this.time = 0;
        this.distance0 = 0;
        this.distanceToReport = 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("model", model);
        json.put("weight", weight);
        json.put("engine force", engineForce);
        json.put("drag area", dragArea);
        return json;
    }
}