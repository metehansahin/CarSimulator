package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {
    private Car testCar;

    @BeforeEach
    void runBefore() {
        testCar = new Car("Tesla Model 3", 1500, 1000, 0.41);
    }

    @Test
    void testCarConstructor() {
        // testing that constructor has been initialized with proper values
        assertEquals("Tesla Model 3", testCar.getModel());
        assertEquals(1500, testCar.getWeight());
        assertEquals(1000, testCar.getEngineForce());
        assertEquals(0.41, testCar.getDragArea());
    }

    @Test
    void addStatsTest() {
        // testing for the case where input is applicable
        testCar.addStats("Lamborghini Huracan", 3000, 2000, 0.10);
        assertEquals("Lamborghini Huracan", testCar.getModel());
        assertEquals(3000, testCar.getWeight());
        assertEquals(2000, testCar.getEngineForce());
        assertEquals(0.10, testCar.getDragArea());

        // testing for the edge cases where length of model is 1 and double parameters are equal to 0
        testCar.addStats("a", 0, 0, 0);
        assertEquals("a", testCar.getModel());
        assertEquals(3000, testCar.getWeight());
        assertEquals(2000, testCar.getEngineForce());
        assertEquals(0.10, testCar.getDragArea());

        // testing for the case where length of model is 0 and double parameters are negative values
        testCar.addStats("", -10, -20, -12341.5);
        assertEquals("a", testCar.getModel());
        assertEquals(3000, testCar.getWeight());
        assertEquals(2000, testCar.getEngineForce());
        assertEquals(0.10, testCar.getDragArea());
    }

    // this test includes methods: calculateData, calculateAcceleration,
    //                             calculateVelocity, calculatePosition,
    @Test
    void calculateDataTest1() {
        // testing the initial start
        testCar.calculateData(1);
        assertEquals(0,testCar.dragForce);
        assertEquals(0.6666666666666666, testCar.getAcceleration());
        assertEquals(0.6666666666666666, testCar.getVelocity());
        assertEquals(0.6666666666666666, testCar.distance1);
        assertEquals(1, testCar.getTime());
        assertEquals(0.6666666666666666, testCar.distance0);
        assertEquals(0.6666666666666666, testCar.getDistance());
    }

    // this test includes methods: calculateData, calculateAcceleration,
    //                             calculateVelocity, calculatePosition,
    @Test
    public void calculateDataTest2() {
        // testing for the case where time passed = 2 seconds
        testCar.calculateData(1);
        testCar.calculateData(1);
        assertEquals(0.1116111111111111,testCar.dragForce);
        assertEquals(0.6665922592592592, testCar.getAcceleration());
        assertEquals(1.333258925925926, testCar.getVelocity());
        assertEquals(1.9999255925925925, testCar.distance1);
        assertEquals(2, testCar.getTime());
        assertEquals(1.9999255925925925, testCar.distance0);
        assertEquals(1.9999255925925925, testCar.getDistance());
    }

    // this test includes methods: calculateData, calculateAcceleration,
    //                             calculateVelocity, calculatePosition,
    @Test
    public void calculateDataTest3() {
        // testing for the case where time passed = 3 seconds
        testCar.calculateData(1);
        testCar.calculateData(1);
        testCar.calculateData(1);
        assertEquals(0.44639461767429467,testCar.dragForce);
        assertEquals(0.6663690702548838, testCar.getAcceleration());
        assertEquals(1.9996279961808097, testCar.getVelocity());
        assertEquals(3.999553588773402, testCar.distance1);
        assertEquals(3, testCar.getTime());
        assertEquals(3.999553588773402, testCar.distance0);
        assertEquals(3.999553588773402, testCar.getDistance());
    }

    @Test
    void resetDataTest() {
        // testing that resetting also works
        testCar.calculateData(1);
        testCar.calculateData(1);
        testCar.calculateData(1);
        testCar.resetData();
        assertEquals(0,testCar.dragForce);
        assertEquals(0, testCar.getAcceleration());
        assertEquals(0, testCar.getVelocity());
        assertEquals(0, testCar.distance1);
        assertEquals(0, testCar.getTime());
        assertEquals(0, testCar.distance0);
        assertEquals(0, testCar.getDistance());
    }
}
