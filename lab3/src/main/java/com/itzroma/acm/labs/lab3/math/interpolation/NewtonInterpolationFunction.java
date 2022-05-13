package com.itzroma.acm.labs.lab3.math.interpolation;

import com.itzroma.acm.labs.lab3.math.MathFunction;

public class NewtonInterpolationFunction extends AbstractInterpolationFunction {
    public NewtonInterpolationFunction(MathFunction mathFunction, double limitA, double limitB, int nodesAmount) {
        super(mathFunction, limitA, limitB, nodesAmount);
    }

    @Override
    public double interpolate(double xx) {
        double result = yNodes[0];
        double buf = 1;
        for (int k = 1; k < xNodes.length; k++) {
            double tempSum = 0;
            for (int i = 0; i <= k; i++) {
                double temp = 1;
                for (int j = 0; j < i; j++)
                    temp = temp * (xNodes[i] - xNodes[j]);
                for (int j = i + 1; j <= k; j++)
                    temp = temp * (xNodes[i] - xNodes[j]);
                temp = yNodes[i] / temp;
                tempSum += temp;
            }
            buf = buf * (xx - xNodes[k - 1]);
            result = result + tempSum * buf;
        }
        return result;
    }

    @Override
    public double error(double xx) {
        final InterpolationFunction that = new NewtonInterpolationFunction(mathFunction, limitA, limitB, nodesAmount + 1);
        return that.interpolate(xx) - interpolate(xx);
    }

    @Override
    public double errorOfError(double xx) {
        return new NewtonInterpolationFunction(mathFunction, limitA, limitB, nodesAmount + 1).error(xx);
    }

    @Override
    public double errorBlur(double xx) {
        return errorOfError(xx) - error(xx);
    }
}
