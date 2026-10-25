package io.github.tavim26.polynomial.model;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class PolynomialParser {

    // groups: 1 = sign, 2 = coefficient, 3 = variable part, 4 = exponent
    private static final Pattern TERM_PATTERN =
            Pattern.compile("([+-])?(\\d+)?(x(?:\\^(\\d+))?)?");

    private PolynomialParser() {
    }

    public static Polynomial parse(String text) {
        if (text == null || text.isBlank()) {
            throw new InvalidPolynomialException("Polynomial is empty");
        }

        String normalized = text.strip()
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s*([+-])\\s*", "$1");

        Polynomial polynomial = new Polynomial();
        for (String term : normalized.split("(?=[+-])")) {
            polynomial.addMonomial(parseTerm(term));
        }
        return polynomial;
    }

    private static Monomial parseTerm(String term) {
        Matcher matcher = TERM_PATTERN.matcher(term);
        String coefficientText = matcher.matches() ? matcher.group(2) : null;
        String variablePart = matcher.matches() ? matcher.group(3) : null;

        if (coefficientText == null && variablePart == null) {
            throw new InvalidPolynomialException("Invalid term: '" + term + "'");
        }

        int sign = "-".equals(matcher.group(1)) ? -1 : 1;
        String exponentText = matcher.group(4);

        try {
            int coefficient = (coefficientText == null) ? 1 : Integer.parseInt(coefficientText);
            int degree = (variablePart == null) ? 0
                    : (exponentText == null) ? 1 : Integer.parseInt(exponentText);

            return new Monomial(degree, sign * coefficient);
        } catch (NumberFormatException e) {
            throw new InvalidPolynomialException("Number too large in term: '" + term + "'");
        }
    }
}