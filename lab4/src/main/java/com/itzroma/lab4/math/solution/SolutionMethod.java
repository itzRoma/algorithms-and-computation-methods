package com.itzroma.lab4.math.solution;

import com.itzroma.lab4.math.MathEquation;
import com.itzroma.lab4.math.util.Range;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public interface SolutionMethod {
    List<Class<? extends AbstractSolutionMethod>> ALL = new ArrayList<>();

    double solve();

    default List<Range> findRangesWithAnswers(MathEquation equation) {
        final int limitA = -100;
        final int limitB = 100;
        final int step = 1;

        final int n = (limitB - limitA) / step;

        int start = limitA;
        int end = limitA + step;
        final List<Range> ranges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (equation.valueAt(start) * equation.valueAt(end) <= 0) {
                ranges.add(new Range(start, end));
            }
            start = end;
            end += step;
        }

        return ranges;
    }

    @SafeVarargs
    static void register(Class<? extends AbstractSolutionMethod>... solutionMethods) {
        ALL.addAll(Stream.of(solutionMethods)
                .distinct()
                .toList());
    }

    @SuppressWarnings("unchecked")
    static SolutionMethod newInstance(
            String className,
            MathEquation mathFunction,
            double limitA,
            double limitB,
            double accuracy
    ) {
        try {
            Constructor<? extends AbstractSolutionMethod> constructor =
                    (Constructor<? extends AbstractSolutionMethod>) Class.forName(className)
                            .getDeclaredConstructor(MathEquation.class, double.class, double.class, double.class);

            return constructor.newInstance(mathFunction, limitA, limitB, accuracy);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 ClassNotFoundException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }
}
