package persistence;

import model.Car;
import model.Style;
import model.StyleList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads style list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads style list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public StyleList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStyleList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses style list from JSON object and returns it
    private StyleList parseStyleList(JSONObject jsonObject) {
        StyleList sl = new StyleList();
        addStyles(sl, jsonObject);
        return sl;
    }

    // MODIFIES: sl
    // EFFECTS: parses styles from JSON object and adds them to style list
    private void addStyles(StyleList sl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("styles");
        for (Object json : jsonArray) {
            JSONObject nextStyle = (JSONObject) json;
            addStyle(sl, nextStyle);
        }
    }

    // MODIFIES: sl
    // EFFECTS: parses style from JSON object and adds it to style list
    private void addStyle(StyleList sl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cars");
        String name = jsonObject.getString("name");

        Style style = new Style(name);
        sl.addStyle(style);

        for (Object json : jsonArray) {
            JSONObject nextCar = (JSONObject) json;
            addCar(style, nextCar);
        }
    }

    // MODIFIES: style
    // EFFECTS: parses car from JSON object and adds it to style
    private void addCar(Style style, JSONObject jsonObject) {
        String model = jsonObject.getString("model");
        Double weight = jsonObject.getDouble("weight");
        Double engineForce = jsonObject.getDouble("engine force");
        Double dragArea = jsonObject.getDouble("drag area");

        Car car = new Car(model, weight, engineForce, dragArea);
        style.addCar(car);
    }
}
