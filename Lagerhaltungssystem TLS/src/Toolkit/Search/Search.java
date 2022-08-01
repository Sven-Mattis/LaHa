package Toolkit.Search;

import Toolkit.SearchComponent;
import View.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class Search {

    public static PopUpMatchView pop;
    public static int activeNumber = 0;

    /**
     * Find the searched String, if pressed enter and only one entry is found then it will be executed
     * @param str the searched String
     * @param v the View where the Search is established
     * @param enter
     * @return null value or empty list to check in the view what happened
     */
    public static ArrayList<String> find(String str, View v, boolean enter) {

        if (pop == null)
            throw new RuntimeException("No Popup defined!");

        if (str.equals(""))
            return new ArrayList<>();

        ArrayList<SearchComponent> list = new ArrayList<>();

        Search.GET(list);

        var context = new Object() {
            SearchComponent[] matches = new SearchComponent[0];
        };

        list.forEach(sc -> {
            if (sc.getName().toLowerCase().contains(str.toLowerCase())) {
                SearchComponent[] nmatches = new SearchComponent[context.matches.length + 1];
                for (int i = 0; i <= context.matches.length; i++) {
                    if (i < context.matches.length)
                        nmatches[i] = context.matches[i];
                    else
                        nmatches[i] = sc;
                }
                context.matches = nmatches;
            }
        });

        pop.set(context.matches, str);

        if (context.matches.length == 1 && enter)
            context.matches[0].getAction().run();

        if (enter)
            activeNumber = -1;

        return null;
    }

    /**
     * Parse every Package
     * Get every class of every package, with nested classes as well
     * parse the class to a stream and get every field and every method,
     * while it checks it marks two booleans and if they both are true,
     * then get the method "get_searchKeysAndActions" from the class
     * and invoke it with an new instance of it self
     *
     * @param list the List to append the SearchComponent´s
     */
    private static void GET(ArrayList<SearchComponent> list) {

        // Get every Package
        Package[] packages = Package.getPackages();

        // Loop through the packages
        for (Package definedPackage : packages) {
            try {
                // Get the Package
                InputStream CLIS = ClassLoader.getSystemClassLoader().getResourceAsStream(definedPackage.getName());

                // Read its content
                BufferedReader bfr = new BufferedReader(new InputStreamReader(CLIS));
                bfr.lines().forEach(e -> {

                    try {
                        boolean[] hasSearchField = new boolean[]{false};
                        boolean[] hasSearchMethod = new boolean[]{false};

                        // Get the Class based on its qualified name
                        Class<?> c = Class.forName(GET_QUALIFEDNAME(e, definedPackage.getName()));

                        // Parse to check if both component are in
                        Arrays.stream(c.getDeclaredFields()).forEach(field -> hasSearchField[0] |= field.getName().equals("searchKeysAndActions"));
                        Arrays.stream(c.getDeclaredMethods()).forEach(method -> hasSearchMethod[0] |= method.getName().equals("get_searchKeysAndActions"));

                        // get the SearchComponent for the class
                        if (hasSearchField[0] && hasSearchMethod[0])
                            c.getMethod("get_searchKeysAndActions", ArrayList.class).invoke(c.newInstance(), list);

                    } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException ignored) {
                    }
                });
            } catch (NullPointerException ignored) {
            }
        }
    }

    /**
     * Get the Qualified name of a class
     *
     * @param className   the class name, does´nt matter if with extension or not
     * @param packageName the package name, like com.sun.
     * @return the Qualified name of this class as String
     */
    private static String GET_QUALIFEDNAME(String className, String packageName) {
        return packageName + "." + className.replace(".class", "");
    }
}
