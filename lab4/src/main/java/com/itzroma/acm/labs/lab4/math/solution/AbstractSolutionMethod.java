package com.itzroma.acm.labs.lab4.math.solution;

import com.itzroma.acm.labs.lab4.math.MathEquation;
import com.itzroma.acm.labs.lab4.math.util.Range;

import java.util.List;

public abstract class AbstractSolutionMethod implements SolutionMethod {
    protected final MathEquation equation;
    protected final double limitA;
    protected final double limitB;
    protected final double accuracy;

    protected AbstractSolutionMethod(MathEquation equation, double limitA, double limitB, double accuracy) {
        if (limitA >= limitB) throw new IllegalArgumentException("Limit B should be greater then limit A!");
        if (accuracy <= 0) throw new IllegalArgumentException("Accuracy must be greater then 0!");

        this.equation = equation;
        this.limitA = limitA;
        this.limitB = limitB;
        this.accuracy = accuracy;
    }

    protected void check() {
        if (equation.valueAt(limitA) * equation.valueAt(limitB) > 0)
            throw new IllegalStateException("Condition 'f(a) * f(b) <= 0' not satisfied!");
    }

    public void showSolution() {
        System.out.printf("%nEquation: %s%n", equation.stringExpression());
        System.out.printf("Method: %s%n", this.getClass().getSimpleName());

        List<Range> ranges = findRangesWithAnswers(equation);
        System.out.println("Diapasons with answers: " + ranges);

        System.out.printf("Roots in [%f; %f]:%n", limitA, limitB);

        boolean answersExist = false;
        for (Range range : ranges) {
            double x0 = SolutionMethod.newInstance(
                    this.getClass().getName(),
                    equation,
                    range.limitA(),
                    range.limitB(),
                    accuracy
            ).solve();

            if (x0 >= limitA && x0 <= limitB) {
                answersExist = true;
                System.out.printf("x = %f; f(x) = %f%n", x0, equation.valueAt(x0));
            }
        }

        if (!answersExist) {
            System.out.println("There are not roots in given range!");
        }
    }
}
