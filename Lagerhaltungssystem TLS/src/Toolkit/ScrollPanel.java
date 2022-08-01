package Toolkit;

import View.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScrollPanel extends JPanel {

    private final int marginLeft_Right = 10;
    private final int marginUp_Down    = 5;
    private boolean scrollbar = false;
    private int scrollbarX = 0;
    private int scrollbarY = 0;
    private final int scrollbarWidth = 10;
    private int scrollbarHeight = 0;
    private int height = 0;
    private double offsetY = 0;

    private final ArrayList<Component> components = new ArrayList<>();
    {
        setLayout(null);
        addMouseWheelListener(e -> {
            if(!scrollbar)
                return;
            offsetY += e.getPreciseWheelRotation()*100;

            if(offsetY < 0)
                offsetY = 0;
            else if(height-offsetY < getHeight())
                offsetY = height - getHeight();

            repositioning();
        });

        MouseAdapter ma = new MouseAdapter() {
            boolean down = false;

            Point start = new Point(0,0);

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (e.getX() > scrollbarX && e.getX() < scrollbarX+scrollbarWidth && e.getY() > scrollbarY && e.getY() < scrollbarY+scrollbarHeight)
                    down = true;

                start = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                down = false;
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseDragged(MouseEvent e) {

                if(!down)
                    return;


                int heightPercentage = (int) Math.abs(getHeight() - ((height+0f) / (getHeight()+0f) * 100));
                offsetY = (e.getY() - start.getY()) / (getHeight()-scrollbarHeight+0f)*getHeight() * (height + getHeight()) / (getHeight()+heightPercentage);

                if(offsetY < 0)
                    offsetY = 0;
                else if(offsetY > (getHeight()-scrollbarHeight)  / (getHeight()-scrollbarHeight+0f)*getHeight() * (height + getHeight()) / (getHeight()+heightPercentage))
                    offsetY = (getHeight()-scrollbarHeight)  / (getHeight()-scrollbarHeight+0f)*getHeight() * (height + getHeight()) / (getHeight()+heightPercentage);

                System.out.println(offsetY);
                repositioning();
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public ScrollPanel(View v) {

        v.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

                new Thread(() -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    repositioning();
                }).start();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                new Thread(() -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    repositioning();
                }).start();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                new Thread(() -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    repositioning();
                }).start();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        this.addContainerListener(new ContainerListener() {
            @Override
            public void componentAdded(ContainerEvent e) {
                new Thread(() -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    repositioning();
                }).start();
            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                new Thread(() -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    repositioning();
                }).start();
            }
        });
    }

    private void repositioning() {

        var ref = new Object() {
            int y = marginUp_Down;
        };

        ArrayList<Component> used = new ArrayList<>();

        components.forEach(component -> {

            int lineHeight = 0;
            int currentWidth = marginLeft_Right;
            while(currentWidth < getWidth() && used.size() < components.size()) {
                Component nc = getComponentForLine(getHeight(component), currentWidth+getWidth(component), used);

                if(nc == null)
                    break;

                lineHeight = getHeight(component);

                used.add(nc);
                nc.setBounds(currentWidth, (int) (ref.y-offsetY), getWidth(nc), getHeight(nc));

                if(nc instanceof Graph)
                    ((Graph) nc).update();

                currentWidth += getWidth(nc)+marginLeft_Right;

            }
            ref.y += lineHeight + marginUp_Down;
        });

        if(ref.y > getHeight()+marginUp_Down) {
            scrollbar = true;
        } else {
            scrollbar = false;
            if(offsetY != 0) {
                offsetY = 0;
                repositioning();
            }
        }

        this.height = ref.y;

        repaint();
    }

    private int getHeight(Component component) {
        return component.getPreferredSize().height;
    }

    private int getWidth(Component component) {
        return component.getPreferredSize().width;
    }

    private Component getComponentForLine (int maxLineHeight, int currentWidth, ArrayList<Component> used) {
        var ref = new Object() {
            Component comp;
        };

        components.forEach(component -> {
            if(maxLineHeight >= getHeight(component) &&
                getWidth()-marginLeft_Right >= currentWidth &&
                !used.contains(component)) {
                ref.comp = component;
            }
        });

        return ref.comp;
    }

    @Override
    public Component add(Component comp) {
        components.add(comp);
        return super.add(comp);
    }


    @Override
    public void remove(Component comp) {
        components.remove(comp);
        super.remove(comp);
    }

    @Override
    public void setLayout(LayoutManager mgr) {
        super.setLayout(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if(!scrollbar)
            return;

        int heightPercentage = (int) Math.abs(getHeight() - ((height+0f) / (this.getHeight()+0f) * 100));
        scrollbarHeight = heightPercentage;
        int offset = (int) (offsetY / (height - getHeight()) * (getHeight()-heightPercentage));
        scrollbarY = offset;

        scrollbarX = getWidth()-13;

        g.fillRect(scrollbarX, scrollbarY, scrollbarWidth, scrollbarHeight);
    }
}
