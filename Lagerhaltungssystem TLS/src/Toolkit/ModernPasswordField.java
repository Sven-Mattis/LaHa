package Toolkit;

import Model.Colorpallet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Create a PasswordField with another Look and Feel
 */
public class ModernPasswordField extends JPasswordField {

    public Color current = Colorpallet.Accent;

    public ModernPasswordField() {

        setBackground(null);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (hasFocus() || !getText().equals(""))
                    return;
                current = Colorpallet.AccentDark;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (hasFocus() || !getText().equals(""))
                    return;
                current = Colorpallet.Accent;
                repaint();
            }
        });

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                current = Colorpallet.AccentTriadeBlueishDark;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!getText().equals(""))
                    current = Colorpallet.AccentTriadeBlueish;
                else
                    current = Colorpallet.Accent;
                repaint();
            }
        });


        setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(current);
                g.fillRect(x, y + height - 1, width, 1);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(1, 1, 1, 1);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });

    }
}
