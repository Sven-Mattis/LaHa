package View;

import Control.LoginControl;
import Model.Colorpallet;
import Toolkit.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginView extends View {

    private final SearchComponent[] searchKeysAndActions = new SearchComponent[] {};
    private final LoginControl c;

    /**
     * Creates the Login View
     */
    public LoginView(LoginControl c) {
        this.c = c;
        setAlwaysOnTop(true);
    }

    @Override
    public void update(Object... args) {
        for (Object arg : args) {
            if(arg instanceof Component) {
                try {
                    super.updateComponent((Component) arg);
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
    }

    @Override
    public void get_searchKeysAndActions(ArrayList<SearchComponent> field) {
        field.addAll(Arrays.asList(searchKeysAndActions));
    }

    public void applyUIDesign() {
        super.removeMenus(true);
        setLayout(null);

        InputField username = new InputField("Username:");
        PasswordField password = new PasswordField("Password:");
        ActionListener[] la = super.getComponentByName("LoginButton").getListeners(ActionListener.class);
        JButton login = new JButton("Login");
        login.addActionListener(la[0]);


        setBackground(new Color(155, 155, 155));

        username.setBounds(25, 165, getWidth() - 60, 40);
        password.setBounds(25, 240, getWidth() - 60, 40);
        login.setBounds((getWidth() / 2) - ((getWidth() - 110) / 2), 300, getWidth() - 110, 40);

        login.setBackground(Colorpallet.Accent);
        login.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                login.setBackground(Colorpallet.AccentTriadeRedish);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                login.setBackground(Colorpallet.AccentTriadeRedish);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                login.setBackground(Colorpallet.Accent);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                login.setBackground(Colorpallet.AccentDark);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                login.setBackground(Colorpallet.Accent);
            }
        });

        login.setBorder(null);

        setMinimumSize(new Dimension((int) getMinimumSize().getWidth(), (int) getMinimumSize().getHeight() + 60));

        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("out/production/Lagerhaltungssystem TLS/img/TLS-Logo.png"));
        logo.setBounds(getWidth() / 4, -getHeight() / 6 - 5, getWidth(), getWidth());
        add(logo);

        update(username, password, login);
        this.c.tf = username;
        this.c.pw = password;

        initBounds(getBounds());
    }

}
