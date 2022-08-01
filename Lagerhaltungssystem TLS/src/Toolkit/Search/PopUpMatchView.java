package Toolkit.Search;

import Model.Colorpallet;
import Toolkit.SearchComponent;
import View.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

public class PopUpMatchView extends JDialog {

    private final View v;
    private final ArrayList<JLabel> labels = new ArrayList<>();
    private final Component c;
    private SearchComponent[] items = new SearchComponent[0];
    private int active = 0;

    {
        // Initialise the Search
        active = Search.activeNumber;
    }

    /**
     * Create a new Pop Up for the entry's of the search
     * @param v the View
     * @param c the Component to orientate to
     */
    public PopUpMatchView(View v, Component c) {
        super(v);

        this.c = c;

        setUndecorated(true);
        setLayout(null);

        setBackground(new Color(0, 0, 0, 0));

        setVisible(true);

        c.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                dispose();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                setBounds(c.getLocationOnScreen().x, c.getLocationOnScreen().y + c.getHeight(), c.getWidth(), items.length * 20);
            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        this.v = v;
    }

    /**
     * Set the new entry´s
     * @param list the new entry list
     * @param str the searched string to mark the matches
     */
    public void set(SearchComponent[] list, String str) {
        labels.forEach(this::remove);

        items = list;
        setBounds(c.getLocationOnScreen().x, c.getLocationOnScreen().y + c.getHeight(), c.getWidth(), items.length * 20);
        for (int i = 0; i < items.length; i++) {
            JLabel l = new SearchLabel(items[i], str);
            l.setBounds(0, i * 20, c.getWidth(), 20);
            PopUpMatchView.this.add(l);
            labels.add(l);
        }
    }

    public int getActive() {
        return active;
    }

    /**
     * set the Active entry
     * Check if it is higher than the entry´s then change to 0 otherwise
     * check if it is lower than 0 then set it to the length of the entry´s
     * @param active the active number
     */
    public void setActive(int active) {
        if (labels.size() <= 0)
            return;

        Search.activeNumber = active;
        this.active = Search.activeNumber;

        int index = active % labels.size();

        if (index == labels.size())
            index = 0;

        if (index < 0)
            index = labels.size() + index;

        this.active = index;

        repaint();
    }

    /**
     * Execute the code of the search
     */
    public void execute() {
        if (labels.size() != 0 && active <= labels.size() && active >= 0) {
            ((SearchLabel) this.labels.get(active)).getSearchComponent().getAction().run();
            dispose();
        }
    }

    public View getView() {
        return this.v;
    }

    class SearchLabel extends JLabel {

        private final SearchComponent sc;
        private final String searchStr;

        public SearchLabel(SearchComponent sc, String searchStr) {
            this.sc = sc;
            this.searchStr = searchStr;

            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    sc.getAction().run();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    PopUpMatchView.this.active = PopUpMatchView.this.labels.indexOf(SearchLabel.this);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    PopUpMatchView.this.active = -1;
                    repaint();
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            g.clearRect(0, 0, getWidth(), getHeight());
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            ArrayList<Integer> indexes = new ArrayList<>();
            indexes.add(this.sc.getName().toLowerCase().indexOf(this.searchStr.toLowerCase()));

            for (int i = 0; i < this.sc.getName().length() && indexes.size() != 0; i++) {
                int newIndex = this.sc.getName().toLowerCase().indexOf(this.searchStr.toLowerCase(), indexes.get(indexes.size() - 1) + this.searchStr.length());
                if (!indexes.contains(newIndex) && newIndex >= 0)
                    indexes.add(newIndex);
            }


            if (PopUpMatchView.this.labels.indexOf(SearchLabel.this) != PopUpMatchView.this.active)
                g.setColor(Colorpallet.BackgroundMonoBaseLighter);
            else
                g.setColor(Colorpallet.BackgroundMonoBase);

            g.fillArc(0, 0, getHeight(), getHeight(), 90, 180);
            g.fillRect(getHeight() / 2, 0, getWidth() - (getHeight()), getHeight());
            g.fillArc(getWidth() - getHeight(), 0, getHeight(), getHeight(), 270, 180);

            int width = 0;
            for (int i = 0; i < this.sc.getName().length(); i++) {

                Rectangle2D fontMetrics;
                String str;
                if (indexes.contains(i)) {
                    g.setColor(Colorpallet.Accent);
                    str = "";
                    for (int j = 0; j < this.searchStr.length(); j++)
                        str += "" + this.sc.getName().charAt(i + j);
                    i += this.searchStr.length() - 1;
                } else {
                    g.setColor(Color.BLACK);
                    str = "" + this.sc.getName().charAt(i);
                }

                fontMetrics = g.getFontMetrics().getStringBounds(str, g);

                g.drawString(str, getHeight() + width, (int) ((getHeight() + fontMetrics.getHeight()) / 2 + fontMetrics.getCenterY() + 4));
                width += fontMetrics.getWidth();
            }
        }

        public SearchComponent getSearchComponent() {
            return sc;
        }
    }

}
