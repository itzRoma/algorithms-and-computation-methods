package com.itzroma.lab4.math.solution;

import com.itzroma.lab4.math.MathEquation;

public class IterationSolutionMethod extends AbstractSolutionMethod {
    private final double intermediateRoot;

    public IterationSolutionMethod(MathEquation equation, double limitA, double limitB, double accuracy) {
        super(equation, limitA, limitB, accuracy);
        intermediateRoot = new HalfDivisionSolutionMethod(
                new MathEquation(null, equation.secondDerivative(), null, null),
                limitA, limitB, accuracy
        ).solve();
    }

    private double findStartX() {
        double a = equation.firstDerivative().apply(limitA);
        double b = equation.firstDerivative().apply(limitB);
        double c = equation.firstDerivative().apply(intermediateRoot);
        return a >= b && a >= c ? limitA : b >= a && b >= c ? limitB : intermediateRoot;
    }

    private double findLambda(double x) {
        return 1. / equation.firstDerivative().apply(x);
    }

    private double solve(double lambda, double x) {
        double x0;

        check();

        do {
            x0 = x;
            x = x - lambda * equation.valueAt(x);
        } while (Math.abs(x - x0) >= accuracy);
        return x0;
    }

    @Override
    public double solve() {
        double x = findStartX();
        return solve(findLambda(x), x);
    }
}
