package Toolkit;

import javax.swing.*;
import java.awt.*;

/**
 * Simply create a PasswordField with the Label already set
 */
public class PasswordField extends JPanel {


    private final ModernPasswordField tf = new ModernPasswordField();

    public PasswordField(String label) {
        JLabel l = new JLabel(label);
        setBackground(null);
        l.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(l);
        setLayout(new GridLayout(2, 1));
        add(tf);
    }

    public ModernPasswordField getField() {
        return tf;
    }

    public String getText() {
        return tf.getText();
    }

    public void setText(String txt) {
        tf.setText(txt);
    }

    public int getCaretPosition() {
        return tf.getCaretPosition();
    }

    public void setCaretPosition(int pos) {
        tf.setCaretPosition(pos);
    }
}
