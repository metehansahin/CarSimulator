package persistence;

import org.json.JSONObject;

// This interface references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
