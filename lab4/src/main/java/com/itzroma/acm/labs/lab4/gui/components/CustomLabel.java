package com.itzroma.acm.labs.lab4.gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomLabel extends JLabel {
    public CustomLabel(String text) {
        super(text);
        setHorizontalAlignment(RIGHT);
        setVerticalAlignment(CENTER);
        setFont(new Font("Calibri", Font.BOLD, 20));
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }
}
