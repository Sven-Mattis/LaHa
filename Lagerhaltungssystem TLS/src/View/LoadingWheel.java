package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;

public class LoadingWheel extends JPanel {

    private final LoadingView view;
    private int circle, yMid, xMid;
    private String txt;

    public LoadingWheel(LoadingView view) {
        this.view = view;

        setSize(view.getSize());
        circle = (int) Math.min(getWidth() * .75, getHeight() * .75);
        xMid = (int) Math.round((getWidth() + circle) / 2 - (circle * 1.05));
        yMid = (int) Math.round((getHeight() + circle) / 2 - (circle * 1.1));

        view.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                setSize(view.getSize());

                circle = (int) Math.min(getWidth() * .75, getHeight() * .75);
                xMid = (getWidth() + circle) / 2 - (int) (circle * 1.05);
                yMid = (getHeight() + circle) / 2 - (int) (circle * 1.1);
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setStroke(new BasicStroke(10));
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, (int) (circle * .05)));
        Rectangle2D stringRectangle = g.getFont().getStringBounds(this.txt, ((Graphics2D) g).getFontRenderContext());
        g.drawString(this.txt,
                (int) Math.round((getWidth() >> 1) - (stringRectangle.getWidth() / 2)),
                (int) Math.round((getHeight() >> 1) - (stringRectangle.getHeight() / 2)));
        g.drawArc(xMid,
                yMid,
                circle,
                circle,
                0,
                (int) (((double) view.i / view.maxPercentage) * 360));
    }

    public void setText(String txt) {
        this.txt = txt;
    }
}
