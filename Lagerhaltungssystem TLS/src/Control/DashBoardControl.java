package Control;

import Model.Colorpallet;
import Model.Lagerort;
import Model.Product;
import Toolkit.Graph;
import View.DashBoardView;
import Worker.Worker;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DashBoardControl implements Control<Object, DashBoardView> {

    private final DashBoardView view = new DashBoardView();

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
        return this.view.isVisible();
    }

    /**
     * Creates the Main menu
     */
    public DashBoardControl() {
        // Crash if this Session isn´t Valid
        if (!Session.isValidSession())
            System.exit(0x999);

        int[] yPointsOld = new int[]{182, 112, 23182, 112, 182, 4};

        LocalDate[] xPoints = new LocalDate[(int) Math.pow(30, 2)];
        int[] yPoints = new int[(int) Math.pow(30, 2)];

        for(Product p : Session.getProducts()) {
            if(p.getAmount() <= p.getMelBestand() && !p.isOrdered())
                Session.getLoggedIn().addNotifications("Produkt: " + p.getArtName() + " sollte dringend nachbestellt werden!");
        }

        for (int i = 0; i < Math.min(xPoints.length, yPoints.length); i++) {
            xPoints[Math.abs(i)] = LocalDate.now().minusDays(Math.abs(i - xPoints.length));
            yPoints[i] = i;
        }

        Session.addProduct(new Product("Bolzen", Product.STK,"445512541", new Lagerort("a", "2", "Rot"), 10, 120, 36, 98, 2, 2, 19));
        Session.addProduct(new Product("Schraube", "Stk", "ka101", new Lagerort("a", "4", "Rot"),10,123,123,123, 123, 123, 19));
        Session.addProduct(new Product("Kaffee", "Liter", "5844a7", new Lagerort("c", "2", "Blue"), 10,12, 12, 60, 11, 49, 19));

        LocalDate[] finalXPoints = xPoints;
        int[] finalYPoints = yPoints;
        Session.getProducts().forEach(p -> {
                p.setxPoints(finalXPoints);
                p.setyPoints(finalYPoints);
        });



        Session.getProducts().forEach(p -> {
            Graph g = new Graph(view, p.getxPoints(), p.getyPoints(), p);
            g.setPreferredSize(new Dimension(300, 150));
            g.setBackground(null);
            view.addComponent(p.getArtNumber(),g);
        });
    }

    /**
     * Get the View
     *
     * @return the View
     */
    @Override
    public DashBoardView getView() {
        return this.view;
    }

    /**
     * Get the Items of the View and the Controller
     *
     * @return the Items
     */
    @Override
    public ArrayList getItems() {
        return null;
    }

    /**
     * Set the Searched items
     *
     * @param searched
     */
    @Override
    public void setSearchedItems(ArrayList searched) {

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
