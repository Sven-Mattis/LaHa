package View;

import Toolkit.SearchComponent;

import java.awt.*;
import java.util.ArrayList;

public class LoadingView extends View {

    private final LoadingWheel loadingWheel = new LoadingWheel(this);
    public int maxPercentage = 1;
    int i = 1;

    public LoadingView() {
        setLayout(new GridLayout(1, 1));
        add(loadingWheel);
    }

    @Override
    public void update(Object... args) {
        i = (Integer) args[0];
        loadingWheel.repaint();
    }

    @Override
    public void get_searchKeysAndActions(ArrayList<SearchComponent> field) {

    }

    public void setText(String txt) {
        loadingWheel.setText(txt);
    }

    public void setMaxPercentage(int maxPercentage) {
        i = 1;
        this.maxPercentage = maxPercentage;
    }
}
