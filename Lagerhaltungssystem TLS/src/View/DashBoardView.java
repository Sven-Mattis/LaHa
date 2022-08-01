package View;

import Control.DashBoardControl;
import Toolkit.Search.Search;
import Toolkit.SearchComponent;

import java.awt.*;
import java.util.ArrayList;

public class DashBoardView extends View {

    private final SearchComponent[] searchKeysAndActions = new SearchComponent[]{
            new SearchComponent("Dashboard", () -> {
                DashBoardControl dbc = new DashBoardControl();
                Rectangle b = Search.pop.getView().getBounds();
                Search.pop.getView().dispose();
                dbc.show();
                dbc.getView().setBounds(b);
            })
    };

    /**
     * Creates the Main Menu View
     */
    public DashBoardView() {
        setTitle("Dashboard");
        setMinimumSize(new Dimension(800,500));
    }

    public void applyUIDesign() {

    }

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
