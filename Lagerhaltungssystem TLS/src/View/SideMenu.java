package View;

import Control.Session;
import Model.Colorpallet;
import Toolkit.SideMenuButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class SideMenu extends JLabel {

    private final ArrayList<String> btnNames = new ArrayList<>();
    private final ArrayList<SideMenuButton> btns = new ArrayList<>();
    private final JLabel name = new JLabel("LaHa");
    private final JPanel main = new JPanel();
    private final JPanel bottom = new JPanel();

    public SideMenu(View owner, JPanel center) {
        if(!Session.isValidSession())
            return;

        // Init the Menu names
        btnNames.add("Dashboard");
        btnNames.add("Waren Entnahme");
        btnNames.add("Waren Eingang");
        btnNames.add("Produktliste");
        btnNames.add("Produktfluss");
        btnNames.add("Profil");
        btnNames.add("Einstellungen");

        if(Session.getLoggedIn().getGroup() >= 5) {
            btnNames.add("Verlauf");
            btnNames.add("Benutzer Verwalten");
        }

        // Set the Bounds
        setPreferredSize(new Dimension(100, owner.getHeight() | 1000));

        // Make it visible
        setOpaque(true);
        setBackground(Colorpallet.BackgroundMonoBaseLighter);
        setLayout(null);

        // Make a Listener to remove a SideMenu where it should´nt be
        owner.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                // Check if the Menu should be removed
                if (!isValid(owner.getTitle()) || owner.getTitle().equals("")) {
                    removeFromOwner(owner);
                }
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });


        owner.addComponentListener(new ComponentListener() {
            private void resize() {
                int newWidth = Math.max(owner.getWidth() / 100 * 20, 150);
                setPreferredSize(new Dimension(newWidth, owner.getHeight()));

                name.setBounds(0, 0, newWidth, getHeight() / 8);
                main.setBounds(0, (int) (getHeight() / 8f), newWidth, (int) (getHeight() / 8f * 5f));
                bottom.setBounds(0, (int) (getHeight() / 8f * 6f), newWidth, (int) (getHeight() / 8f * 2f));

                btns.forEach(comp -> {
                    comp.FontSize = Math.max(16, (owner.getHeight() < owner.getWidth() ? (int) (owner.getHeight() / 40f) : (int) (owner.getWidth() / 40f)));
                });
                name.setFont(new Font(getFont().getName(), Font.PLAIN, (int) (getHeight() / 100f * 8f) | 16));

                center.setBounds(newWidth, 0, owner.getWidth() - newWidth, owner.getHeight());

            }

            @Override
            public void componentResized(ComponentEvent e) {
                resize();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                resize();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                resize();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                resize();
            }
        });

        setLayout(null);

        name.setForeground(Colorpallet.Accent);
        name.setFont(new Font(getFont().getName(), Font.PLAIN, 16));
        name.setHorizontalAlignment(CENTER);
        add(name);

        main.setLayout(new GridLayout(0, 1));
        main.setOpaque(true);
        main.setBackground(null);
        main.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(Colorpallet.Accent);
                g.fillRect(0, height - 1, (int) (width * 2.2), height);
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

        add(main);

        bottom.setLayout(new GridLayout(0, 1));
        bottom.setOpaque(true);
        bottom.setBackground(null);

        add(bottom);

        btnNames.forEach(e -> {
            SideMenuButton btn = new SideMenuButton(e, e + ".png", this);
            if (e.equals("Profil") || e.equals("Einstellungen")) {
                bottom.add(btn);
            } else {
                if (owner.getClass().getName().toLowerCase().contains(e.toLowerCase()))
                    btn.setStartingBackground(Colorpallet.AccentShadow230);
                main.add(btn);
            }
            btns.add(btn);
        });

    }

    private void removeFromOwner(View owner) {
        owner.remove(this);
    }

    private boolean isValid(String title) {
        for (String txt : btnNames)
            if (txt.toLowerCase().contains(title.toLowerCase()))
                return true;

        return false;
    }

}
