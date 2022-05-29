package com.itzroma.lab4.math.solution;

import com.itzroma.lab4.math.MathEquation;

public class ChordSolutionMethod extends AbstractSolutionMethod {
    public ChordSolutionMethod(MathEquation equation, double limitA, double limitB, double accuracy) {
        super(equation, limitA, limitB, accuracy);
    }

    @Override
    public double solve() {
        check();

        double a = limitA;
        double b = limitB;

        if (!(equation.firstDerivative().apply((b + a) / 2) * equation.secondDerivative().apply((b + a) / 2) > 0)) {
            final double temp = a;
            a = b;
            b = temp;
        }

        while (true) {
            double x = a - (equation.valueAt(a) * (b - a)) / (equation.valueAt(b) - equation.valueAt(a));

            if (Double.isNaN(x)) throw new ArithmeticException("Undefined value occurred!");

            if (Math.abs(x - a) < accuracy) return x;

            a = x;
        }
    }
}
