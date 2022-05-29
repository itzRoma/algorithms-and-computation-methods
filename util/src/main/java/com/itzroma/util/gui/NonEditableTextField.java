package com.itzroma.util.gui;

import javax.swing.JTextField;

public class NonEditableTextField extends JTextField {
    public NonEditableTextField(int columns) {
        super(columns);
        setEditable(false);
    }
}
