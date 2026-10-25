package io.github.tavim26.polynomial.model;

import java.math.BigInteger;
import java.util.Objects;

public record Rational(BigInteger numerator, BigInteger denominator) {

    public static final Rational ZERO = Rational.of(0);
    public static final Rational ONE = Rational.of(1);

    public Rational {
        Objects.requireNonNull(numerator, "numerator");
        Objects.requireNonNull(denominator, "denominator");

        if (denominator.signum() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (denominator.signum() < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }

        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
    }

    public static Rational of(long value) {
        return new Rational(BigInteger.valueOf(value), BigInteger.ONE);
    }

    public static Rational of(long numerator, long denominator) {
        return new Rational(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public Rational add(Rational other) {
        return new Rational(
                numerator.multiply(other.denominator).add(other.numerator.multiply(denominator)),
                denominator.multiply(other.denominator));
    }

    public Rational subtract(Rational other) {
        return add(other.negate());
    }

    public Rational multiply(Rational other) {
        return new Rational(
                numerator.multiply(other.numerator),
                denominator.multiply(other.denominator));
    }

    public Rational divide(Rational other) {
        return new Rational(
                numerator.multiply(other.denominator),
                denominator.multiply(other.numerator));
    }

    public Rational negate() {
        return new Rational(numerator.negate(), denominator);
    }

    public int signum() {
        return numerator.signum();
    }

    public boolean isZero() {
        return numerator.signum() == 0;
    }

    public boolean isInteger() {
        return denominator.equals(BigInteger.ONE);
    }

    @Override
    public String toString() {
        return isInteger() ? numerator.toString() : numerator + "/" + denominator;
    }
}