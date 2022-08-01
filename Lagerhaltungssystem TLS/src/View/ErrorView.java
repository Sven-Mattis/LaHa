package View;

import Model.Error;
import Toolkit.SearchComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class ErrorView extends View {

    private final String msg;
    private final JButton msgBtn;

    /**
     * Create the ErrorView
     *
     * @param msg       the msg to display
     * @param errorCode the Error Code
     * @param error     what to do on exit
     */
    public ErrorView(String msg, int errorCode, Error error) {
        this.msg = msg;


        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setTitle("Error: " + errorCode);
        setSize(500, 300);

        msgBtn = new JButton(msg);
        msgBtn.setBackground(new Color(255, 150, 150));
        add(msgBtn);

    }

    /**
     * Add a action
     *
     * @param errorAction Runnable
     */
    public void addAction(Runnable errorAction) {
        msgBtn.addActionListener(e -> {
            dispose();
            errorAction.run();
        });
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                dispose();
                errorAction.run();
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
    }

    @Override
    public void update(Object... args) {

    }

    @Override
    public void get_searchKeysAndActions(ArrayList<SearchComponent> field) {

    }
}
