package Model;

import Control.Session;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String username, firstName, lastName;
    private HashString password;
    private int group;
    private final Gender gender;
    private final ArrayList<Object> notifications = new ArrayList<>();
    private boolean firstLogin = true;

    /**
     * Creates a User
     *
     * @param Username  the Username, to log in to the Program
     * @param firstName the first name of this User, to create correct papers
     * @param lastName  the last name of the User, to create correct papers
     * @param gender    the Gender of the User, to create correct papers
     * @param password  the Password, as a Hash String
     * @param group     the User group of the User
     */
    public User(String Username, String firstName, String lastName, Gender gender, HashString password, int group) {
        this.username = Username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.password = password;
        this.group = group;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(HashString password) {
        this.password = password;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    /**
     * Get the Username
     *
     * @return Username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the First name
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the Last name
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the Gender
     *
     * @return Gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Get if it ist the First login
     *
     * @return firstLogin
     */
    public boolean isFirstLogin() {
        return firstLogin;
    }

    /**
     * Set the firstLogin to false
     */
    public void toggleFirstLogin() {
        this.firstLogin = false;
    }

    /**
     * Get the Hashed UserPassword as String
     *
     * @return User password
     */
    public String getPassword() {
        return this.password.get();
    }

    /**
     * Get the Usergroup
     *
     * @return group
     */
    public int getGroup() {
        return group;
    }

    /**
     * Get the List of Notifications
     *
     * @return the List of Notifications
     */
    public ArrayList<Object> getNotifications() {
        return this.notifications;
    }

    /**
     * Add a Notification to this account
     *
     * @param msg the Notification Content
     */
    public void addNotifications(Object msg) {
        this.notifications.add(msg);
        Session.updateViews();
    }

    /**
     * Remove a Notification from the Notification list
     *
     * @param index the index to remove
     */
    public void removeNotifications(int index) {
        this.notifications.remove(index);
        Session.updateViews();
    }

    /**
     * Remove a Notification from the Notification list
     *
     * @param item the item to remove
     */
    public void removeNotifications(Object item) {
        this.notifications.remove(item);
        Session.updateViews();
    }
}
