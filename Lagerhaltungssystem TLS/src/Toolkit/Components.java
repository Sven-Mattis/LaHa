package Toolkit;

import java.util.ArrayList;

/**
 * A Map for the Components
 *
 * @param <S> The Type to get an Component Back, Mostly a String
 * @param <T> The Type of Component
 */
public class Components<S, T> {

    private final ArrayList<S> names = new ArrayList<>();
    private final ArrayList<T> types = new ArrayList<>();

    /**
     * This Class is for the Views and there Components
     *
     * @param name access name of the Component
     * @param type the component self
     */
    public void add(S name, T type) {
        names.add(name);
        types.add(type);
    }

    /**
     * Get the name of a Component
     *
     * @param type the Component you looking for the name
     * @return the name of the Component
     */
    public S getName(T type) {
        return names.get(types.indexOf(type));
    }

    /**
     * Get a component from it access name
     *
     * @param name
     * @return
     */
    public T get(S name) {
        return types.get(names.indexOf(name));
    }

    /**
     * Remove the Component with the name
     *
     * @param name
     */
    public void remove(S name) {
        types.remove(get(name));
        names.remove(name);
    }

    /**
     * Remove the Component
     *
     * @param type
     */
    public void removeComponent(T type) {
        names.remove(getName(type));
        types.remove(type);
    }

    /**
     * Get the Size
     *
     * @return the Size as int
     */
    public int size() {
        return names.size() | types.size();
    }

    /**
     * Get a Component at index i
     *
     * @param i
     * @return the Component
     */
    public T getComponentAt(int i) {
        return types.get(i);
    }
}
