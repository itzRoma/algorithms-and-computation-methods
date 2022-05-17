package com.itzroma.acm.labs.lab4.gui;

import com.itzroma.acm.labs.lab4.gui.components.CustomLabel;
import com.itzroma.acm.labs.lab4.gui.components.NotEditableTextField;
import com.itzroma.acm.labs.lab4.math.MathEquation;
import com.itzroma.acm.labs.lab4.math.solution.AbstractSolutionMethod;
import com.itzroma.acm.labs.lab4.math.solution.SolutionMethod;
import com.itzroma.acm.labs.lab4.math.util.Range;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Gui extends JFrame {
    private static final String TITLE = "Лабораторна робота №4";
    private static final String THEME = "Розв’язання нелінійних рівнянь на комп’ютері";
    private static final String VARIANT = "Варіант №1";
    private static final String AUTHOR_GROUP = "Бондаренко Роман ІО-03";

    private final List<MathEquation> mathEquations;
    private final List<Class<? extends AbstractSolutionMethod>> solutionMethods;

    public Gui(
            List<MathEquation> mathEquations,
            List<Class<? extends AbstractSolutionMethod>> solutionMethods
    ) {
        this.mathEquations = mathEquations;
        this.solutionMethods = solutionMethods;

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (size.getWidth() / 2), (int) (size.getHeight() / 2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("%s – %s | %s - %s".formatted(TITLE, THEME, VARIANT, AUTHOR_GROUP));

        build();

        setVisible(true);
    }

    private String[] getNamesOfMathEquations() {
        return mathEquations.stream()
                .map(MathEquation::stringExpression)
                .toArray(String[]::new);
    }

    private String[] getNamesOfSolutionMethods() {
        return solutionMethods.stream()
                .map(Class::getSimpleName)
                .toArray(String[]::new);
    }

    private void addToPanel(JPanel panel, JComponent... components) {
        Arrays.stream(components).forEach(panel::add);
    }

    private void build() {
        final JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.white);

        final JPanel inputsAndResultsPanel = new JPanel(new GridLayout(12, 2));
        inputsAndResultsPanel.setBackground(Color.white);

        final JComboBox<String> mathEquationsComboBox = new JComboBox<>(getNamesOfMathEquations());
        final JComboBox<String> solutionMethodsComboBox = new JComboBox<>(getNamesOfSolutionMethods());
        final JTextField limitATextField = new JTextField(10);
        final JTextField limitBTextField = new JTextField(10);
        final JTextField accuracyTextField = new JTextField(10);

        final JButton clearButton = new JButton("Очистити");
        final JButton calculateButton = new JButton("Обчислити");

        final NotEditableTextField rootsTextField = new NotEditableTextField(10);
        final NotEditableTextField rangesTextField = new NotEditableTextField(10);

        final XYSeriesCollection seriesCollection = new XYSeriesCollection();
        final XYSeries series = new XYSeries("Графік рівняння");

        clearButton.addActionListener(ae -> {
            limitATextField.setText(null);
            limitBTextField.setText(null);
            accuracyTextField.setText(null);

            rootsTextField.setText(null);
            rangesTextField.setText(null);
            series.clear();
        });

        calculateButton.addActionListener(ae -> {
            series.clear();
            rootsTextField.setText(null);
            rangesTextField.setText(null);

            final MathEquation selectedMathEquation =
                    mathEquations.get(mathEquationsComboBox.getSelectedIndex());

            final Class<? extends AbstractSolutionMethod> selectedSolutionMethod =
                    solutionMethods.get(solutionMethodsComboBox.getSelectedIndex());

            final double limitA = Double.parseDouble(limitATextField.getText());
            final double limitB = Double.parseDouble(limitBTextField.getText());
            final double accuracy = Double.parseDouble(accuracyTextField.getText());

            final SolutionMethod solutionMethod = SolutionMethod.newInstance(
                    selectedSolutionMethod.getName(),
                    selectedMathEquation,
                    limitA, limitB, accuracy
            );

            for (double i = limitA; i <= limitB; i += 0.01) {
                series.add(i, selectedMathEquation.valueAt(i));
            }

            final List<Range> ranges = solutionMethod.findRangesWithAnswers(selectedMathEquation);

            boolean rootsExist = false;
            final StringBuilder roots = new StringBuilder();
            for (Range range : ranges) {
                double x0 = SolutionMethod.newInstance(
                        selectedSolutionMethod.getName(),
                        selectedMathEquation,
                        range.limitA(), range.limitB(), accuracy
                ).solve();

                if (x0 >= limitA && x0 <= limitB) {
                    rootsExist = true;
                    roots.append("x = %f; f(x) = %f; ".formatted(x0, selectedMathEquation.valueAt(x0)));
                }
            }

            if (!rootsExist) {
                roots.append("There are not roots in [%f; %f]".formatted(limitA, limitB));
            }

            rootsTextField.setText(roots.toString());
            rangesTextField.setText(ranges.toString());
        });

        seriesCollection.addSeries(series);

        final ChartPanel chartPanel = new ChartPanel(ChartFactory.createXYLineChart(
                "Графік рівняння",
                "X", "Y", seriesCollection,
                PlotOrientation.VERTICAL,
                true, true, false));
        chartPanel.setPreferredSize(new java.awt.Dimension(getWidth() / 2, getHeight()));

        addToPanel(
                inputsAndResultsPanel,
                // ===================
                new CustomLabel(""), new CustomLabel(""),
                new CustomLabel("Рівняння"), mathEquationsComboBox,
                new CustomLabel("Метод"), solutionMethodsComboBox,
                new CustomLabel("Границя A"), limitATextField,
                new CustomLabel("Границя B"), limitBTextField,
                new CustomLabel("Точність"), accuracyTextField,
                new CustomLabel(""), new CustomLabel(""),
                clearButton, calculateButton,
                new CustomLabel(""), new CustomLabel(""),
                new CustomLabel("Результат"), rootsTextField,
                new CustomLabel("Корені містяться в"), rangesTextField
        );

        mainPanel.add(inputsAndResultsPanel);
        mainPanel.add(chartPanel);

        add(mainPanel);
    }
}
