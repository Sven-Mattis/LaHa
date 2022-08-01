package Toolkit;

import javax.swing.*;
import java.awt.*;

/**
 * Simply creates an Input Field with the Label already set
 */
public class InputField extends JPanel {


    private final ModernTextField tf = new ModernTextField();

    public InputField(String label) {
        JLabel l = new JLabel(label);
        setBackground(null);
        l.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(l);
        setLayout(new GridLayout(2, 1));
        add(tf);
    }

    public ModernTextField getField() {
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
