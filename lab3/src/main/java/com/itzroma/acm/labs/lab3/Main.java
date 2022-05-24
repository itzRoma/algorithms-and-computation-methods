package com.itzroma.acm.labs.lab3;

import com.itzroma.acm.labs.lab3.gui.Gui;
import com.itzroma.acm.labs.lab3.math.MathFunction;
import com.itzroma.acm.labs.lab3.math.interpolation.*;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        new Gui(registerMathFunctions(), registerInterpolationFunctions());
    }

    private static List<MathFunction> registerMathFunctions() {
        return Stream.of(
                new MathFunction("sin(x)", Math::sin), // for testing
                new MathFunction("sin(x^2)", x -> Math.sin(Math.pow(x, 2))) // variant 1
                // insert your new MathFunction here
        ).distinct().toList();
    }

    private static List<Class<? extends AbstractInterpolationFunction>> registerInterpolationFunctions() {
        InterpolationFunction.register(
                LagrangeInterpolationFunction.class, // lagrange
                NewtonInterpolationFunction.class, // newton
                AitkenInterpolationFunction.class // aitken
        );
        return InterpolationFunction.ALL;
    }
}
