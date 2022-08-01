package Toolkit.Notifications;

import Control.Session;

import java.io.Serializable;

public class Notification implements Serializable {

    private final Runnable r;
    private final String name;

    public Notification(String name, Runnable r) {
        this.name = name;
        if (!(r instanceof Serializable))
            throw new ClassCastException("Notification Runnable must be intersection of Runnable and Serializable!");
        this.r = r;
    }

    public String toString() {
        return this.name;
    }

    public void run() {
        r.run();
        Session.getLoggedIn().removeNotifications(this);
    }
}
