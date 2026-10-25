package io.github.tavim26.polynomial.model;

public class Monomial {

    private final int degree;
    private int coefficient;

    public Monomial(int degree, int coefficient) {
        this.degree = degree;
        this.coefficient = coefficient;
    }

    public int getDegree() {
        return degree;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString() {
        if (coefficient == 0) {
            return "0";
        }
        if (degree == 0) {
            return String.valueOf(coefficient);
        }

        String coefficientPart = switch (coefficient) {
            case 1 -> "";
            case -1 -> "-";
            default -> String.valueOf(coefficient);
        };
        String powerPart = (degree == 1) ? "" : "^" + degree;

        return coefficientPart + "x" + powerPart;
    }
}