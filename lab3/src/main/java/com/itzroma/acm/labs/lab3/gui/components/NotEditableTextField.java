package com.itzroma.acm.labs.lab3.gui.components;

import javax.swing.JTextField;

public class NotEditableTextField extends JTextField {
    public NotEditableTextField(int columns) {
        super(columns);
        setEditable(false);
    }
}
