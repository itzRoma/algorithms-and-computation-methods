package com.itzroma.lab5.gui;

import com.itzroma.lab5.math.slae.AbstractSolutionMethod;
import com.itzroma.lab5.math.slae.SolutionMethod;
import com.itzroma.util.gui.CustomLabel;
import com.itzroma.util.gui.NonEditableTextField;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Gui extends JFrame {
    private static final String TITLE = "Лабораторна робота №5";
    private static final String THEME = "Розв’язання систем лінійних алгебраїчних рівнянь";
    private static final String VARIANT = "Варіант №1";
    private static final String AUTHOR_GROUP = "Бондаренко Роман ІО-03";

    private final JTextField[][] inputXCoefficients;
    private final JTextField[] inputFreeMembers;
    private final Class<? extends AbstractSolutionMethod> solutionMethod;

    public Gui(int numberOfVariables, Class<? extends AbstractSolutionMethod> solutionMethod) {
        if (numberOfVariables < 2) throw new IllegalArgumentException("Provide at least 2 variables!");

        inputXCoefficients = new JTextField[numberOfVariables][numberOfVariables];
        inputFreeMembers = new JTextField[numberOfVariables];
        this.solutionMethod = solutionMethod;

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (size.getWidth() / 3), (int) (size.getHeight() / 3));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("%s – %s | %s - %s".formatted(TITLE, THEME, VARIANT, AUTHOR_GROUP));

        build();

        setVisible(true);
    }

    private void addToPanel(JPanel panel, JComponent... components) {
        Arrays.stream(components).forEach(panel::add);
    }

    private void build() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.setBackground(Color.white);

        JPanel inputPanel = new JPanel(new GridLayout(inputFreeMembers.length, 2 * inputXCoefficients.length + 1));
        inputPanel.setBackground(Color.white);

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 2, 20, 5));
        buttonsPanel.setBackground(Color.white);

        JButton calculateButton = new JButton("Обчислити");
        JButton clearButton = new JButton("Очистити");

        NonEditableTextField answers = new NonEditableTextField(1);
        answers.setFont(new Font("Calibri", Font.BOLD, 20));
        answers.setHorizontalAlignment(SwingConstants.CENTER);
        answers.setBackground(Color.white);

        for (int i = 0; i < inputFreeMembers.length; i++) {
            for (int j = 1; j < inputXCoefficients.length; j++) {
                JTextField xTextField = new JTextField(1);
                inputXCoefficients[i][j - 1] = xTextField;

                CustomLabel xLabel = new CustomLabel("x%d + ".formatted(j));
                xLabel.setHorizontalAlignment(SwingConstants.CENTER);

                inputPanel.add(xTextField);
                inputPanel.add(xLabel);
            }
            JTextField xTextField = new JTextField(1);
            inputXCoefficients[i][inputXCoefficients.length - 1] = xTextField;

            CustomLabel xLabel = new CustomLabel("x%d = ".formatted(inputXCoefficients.length));
            xLabel.setHorizontalAlignment(SwingConstants.CENTER);

            inputPanel.add(xTextField);
            inputPanel.add(xLabel);

            JTextField answerTextField = new JTextField(1);
            inputFreeMembers[i] = answerTextField;

            inputPanel.add(answerTextField);
        }

        calculateButton.addActionListener(ae -> {
            double[][] xCoefficients = new double[inputXCoefficients.length][inputXCoefficients.length];
            double[] freeMembers = new double[inputFreeMembers.length];

            try {
                for (int i = 0; i < inputXCoefficients.length; i++) {
                    for (int j = 0; j < inputXCoefficients[i].length; j++) {
                        xCoefficients[i][j] = Double.parseDouble(inputXCoefficients[i][j].getText());
                    }
                    freeMembers[i] = Double.parseDouble(inputFreeMembers[i].getText());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Некоректно введені дані!", "Помилка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double[][] matrix = new double[xCoefficients.length][xCoefficients.length + 1];
            for (int i = 0; i < xCoefficients.length; i++) {
                System.arraycopy(xCoefficients[i], 0, matrix[i], 0, xCoefficients[i].length);
                matrix[i][xCoefficients[i].length] = freeMembers[i];
            }

            double[] results = SolutionMethod.newInstance(solutionMethod.getName(), matrix).solve();

            boolean noResults = Arrays.stream(results).anyMatch(Double::isNaN);
            if (noResults) {
                answers.setText("Розв'язків немає!");
            } else {
                StringBuilder out = new StringBuilder();
                for (int i = 1; i <= results.length; i++) {
                    out.append("x%d = %.2f; ".formatted(i, results[i - 1]));
                }
                answers.setText(out.toString());
            }
        });

        clearButton.addActionListener(ae -> {
            Arrays.stream(inputXCoefficients)
                    .forEach(jTextFields -> Arrays.stream(jTextFields).forEach(jTextField -> jTextField.setText(null)));

            Arrays.stream(inputFreeMembers)
                    .forEach(jTextField -> jTextField.setText(null));

            answers.setText(null);
        });

        addToPanel(
                buttonsPanel,
                // ===============================================
                new JLabel(), new JLabel(),
                calculateButton, clearButton,
                new JLabel(), new JLabel()
        );

        addToPanel(
                mainPanel,
                // =========
                inputPanel,
                buttonsPanel,
                answers
        );

        add(mainPanel);
    }

    private void showEquations() {
        for (int i = 0; i < inputXCoefficients.length; i++) {
            for (int j = 1; j < inputXCoefficients[i].length; j++) {
                System.out.printf("%s * x%d + ", inputXCoefficients[i][j - 1].getText(), j);
            }
            System.out.printf(
                    "%s * x%d = %s%n",
                    // ================================================
                    inputXCoefficients[i][inputXCoefficients[i].length - 1].getText(),
                    inputXCoefficients.length,
                    inputFreeMembers[i].getText()
            );
        }
    }
}
