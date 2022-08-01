package Control;

import Model.Gender;
import Model.HashString;
import Model.User;
import Model.Usergroup;
import Toolkit.Database.Connection;
import Toolkit.Database.CreateUser;
import View.CreateUserView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateUserControl implements Control<Object, CreateUserView> {

    private final CreateUserView view = new CreateUserView();

    /**
     * Constructs a control with the specified type
     */
    public CreateUserControl() {
        view.addComponent("UsernameLabel", new JLabel("Username:"));
        view.addComponent("Username", new JTextField());
        view.addComponent("PasswordLabel", new JLabel("Password:"));
        view.addComponent("Password", new JPasswordField());
        view.addComponent("FirstnameLabel", new JLabel("First Name:"));
        view.addComponent("Firstname", new JTextField());
        view.addComponent("LastnameLabel", new JLabel("Last Name:"));
        view.addComponent("Lastname", new JTextField());
        view.addComponent("UsergroupLabel", new JLabel("Usergroup:"));

        // Initialise the Array
        final String[] groups = new String[Usergroup.class.getDeclaredFields().length];
        // Make the Iterator accessibly in the Lambda
        var ref = new Object() {
            int i = 0;
        };
        // Fetch the Data as a Stream
        Stream.of(Usergroup.class.getDeclaredFields()).forEach(field -> {
            groups[ref.i] = field.getName();
            ref.i++;
        });

        view.addComponent("Usergroup", new JComboBox(Arrays.stream(groups).sorted().toArray()));

        JButton create = new JButton("create");
        create.addActionListener(e -> {
            System.out.println(e);
            String username = ((JTextField) view.getComponentByName("Username")).getText();
            HashString password = new HashString(((JTextField) view.getComponentByName("Password")).getText());
            String firstname = ((JTextField) view.getComponentByName("Firstname")).getText();
            String lastname = ((JTextField) view.getComponentByName("Lastname")).getText();
            String groupStr = (String) ((JComboBox) view.getComponentByName("Usergroup")).getItemAt(((JComboBox) view.getComponentByName("Usergroup")).getSelectedIndex());
            int group = 0;
            try {
                group = Usergroup.parseGroup(groupStr);
                new CreateUser(username, firstname, lastname, Gender.Divers, password, group);
                remove();
                if(!Session.isValidSession()) {
                    // new LoadingControl();
                }
            } catch (IllegalAccessException | SQLException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        });

        view.addComponent("create", create);

        JButton cancel = new JButton("cancel");
        cancel.addActionListener(e -> {
            view.dispose();
        });

        view.addComponent("cancel", cancel);

        show();
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
    public CreateUserView getView() {
        return view;
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
}
