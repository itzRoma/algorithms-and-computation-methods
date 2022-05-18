package com.itzroma.acm.labs.lab4;

import com.itzroma.acm.labs.lab4.math.MathEquation;
import com.itzroma.acm.labs.lab4.math.solution.*;

public class Test {
    public static void main(String[] args) {
        MathEquation test = new MathEquation(
                "sin(x) = 0",
                Math::sin,
                Math::cos,
                x -> -Math.sin(x)
        );
        MathEquation var1 = new MathEquation(
                "x^3 - x + 1 = 0",
                x -> Math.pow(x, 3) - x + 1,
                x -> 3 * Math.pow(x, 2) - 1,
                x -> 6 * x
        );
        MathEquation var19 = new MathEquation(
                "x - cos(x)",
                x -> x - Math.cos(x),
                x -> 1 + Math.sin(x),
                Math::cos
        );
        MathEquation var26 = new MathEquation(
                "ln(x) + (x + 1)^3 = 0",
                x -> Math.log(x) + Math.pow((x + 1), 3),
                x -> 1 / x + 3 * Math.pow(x, 2) + 6 * x + 3,
                x -> -(1 / Math.pow(x, 2)) + 6 * x + 6
        );

        final double a = -2;
        final double b = 1;
        final double e = 0.001;

        AbstractSolutionMethod halfDivisionTest = new HalfDivisionSolutionMethod(test, a, b, e);
        AbstractSolutionMethod chordTest = new ChordSolutionMethod(test, a, b, e);

        AbstractSolutionMethod halfDivision1 = new HalfDivisionSolutionMethod(var1, a, b, e);
        AbstractSolutionMethod chord1 = new ChordSolutionMethod(var1, a, b, e);

        AbstractSolutionMethod halfDivision19 = new HalfDivisionSolutionMethod(var19, a, b, e);
        AbstractSolutionMethod chord19 = new ChordSolutionMethod(var19, a, b, e);

        AbstractSolutionMethod halfDivision26 = new HalfDivisionSolutionMethod(var26, a, b, e);
        AbstractSolutionMethod chord26 = new ChordSolutionMethod(var26, a, b, e);

        halfDivisionTest.showSolution();
        chordTest.showSolution();

        halfDivision1.showSolution();
        chord1.showSolution();

        halfDivision19.showSolution();
        chord19.showSolution();

        halfDivision26.showSolution();
        chord26.showSolution();
    }
}
