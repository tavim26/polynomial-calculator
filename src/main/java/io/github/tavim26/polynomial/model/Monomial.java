package io.github.tavim26.polynomial.model;

import java.util.Objects;

/**
 * Immutable term of the form {@code coefficient * x^degree}.
 */
public record Monomial(int degree, Rational coefficient) {

    public Monomial {
        if (degree < 0) {
            throw new IllegalArgumentException("Degree must be non-negative: " + degree);
        }
        Objects.requireNonNull(coefficient, "coefficient");
    }

    public Monomial negate() {
        return new Monomial(degree, coefficient.negate());
    }

    public Monomial multiply(Monomial other) {
        return new Monomial(degree + other.degree, coefficient.multiply(other.coefficient));
    }

    public Monomial divide(Monomial other) {
        return new Monomial(degree - other.degree, coefficient.divide(other.coefficient));
    }

    @Override
    public String toString() {
        if (coefficient.isZero()) {
            return "0";
        }
        if (degree == 0) {
            return coefficient.toString();
        }

        String coefficientPart;
        if (coefficient.equals(Rational.ONE)) {
            coefficientPart = "";
        } else if (coefficient.equals(Rational.ONE.negate())) {
            coefficientPart = "-";
        } else {
            coefficientPart = coefficient.toString();
        }
        String powerPart = (degree == 1) ? "" : "^" + degree;

        return coefficientPart + "x" + powerPart;
    }
}