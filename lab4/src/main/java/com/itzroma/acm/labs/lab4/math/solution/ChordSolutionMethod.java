package com.itzroma.acm.labs.lab4.math.solution;

import com.itzroma.acm.labs.lab4.math.MathEquation;

public class ChordSolutionMethod extends AbstractSolutionMethod {
    public ChordSolutionMethod(MathEquation equation, double limitA, double limitB, double accuracy) {
        super(equation, limitA, limitB, accuracy);
    }

    @Override
    public double solve() {
        double x;
        double a = limitA;
        double b = limitB;

        while (true) {
            x = a - (equation.valueAt(a) * (b - a)) / (equation.valueAt(b) - equation.valueAt(a));

            if (Math.abs(x - a) < accuracy) {
                return x;
            } else {
                a = x;
            }
        }
    }
}
