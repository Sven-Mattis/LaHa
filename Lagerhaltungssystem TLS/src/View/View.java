package View;

import Control.Session;
import CustomView.ViewBase;
import Model.Colorpallet;
import Toolkit.Components;
import Toolkit.Notifications.Notification;
import Toolkit.ScrollPanel;
import Toolkit.Search.PopUpMatchView;
import Toolkit.Search.Search;
import Toolkit.SearchComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creates the Basic View Frame
 * <p>
 * a lot of things in the Base also have controlling functions
 * but these are necessary to make the base View work properly in the
 * different ways
 * <p>
 * Also creates a Resize Handler and remove nearly all basic JFrame features
 * <p>
 * To remove basic Menu call the removeMenus(true) before appending the Components
 */
public abstract class View extends ViewBase implements Serializable {

    private final Components<String, Component> components = new Components<>();
    private final JLabel fullscreen = new JLabel();
    private final JPanel main = new ScrollPanel(this);
    private final JPanel center = new JPanel();
    private final boolean fullscreenV = true;
    private boolean removeMenus = false;

    {
        super.setBorderAccent(Colorpallet.AccentTopbar);

        // Basic stuff for the View
        getContentPane().setBackground(Colorpallet.BackgroundMonoBaseWhite);
        Session.addView(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 300));
        setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);

        // Save this for anonymous classes
        View v = this;

        // Add Listener to Remove view from Session on close
        // and reset the Session Timer if something is done in the View
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                Session.resetTimer();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Session.removeView(v);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Session.removeView(v);
            }

            @Override
            public void windowIconified(WindowEvent e) {
                Session.resetTimer();
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                Session.resetTimer();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                Session.resetTimer();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                Session.resetTimer();
            }
        });

        setLayout(new BorderLayout());

        center.setLayout(null);
        center.setBounds(0, 0, getWidth(), getHeight());


        /*
         * Upper Menu with the Search bar and Notification / logout
         */
        JPanel upperMenu = new JPanel();
        upperMenu.setBounds(0, 0, getWidth(), 50);
        upperMenu.setBackground(null);
        upperMenu.setLayout(null);
        upperMenu.setOpaque(true);

        /*
         * The Searchbar
         */

        class SearchPanel extends JPanel {

            private final View owner;

            public SearchPanel(View v) {
                this.owner = v;
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);

                g.setColor(Colorpallet.BackgroundMonoBaseLighter);

                g.fillArc(0, 0, getHeight(), getHeight(), 90, 180);
                g.fillArc(getWidth() - (getHeight()), 0, getHeight(), getHeight(), 270, 180);


                int fontSize = (int) Math.min((getWidth() - getHeight()) * .75, getHeight() * .75);
                int width = fontSize;
                int height = fontSize;
                int x = getWidth() - (getHeight() / 2) - fontSize;
                int y = (getHeight() - fontSize) / 2;

                g.fillRect(x, 0, width, y);
                g.fillRect(x, height + y, width, getHeight());
            }
        }

        JPanel searchPanel = new SearchPanel(this);
        searchPanel.setLayout(null);
        searchPanel.setOpaque(true);

        /*
         * the inner search field
         */
        JTextField searchField = new JTextField();
        searchPanel.add(searchField);
        searchField.setBackground(Colorpallet.BackgroundMonoBaseLighter);
        searchField.setBorder(null);
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (Search.pop == null)
                    Search.pop = new PopUpMatchView(View.this, searchPanel);

                if (e.getKeyCode() == 38) {
                    Search.pop.setActive(Search.pop.getActive() - 1);
                    return;
                } else if (e.getKeyCode() == 40) {
                    Search.pop.setActive(Search.pop.getActive() + 1);
                    return;
                } else if (e.getKeyCode() == 27) {
                    requestFocus(false);
                    return;
                } else if (e.getKeyCode() == 10) {
                    Search.pop.execute();
                    return;
                }

                if (Search.pop != null)
                    Search.pop.dispose();

                Search.pop = null;


                Search.pop = new PopUpMatchView(View.this, searchPanel);


                String str = searchField.getText();
                Search.find(str, View.this, (e.getKeyCode() == 10));
                searchField.requestFocus(true);
            }
        });

        /*
         * The SearchIcon
         */
        JLabel searchIcon = new JLabel();
        searchIcon.setOpaque(true);
        searchIcon.setBackground(Colorpallet.BackgroundMonoBaseLighter);
        searchPanel.add(searchIcon);
        searchIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String str = searchField.getText();
                Search.find(str, View.this, true);
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
            }
        });
        ImageIcon searchIconImg = new ImageIcon("out/production/Lagerhaltungssystem TLS/img/searchIcon.png");

        /*
         * The Notification Panel
         */
        class NotificationPanel extends JPanel {

            @Override
            public void paint(Graphics g) {
                super.paint(g);

                g.setColor(Colorpallet.BackgroundMonoBaseLighter);
                g.fillArc(0, 0, getHeight(), getHeight(), 90, 180);
                g.fillArc(getWidth() - getHeight(), 0, getHeight(), getHeight(), 270, 180);

                g.setColor(Colorpallet.AccentTriadeBlueish);
                // g.fillRect(getHeight()/2, 0, getWidth()-getHeight(), getHeight());
            }

        }
        NotificationPanel notificationPanel = new NotificationPanel();
        notificationPanel.setBackground(null);
        notificationPanel.setLayout(null);

        AtomicInteger notificationPanelWidth = new AtomicInteger(150);

        // The NotificationBell
        class NotificationBell extends JLabel {

            @Override
            public void paint(Graphics g) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g.clearRect(0, 0, getWidth(), getHeight());

                g.setColor(Colorpallet.BackgroundMonoBaseLighter);
                g.fillRect(0, 0, getWidth(), getHeight());

                int baseX = 33;
                int baseY = 7;
                if (Session.isValidSession()) {
                    String str = "" + Session.getLoggedIn().getNotifications().size();

                    if (!str.equals("0") && !str.isEmpty()) {

                        if (str.length() > 1)
                            str = "9+";

                        g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 10));
                        Rectangle2D metrics = g.getFontMetrics().getStringBounds(str, g);

                        int width = metrics.getWidth() >= metrics.getHeight() ? (int) metrics.getWidth() : 0;

                        g.setColor(Colorpallet.Accent);

                        // Left Corner
                        g.fillArc(baseX,
                                baseY / 2 - 2,
                                (int) metrics.getHeight(),
                                (int) metrics.getHeight(),
                                90,
                                180);

                        // Middle
                        g.fillRect((int) (baseX + (metrics.getHeight() / 2)),
                                baseY / 2 - 2,
                                width,
                                (int) metrics.getHeight());

                        // Right Corner
                        g.fillArc(baseX + width,
                                baseY / 2 - 2,
                                (int) metrics.getHeight(),
                                (int) metrics.getHeight(),
                                270,
                                180);

                        g.fillPolygon(new int[]{baseX, 11 + baseX, baseX + 2}, new int[]{baseY, (int) (baseY + metrics.getHeight() / 2) - 3, baseY + 12}, 3);

                        g.setColor(Color.BLACK);
                        g.drawString(str, (int) (baseX + (metrics.getHeight() + width) / 2 - metrics.getCenterX() + 1), (int) (baseY - metrics.getCenterY()));
                    }

                    // Draw the Icon
                    Image icon = ((ImageIcon) getIcon()).getImage();
                    g.drawImage(icon, 10, (getHeight() - icon.getHeight(null)) / 2, null);
                }
            }
        }
        JLabel notificationBell = new NotificationBell();
        notificationBell.setIcon(new ImageIcon(new ImageIcon("out/production/Lagerhaltungssystem TLS/img/notificationBell.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        notificationPanel.add(notificationBell);

        /*
         * Logout
         */
        class LogoutLabel extends JLabel {

            final ImageIcon icon = new ImageIcon(new ImageIcon("out/production/Lagerhaltungssystem TLS/img/logout.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

            {
                this.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Session.InvalidateSession();
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

                    }
                });
            }

            @Override
            public void paint(Graphics g) {
                g.clearRect(0, 0, getWidth(), getHeight());
                g.setColor(Colorpallet.BackgroundMonoBaseLighter);
                g.fillRect(0, 0, getWidth(), getHeight());

                g.drawImage(icon.getImage(), (getWidth() - 35) / 2, (getHeight() - 35) / 2, null);

            }
        }

        JLabel logout = new LogoutLabel();
        notificationPanel.add(logout);

        /*
         * showNotificationPanel
         */
        class ShowNotificationPanel extends JDialog {

            {
                addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Object notification = Session.getLoggedIn().getNotifications().get(e.getY() / 50);

                        if (notification instanceof Notification)
                            ((Notification) notification).run();
                        else
                            Session.getLoggedIn().getNotifications().remove(notification);

                        repaint();

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

                    }
                });
            }

            @Override
            public void setVisible(boolean b) {
                setBackground(new Color(0, 0, 0, 0));
                super.setVisible(b);
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(new Color(0, 0, 0, 0));
                g.fillRect(0, 0, getWidth() * 2, getHeight() * 2);

                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                for (int i = 0; i < Math.min(Session.getLoggedIn().getNotifications().size(), 9); i++)
                    paintMessage(g, i);
            }

            private void paintMessage(Graphics g, int i) {
                try {
                    StringBuilder textString = new StringBuilder(Session.getLoggedIn().getNotifications().get(i).toString());
                    int height = 50;
                    int y = height * i;
                    int width = getWidth() - height;


                    g.setColor(Colorpallet.BackgroundMonoBaseLighter);
                    g.fillArc(0, y, height, height, 90, 180);
                    g.fillRect(height / 2, y, width, height);
                    g.fillArc(getWidth() - height, y, height, height, 270, 180);


                    int iconHeight = (int) (height * 0.8);

                    Image icon = new ImageIcon(new ImageIcon("out/production/Lagerhaltungssystem TLS/img/caution.png").getImage().getScaledInstance(iconHeight, iconHeight, Image.SCALE_SMOOTH)).getImage();
                    g.drawImage(icon, (int) (height * .1), (int) (y + height * .1), null);


                    g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 16));
                    Rectangle2D fontMetrics = g.getFont().getStringBounds(textString.toString(), g.getFontMetrics().getFontRenderContext());

                    while (fontMetrics.getWidth() > width - (height / 4)) {
                        textString.replace(textString.length() - 4, textString.length(), "...");
                        fontMetrics = g.getFont().getStringBounds(textString.toString(), g.getFontMetrics().getFontRenderContext());
                    }

                    g.setColor(Color.BLACK);
                    g.drawString(textString.toString(), height, (int) ((y + ((height + fontMetrics.getHeight()) / 2)) + fontMetrics.getCenterY()));


                } catch (Exception e) {

                }
            }

            @Override
            public void dispose() {
                super.dispose();
            }
        }

        JDialog showNotificationPanel = new ShowNotificationPanel();
        showNotificationPanel.setUndecorated(true);
        showNotificationPanel.setBackground(new Color(0, 0, 0, 0));
        showNotificationPanel.setAlwaysOnTop(true);
        notificationBell.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                showNotificationPanel.repaint();
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

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                showNotificationPanel.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                showNotificationPanel.dispose();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        Runnable resize = new Runnable() {

            /**
             * When an object implementing interface {@code Runnable} is used
             * to create a thread, starting the thread causes the object's
             * {@code run} method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method {@code run} is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {

                // Calculate the new Width (and height) for the Searc h Panel and Search Field
                float baseWidth = upperMenu.getWidth() / 100f;
                searchPanel.setBounds((int) (baseWidth * 20), upperMenu.getHeight() / 10 * 2, (int) (baseWidth * (60) - notificationPanel.getWidth() - 20), upperMenu.getHeight() / 10 * 6);
                searchField.setBounds(searchPanel.getHeight() / 2, 0, searchPanel.getWidth() - searchPanel.getHeight(), searchPanel.getHeight());


                // Font size is in this context the height and width for the Search Button
                int fontSize = (int) Math.min(searchField.getWidth() * .75, searchField.getHeight() * .75);

                // Set the height and width, x and y for the search button
                searchIcon.setBounds(searchPanel.getWidth() - (searchPanel.getHeight() / 2) - fontSize, (searchPanel.getHeight() - fontSize) / 2, fontSize, fontSize);
                // get an scaled instance with the new size of the icon
                try {
                    Image searchImg = searchIconImg.getImage().getScaledInstance(fontSize, fontSize, Image.SCALE_SMOOTH);
                    searchIcon.setIcon(new ImageIcon(searchImg));

                    // Make it fade in if there isnt enogh space to display completely
                    if (searchPanel.getWidth() < searchPanel.getHeight()) {
                        fontSize = 0;
                        searchPanel.setBounds(searchPanel.getX(), (upperMenu.getHeight() - searchPanel.getWidth()) / 2, searchPanel.getWidth(), searchPanel.getWidth());
                    }
                } catch (IllegalArgumentException ignored) {
                }

                // Set the new Width for the search field, because of the icon
                searchField.setBounds(searchPanel.getHeight() / 2, 0, searchPanel.getWidth() - searchPanel.getHeight() - fontSize, searchPanel.getHeight());

                // adjust the font size of the search field
                fontSize = (int) Math.min(searchField.getWidth() * .4, searchField.getHeight() * .4);
                searchField.setFont(new Font(getName(), Font.PLAIN, fontSize));

                // Set Position of the NotificationPanel
                notificationPanel.setBounds((int) (baseWidth * (100) - notificationPanelWidth.get() - 20), (upperMenu.getHeight() / 10 * 2), notificationPanelWidth.get(), upperMenu.getHeight() / 10 * 6);

                // Show NotificationPanel
                if (Session.isValidSession() && !removeMenus && showNotificationPanel.isVisible())
                    showNotificationPanel.setBounds(notificationPanel.getLocationOnScreen().x, notificationPanel.getLocationOnScreen().y + notificationPanel.getHeight(), notificationPanel.getWidth(), 50 * Math.min(Session.getLoggedIn().getNotifications().size(), 9));

                // The NotificationBell
                notificationBell.setBounds(notificationPanel.getHeight() / 2, 0, (notificationPanel.getWidth() - notificationPanel.getHeight()) - 50, notificationPanel.getHeight());

                // The Logout Button
                logout.setBounds(notificationPanel.getHeight() / 2 + notificationBell.getWidth(), 0, (notificationPanel.getWidth() - notificationPanel.getHeight()) - notificationBell.getWidth(), notificationPanel.getHeight());

            }
        };

        // The Resize Listener
        upperMenu.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resize.run();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                resize.run();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                resize.run();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                resize.run();
            }
        });

        final boolean[] bellHover = {false};
        final boolean[] showHover = {false};

        Timer hide = new Timer(1, null);
        hide.addActionListener(e -> {
            if (notificationPanelWidth.get() > 150) {
                notificationPanelWidth.set((int) (notificationPanelWidth.get() - (Math.max(10, getWidth() * .25 / 10))));
                resize.run();
            } else {
                showNotificationPanel.setVisible(false);
                hide.stop();
            }
        });
        Timer show = new Timer(1, null);
        show.addActionListener(e -> {
            if(!showNotificationPanel.isVisible())
                showNotificationPanel.setVisible(true);
            if (Session.getLoggedIn().getNotifications().size() == 0) {
                show.stop();
                return;
            }
            if (notificationPanelWidth.get() < Math.max(300, getWidth() * .25 / 10)) {
                hide.stop();
                notificationPanelWidth.set((int) (notificationPanelWidth.get() + (Math.max(10, getWidth() * .25 / 10))));
                resize.run();
            } else {
                show.stop();
            }
        });

        Runnable updateShowNotifications = () -> {
            if (bellHover[0] || showHover[0]) {
                show.start();
            } else {
                hide.start();
            }
        };

        notificationBell.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // ##############################################################
                // ######################## TEST PURPOSE ########################
                // ##############################################################
                Session.getLoggedIn().addNotifications(new Notification("Test" + Math.random(), (Runnable & Serializable) () -> {
                    System.out.println("It works");
                }));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                bellHover[0] = true;
                updateShowNotifications.run();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bellHover[0] = false;
                updateShowNotifications.run();
            }
        });
        showNotificationPanel.addMouseListener(new MouseListener() {
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
                showHover[0] = true;
                updateShowNotifications.run();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showHover[0] = false;
                updateShowNotifications.run();
            }
        });
        upperMenu.add(notificationPanel);

        upperMenu.add(searchPanel);

        center.add(upperMenu);

        main.setLayout(null);
        main.setBounds(0, 0, getWidth(), getHeight());

        center.add(main);

        add(center, BorderLayout.CENTER);
        add(new SideMenu(this, center), BorderLayout.WEST);

        center.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                upperMenu.setBounds(0, 0, center.getWidth(), 75);
                main.setBounds(0, upperMenu.getHeight(), center.getWidth(), center.getHeight() - upperMenu.getHeight());
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

    /**
     * Add a component to the view, with an access name
     *
     * @param name the Access name
     * @param c    the component
     */
    public void addComponent(String name, Component c) {

        c.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Session.resetTimer();
            }
        });

        c.addMouseListener(new MouseListener() {
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
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        components.add(name, c);

        if (!removeMenus)
            main.add(c);
        else
            add(c);
    }

    /**
     * Remove the component with the name
     *
     * @param name the name of the Component to get removed
     */
    public void removeComponent(String name) {
        removeComponent(components.get(name));
    }

    /**
     * Remove a component
     *
     * @param c remove the Component
     */
    public void removeComponent(Component c) {
        components.removeComponent(c);
    }

    /**
     * Get the component with the name
     *
     * @param name the Access name of the component
     * @return the Component
     */
    public Component getComponentByName(String name) {
        try {
            return components.get(name);
        } catch (IndexOutOfBoundsException e) {
            new Toast(this, "No Entry found in components for " + name, 10000);
        }

        return null;
    }

    /**
     * Update the View
     *
     * @param args objects to tell explicit to update
     */
    public abstract void update(Object... args);

    public void addToAllKeyListener(KeyListener keyListener) {
        for (int i = 0; i < components.size(); i++) {
            components.getComponentAt(i).addKeyListener(keyListener);
        }
    }

    /**
     * Correct the new Bounds to the correct bounds
     * Necessary because the new border needs to be included
     * @param bounds
     */
    public void initBounds(Rectangle bounds) {
        bounds.height += 16;
        super.setBounds(bounds);
    }

    @Override
    public void setResizable(boolean resizable) {
        super.setResizable(resizable);
        try {
            if (!resizable && fullscreenV)
                getRootPane().remove(3);
            else
                getRootPane().add(fullscreen);
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    /**
     * Tell the view that in this view the menus does´nt exists
     * @param b
     */
    protected void removeMenus(boolean b) {
        this.removeMenus = b;
        if (b) {
            center.remove(main);
            remove(center);
        }
    }

    @Override
    public void setLayout(LayoutManager manager) {
        if(removeMenus || main == null)
            super.setLayout(manager);
        else
            main.setLayout(manager);
    }

    public String getComponentsName (Component c) {
        return components.getName(c);
    }

    /**
     * NECESSARY FOR THE SEARCH
     * @param list
     */
    public abstract void get_searchKeysAndActions(ArrayList<SearchComponent> list);

    protected void updateComponent(Component c) {
        remove(c);
        main.remove(c);
        center.remove(c);
        if(!removeMenus) {
            main.add(c);
        } else
            add(c);
    }
}