package Control;

import Model.*;
import Model.Error;
import Toolkit.Database.Connection;
import Toolkit.Database.GetUser;
import Toolkit.Database.Install;
import Toolkit.Search.Search;
import Toolkit.SearchComponent;
import View.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Session extends Thread {

    private static Process mysqlProc;

    public static String PORT = ""+getOpenPort();

    private static final String SQL_INSTALL_DIR = "C:\\xampp\\XamppSQL\\mysql";// getSqlInstallDir();
    private static String commandStart = SQL_INSTALL_DIR + "/bin/mysqld -P";


    private static final ArrayList<Product> products = new ArrayList<>();
    private static final ArrayList<View> views = new ArrayList<>();
    private static boolean validSession = false;
    private static User loggedIn;
    private static Session session;
    private final SearchComponent[] searchKeysAndActions = new SearchComponent[]{
            new SearchComponent("Ausloggen", Session::InvalidateSession),
            new SearchComponent("Sitzung prüfen", () -> {
                if (!Session.isValidSession())
                    Session.InvalidateSession();
                else
                    new Toast(Search.pop.getView(), "Die Sitzung wurde überprüft!", 1000).run();
            }),
            new SearchComponent("Check Session", () -> {
                if (!Session.isValidSession())
                    Session.InvalidateSession();
                else
                    new Toast(Search.pop.getView(), "Die Sitzung wurde überprüft!", 1000).run();
            }),
            new SearchComponent("Logout", Session::InvalidateSession)
    };
    private int timer = 300;
    private boolean kill = false;

    /**
     * Creates a new Session
     * To create an Automatically Logout after 5 Minutes of inactivity
     */
    public Session() {
        Session.session = this;
        this.start();
    }

    /**
     * Add a User to the List
     *
     * @param user the new User
     */
    public static void addUser(User user) {

    }

    public static ArrayList<View> getViews() {
        return Session.views;
    }

    /**
     * Reset the Timer for logout
     */
    public static void resetTimer() {
        if (session == null)
            return;
        Session.session.timer = 300;
    }

    /**
     * Get boolean of the Session Validity
     *
     * @return true, if the Session is Valid
     */
    public static boolean isValidSession() {
        return validSession;
    }

    /**
     * Validate the Session if the Username and Password is Valid
     *
     * @param name     the Username
     * @param password the password of the User with this Password
     */
    public static void ValidateSession(String name, HashString password) {
        try {
            GetUser u = new GetUser(name, password.get());
            if(u.exists()) {
                Session.validSession = u.exists();
                Session.session = new Session();
                Session.loggedIn = new User(name,"Firstname","Lastname", Gender.Maennlich, password, u.getGroup());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Invalidate the Session, like on logout
     */
    public static void InvalidateSession() {
        Session.session.kill();

        for (Object v : views.toArray())
            ((View) v).dispose();

        LoginControl l = new LoginControl();
        l.show();
        new Toast(l.getView(), "Logged out from System", 10000).run();

        validSession = false;
    }

    /**
     * Get the Logged in User
     *
     * @return the logged in User
     */
    public static User getLoggedIn() {
        if (!Session.isValidSession())
            throw new SecurityException("No Valid Session");
        return loggedIn;
    }

    /**
     * Add a new View to the Session
     *
     * @param v the new View
     */
    public static void addView(View v) {
        views.add(v);
    }

    /**
     * Remove a view from the Session
     *
     * @param v the View that needs the removed
     */
    public static void removeView(View v) {
        views.remove(v);
        Runtime.getRuntime().gc();
        if(Session.getViews().size() == 0)
            System.exit(0);
    }

    /**
     * Repaint all views
     */
    public static void updateViews() {
        views.forEach(View::repaint);
    }

    /**
     * Get the Products
     * @return a ArrayList with all Products
     */
    public static ArrayList<Product> getProducts() {
        return Session.products;
    }

    public static void addProduct (Product p) {
        var obj = new Object() {
            public boolean artNumberSame = false;
        };

        products.forEach(e -> obj.artNumberSame |= p.getArtNumber().equals(e.getArtNumber()));

        if(!obj.artNumberSame)
            Session.products.add(p);
        else
            loggedIn.addNotifications("Artikel Existiert bereits! " + p.getArtName());
    }
    
    public Product getProdudt(String name) {
        for (Product product : Session.products) {
            if(product.getArtName().equals(name))
                return product;
        }

        return null;
    }

    @Override
    public void run() {
        while (timer > 0 && !kill) {
            try {
                if (views.size() == 0)
                    return;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer--;
        }
        kill = false;
        if(Session.isValidSession())
            Session.InvalidateSession();
    }

    public void kill() {
        this.kill = true;
    }

    public void get_searchKeysAndActions(ArrayList<SearchComponent> field) {
        for (SearchComponent searchKeysAndAction : searchKeysAndActions) {
            field.add(searchKeysAndAction);
        }
        for (Product product : products) {
            field.add(new SearchComponent("Produkt: " + product.getArtName(), () -> {
                new ProductView(product).setVisible(true);
            }));
        }
    }

    public static String getSqlInstallDir() {
        String str = Session.class.getClassLoader().getResource("").getPath() + "SQL";
        str = str.replaceFirst("/", "");
        str = str.replaceAll("%20", " ");
        return str;
    }

    private static int getOpenPort() {
        int openPort = 0;
        int i = 3200;

        long startTime = System.currentTimeMillis();

        // Start Loop for check which port is free to use
        // Also check the time it takes and stop if no port was found in 10s
        while(openPort == 0 && System.currentTimeMillis()-startTime < 10000) {

                try {
                    ServerSocket s = new ServerSocket(i);
                    s.close();
                    openPort = i;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            i++;
        }

        // Wait 1s to make sure the server is ready to go
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(openPort == 0)
            System.exit(0x99);

        return openPort;
    }

    public static void startServer() {
        try {
            mysqlProc = Runtime.getRuntime().exec(commandStart+Session.PORT);
            Thread.sleep(100);
            System.out.println(String.format("MySQL server started successfully on Port %s!", Session.PORT));
            Runtime.getRuntime().addShutdownHook(new Thread(Session::killServer));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(0x999);
        }

        try {
            Connection c = new Connection("LaHa", "root", "");
            c.close();
        } catch (SQLException throwables) {
            try {
                new Install();
            } catch (SQLException e) {
                new ErrorControl(null,"No DatabaseSet found and a error occurred during operation!", 0x701, Error.Kill_On_Exit).show();
                e.printStackTrace();
            }
        }
    }

    public static void killServer() {
        mysqlProc.children().forEach(e -> e.destroy());
        mysqlProc.destroy();
        System.out.println("Server Closed!");
    }
}