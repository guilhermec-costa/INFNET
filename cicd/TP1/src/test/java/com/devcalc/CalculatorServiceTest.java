package com.devcalc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private final CalculatorService service = new CalculatorService();

    @Test
    void testAdd() {
        assertEquals(15, service.add(10, 5));
        assertEquals(0, service.add(-5, 5));
        assertEquals(-10, service.add(-5, -5));
    }

    @Test
    void testSubtract() {
        assertEquals(5, service.subtract(10, 5));
        assertEquals(-10, service.subtract(5, 15));
        assertEquals(0, service.subtract(10, 10));
    }

    @Test
    void testMultiply() {
        assertEquals(50, service.multiply(10, 5));
        assertEquals(0, service.multiply(10, 0));
        assertEquals(-50, service.multiply(-10, 5));
    }

    @Test
    void testDivide() {
        assertEquals(2.0, service.divide(10, 5));
        assertEquals(2.5, service.divide(5, 2));
    }

    @Test
    void testDivideByZero() {
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> service.divide(10, 0)
        );
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }
}
