package com.itzroma.lab4;

import com.itzroma.lab4.gui.Gui;
import com.itzroma.lab4.math.MathEquation;
import com.itzroma.lab4.math.solution.AbstractSolutionMethod;
import com.itzroma.lab4.math.solution.ChordSolutionMethod;
import com.itzroma.lab4.math.solution.HalfDivisionSolutionMethod;
import com.itzroma.lab4.math.solution.SolutionMethod;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        new Gui(registerMathEquations(), registerSolutionMethods());
    }

    private static List<MathEquation> registerMathEquations() {
        return Stream.of(
                new MathEquation(
                        "x^3 - x + 1 = 0",
                        x -> Math.pow(x, 3) - x + 1,
                        x -> 3 * Math.pow(x, 2) - 1,
                        x -> 6 * x
                ),
                new MathEquation(
                        "x - cos(x) = 0",
                        x -> x - Math.cos(x),
                        x -> 1 + Math.sin(x),
                        Math::cos
                ),
                new MathEquation(
                        "ln(x) + (x + 1)^3 = 0",
                        x -> Math.log(x) + Math.pow((x + 1), 3),
                        x -> 1 / x + 3 * Math.pow(x, 2) + 6 * x + 3,
                        x -> -(1 / Math.pow(x, 2)) + 6 * x + 6
                )
        ).distinct().toList();
    }

    private static List<Class<? extends AbstractSolutionMethod>> registerSolutionMethods() {
        SolutionMethod.register(
                HalfDivisionSolutionMethod.class,
                ChordSolutionMethod.class
        );
        return SolutionMethod.ALL;
    }
}
