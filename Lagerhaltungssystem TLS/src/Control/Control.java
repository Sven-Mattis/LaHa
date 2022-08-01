package Control;

import java.util.ArrayList;

/**
 * The base of every Controller
 *
 * @param <T> Type of item in the Controller and the View
 * @param <V> the View
 */
public interface Control<T, V> {

    /**
     * Say the View to show
     */
    void show();

    /**
     * Get the boolean value of the View Visibility
     *
     * @return the boolean value of the View Visibility
     */
    boolean viewVisible();

    /**
     * Get the View
     *
     * @return the View
     */
    V getView();

    /**
     * Get the Items of the View and the Controller
     *
     * @return the Items
     */
    ArrayList<T> getItems();

    /**
     * Set the Searched items
     */
    void setSearchedItems(ArrayList<T> searched);

    /**
     * Say the View to update
     */
    void updateView();

    /**
     * Remove the Controller
     */
    void remove();

    /**
     * set the MenuBar
     */
    void setMenu();

    /**
     * Set the Action that
     */
    void setOnError(Runnable errorAction);

    /**
     * Get the boolean of if the view has encountered an Error
     *
     * @return the boolean of if the view has encountered an Error
     */
    boolean hasError();

    /**
     * Get if the View has Focus
     *
     * @return if the View has Focus as a boolean
     */
    boolean hasFocus();

    /**
     * Get the Loaded value
     *
     * @return the Loaded value as a boolean
     */
    boolean isLoaded();

    /**
     * Retry to create the View and Controller
     */
    void retry();

}
