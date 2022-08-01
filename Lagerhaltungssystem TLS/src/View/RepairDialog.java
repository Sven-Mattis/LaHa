package View;

import Control.CreateUserControl;
import Model.User;

import javax.swing.*;
import java.awt.*;

public class RepairDialog extends JDialog {

    private boolean finished;

    public RepairDialog(View view, String fileName) {
        super(view);

        setLocationRelativeTo(view);
        setResizable(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JButton repair = new JButton("Repair");
        repair.addActionListener(event -> {
            if (fileName.equals(User.class.getName())) {
                CreateUserControl c = new CreateUserControl();

                c.getView().setAlwaysOnTop(true);
                c.getView().setResizable(false);
            }
            finished = true;
            dispose();
        });

        JButton exit = new JButton("Exit");
        exit.addActionListener(event -> {
            System.exit(0x100);
        });

        setLayout(new BorderLayout());

        JPanel buttons = new JPanel();

        buttons.setLayout(new GridLayout(1, 2));

        buttons.add(repair);
        buttons.add(exit);

        add(new JLabel("File: " + fileName + " not found!"), BorderLayout.NORTH);

        add(buttons, BorderLayout.SOUTH);

        setSize(new Dimension(200, 100));

        setResizable(false);

        setVisible(true);
    }

    public boolean isFinished() {
        return finished;
    }
}
