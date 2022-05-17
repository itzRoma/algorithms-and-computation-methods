package com.itzroma.acm.labs.lab4.math.solution;

import com.itzroma.acm.labs.lab4.math.MathEquation;

public class HalfDivisionSolutionMethod extends AbstractSolutionMethod {
    public HalfDivisionSolutionMethod(MathEquation equation, double limitA, double limitB, double accuracy) {
        super(equation, limitA, limitB, accuracy);
    }

    @Override
    public double solve() {
        double a = limitA;
        double b = limitB;

        while (Math.abs(b - a) > accuracy) {
            double c = (b + a) / 2;

            if (equation.valueAt(c) == 0)
                return c;

            if (equation.valueAt(a) * equation.valueAt(c) <= 0)
                b = c;
            else
                a = c;
        }

        return (b + a) / 2;
    }
}
