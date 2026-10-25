package io.github.tavim26.polynomial.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PolynomialParserTest {

    @Test
    void parsesPolynomialWithSpacesAndSigns() {
        Polynomial expected = Polynomial.of(
                new Monomial(2, Rational.of(3)),
                new Monomial(1, Rational.of(-1)),
                new Monomial(0, Rational.of(5)));

        assertEquals(expected, PolynomialParser.parse("3x^2 - x + 5"));
    }

    @Test
    void parsesFractionalCoefficients() {
        Polynomial expected = Polynomial.of(new Monomial(1, Rational.of(1, 2)));

        assertEquals(expected, PolynomialParser.parse("1/2x"));
    }

    @Test
    void combinesLikeTermsAndFormatsResult() {
        assertEquals("x^2+2x-1", PolynomialParser.parse("x + x^2 + x - 1").toString());
        assertEquals("0", PolynomialParser.parse("x - x").toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "2.5x", "x^", "3x2", "x+", "2*x", "1/0x"})
    void rejectsInvalidInput(String input) {
        assertThrows(InvalidPolynomialException.class, () -> PolynomialParser.parse(input));
    }
}