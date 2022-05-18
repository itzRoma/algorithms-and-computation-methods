package com.itzroma.acm.labs.lab4;

import com.itzroma.acm.labs.lab4.math.MathEquation;
import com.itzroma.acm.labs.lab4.math.solution.*;

public class Test {
    public static void main(String[] args) {
        MathEquation t14v1 = new MathEquation(
                "x^3 + 3x^2 - 3 = 0",
                x -> Math.pow(x, 3) + 3 * Math.pow(x, 2) - 3,
                null, null
        );
        MathEquation t14v2 = new MathEquation(
                "x^3 + 3x^2 - 24x + 1 = 0",
                x -> Math.pow(x, 3) + 3 * Math.pow(x, 2) - 24 * x + 1,
                null, null
        );
        MathEquation t14v3 = new MathEquation(
                "x^3 - 6x^2 + 9x - 3 = 0",
                x -> Math.pow(x, 3) - 6 * Math.pow(x, 2) + 9 * x - 3,
                null, null
        );
        MathEquation t14v4 = new MathEquation(
                "x^3 - x - 3 = 0",
                x -> Math.pow(x, 3) - x - 3,
                null, null
        );
        MathEquation t14v5 = new MathEquation(
                "x - cos(x) = 0",
                x -> x - Math.cos(x),
                null, null
        );

        final double a = -10;
        final double b = 10;
        final double e = 0.001;

        AbstractSolutionMethod t14v1a = new HalfDivisionSolutionMethod(t14v1, a, b, e);
        AbstractSolutionMethod t14v2a = new HalfDivisionSolutionMethod(t14v2, a, b, e);
        AbstractSolutionMethod t14v3a = new HalfDivisionSolutionMethod(t14v3, a, b, e);
        AbstractSolutionMethod t14v4a = new HalfDivisionSolutionMethod(t14v4, a, b, e);
        AbstractSolutionMethod t14v5a = new HalfDivisionSolutionMethod(t14v5, a, b, e);

        t14v1a.showSolution(); // correct
        t14v2a.showSolution(); // correct
        t14v3a.showSolution(); // correct
        t14v4a.showSolution(); // correct
        t14v5a.showSolution(); // correct
    }
}
