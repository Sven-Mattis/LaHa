package Toolkit;

public class SearchComponent {

    private final Runnable action;
    private final String name;

    public SearchComponent(String name, Runnable action) {
        this.action = action;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Runnable getAction() {
        return action;
    }
}
