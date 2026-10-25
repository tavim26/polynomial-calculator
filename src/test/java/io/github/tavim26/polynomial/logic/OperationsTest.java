package io.github.tavim26.polynomial.logic;

import io.github.tavim26.polynomial.model.Polynomial;
import io.github.tavim26.polynomial.model.PolynomialParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationsTest {

    private static Polynomial p(String text) {
        return PolynomialParser.parse(text);
    }

    @Test
    void add() {
        assertEquals(p("x^2+2x"), Operations.add(p("x^2+x"), p("x")));
    }

    @Test
    void subtract() {
        assertEquals(p("2x+1"), Operations.subtract(p("x^2+3x"), p("x^2+x-1")));
    }

    @Test
    void multiply() {
        assertEquals(p("x^2-1"), Operations.multiply(p("x+1"), p("x-1")));
    }

    @Test
    void divideWithoutRemainder() {
        DivisionResult result = Operations.divide(p("x^3-1"), p("x-1"));

        assertEquals(p("x^2+x+1"), result.quotient());
        assertEquals(Polynomial.ZERO, result.remainder());
    }

    @Test
    void divideWithRemainder() {
        DivisionResult result = Operations.divide(p("x^2+1"), p("x-1"));

        assertEquals(p("x+1"), result.quotient());
        assertEquals(p("2"), result.remainder());
    }

    @Test
    void divideByZeroPolynomialThrows() {
        assertThrows(ArithmeticException.class,
                () -> Operations.divide(p("x+1"), Polynomial.ZERO));
    }

    @Test
    void derivative() {
        assertEquals(p("3x^2-1"), Operations.derivative(p("x^3-x+7")));
    }

    @Test
    void integralUsesExactFractions() {
        assertEquals(p("1/2x^2"), Operations.integral(p("x")));
        assertEquals(p("x^3+x"), Operations.integral(p("3x^2+1")));
    }
}