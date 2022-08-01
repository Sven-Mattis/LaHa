package Toolkit.Database;

import Model.Gender;
import Model.HashString;

import java.sql.SQLException;

public class CreateUser {

    public CreateUser(String username, String firstname, String lastname, Gender divers, HashString password, int group) throws SQLException {
        Connection c = new Connection("LaHa", "users", "de56ca7458a10051a8411e7fe1fa19f616418753c4140a834fdda455898f1bf4");
        c.QueryResult(String.format("INSERT INTO LaHa.users (USERNAME, PASSWORD, FirstName, LastName, UserGroup, Gender) VALUES('%s','%s','%s','%s','%s','%s');",username, password.get(), firstname, lastname, group, divers));
    }
}
