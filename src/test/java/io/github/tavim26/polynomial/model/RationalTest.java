package io.github.tavim26.polynomial.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RationalTest {

    @Test
    void fractionsAreStoredInLowestTermsWithPositiveDenominator() {
        assertEquals(Rational.of(-1, 2), Rational.of(2, -4));
        assertEquals("-1/2", Rational.of(2, -4).toString());
    }

    @Test
    void addsFractions() {
        assertEquals(Rational.of(5, 6), Rational.of(1, 2).add(Rational.of(1, 3)));
    }

    @Test
    void zeroDenominatorIsRejected() {
        assertThrows(ArithmeticException.class, () -> Rational.of(1, 0));
    }
}