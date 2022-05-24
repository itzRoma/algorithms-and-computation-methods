package com.itzroma.acm.labs.lab3.math.interpolation;

import com.itzroma.acm.labs.lab3.math.MathFunction;

public class AitkenInterpolationFunction extends AbstractInterpolationFunction {
    public AitkenInterpolationFunction(MathFunction mathFunction, double limitA, double limitB, int nodesAmount) {
        super(mathFunction, limitA, limitB, nodesAmount);
    }

    @Override
    public double interpolate(double xx) {
        final int n = xNodes.length;
        final double[][] A = new double[n][n];

        int k1 = 0;

        for (int i = 0; i < n - 2; i++) {
            if (xx >= xNodes[i] && xx < xNodes[i + 1]) {
                k1 = i;
                break;
            }
        }

        int k = n;
        double res = 0;
        double res1 = 999;
        double res2;

        for (int j = 0; j < n - 1; j++) {
            for (int i = 0; i < k - 1; i++) {
                if (j == 0) {
                    A[i][j] = (((xx - xNodes[i]) * yNodes[i + 1]) - ((xx - xNodes[i + 1]) * yNodes[i])) / (xNodes[i + 1] - xNodes[i]);
                } else {
                    A[i][j] = (((xx - xNodes[i]) * A[i + 1][j - 1]) - ((xx - xNodes[i + j + 1]) * A[i][j - 1])) / (xNodes[i + j + 1] - xNodes[i]);
                }
            }
            k--;
        }

        for (int j = 0; j < n - 1; j++) {
            res2 = Math.abs(A[k1][j + 1] - A[k1][j]);
            if (res2 < res1) {
                res1 = res2;
            } else {
                res = A[k1][j];
                break;
            }
        }

        return res;
    }
}
