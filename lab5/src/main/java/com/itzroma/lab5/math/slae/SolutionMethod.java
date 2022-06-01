package com.itzroma.lab5.math.slae;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public interface SolutionMethod {
    double[] solve();

    @SuppressWarnings("unchecked")
    static SolutionMethod newInstance(String className, double[][] matrix) {
        try {
            Constructor<? extends AbstractSolutionMethod> constructor =
                    (Constructor<? extends AbstractSolutionMethod>) Class.forName(className)
                            .getDeclaredConstructor(double[][].class);

            return constructor.newInstance((Object) matrix);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 ClassNotFoundException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }
}
