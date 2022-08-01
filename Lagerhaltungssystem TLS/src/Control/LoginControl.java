package Control;

import Model.Colorpallet;
import Model.Gender;
import Model.HashString;
import Toolkit.InputField;
import Toolkit.ModernTextField;
import Toolkit.PasswordField;
import View.LoginView;
import View.Toast;
import Worker.Worker;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoginControl extends Worker implements Control<Object, LoginView> {

    private final LoginView view = new LoginView(this);

    public InputField tf = null;
    public PasswordField pw = null;

    /**
     * The Login
     */
    public LoginControl() {
        view.setResizable(false);
        view.addComponent("UsernameField", new InputField("Username:"));
        view.addComponent("PasswordField", new PasswordField("Password:"));
        JButton login = new JButton("Login");
        login.addActionListener(e -> {
            login();
        });
        view.addComponent("LoginButton", login);
        view.setFocusable(true);
        view.addToAllKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 10) {
                    login();
                    ((JComponent) e.getSource()).requestFocus();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        view.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                view.applyUIDesign();
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    /**
     * Try to log the User in
     */
    public void login() {
        // Automatically waits for User Input
        // Try to Validate the User Input
        if(pw == null || tf == null)
            return;

        Session.ValidateSession(tf.getText(),
                new HashString(pw.getText()));
        // Check if the Session is now Valid
        if (Session.isValidSession()) {
            // Goto DashBoard
            DashBoardControl m = new DashBoardControl();
            m.show();
            // Message of the Day
            // Hallo NAME
            // Willkommen (zurück) Frau/Herr NAME
            String motd =
                    ((Session.getLoggedIn().getGender() == Gender.Divers ? "Hallo " :
                            (Session.getLoggedIn().getGender() == Gender.Weiblich ? "Willkommen " + (Session.getLoggedIn().isFirstLogin() ? "" : "zurück ") + "Frau " :
                                    "Willkommen " + (Session.getLoggedIn().isFirstLogin() ? "" : "zurück ") + "Herr ")));
            // Display the Message of the Day as a Toast
            new Toast(m.getView(), (motd + (Session.getLoggedIn().getLastName())),
                    3000).run();

            // Delete the Login View
            remove();
        } else {
            // Create a Message to tell the User that the Input isn´t Valid
            new Toast(view, "Unknown User", 1000).run();

            // Reset the Login
            ((InputField) view.getComponentByName("UsernameField")).getField().current = Colorpallet.Accent;
            ((PasswordField) view.getComponentByName("PasswordField")).getField().current = Colorpallet.Accent;
            view.getComponentByName("UsernameField").repaint();
            view.getComponentByName("PasswordField").repaint();
            ((InputField) view.getComponentByName("UsernameField")).setText("");
            ((PasswordField) view.getComponentByName("PasswordField")).setText("");
        }
    }

    /**
     * Say the View to show
     */
    @Override
    public void show() {
        view.setVisible(!view.isVisible());
    }

    /**
     * Get the boolean value of the View Visibility
     *
     * @return the boolean value of the View Visibility
     */
    @Override
    public boolean viewVisible() {
        return false;
    }

    /**
     * Get the View
     *
     * @return the View
     */
    @Override
    public LoginView getView() {
        return this.view;
    }

    /**
     * Get the Items of the View and the Controller
     *
     * @return the Items
     */
    @Override
    public ArrayList<Object> getItems() {
        return null;
    }

    /**
     * Set the Searched items
     *
     * @param searched
     */
    @Override
    public void setSearchedItems(ArrayList<Object> searched) {

    }

    /**
     * Say the View to update
     */
    @Override
    public void updateView() {

    }

    /**
     * Remove the Controller
     */
    @Override
    public void remove() {
        view.dispose();
    }

    /**
     * set the MenuBar
     */
    @Override
    public void setMenu() {

    }

    /**
     * Set the Action that
     *
     * @param errorAction
     */
    @Override
    public void setOnError(Runnable errorAction) {

    }

    /**
     * Get the boolean of if the view has encountered an Error
     *
     * @return the boolean of if the view has encountered an Error
     */
    @Override
    public boolean hasError() {
        return false;
    }

    /**
     * Get if the View has Focus
     *
     * @return if the View has Focus as a boolean
     */
    @Override
    public boolean hasFocus() {
        return false;
    }

    /**
     * Get the Loaded value
     *
     * @return the Loaded value as a boolean
     */
    @Override
    public boolean isLoaded() {
        return false;
    }

    /**
     * Retry to create the View and Controller
     */
    @Override
    public void retry() {

    }

    public void login(ActionEvent actionEvent) {
        login();
    }
}
