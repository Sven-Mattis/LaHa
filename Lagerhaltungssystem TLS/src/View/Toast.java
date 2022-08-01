package View;

import Model.Colorpallet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;

public class Toast extends JDialog {

    private final int ttba, height = 100;
    private final View owner;

    private int percentage = 1;

    public Toast(View owner, String msg, int timeToBeAlive) {
        super(owner, msg);

        this.owner = owner;

        setUndecorated(true);

        this.setBounds(0, 0, owner.getWidth(), 30);
        setBackground(new Color(0, 0, 0, 0));

        owner.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                setPosition();
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        this.ttba = timeToBeAlive;
    }

    private void setPosition() {

        int x = owner.getLocation().x;
        int y = owner.getLocation().y;

        int height = (this.height) / 100 * (percentage / 3);

        this.setBounds(x + 8, y + (owner.getHeight() - height), owner.getWidth() - 12, height);

    }

    public void run() {
        setVisible(false);
        JLabel text = new JLabel();
        text.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                ((Graphics2D) g).setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

                Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(getTitle(), g);

                int centerX = x + (getWidth() / 2);
                int centerY = y + (getHeight() / 2);

                g.setColor(Colorpallet.AccentTriadeBlueishLight600);

                g.fillRect(
                        (int) (centerX + stringBounds.getX() - (stringBounds.getWidth() / 2)),
                        (int) (centerY + stringBounds.getY() + (stringBounds.getHeight() / 2)),
                        (int) (stringBounds.getWidth()),
                        (int) (stringBounds.getHeight()));

                g.fillArc(
                        (int) (centerX + stringBounds.getX() - (stringBounds.getWidth() / 2) - (20 / 2)),
                        (int) (centerY + stringBounds.getY() + (stringBounds.getHeight() / 2)),
                        20,
                        (int) (stringBounds.getHeight()), 90, 180);

                g.fillArc(
                        (int) (centerX + stringBounds.getX() + (stringBounds.getWidth() / 2) - (20 / 2)),
                        (int) (centerY + stringBounds.getY() + (stringBounds.getHeight() / 2)),
                        20,
                        (int) (stringBounds.getHeight()), 180 + 90, 180);


                g.setColor(Color.BLACK);
                g.drawString(getTitle(),
                        (int) (centerX - (stringBounds.getWidth() / 2)),
                        (int) (centerY + (stringBounds.getHeight() / 2)));
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return c.getParent().getInsets();
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });
        add(text);
        setVisible(true);

        Timer showingUp = new Timer(10, e -> {
            percentage += 10;
            setPosition();
        });


        Timer showingDown = new Timer(10, e -> {
            percentage -= 10;
            setPosition();
        });

        Timer close = new Timer(15 * 100 + 10, e -> {
            showingDown.setRepeats(false);
            dispose();
        });

        Timer showing = new Timer(this.ttba, e -> {
            showingDown.start();
            close.start();
        });

        Timer endShowingUp = new Timer(15 * 10 + 10, e -> {
            showingUp.setRepeats(false);
            showing.start();
        });

        endShowingUp.start();
        endShowingUp.setRepeats(false);
        showing.setRepeats(false);
        showingUp.start();

        close.setRepeats(false);
    }
}
