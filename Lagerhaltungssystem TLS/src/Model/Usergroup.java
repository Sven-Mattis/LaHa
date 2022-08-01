package Model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Groups for the Users
 */
public class Usergroup {

    public static final int User = 0,
            UserPlus = 1,
            Admin = 5,
            Owner = 100;

    /**
     * Get the Group of a String
     * <p>
     * All Fields that are Declared will be checked for matched,
     * based on their names
     *
     * @param groupStr the String to look for a group
     * @return the value as int of this group
     * @throws IllegalAccessException will be thrown if there is no matching group
     */
    public static int parseGroup(String groupStr) throws IllegalAccessException {
        // Read all fields of this class
        for (Field declaredField : Usergroup.class.getDeclaredFields()) {
            // if field name is equal to the searched group
            if (declaredField.getName().equals(groupStr))
                // return the value of the field
                return (int) declaredField.get("value");
        }
        // If Group don´t exists
        throw new InputMismatchException("Cannot find Group " + groupStr);
    }

    /**
     * Check if a Group, representing the given String, is available
     *
     * @param group the Group to look for
     * @return true if there is a group for the String
     */
    public static boolean isGroup(String group) {
        return Arrays.stream(Usergroup.class.getDeclaredFields()).anyMatch(field -> field.getName().contains(group));
    }
}
