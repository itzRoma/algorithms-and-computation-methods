package com.itzroma.acm.labs.lab3.math.interpolation;

import com.itzroma.acm.labs.lab3.math.MathFunction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public interface InterpolationFunction {
    List<Class<? extends AbstractInterpolationFunction>> ALL = new ArrayList<>();

    double interpolate(double xx);
    double error(double xx);
    double errorOfError(double xx);
    double errorBlur(double xx);

    double[] getXNodes();
    double[] getYNodes();
    double[] getInterpolatedXNodes();
    double[] getInterpolatedYNodes();

    @SafeVarargs
    static void register(Class<? extends AbstractInterpolationFunction>... interpolationFunctions) {
        ALL.addAll(Stream.of(interpolationFunctions)
                .distinct()
                .toList());
    }

    @SuppressWarnings("unchecked")
    static InterpolationFunction newInstance(
            String className,
            MathFunction mathFunction,
            double limitA,
            double limitB,
            int nodesAmount
    ) {
        try {
            Constructor<? extends AbstractInterpolationFunction> constructor =
                    (Constructor<? extends AbstractInterpolationFunction>) Class.forName(className)
                            .getDeclaredConstructor(MathFunction.class, double.class, double.class, int.class);

            return constructor.newInstance(mathFunction, limitA, limitB, nodesAmount);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 ClassNotFoundException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }
}
