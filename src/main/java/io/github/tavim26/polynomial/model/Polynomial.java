package io.github.tavim26.polynomial.model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {


    private Map<Integer, Monomial> monomials;

    public Polynomial() {
        this.monomials = new HashMap<>();
    }

    public Polynomial(String polynomialText) {
        this();
        parsePolynomialText(polynomialText);
    }

    public void addMonomial(Monomial monomial) {
        int degree = monomial.getDegree();
        int coefficient = monomial.getCoefficient();

        if (monomials.containsKey(degree)) {
            Monomial existingMonomial = monomials.get(degree);
            int updatedCoefficient = existingMonomial.getCoefficient() + coefficient;
            existingMonomial.setCoefficient(updatedCoefficient);
        } else {
            monomials.put(degree, monomial);
        }
    }


    public Map<Integer, Monomial> getMonomials() {
        return monomials;
    }

    public void setMonomials(Map<Integer, Monomial> monomials) {
        this.monomials = monomials;
    }



    @Override
    public String toString()
    {
        StringBuilder polynomialString = new StringBuilder();

        TreeMap<Integer, Monomial> sortedMonomials = new TreeMap<>(Collections.reverseOrder());
        sortedMonomials.putAll(monomials);

        boolean isFirstMonomial = true;

        for (Map.Entry<Integer, Monomial> entry : sortedMonomials.entrySet())
        {
            Monomial monomial = entry.getValue();
            String monomialString = monomial.toString();

            if (!monomialString.isEmpty() && monomial.getCoefficient() != 0)
            {
                if (monomial.getCoefficient() >= 0 && !isFirstMonomial)
                {
                    polynomialString.append("+");
                }
                polynomialString.append(monomialString);

                if (isFirstMonomial)
                {
                    isFirstMonomial = false;
                }
            }
        }

        if (polynomialString.length() == 0)
        {
            return "0";
        }

        return polynomialString.toString();
    }






    private void parsePolynomialText(String polynomialText)
    {

        //initializam un pattern pentru identificarea puterilor si a coeficientilor polinomului
        Pattern pattern = Pattern.compile("([-+]?(\\d*)x(\\^(-?\\d+))?)|([-+]?(\\d+))");

        //se aplica pattern-ul asupra textului polinomului
        Matcher matcher = pattern.matcher(polynomialText);

        //se parcurg toate grupurile de caractere care corespund pattern-ului in text
        while (matcher.find())
        {
            int coefficient;
            int degree;

            String monomialStr = matcher.group(1);  //grupul ce reprezinta un monom
            String constantTermStr = matcher.group(5);  //grupul ce reprezinta un termen constant


            //se verif daca grupul de caractere e un monom sau un t. constant
            if (monomialStr != null && !monomialStr.isEmpty())
            {

                //daca e un monom
                if (monomialStr.equals("x"))
                {
                    //se seteaza coeff si puterea implicita
                    coefficient = 1;
                    degree = 1;
                }
                else
                {
                    //se imparte monomul in coeficient si putere

                    String[] parts = monomialStr.split("x");
                    coefficient = parts.length > 0 && !parts[0].isEmpty() ? Integer.parseInt(parts[0]) : 1;
                    degree = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1].substring(1)) : 1;
                }
            }

            //altfel, daca grupul de caractere reprezinta un termen constant
            else if (constantTermStr != null && !constantTermStr.isEmpty())
            {
                coefficient = Integer.parseInt(constantTermStr);    //coeficientul ia val termenului constant
                degree = 0; //puterea este 0
            }

            //daca grupul de caractere nu reprezinta nici monom nici termen constant
            else
            {
                continue;   // se trece la urmatorul grup de caractere
            }


            //se creeaza un obiect de tip Monomial cu puterea si coeficientul determinate si se adauga la polinom
            Monomial mon = new Monomial(degree, coefficient);
            addMonomial(mon);
        }
    }




}
