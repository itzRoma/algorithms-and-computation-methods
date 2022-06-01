package com.itzroma.lab5.math.slae;

public abstract class AbstractSolutionMethod implements SolutionMethod {
    protected final double[][] matrix;

    protected AbstractSolutionMethod(double[][] matrix) {
        this.matrix = matrix;
    }
}
