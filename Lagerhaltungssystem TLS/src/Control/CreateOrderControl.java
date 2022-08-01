package Control;

import View.CreateOrderView;

import java.util.ArrayList;

public class CreateOrderControl implements Control<Object, CreateOrderView>{
    /**
     * Say the View to show
     */
    @Override
    public void show() {
        
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
    public CreateOrderView getView() {
        return null;
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
