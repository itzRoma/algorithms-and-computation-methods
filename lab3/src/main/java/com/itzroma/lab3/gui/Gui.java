package com.itzroma.lab3.gui;

import com.itzroma.lab3.math.MathFunction;
import com.itzroma.lab3.math.interpolation.AbstractInterpolationFunction;
import com.itzroma.lab3.math.interpolation.InterpolationFunction;
import com.itzroma.util.gui.CustomLabel;
import com.itzroma.util.gui.NonEditableTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Gui extends JFrame {
    private static final String TITLE = "Лабораторна робота №3";
    private static final String THEME = "Інтерполяція функцій";
    private static final String VARIANT = "Варіант №1";
    private static final String AUTHOR_GROUP = "Бондаренко Роман ІО-03";

    private final List<MathFunction> mathFunctions;
    private final List<Class<? extends AbstractInterpolationFunction>> interpolationFunctions;

    public Gui(
            List<MathFunction> mathFunctions,
            List<Class<? extends AbstractInterpolationFunction>> interpolationFunctions
    ) {
        this.mathFunctions = mathFunctions;
        this.interpolationFunctions = interpolationFunctions;

        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("%s – %s | %s - %s".formatted(TITLE, THEME, VARIANT, AUTHOR_GROUP));

        build();

        setVisible(true);
    }

    private String[] getNamesOfMathFunctions() {
        return mathFunctions.stream()
                .map(MathFunction::stringExpression)
                .toArray(String[]::new);
    }

    private String[] getNamesOfInterpolationFunctions() {
        return interpolationFunctions.stream()
                .map(Class::getSimpleName)
                .toArray(String[]::new);
    }

    private void addToPanel(JPanel panel, JComponent... components) {
        Arrays.stream(components).forEach(panel::add);
    }

    private void clear(JTextField... fields) {
        Arrays.stream(fields).forEach(field -> field.setText(null));
    }

    private void build() {
        final JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.setBackground(Color.white);

        final JPanel inputsAndResultsPanel = new JPanel(new GridLayout(13, 2));
        inputsAndResultsPanel.setBackground(Color.white);

        final JPanel errorEstimationPanel = new JPanel(new GridLayout(1, 1));
        errorEstimationPanel.setBackground(Color.white);

        final JComboBox<String> mathFunctionsComboBox = new JComboBox<>(getNamesOfMathFunctions());
        final JComboBox<String> interpolationTypesComboBox = new JComboBox<>(getNamesOfInterpolationFunctions());

        final JTextField limitATextField = new JTextField(10);
        final JTextField limitBTextField = new JTextField(10);
        final JTextField nodesAmountTextField = new JTextField(10);
        final JTextField xTextField = new JTextField(10);

        final JButton clearButton = new JButton("Очистити");
        final JButton calculateButton = new JButton("Обчислити");

        final NonEditableTextField yField = new NonEditableTextField(10);
        final NonEditableTextField errorField = new NonEditableTextField(10);
        final NonEditableTextField errorOfErrorField = new NonEditableTextField(10);
        final NonEditableTextField errorBlurField = new NonEditableTextField(10);

        final JTable errorEstimationTable = new JTable(13, 4);
        final TableModel model = errorEstimationTable.getModel();
        model.setValueAt("n", 0, 0);
        model.setValueAt("Оцінка похибки", 0, 1);
        model.setValueAt("Точна похибка", 0, 2);
        model.setValueAt("Коефіцієнт уточнення", 0, 3);

        final XYSeriesCollection functionsSeriesCollection = new XYSeriesCollection();
        final XYSeriesCollection errorSeriesCollection = new XYSeriesCollection();
        final XYSeries inputFunctionSeries = new XYSeries("Вихідна функція");
        final XYSeries interpolatedFunctionSeries = new XYSeries("Інтерпольована функція");
        final XYSeries errorSeries = new XYSeries("Похибка");

        clearButton.addActionListener(ae -> {
            clear(
                    limitATextField, limitBTextField, nodesAmountTextField, xTextField,
                    yField, errorField, errorOfErrorField, errorBlurField
            );

            inputFunctionSeries.clear();
            interpolatedFunctionSeries.clear();
            errorSeries.clear();

            for (int i = 1; i <= 12; i++) for (int j = 0; j <= 3; j++) model.setValueAt(null, i, j);
        });

        calculateButton.addActionListener(ae -> {
            inputFunctionSeries.clear();
            interpolatedFunctionSeries.clear();
            errorSeries.clear();

            for (int i = 1; i <= 12; i++) for (int j = 0; j <= 3; j++) model.setValueAt(null, i, j);

            final MathFunction selectedMathFunction =
                    mathFunctions.get(mathFunctionsComboBox.getSelectedIndex());

            final Class<? extends AbstractInterpolationFunction> selectedInterpolationType =
                    interpolationFunctions.get(interpolationTypesComboBox.getSelectedIndex());

            final double limitA = Double.parseDouble(limitATextField.getText());
            final double limitB = Double.parseDouble(limitBTextField.getText());
            final int nodesAmount = Integer.parseInt(nodesAmountTextField.getText());
            final double xx = Double.parseDouble(xTextField.getText());

            final InterpolationFunction interpolationFunction = InterpolationFunction.newInstance(
                    selectedInterpolationType.getName(),
                    selectedMathFunction,
                    limitA, limitB, nodesAmount
            );

            final double[] xNodes = interpolationFunction.getXNodes();
            final double[] yNodes = interpolationFunction.getYNodes();
            for (int i = 0; i < xNodes.length; i++) {
                inputFunctionSeries.add(xNodes[i], yNodes[i]);
            }

            final double[] interpolatedXNodes = interpolationFunction.getInterpolatedXNodes();
            final double[] interpolatedYNodes = interpolationFunction.getInterpolatedYNodes();
            for (int i = 0; i < interpolatedXNodes.length; i++) {
                interpolatedFunctionSeries.add(interpolatedXNodes[i], interpolatedYNodes[i]);
                errorSeries.add(interpolatedXNodes[i], interpolationFunction.error(interpolatedXNodes[i]));
            }

            yField.setText(String.valueOf(interpolationFunction.interpolate(xx)));
            errorField.setText(String.valueOf(interpolationFunction.error(xx)));
            errorOfErrorField.setText(String.valueOf(interpolationFunction.errorOfError(xx)));
            errorBlurField.setText(String.valueOf(interpolationFunction.errorBlur(xx)));

            for (int n = 1; n <= xNodes.length; n++) {
                InterpolationFunction function = InterpolationFunction.newInstance(
                        selectedInterpolationType.getName(),
                        selectedMathFunction,
                        limitA, limitB, n + 1
                );

                double error = function.error(xx);
                double errorEstimation = function.interpolate(xx) - selectedMathFunction.evaluate(xx);
                double estimationCoefficient = 1 - errorEstimation / error;

                model.setValueAt(n, n, 0);
                model.setValueAt(error, n, 1);
                model.setValueAt(errorEstimation, n, 2);
                model.setValueAt(estimationCoefficient, n, 3);
            }
        });

        functionsSeriesCollection.addSeries(inputFunctionSeries);
        functionsSeriesCollection.addSeries(interpolatedFunctionSeries);
        errorSeriesCollection.addSeries(errorSeries);

        final ChartPanel functionsChart = new ChartPanel(ChartFactory.createXYLineChart(
                "Графіки вихідної та інтерпольованої функцій",
                "X", "Y", functionsSeriesCollection,
                PlotOrientation.VERTICAL,
                true, true, false));
        functionsChart.setPreferredSize(new java.awt.Dimension(300, 400));

        final ChartPanel errorChart = new ChartPanel(ChartFactory.createXYLineChart(
                "Похибка",
                "X", "Y", errorSeriesCollection,
                PlotOrientation.VERTICAL,
                true, true, false));
        errorChart.setPreferredSize(new java.awt.Dimension(300, 400));

        addToPanel(
                inputsAndResultsPanel,
                // ===================================================================
                new CustomLabel("Оберіть функцію"), mathFunctionsComboBox,
                new CustomLabel("Оберіть тип інтерполяції"), interpolationTypesComboBox,
                new CustomLabel("Границя A"), limitATextField,
                new CustomLabel("Границя B"), limitBTextField,
                new CustomLabel("Кількість точок"), nodesAmountTextField,
                new CustomLabel("x"), xTextField,

                new CustomLabel(""), new CustomLabel(""),
                clearButton, calculateButton,
                new CustomLabel(""), new CustomLabel(""),

                new CustomLabel("y(x)"), yField,
                new CustomLabel("Похибка"), errorField,
                new CustomLabel("Похибка похибки"), errorOfErrorField,
                new CustomLabel("Відносна розмитість похибки"), errorBlurField
        );

        errorEstimationPanel.add(errorEstimationTable, BorderLayout.SOUTH);
        errorEstimationPanel.setPreferredSize(new java.awt.Dimension(800, 800));

        addToPanel(
                mainPanel,
                // ========================
                inputsAndResultsPanel, functionsChart,
                errorEstimationPanel, errorChart
        );

        add(mainPanel);
    }
}
