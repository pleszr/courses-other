package com.example.unitTests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("When running MathUtils")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MathUtilsTest {

    MathUtils mathUtils;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeEach
    public void init(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        mathUtils = new MathUtils();
        testReporter.publishEntry("TestClass: " + testInfo.getTestClass() + ", TestMethod: " + testInfo.getTestMethod());
    }

    @Test
    @DisplayName("Adding up two numbers")
    void add() {
        assertAll(
                () -> assertEquals(4,mathUtils.add(2,2),"The add method should add two values together"),
                () -> assertEquals(-1,mathUtils.add(-2,1)),
                () -> assertEquals(5,mathUtils.add(6,-1)),
                () -> assertEquals(-8,mathUtils.add(-4,-4)));
    }

    @RepeatedTest(5)
    @Tag("Repeated") //typical use-case could be difference between unit tests and integration tests
    @DisplayName("Multiplying two numbers")
    void multiply(RepetitionInfo repetitionInfo) {
        //System.out.println(repetitionInfo);
        int expected = 25;
        int actual = mathUtils.multiply(5,5);
        assertEquals(expected,actual,() -> "Expects " + expected + ", returns " + actual);
        //using lambda is better because message gets only compiled if it is an error
            //with a simple string it doesn't matter, but if its more complex it does matter
    }

    @Test
    void testComputeCircleArea() {
        double expected = 314.1592653589793;
        double actual = mathUtils.computeCircleArea(10);
        assertEquals(expected,actual,"method should calculate the area of a circle based on radius");
    }

    @Test
    void testDivideException() {
        assertThrows(ArithmeticException.class,()-> mathUtils.divide(1,0),"divide by zero should throw");
    }

    @Test
    void testDivide() {
        double expected = 5;
        double actual = mathUtils.divide(10,2);

        //this is used to print reports tipically
        System.out.println("TestClass: " + testInfo.getTestClass() + ", TestMethod: " + testInfo.getTestMethod());
        testReporter.publishEntry("JUNIT OUTPUT");
        assertEquals(expected,actual);
    }

    @Nested
    @DisplayName("disabled methods")
    class TestingDisables {
        @Test
        @DisplayName("when @Disabled is used")
        @Disabled
        void failTest() {
            fail();
        }

        @Test
        @DisplayName("when it gets disabled on Tuesdays")
        void AssumeTrueTest() {
            boolean isItTuesday = false;
            LocalDate today = LocalDate.now();
            if (today.getDayOfWeek() == DayOfWeek.TUESDAY) {
                isItTuesday = true;
            }
            //typical use case is to manage dependencies (to know if external system is down or my code fails)
            assumeTrue(!isItTuesday,"Should be disabled on Tuesdays");
        }
    }


    @Test
    @DisplayName("EnabledOnOSTest")
    @EnabledOnOs(OS.MAC)
    void enabledOnOsTest(){
        //succeed
    }


}