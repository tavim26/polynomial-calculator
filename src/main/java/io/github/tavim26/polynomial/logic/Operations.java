package io.github.tavim26.polynomial.logic;

import io.github.tavim26.polynomial.model.Monomial;
import io.github.tavim26.polynomial.model.Polynomial;

import java.util.*;

public class Operations {


    public Polynomial add(Polynomial polynomial1, Polynomial polynomial2)
    {
        Polynomial result = new Polynomial();
        Map<Integer, Monomial> resultMonomials = new HashMap<>();

        // se pun monoamele din primul polinom in rezultat
        for (Map.Entry<Integer, Monomial> entry : polynomial1.getMonomials().entrySet())
        {
            int degree = entry.getKey();
            Monomial monomial = entry.getValue();
            resultMonomials.put(degree, new Monomial(monomial.getDegree(), monomial.getCoefficient()));
        }

        // se pun monoamele din al doilea polinom in rezultat
        for (Map.Entry<Integer, Monomial> entry : polynomial2.getMonomials().entrySet())
        {
            int degree = entry.getKey();
            Monomial monomial = entry.getValue();


            if (resultMonomials.containsKey(degree))
            {
                Monomial existingMonomial = resultMonomials.get(degree);
                existingMonomial.setCoefficient(existingMonomial.getCoefficient() + monomial.getCoefficient());
            }
            else
            {
                resultMonomials.put(degree, new Monomial(monomial.getDegree(), monomial.getCoefficient()));
            }
        }

        result.setMonomials(resultMonomials);
        return result;
    }


    public Polynomial subtract(Polynomial polynomial1, Polynomial polynomial2)
    {
        Polynomial result = new Polynomial();
        Map<Integer, Monomial> resultMonomials = new HashMap<>();

        for (Map.Entry<Integer, Monomial> entry : polynomial1.getMonomials().entrySet())
        {
            int degree = entry.getKey();
            Monomial monomial = entry.getValue();
            resultMonomials.put(degree, new Monomial(monomial.getDegree(), monomial.getCoefficient()));
        }

        for (Map.Entry<Integer, Monomial> entry : polynomial2.getMonomials().entrySet())
        {
            int degree = entry.getKey();
            Monomial monomial = entry.getValue();


            if (resultMonomials.containsKey(degree))
            {

                Monomial existingMonomial = resultMonomials.get(degree);
                existingMonomial.setCoefficient(existingMonomial.getCoefficient() - monomial.getCoefficient());
            }
            else
            {
                resultMonomials.put(degree, new Monomial(monomial.getDegree(), -monomial.getCoefficient()));
            }
        }

        result.setMonomials(resultMonomials);
        return result;
    }


    public Polynomial multiply(Polynomial polynomial1, Polynomial polynomial2)
    {
        Polynomial result = new Polynomial();

        // se parcurge fiecare monom din primul polinom
        for (Map.Entry<Integer, Monomial> entry1 : polynomial1.getMonomials().entrySet())
        {
            int degree1 = entry1.getKey();
            Monomial monomial1 = entry1.getValue();

            // se parcurge fiecare monom din al doilea polinom
            for (Map.Entry<Integer, Monomial> entry2 : polynomial2.getMonomials().entrySet())
            {
                int degree2 = entry2.getKey();
                Monomial monomial2 = entry2.getValue();

                // se inmultesc coeficientii si se aduna gradele
                int resultDegree = degree1 + degree2;
                int resultCoefficient = monomial1.getCoefficient() * monomial2.getCoefficient();

                //daca rez inmultirii exista deja in result, atunci se aduna coeficientul
                if (result.getMonomials().containsKey(resultDegree))
                {
                    Monomial existingMonomial = result.getMonomials().get(resultDegree);
                    existingMonomial.setCoefficient(existingMonomial.getCoefficient() + resultCoefficient);
                }
                //altfel, se adauga rezultatul inmultirii in result
                else
                {
                    result.getMonomials().put(resultDegree, new Monomial(resultDegree, resultCoefficient));
                }
            }
        }

        return result;
    }








    public Polynomial derivate(Polynomial polynomial)
    {
        Polynomial result = new Polynomial();

        //se parcurge fiecare monom din polinomul dat
        for (Map.Entry<Integer, Monomial> entry : polynomial.getMonomials().entrySet())
        {
            int degree = entry.getKey();
            Monomial monomial = entry.getValue();

            //se aplica regula de derivare a unui monom
            int derivativeCoefficient = monomial.getCoefficient() * degree;
            int derivativeDegree = degree - 1;


            if (derivativeDegree >= 0 && derivativeCoefficient != 0)
            {
                result.getMonomials().put(derivativeDegree, new Monomial(derivativeDegree, derivativeCoefficient));
            }


        }

        return result;
    }


    public Polynomial integrate(Polynomial polynomial) {
        Polynomial result = new Polynomial();

        // Se parcurge fiecare monom din polinomul dat
        for (Map.Entry<Integer, Monomial> entry : polynomial.getMonomials().entrySet()) {
            int degree = entry.getKey();
            Monomial monomial = entry.getValue();

            // Se calculează coeficientul integrat și gradul integrat
            int integratedCoefficient = monomial.getCoefficient() / (degree + 1);
            int integratedDegree = degree + 1;

            // Se adaugă monomul integrat în polinomul rezultat
            result.addMonomial(new Monomial(integratedDegree, integratedCoefficient));
        }

        return result;
    }




}
