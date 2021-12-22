package persistence;

import model.Car;
import model.StyleList;
import model.Style;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            StyleList sl = new StyleList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStyleList() {
        try {
            StyleList sl = new StyleList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStyleList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStyleList.json");
            sl = reader.read();
            assertEquals(0, sl.getStyles().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStyleList() {
        try {
            Style s1 = new Style("suv");
            s1.addCar(new Car ("urus", 3000, 2500, 0.41));
            s1.addCar(new Car ("cherokee", 2500, 1500, 0.55));
            Style s2 = new Style("sedan");
            s2.addCar(new Car ("tesla", 2000, 1500, 0.21));
            s2.addCar(new Car ("audi", 1800, 1700, 0.30));
            StyleList sl = new StyleList();
            sl.addStyle(s1);
            sl.addStyle(s2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStyleList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStyleList.json");
            sl = reader.read();
            List<Style> styles = sl.getStyles();
            List<Car> style1 = styles.get(0).getCars();
            List<Car> style2 = styles.get(1).getCars();
            assertEquals(2, styles.size());
            checkStyle("suv", (ArrayList<Car>) style1, styles.get(0));
            checkStyle("sedan", (ArrayList<Car>) style2, styles.get(1));
            assertEquals(2, style1.size());
            checkCar("urus", 3000, 2500, 0.41, style1.get(0));
            checkCar("cherokee", 2500, 1500, 0.55, style1.get(1));
            assertEquals(2, style2.size());
            checkCar("tesla", 2000, 1500, 0.21, style2.get(0));
            checkCar("audi", 1800, 1700, 0.30, style2.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
