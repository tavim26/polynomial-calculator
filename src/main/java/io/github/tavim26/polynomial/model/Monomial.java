package io.github.tavim26.polynomial.model;

public class Monomial {

    private int degree;

    private int coefficient;

    public Monomial(int degree, int coefficient) {
        this.degree = degree;
        this.coefficient = coefficient;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }


    public String toString()
    {
        StringBuilder result = new StringBuilder();

        if (coefficient != 0)
        {
            if (coefficient != 1 || degree == 0 || coefficient!=-1)
            {
                result.append(coefficient);
            }

            if (degree != 0)
            {
                result.append("x");

                if (degree != 1)
                {
                    result.append("^").append(degree);
                }
            }
        }
        else
        {

            result.append(0);
        }

        return result.toString();
    }












}