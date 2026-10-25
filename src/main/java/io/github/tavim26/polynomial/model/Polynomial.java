package io.github.tavim26.polynomial.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Polynomial {

    private Map<Integer, Monomial> monomials = new HashMap<>();

    public void addMonomial(Monomial monomial) {
        Monomial existing = monomials.get(monomial.getDegree());

        if (existing != null) {
            existing.setCoefficient(existing.getCoefficient() + monomial.getCoefficient());
        } else {
            monomials.put(monomial.getDegree(),
                    new Monomial(monomial.getDegree(), monomial.getCoefficient()));
        }
    }

    public Map<Integer, Monomial> getMonomials() {
        return monomials;
    }

    public void setMonomials(Map<Integer, Monomial> monomials) {
        this.monomials = monomials;
    }

    @Override
    public String toString() {
        Map<Integer, Monomial> sortedByDegree = new TreeMap<>(Collections.reverseOrder());
        sortedByDegree.putAll(monomials);

        StringBuilder result = new StringBuilder();
        for (Monomial monomial : sortedByDegree.values()) {
            if (monomial.getCoefficient() == 0) {
                continue;
            }
            if (monomial.getCoefficient() > 0 && !result.isEmpty()) {
                result.append('+');
            }
            result.append(monomial);
        }

        return result.isEmpty() ? "0" : result.toString();
    }
}