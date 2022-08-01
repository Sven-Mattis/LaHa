package Control;

import Model.Error;
import View.ErrorView;

import java.util.ArrayList;

public class ErrorControl implements Control<Object, ErrorView> {

    private final ErrorView view;

    /**
     * To show an Error
     *
     * @param owner     the owner, where the Error happens
     * @param msg       the Error message
     * @param errorCode the Error code
     * @param error     What to do on close?
     */
    public ErrorControl(Control<?, ?> owner, String msg, int errorCode, Error error) {
        this.view = new ErrorView(msg, errorCode, error);

        view.setAlwaysOnTop(true);

        view.addAction(() -> {
            if (error == Error.Retry_On_Exit)
                owner.retry();
            else if (error == Error.Kill_On_Exit)
                System.exit(errorCode);
            else
                owner.remove();
        });
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
    public ErrorView getView() {
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
