package com.itzroma.acm.labs.lab4.gui.components;

import javax.swing.*;

public class NotEditableTextField extends JTextField {
    public NotEditableTextField(int columns) {
        super(columns);
        setEditable(false);
    }
}
