package Toolkit;

import Model.Colorpallet;
import View.SideMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Creates an Button that is in the Look and Feel of the SideMenu
 */
public class SideMenuButton extends JButton {

    private final SideMenu sideMenu;
    public int FontSize = 12;
    private BufferedImage img;
    private Color startingBackground;

    public SideMenuButton(String name, String iconName, SideMenu sideMenu) {
        super(name);
        this.sideMenu = sideMenu;
        try {
            this.img = ImageIO.read(new File("out/production/Lagerhaltungssystem TLS/img/" + iconName));
        } catch (IOException e) {
            img = null;
        }
        setBackground(null);
        setOpaque(true);

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
                setBackground(Colorpallet.Accent);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                FontSize += 1;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(startingBackground);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                FontSize -= 1;
            }
        });

    }

    @Override
    public void paint(Graphics g) {

        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.black);
        Color startColor = g.getColor();

        g.clearRect(0, 0, getWidth(), getHeight());

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(startColor);

        if (getText() == null)
            return;

        g.setFont(new Font(getFont().getName(), Font.PLAIN, FontSize));

        StringBuilder str = new StringBuilder(getText());
        Rectangle2D txt = g.getFontMetrics().getStringBounds(str.toString(), g);
        try {
            while (txt.getWidth() + (FontSize * 3) + 4 > sideMenu.getWidth()) {
                str.replace(str.toString().length() - 4, str.toString().length(), "");
                str.append("...");
                txt = g.getFontMetrics().getStringBounds(str.toString(), g);
            }
        } catch (StringIndexOutOfBoundsException ignored) {
        }
        g.drawString(str.toString(), (FontSize * 3) + 4, (int) ((getHeight() / 2) + (txt.getHeight() / 4)));

        if (img == null)
            return;

        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int height = FontSize * 2;
        g.drawImage(img, FontSize / 2, ((getHeight() / 2) - (height / 2)), height, height, null);

    }

    public void setStartingBackground(Color startingBackground) {
        this.startingBackground = startingBackground;
        this.setBackground(startingBackground);
    }
}
