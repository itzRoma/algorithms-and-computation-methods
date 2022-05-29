package com.itzroma.lab4.math;

import java.util.Objects;
import java.util.function.DoubleFunction;

public record MathEquation(
        String stringExpression,
        DoubleFunction<Double> javaExpression,
        DoubleFunction<Double> firstDerivative,
        DoubleFunction<Double> secondDerivative
) {
    public double valueAt(double x) {
        return javaExpression.apply(x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathEquation that = (MathEquation) o;
        return Objects.equals(stringExpression, that.stringExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringExpression);
    }
}
