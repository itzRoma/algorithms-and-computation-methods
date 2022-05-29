package com.itzroma.lab3.math.interpolation;

import com.itzroma.lab3.math.MathFunction;

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
}
