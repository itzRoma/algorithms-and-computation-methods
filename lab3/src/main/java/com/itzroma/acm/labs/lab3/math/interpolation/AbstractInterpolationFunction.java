package com.itzroma.acm.labs.lab3.math.interpolation;

import com.itzroma.acm.labs.lab3.math.MathFunction;

public abstract class AbstractInterpolationFunction implements InterpolationFunction {
    private static final int ACCURACY = 10;

    protected final MathFunction mathFunction;
    protected final double limitA;
    protected final double limitB;
    protected final int nodesAmount;

    protected final double[] xNodes;
    protected final double[] yNodes;

    protected final double[] interpolatedXNodes;
    protected final double[] interpolatedYNodes;

    protected AbstractInterpolationFunction(MathFunction mathFunction, double limitA, double limitB, int nodesAmount) {
        if (nodesAmount < 1) throw new IllegalArgumentException("Amount of nodes cannot be less then 1!");
        if (limitA >= limitB) throw new IllegalArgumentException("Limit B should be greater then limit A!");

        this.mathFunction = mathFunction;
        this.limitA = limitA;
        this.limitB = limitB;
        this.nodesAmount = nodesAmount;

        xNodes = new double[this.nodesAmount];
        yNodes = new double[this.nodesAmount];
        final double stepH = (this.limitB - this.limitA) / (this.nodesAmount - 1);
        for (int i = 0; i < xNodes.length; i++) {
            double x = this.limitA + stepH * i;
            xNodes[i] = x;
            yNodes[i] = this.mathFunction.evaluate(x);
        }

        final int interpolatedNodesAmount = this.nodesAmount * ACCURACY;
        interpolatedXNodes = new double[interpolatedNodesAmount];
        interpolatedYNodes = new double[interpolatedNodesAmount];
        final double stepHInterpolated = (this.limitB - this.limitA) / (interpolatedNodesAmount - 1);
        for (int i = 0; i < interpolatedXNodes.length; i++) {
            final double x = this.limitA + stepHInterpolated * i;
            interpolatedXNodes[i] = x;
            interpolatedYNodes[i] = interpolate(x);
        }
    }

    @Override
    public double error(double xx) {
        final InterpolationFunction that = InterpolationFunction.newInstance(
                this.getClass().getName(),
                mathFunction,
                limitA, limitB, nodesAmount + 1
        );
        return that.interpolate(xx) - interpolate(xx);
    }

    @Override
    public double errorOfError(double xx) {
        final InterpolationFunction that = InterpolationFunction.newInstance(
                this.getClass().getName(),
                mathFunction,
                limitA, limitB, nodesAmount + 1
        );
        return that.error(xx);
    }

    @Override
    public double errorBlur(double xx) {
        return errorOfError(xx) - error(xx);
    }

    @Override
    public double[] getXNodes() {
        return xNodes;
    }

    @Override
    public double[] getYNodes() {
        return yNodes;
    }

    @Override
    public double[] getInterpolatedXNodes() {
        return interpolatedXNodes;
    }

    @Override
    public double[] getInterpolatedYNodes() {
        return interpolatedYNodes;
    }
}
