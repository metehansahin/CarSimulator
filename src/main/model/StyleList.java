package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a style list
public class StyleList implements Writable {
    private ArrayList<Style> styleList;  // list of all the styles

    /*
     * EFFECTS: creates a new ArrayList that contains data of type Style
     */
    public StyleList() {
        styleList = new ArrayList<Style>();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  if style is not in the style list:
     *              adds style to the styleList
     */
    public void addStyle(Style style) {
        if (!styleList.contains(style)) {
            this.styleList.add(style);
            EventLog.getInstance().logEvent(new Event(style.getStyleName()
                    + " has been added to the style list."));
        }
    }

    /*
     * EFFECTS: return the list of all the styles
     */
    public ArrayList getStyles() {
        return this.styleList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("styles", stylesToJson());
        return json;
    }

    // EFFECTS: returns styles in this style list as a JSON array
    private JSONArray stylesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Style s : styleList) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
