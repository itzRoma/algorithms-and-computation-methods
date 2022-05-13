package com.itzroma.acm.labs.lab3.math.interpolation;

import com.itzroma.acm.labs.lab3.math.MathFunction;

public class LagrangeInterpolationFunction extends AbstractInterpolationFunction {
    public LagrangeInterpolationFunction(MathFunction mathFunction, double limitA, double limitB, int nodesAmount) {
        super(mathFunction, limitA, limitB, nodesAmount);
    }

    @Override
    public double interpolate(double xx) {
        double result = 0;
        for (int i = 0; i < xNodes.length; i++) {
            double temp = 1;
            for (int j = 0; j < i; j++) {
                temp = temp * (xx - xNodes[j]) / (xNodes[i] - xNodes[j]);
            }
            for (int j = i + 1; j < xNodes.length; j++) {
                temp = temp * (xx - xNodes[j]) / (xNodes[i] - xNodes[j]);
            }
            result = result + temp * yNodes[i];
        }
        return result;
    }

    @Override
    public double error(double xx) {
        final InterpolationFunction that = new LagrangeInterpolationFunction(mathFunction, limitA, limitB, nodesAmount + 1);
        return that.interpolate(xx) - interpolate(xx);
    }

    @Override
    public double errorOfError(double xx) {
        return new LagrangeInterpolationFunction(mathFunction, limitA, limitB, nodesAmount + 1).error(xx);
    }

    @Override
    public double errorBlur(double xx) {
        return errorOfError(xx) - error(xx);
    }
}
