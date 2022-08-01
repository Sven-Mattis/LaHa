package Toolkit;

import Model.Colorpallet;
import Model.Product;
import View.View;
import View.ProductView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A Graph for any Product or something like this
 */
public class Graph extends JPanel {

    private final View owner;
    private final Product product;
    private final LocalDate[] xPoints;
    private final int[] yPoints;
    private final int[] xPointsCorrection;
    private final int[] yPointsCorrection;
    private final int maxY;
    private final int maxX;
    private final String name;
    private final String yLabel;

    private final int marginLeft = 0;
    private final int marginRight = 0;
    private final int marginTop = 20;
    private final int marginBottom = 0;
    private String hint;
    private LocalDate hintOriX;
    private int hintX;
    private int hintY;

    /**
     * Creates a Graph representing the x and y positions of the arrays
     *
     * @param owner     the Owner View
     * @param xPoints   the Points fro the x-axis
     * @param yPoints   the Points for the y-axis
     * @param forObject the Object that gets represented by the Graph, title of the Graph is getting from this, with .toString()
     */
    public Graph(View owner, LocalDate[] xPoints, int[] yPoints, Product forObject) {
        this.owner = owner;
        this.product = forObject;
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.name = forObject.toString();
        this.yLabel = forObject.getEinheit();
        this.maxY = Arrays.stream(yPoints).max().getAsInt();
        this.maxX = countDays(xPoints[0], xPoints[xPoints.length-1]);

        xPointsCorrection = new int[xPoints.length + 3];
        yPointsCorrection = new int[yPoints.length + 3];

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int[] coordsInSystem = isPoint(e.getPoint());
                if (coordsInSystem != null) {
                    setHint(coordsInSystem[0] + "", xPoints[coordsInSystem[1]], coordsInSystem[2], coordsInSystem[3]);
                    repaint();
                } else if (hint != null) {
                    hint = null;
                    repaint();
                }
            }
        });

        AtomicBoolean dbl = new AtomicBoolean(false);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(owner.getClass().equals(ProductView.class))
                    return;

                if(!dbl.get())
                    new Thread(() -> {
                        try {
                            dbl.set(true);
                            Thread.sleep(500);
                            dbl.set(false);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }).start();
                else
                    new ProductView(product);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                hint = null;
                repaint();
            }
        });

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                update();
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

        setBackground(Color.WHITE);
    }

    private void setHint(String hint, LocalDate oriX, int x, int y) {
        this.hint = hint;
        this.hintY = y;
        this.hintX = x;
        this.hintOriX = oriX;
    }

    /**
     * Check if this Point is also a Point in the Graph
     *
     * @param point point to look for in the Graph
     * @return a Integer Array of length 4 -> [Label for Y, Label for X, XPosition in the Panel, YPosition in the Panel]
     */
    private int[] isPoint(Point point) {
        // Just Loop trough the x/yPoints Array this are the only valid values
        for (int i = 0; i < Math.min(yPoints.length, xPoints.length); i++) {
            // Check if the Pos is valid, with an extra space for the space in between the points
            if (xPointsCorrection[i] - ((getWidth() / xPoints.length) / 3) <= point.x + ((getWidth() / xPoints.length) / 3) &&
                    xPointsCorrection[i] + ((getWidth() / xPoints.length) / 3) >= point.x - ((getWidth() / xPoints.length) / 3) &&
                    yPointsCorrection[i] <= point.y + (getHeight() * .15) &&
                    yPointsCorrection[i] >= point.y - (getHeight() * .15))
                return new int[]{-(yPoints[i] - maxY), i, xPointsCorrection[i], yPointsCorrection[i]};
        }
        return null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Color old = g.getColor();


        g.setColor(new Color(Colorpallet.AccentTriadeRedish.getRed(), Colorpallet.AccentTriadeRedish.getBlue(),Colorpallet.AccentTriadeRedish.getGreen(), 255/4));
        int y = (int) ((this.getHeight()-this.marginTop)-((this.product.getMelBestand()+0f) / maxY * (this.getHeight()-this.marginBottom-this.marginTop)));
        g.fillRect(marginLeft,y+marginTop,getWidth()-marginRight, this.getHeight()-y);
        g.setColor(old);

        g.setColor(new Color(Colorpallet.AccentTriadeRedish.getRed(), Colorpallet.AccentTriadeRedish.getBlue(),Colorpallet.AccentTriadeRedish.getGreen(), 255));
        y = (int) ((this.getHeight()-this.marginTop)-((this.product.getMinBestand()+0f) / maxY * (this.getHeight()-this.marginBottom-this.marginTop)));
        g.fillRect(marginLeft,y+marginTop,getWidth()-marginRight, this.getHeight()-y);
        g.setColor(old);

        Rectangle2D title = g.getFontMetrics().getStringBounds(name, g);

        g.drawString(name, (int) (getWidth() / 2 - title.getWidth() / 2), (int) (title.getHeight()));

        g.setColor(Colorpallet.Accent);
        g.fillPolygon(xPointsCorrection, yPointsCorrection, Math.min(yPointsCorrection.length, xPointsCorrection.length));
        g.setColor(old);

        if (hint != null) {
            Rectangle2D hintMetrics = g.getFontMetrics().getStringBounds(hint + " " + yLabel, g);
            Rectangle2D dateMetrics = g.getFontMetrics().getStringBounds((hintOriX.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))), g);
            if(hintX +hintMetrics.getWidth() < getWidth()) {
                g.setColor(new Color(55, 55, 55, 200));
                g.fillRect(hintX - 5 - 2,
                        hintY - (int) hintMetrics.getHeight() - 8,
                        (int) hintMetrics.getWidth() + 5,
                        (int) hintMetrics.getHeight());

                g.fillPolygon(new int[]{hintX - 7, hintX, hintX + 7},
                        new int[]{hintY - 8, hintY, hintY - 8},
                        3);
                g.setColor(Color.lightGray);

                g.drawString(hint + " " + yLabel, hintX - 5, hintY - 8 - ((int) hintMetrics.getHeight() / 4));

                g.setColor(old);
                if( hintX + dateMetrics.getWidth() < getWidth() - 5 ) {
                    g.drawString((hintOriX.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))),
                            hintX,
                            getHeight());
                } else {
                    g.drawString((hintOriX.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))),
                            (int) (getWidth()-dateMetrics.getWidth()-5),
                            getHeight());
                }
            } else {
                g.setColor(new Color(55, 55, 55, 200));
                g.fillRect((int) (getWidth() - hintMetrics.getWidth()) - 5 - 2,
                        hintY - (int) hintMetrics.getHeight() - 8,
                        (int) hintMetrics.getWidth() + 5,
                        (int) hintMetrics.getHeight());

                g.fillPolygon(new int[]{(int) (getWidth()-hintMetrics.getWidth()-7), hintX, (int) (getWidth()-hintMetrics.getWidth() + 14 - 7)},
                        new int[]{hintY - 8, hintY, hintY - 8},
                        3);
                g.setColor(Color.lightGray);

                g.drawString(hint + " " + yLabel, (int) (getWidth()-hintMetrics.getWidth()-5), hintY - 8 - ((int) hintMetrics.getHeight() / 4));

                g.setColor(old);

                g.drawString((hintOriX.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))),
                        (int) (getWidth()-dateMetrics.getWidth()-5),
                        getHeight());
            }
        }
        g.setColor(old);

    }

    /**
     * Update the points to match the Correct Position
     * And make the Array finish a block to fully fill it
     */
    public void update() {
        for (int i = 0; i < xPoints.length + 1; i++) {
            if (i < xPoints.length) {
                int days = 1;



                float percentage = countDays(xPoints[0], xPoints[i]) / (maxX + 0f);

                xPointsCorrection[i] = (int) ((percentage * (getWidth()-marginRight-marginLeft)) + marginLeft);
            } else {
                xPointsCorrection[i] = xPointsCorrection[i-1];
                xPointsCorrection[i + 1] = marginLeft;
                xPointsCorrection[i + 2] = marginLeft;
            }
        }


        for (int i = 0; i < yPoints.length + 1; i++) {
            if (i < yPoints.length) {

                float percentage = (yPoints[i]) / (maxY+0f);

                yPointsCorrection[i] = (int) ((percentage * (getHeight()-marginTop-marginBottom)) + marginTop - marginBottom);
            } else {
                yPointsCorrection[i] = getHeight()-marginBottom;
                yPointsCorrection[i + 1] = getHeight()-marginBottom;
                yPointsCorrection[i + 2] = getHeight()-marginBottom;
            }
        }
    }

    private int countDays(LocalDate start, LocalDate end) {

        int days = 1;

        try {
            days = 1;
            days += start.datesUntil(end).count();
        } catch (IllegalArgumentException e) {
            try {
                days = 1;
                days += end.datesUntil(start).count();
            } catch (Exception ignored) {days = getWidth();}
        }

        return days;
    }
}
