package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StyleListTest {
    private StyleList testStyleList;

    @BeforeEach
    void runBefore() {
        testStyleList = new StyleList();
    }

    @Test
    void testStyleListConstructor() {
        // checking that the size of initialized arrayList is equal to 0
        assertEquals(0, testStyleList.getStyles().size());
    }

    // addStyle1, addStyle2, addStyle3, and addStyle4 are enough
    // to test both addStyle and getStyles because we are using
    // both methods in these test methods
    @Test
    void addStyleTest1() {
        Style suv = new Style("suv");
        Style sedan = new Style("sedan");
        Style coupe = new Style("coupe");

        // testing for the case where only 1 of 3 cars has been added
        testStyleList.addStyle(suv);
        assertTrue(testStyleList.getStyles().contains(suv));
        assertFalse(testStyleList.getStyles().contains(sedan));
        assertFalse(testStyleList.getStyles().contains(coupe));
    }

    // addStyle1, addStyle2, addStyle3, and addStyle4 are enough
    // to test both addStyle and getStyles because we are using
    // both methods in these test methods
    @Test
    void addStyleTest2() {
        Style suv = new Style("suv");
        Style sedan = new Style("sedan");
        Style coupe = new Style("coupe");

        // testing for the case where only 2 of 3 cars have been added
        testStyleList.addStyle(suv);
        testStyleList.addStyle(sedan);
        assertTrue(testStyleList.getStyles().contains(suv));
        assertTrue(testStyleList.getStyles().contains(sedan));
        assertFalse(testStyleList.getStyles().contains(coupe));
    }

    // addStyle1, addStyle2, addStyle3, and addStyle4 are enough
    // to test both addStyle and getStyles because we are using
    // both methods in these test methods
    @Test
    void addStyleTest3() {
        Style suv = new Style("suv");
        Style sedan = new Style("sedan");
        Style coupe = new Style("coupe");

        // testing for the case where only 3 of 3 cars have been added
        testStyleList.addStyle(suv);
        testStyleList.addStyle(sedan);
        testStyleList.addStyle(coupe);
        assertTrue(testStyleList.getStyles().contains(suv));
        assertTrue(testStyleList.getStyles().contains(sedan));
        assertTrue(testStyleList.getStyles().contains(coupe));
    }

    // addStyle1, addStyle2, addStyle3, and addStyle4 are enough
    // to test both addStyle and getStyles because we are using
    // both methods in these test methods
    @Test
    void addStyleTest4() {
        Style suv = new Style("suv");
        Style sedan = new Style("sedan");
        Style coupe = new Style("coupe");

        // testing for the case where we try to insert a car that already exists
        testStyleList.addStyle(suv);
        testStyleList.addStyle(sedan);
        testStyleList.addStyle(coupe);
        testStyleList.addStyle(coupe);
        assertEquals(suv, testStyleList.getStyles().get(0));
        assertEquals(sedan, testStyleList.getStyles().get(1));
        assertEquals(coupe, testStyleList.getStyles().get(2));
        assertEquals(3,testStyleList.getStyles().size());
    }
}
