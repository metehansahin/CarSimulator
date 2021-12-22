package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StyleTest {
    private Style testStyle;

    @BeforeEach
    void runBefore() {
        testStyle = new Style("Sport");
    }

    @Test
    void testStyleConstructor() {
        // checking that the size of initialized arrayList is equal to 0 and
        // the name of the style is indeed Sport
        assertEquals(0, testStyle.getCars().size());
        assertEquals("Sport", testStyle.getStyleName());
    }

    // this test method is enough to test both addCar and getCars
    // because we are using both methods here
    @Test
    void addCarTest1() {
        Car car1 = new Car("Tesla Model 3", 1500, 1000, 0.41);
        Car car2 = new Car("Lamborghini Huracan", 3000, 2000, 0.10);
        Car car3 = new Car("Porsche 911", 2000, 1800, 0.20);

        // testing for the case where only 1 of 3 cars has been added
        testStyle.addCar(car1);
        assertTrue(testStyle.getCars().contains(car1));
        assertFalse(testStyle.getCars().contains(car2));
        assertFalse(testStyle.getCars().contains(car3));
    }

    // this test method is enough to test both addCar and getCars
    // because we are using both methods here
    @Test
    void addCarTest2() {
        Car car1 = new Car("Tesla Model 3", 1500, 1000, 0.41);
        Car car2 = new Car("Lamborghini Huracan", 3000, 2000, 0.10);
        Car car3 = new Car("Porsche 911", 2000, 1800, 0.20);

        // testing for the case where only 2 of 3 cars have been added
        testStyle.addCar(car1);
        testStyle.addCar(car2);
        assertTrue(testStyle.getCars().contains(car1));
        assertTrue(testStyle.getCars().contains(car2));
        assertFalse(testStyle.getCars().contains(car3));
    }

    // this test method is enough to test both addCar and getCars
    // because we are using both methods here
    @Test
    void addCarTest3() {
        Car car1 = new Car("Tesla Model 3", 1500, 1000, 0.41);
        Car car2 = new Car("Lamborghini Huracan", 3000, 2000, 0.10);
        Car car3 = new Car("Porsche 911", 2000, 1800, 0.20);

        // testing for the case where only 3 of 3 cars have been added
        testStyle.addCar(car1);
        testStyle.addCar(car2);
        testStyle.addCar(car3);
        assertTrue(testStyle.getCars().contains(car1));
        assertTrue(testStyle.getCars().contains(car2));
        assertTrue(testStyle.getCars().contains(car3));
    }

    // this test method is enough to test both addCar and getCars
    // because we are using both methods here
    @Test
    void addCarTest4() {
        Car car1 = new Car("Tesla Model 3", 1500, 1000, 0.41);
        Car car2 = new Car("Lamborghini Huracan", 3000, 2000, 0.10);
        Car car3 = new Car("Porsche 911", 2000, 1800, 0.20);

        // testing for the case where we try to insert a car that already exists
        testStyle.addCar(car1);
        testStyle.addCar(car2);
        testStyle.addCar(car3);
        testStyle.addCar(car3);
        assertEquals(car1, testStyle.getCars().get(0));
        assertEquals(car2, testStyle.getCars().get(1));
        assertEquals(car3, testStyle.getCars().get(2));
        assertEquals(3,testStyle.getCars().size());
    }
}
