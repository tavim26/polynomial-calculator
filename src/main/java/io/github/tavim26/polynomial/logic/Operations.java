package io.github.tavim26.polynomial.logic;

import io.github.tavim26.polynomial.model.Monomial;
import io.github.tavim26.polynomial.model.Polynomial;
import io.github.tavim26.polynomial.model.Rational;

import java.util.ArrayList;
import java.util.List;

/** Arithmetic and calculus operations on polynomials. */
public final class Operations {

    private Operations() {
    }

    public static Polynomial add(Polynomial first, Polynomial second) {
        List<Monomial> terms = new ArrayList<>(first.monomials());
        terms.addAll(second.monomials());
        return Polynomial.of(terms);
    }

    public static Polynomial negate(Polynomial polynomial) {
        return Polynomial.of(polynomial.monomials().stream()
                .map(Monomial::negate)
                .toList());
    }

    public static Polynomial subtract(Polynomial first, Polynomial second) {
        return add(first, negate(second));
    }

    public static Polynomial multiply(Polynomial first, Polynomial second) {
        List<Monomial> products = new ArrayList<>();
        for (Monomial a : first.monomials()) {
            for (Monomial b : second.monomials()) {
                products.add(a.multiply(b));
            }
        }
        return Polynomial.of(products);
    }

    /**
     * Polynomial long division.
     *
     * @throws ArithmeticException if the divisor is the zero polynomial
     */
    public static DivisionResult divide(Polynomial dividend, Polynomial divisor) {
        if (divisor.isZero()) {
            throw new ArithmeticException("Cannot divide by the zero polynomial");
        }

        Monomial divisorLeadingTerm = divisor.leadingTerm();
        Polynomial quotient = Polynomial.ZERO;
        Polynomial remainder = dividend;

        while (!remainder.isZero() && remainder.degree() >= divisorLeadingTerm.degree()) {
            Polynomial step = Polynomial.of(remainder.leadingTerm().divide(divisorLeadingTerm));
            quotient = add(quotient, step);
            remainder = subtract(remainder, multiply(step, divisor));
        }

        return new DivisionResult(quotient, remainder);
    }

    public static Polynomial derivative(Polynomial polynomial) {
        return Polynomial.of(polynomial.monomials().stream()
                .filter(monomial -> monomial.degree() > 0)
                .map(monomial -> new Monomial(
                        monomial.degree() - 1,
                        monomial.coefficient().multiply(Rational.of(monomial.degree()))))
                .toList());
    }

    /** Antiderivative without the integration constant. */
    public static Polynomial integral(Polynomial polynomial) {
        return Polynomial.of(polynomial.monomials().stream()
                .map(monomial -> new Monomial(
                        monomial.degree() + 1,
                        monomial.coefficient().divide(Rational.of(monomial.degree() + 1))))
                .toList());
    }
}