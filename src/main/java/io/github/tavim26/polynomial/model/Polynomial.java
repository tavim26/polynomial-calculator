package io.github.tavim26.polynomial.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Immutable polynomial with rational coefficients.
 * Terms are kept sorted by descending degree and zero coefficients are never stored.
 */
public final class Polynomial {

    public static final Polynomial ZERO = new Polynomial(Map.of());

    private final SortedMap<Integer, Rational> coefficientsByDegree;

    private Polynomial(Map<Integer, Rational> coefficients) {
        SortedMap<Integer, Rational> sorted = new TreeMap<>(Comparator.reverseOrder());
        coefficients.forEach((degree, coefficient) -> {
            if (!coefficient.isZero()) {
                sorted.put(degree, coefficient);
            }
        });
        this.coefficientsByDegree = Collections.unmodifiableSortedMap(sorted);
    }

    public static Polynomial of(Monomial... monomials) {
        return of(Arrays.asList(monomials));
    }

    /** Builds a polynomial from the given terms, adding up terms of equal degree. */
    public static Polynomial of(Collection<Monomial> monomials) {
        Map<Integer, Rational> sums = new HashMap<>();
        for (Monomial monomial : monomials) {
            sums.merge(monomial.degree(), monomial.coefficient(), Rational::add);
        }
        return new Polynomial(sums);
    }

    public boolean isZero() {
        return coefficientsByDegree.isEmpty();
    }

    /** @throws IllegalStateException for the zero polynomial */
    public int degree() {
        return leadingTerm().degree();
    }

    /** @throws IllegalStateException for the zero polynomial */
    public Monomial leadingTerm() {
        if (isZero()) {
            throw new IllegalStateException("The zero polynomial has no leading term");
        }
        int degree = coefficientsByDegree.firstKey();
        return new Monomial(degree, coefficientsByDegree.get(degree));
    }

    /** Returns the terms sorted by descending degree. */
    public List<Monomial> monomials() {
        return coefficientsByDegree.entrySet().stream()
                .map(entry -> new Monomial(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Polynomial other
                && coefficientsByDegree.equals(other.coefficientsByDegree);
    }

    @Override
    public int hashCode() {
        return coefficientsByDegree.hashCode();
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        for (Monomial monomial : monomials()) {
            if (!result.isEmpty() && monomial.coefficient().signum() > 0) {
                result.append('+');
            }
            result.append(monomial);
        }
        return result.toString();
    }
}