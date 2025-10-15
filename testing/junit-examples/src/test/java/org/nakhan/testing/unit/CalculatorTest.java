package org.nakhan.testing.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Example unit test class demonstrating JUnit 5 features.
 * This class shows basic unit testing patterns and best practices.
 *
 * @author Full Stack Java Developer Toolkit
 * @version 1.0.0
 */
@DisplayName("Calculator Unit Tests")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        System.out.println("Setting up Calculator test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tearing down Calculator test");
    }

    @Test
    @DisplayName("Should add two positive numbers correctly")
    void shouldAddTwoPositiveNumbers() {
        // Given
        int a = 5;
        int b = 3;

        // When
        int result = calculator.add(a, b);

        // Then
        assertEquals(8, result, "Addition of 5 + 3 should equal 8");
        assertTrue(result > 0, "Result should be positive");
    }

    @Test
    @DisplayName("Should subtract two numbers correctly")
    void shouldSubtractTwoNumbers() {
        // Given
        int a = 10;
        int b = 4;

        // When
        int result = calculator.subtract(a, b);

        // Then
        assertEquals(6, result);
    }

    @Test
    @DisplayName("Should multiply two numbers correctly")
    void shouldMultiplyTwoNumbers() {
        // Given
        int a = 6;
        int b = 7;

        // When
        int result = calculator.multiply(a, b);

        // Then
        assertEquals(42, result);
    }

    @Test
    @DisplayName("Should divide two numbers correctly")
    void shouldDivideTwoNumbers() {
        // Given
        int a = 15;
        int b = 3;

        // When
        double result = calculator.divide(a, b);

        // Then
        assertEquals(5.0, result, 0.001);
    }

    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void shouldThrowExceptionWhenDividingByZero() {
        // Given
        int a = 10;
        int b = 0;

        // When & Then
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(a, b),
            "Division by zero should throw ArithmeticException"
        );

        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("Should handle various positive numbers for addition")
    void shouldHandleVariousPositiveNumbers(int number) {
        // Given
        int a = number;
        int b = 10;

        // When
        int result = calculator.add(a, b);

        // Then
        assertEquals(number + 10, result);
        assertTrue(result > a, "Result should be greater than first operand");
        assertTrue(result > b, "Result should be greater than second operand");
    }

    @ParameterizedTest
    @CsvSource({
        "2, 3, 5",
        "10, 15, 25",
        "-5, -3, -8",
        "0, 5, 5"
    })
    @DisplayName("Should add various number combinations")
    void shouldAddVariousNumberCombinations(int a, int b, int expected) {
        // When
        int result = calculator.add(a, b);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Should handle edge cases")
    void shouldHandleEdgeCases() {
        // Test with zero
        assertEquals(0, calculator.add(0, 0));
        assertEquals(5, calculator.add(5, 0));
        assertEquals(0, calculator.add(0, 5));

        // Test with negative numbers
        assertEquals(-8, calculator.add(-5, -3));
        assertEquals(2, calculator.add(5, -3));
        assertEquals(-2, calculator.add(-5, 3));
    }

    @Test
    @DisplayName("Should demonstrate assertion methods")
    void shouldDemonstrateAssertionMethods() {
        // Given
        int a = 10;
        int b = 20;

        // When
        int result = calculator.add(a, b);

        // Then - demonstrate various assertion methods
        assertEquals(30, result);
        assertNotEquals(25, result);
        assertTrue(result > 25);
        assertFalse(result < 25);
        assertNotNull(calculator);

        // Demonstrate array/list assertions
        int[] numbers = {1, 2, 3, 4, 5};
        assertTrue(numbers.length == 5);
        assertFalse(numbers.length == 3);
    }
}

/**
 * Simple Calculator class for testing demonstration.
 */
class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return (double) a / b;
    }
}
