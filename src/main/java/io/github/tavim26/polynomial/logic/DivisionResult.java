package io.github.tavim26.polynomial.logic;

import io.github.tavim26.polynomial.model.Polynomial;

/** Result of polynomial long division: dividend = quotient * divisor + remainder. */
public record DivisionResult(Polynomial quotient, Polynomial remainder) {
}