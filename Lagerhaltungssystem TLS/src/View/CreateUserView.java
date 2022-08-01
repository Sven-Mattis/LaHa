package View;

import Control.CreateUserControl;
import Toolkit.SearchComponent;

import java.awt.*;
import java.util.ArrayList;

public class CreateUserView extends View {

    public static SearchComponent[] searchKeysAndActions = new SearchComponent[]{
            new SearchComponent("Nutzer Erstellen", () -> new CreateUserControl().getView().setVisible(true))
    };


    /**
     * Create the UserCreateView
     */
    public CreateUserView() {
        removeMenus(true);
        setLayout(new GridLayout(6, 2));
    }

    /**
     * Update the View
     *
     * @param args objects to tell explicit to update
     */
    @Override
    public void update(Object... args) {

    }

    @Override
    public void get_searchKeysAndActions(ArrayList<SearchComponent> field) {
        for (SearchComponent searchKeysAndAction : searchKeysAndActions) {
            field.add(searchKeysAndAction);
        }
    }
}
