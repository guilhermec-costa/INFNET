package com.infnet.test_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScientificCalculatorTest {
    
    private ScientificCalculator calculator;
    
    // Etapa 4: Test Fixture com BeforeEach
    @BeforeEach
    void setUp() {
        calculator = new ScientificCalculator();
    }
    
    // Etapa 2: Primeiro teste - testAddition
    @Test
    void testAddition() {
        double result = calculator.add(5.0, 3.0);
        assertEquals(8.0, result, 0.001);
    }
    
    // Etapa 3: Aplicando as 4 fases de um teste
    @Test
    void shouldReturnCorrectResultWhenSubtractingTwoNumbers() {
        double minuend = 10.0;
        double subtrahend = 4.0;
        double expectedResult = 6.0;
        
        double actualResult = calculator.subtract(minuend, subtrahend);
        
        assertEquals(expectedResult, actualResult, 0.001, 
                    "Subtraction should return correct result");
        
    }
    
    @Test
    void shouldReturnCorrectResultWhenMultiplyingTwoNumbers() {
        double result = calculator.multiply(4.0, 5.0);
        assertEquals(20.0, result, 0.001);
    }
    
    // Etapa 7: Cenário de exceção (divisão por zero)
    @Test
    void shouldThrowExceptionWhenDividingByZero() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.divide(10.0, 0.0),
            "Division by zero should throw IllegalArgumentException"
        );
        assertEquals("Division by zero", exception.getMessage());
    }
    
    @Test
    void shouldReturnCorrectResultWhenDividingValidNumbers() {
        double result = calculator.divide(15.0, 3.0);
        assertEquals(5.0, result, 0.001);
    }
    
    // Etapa 5: Testando um cenário de sucesso (Happy Path)
    @Test
    void shouldReturnCorrectResultWhenCalculatingSquareRootOfPositiveNumber() {
        double result = calculator.squareRoot(25.0);
        assertEquals(5.0, result, 0.001, "Square root of 25 should be 5");
    }
    
    @Test
    void shouldReturnCorrectResultWhenCalculatingSquareRootOfZero() {
        double result = calculator.squareRoot(0.0);
        assertEquals(0.0, result, 0.001);
    }
    
    // Etapa 6: Testando um cenário patológico (Corner Case)
    @Test
    void shouldThrowExceptionWhenCalculatingSquareRootOfNegativeNumber() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.squareRoot(-4.0),
            "Square root of negative number should throw IllegalArgumentException"
        );
        assertEquals("Negative number", exception.getMessage());
    }
    
    @Test
    void shouldReturnCorrectResultWhenCalculatingPower() {
        double result = calculator.power(2.0, 3.0);
        assertEquals(8.0, result, 0.001);
    }
    
    @Test
    void shouldReturnOneWhenCalculatingPowerOfZero() {
        double result = calculator.power(5.0, 0.0);
        assertEquals(1.0, result, 0.001);
    }
    
    // Etapa 8: Combinando múltiplas entradas (log)
    @Test
    void shouldReturnCorrectResultWhenCalculatingNaturalLogOfValidNumbers() {
        double resultE = calculator.log(Math.E);
        assertEquals(1.0, resultE, 0.001, "ln(e) should be 1");
        
        double resultOne = calculator.log(1.0);
        assertEquals(0.0, resultOne, 0.001, "ln(1) should be 0");
    }
    
    @Test
    void shouldThrowExceptionWhenCalculatingLogOfZero() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.log(0.0),
            "Log of zero should throw IllegalArgumentException"
        );
        assertEquals("Log of non-positive number", exception.getMessage());
    }
    
    @Test
    void shouldThrowExceptionWhenCalculatingLogOfNegativeNumber() {
        assertThrows(
            IllegalArgumentException.class,
            () -> calculator.log(-5.0),
            "Log of negative number should throw IllegalArgumentException"
        );
    }
    
    // Etapa 8: Combinando múltiplas entradas (seno)
    @Test
    void shouldReturnCorrectResultWhenCalculatingSineOfCommonAngles() {
        double sin0 = calculator.sin(0.0);
        assertEquals(0.0, sin0, 0.001, "sin(0°) should be 0");
        
        double sin30 = calculator.sin(30.0);
        assertEquals(0.5, sin30, 0.001, "sin(30°) should be 0.5");
        
        double sin90 = calculator.sin(90.0);
        assertEquals(1.0, sin90, 0.001, "sin(90°) should be 1");
    }
    
    @Test
    void shouldReturnCorrectResultWhenCalculatingCosineOfCommonAngles() {
        double cos0 = calculator.cos(0.0);
        assertEquals(1.0, cos0, 0.001, "cos(0°) should be 1");
        
        double cos60 = calculator.cos(60.0);
        assertEquals(0.5, cos60, 0.001, "cos(60°) should be 0.5");
        
        double cos90 = calculator.cos(90.0);
        assertEquals(0.0, cos90, 0.001, "cos(90°) should be 0");
    }
}