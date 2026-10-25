package io.github.tavim26.polynomial.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses polynomials written as text, e.g. {@code "3x^2 - x + 1/2"}.
 * Each term has an optional sign, an optional integer or fractional coefficient
 * and an optional {@code x} with an optional non-negative integer exponent.
 * A fractional coefficient applies to the whole term: {@code 1/2x} means (1/2)·x.
 */
public final class PolynomialParser {

    // groups: 1 = sign, 2 = coefficient, 3 = variable part, 4 = exponent
    private static final Pattern TERM_PATTERN =
            Pattern.compile("([+-])?(\\d+(?:/\\d+)?)?(x(?:\\^(\\d+))?)?");

    private PolynomialParser() {
    }

    public static Polynomial parse(String text) {
        if (text == null || text.isBlank()) {
            throw new InvalidPolynomialException("Polynomial is empty");
        }

        String normalized = text.strip()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s*([+-])\\s*", "$1");

        List<Monomial> terms = new ArrayList<>();
        for (String term : normalized.split("(?=[+-])")) {
            terms.add(parseTerm(term));
        }
        return Polynomial.of(terms);
    }

    private static Monomial parseTerm(String term) {
        Matcher matcher = TERM_PATTERN.matcher(term);
        if (!matcher.matches() || (matcher.group(2) == null && matcher.group(3) == null)) {
            throw new InvalidPolynomialException("Invalid term: '" + term + "'");
        }

        try {
            Rational coefficient = (matcher.group(2) == null)
                    ? Rational.ONE
                    : parseCoefficient(matcher.group(2));
            if ("-".equals(matcher.group(1))) {
                coefficient = coefficient.negate();
            }

            int degree = (matcher.group(3) == null) ? 0
                    : (matcher.group(4) == null) ? 1 : Integer.parseInt(matcher.group(4));

            return new Monomial(degree, coefficient);
        } catch (NumberFormatException e) {
            throw new InvalidPolynomialException("Exponent too large in term: '" + term + "'");
        } catch (ArithmeticException e) {
            throw new InvalidPolynomialException("Division by zero in term: '" + term + "'");
        }
    }

    private static Rational parseCoefficient(String text) {
        String[] parts = text.split("/");
        BigInteger numerator = new BigInteger(parts[0]);
        BigInteger denominator = (parts.length == 2) ? new BigInteger(parts[1]) : BigInteger.ONE;
        return new Rational(numerator, denominator);
    }
}