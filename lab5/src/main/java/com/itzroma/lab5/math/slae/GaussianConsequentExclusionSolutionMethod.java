package com.itzroma.lab5.math.slae;

public class GaussianConsequentExclusionSolutionMethod extends AbstractSolutionMethod {
    public GaussianConsequentExclusionSolutionMethod(double[][] matrix) {
        super(matrix);
    }

    @Override
    public double[] solve() {
        int n = matrix.length;

        double[][] matrixOriginal = new double[matrix.length][matrix[0].length];
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                System.arraycopy(matrix[i], 0, matrixOriginal[i], 0, n + 1);
            }
        }

        double[][] matrixClone = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrixOriginal[i], 0, matrixClone[i], 0, n + 1);
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n + 1; i++) {
                matrixClone[k][i] = matrixClone[k][i] / matrixOriginal[k][k];
            }

            for (int i = k + 1; i < n; i++) {
                double coefficient = matrixClone[i][k] / matrixClone[k][k];
                for (int j = 0; j < n + 1; j++) {
                    matrixClone[i][j] = matrixClone[i][j] - matrixClone[k][j] * coefficient;
                }
            }

            for (int i = 0; i < n; i++) {
                System.arraycopy(matrixClone[i], 0, matrixOriginal[i], 0, n + 1);
            }
        }

        for (int k = n - 1; k > -1; k--) {
            for (int i = n; i > -1; i--) {
                matrixClone[k][i] = matrixClone[k][i] / matrixOriginal[k][k];
            }

            for (int i = k - 1; i > -1; i--) {
                double coefficient = matrixClone[i][k] / matrixClone[k][k];
                for (int j = n; j > -1; j--) {
                    matrixClone[i][j] = matrixClone[i][j] - matrixClone[k][j] * coefficient;
                }
            }
        }

        double[] answers = new double[n];
        for (int i = 0; i < n; i++) {
            answers[i] = matrixClone[i][n];
        }

        return answers;
    }
}
