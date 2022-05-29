package com.itzroma.lab3.math;

import java.util.Objects;
import java.util.function.DoubleFunction;

public record MathFunction(String stringExpression, DoubleFunction<Double> javaExpression) {
    public double evaluate(double xNode) {
        return javaExpression.apply(xNode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathFunction that = (MathFunction) o;
        return Objects.equals(stringExpression, that.stringExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringExpression);
    }
}
