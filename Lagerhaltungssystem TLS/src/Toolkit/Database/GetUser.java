package Toolkit.Database;

import Model.HashString;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUser {

    private final String username, password;
    private final Connection con = new Connection("LaHa", "users", "de56ca7458a10051a8411e7fe1fa19f616418753c4140a834fdda455898f1bf4");

    /**
     * Creates a connection to the UserTable
     * @param username the username that is requested
     * @param password the password for the user that is requested
     * @throws SQLException
     */
    public GetUser (String username, String password) throws SQLException {
        this.username = username;
        this.password = password;
    }

    /**
     * Check if the User exists in the table
     * @return boolean
     */
    public boolean exists() {
        try {
            ResultSet rs = con.QueryResult("SELECT COUNT(1) FROM users WHERE USERNAME='"+String.format(username)+"' AND PASSWORD='"+String.format(password)+"'");

            rs.next();

            return rs.getBoolean(rs.getMetaData().getColumnCount());

        } catch (SQLException throwables) {
            System.err.println("An Error Occured, please make sure that the Server is Running");
        }
        return false;
    }

    /**
     * Get the user group of the user
     * @return integer
     */
    public int getGroup() {
        try {
            ResultSet rs = con.QueryResult("SELECT UserGroup FROM users WHERE USERNAME='"+String.format(username)+"' AND PASSWORD='"+String.format(password)+"'");

            rs.next();

            return rs.getInt(rs.getMetaData().getColumnCount());

        } catch (SQLException throwables) {
            System.err.println("An Error Occured, please make sure that the Server is Running");
        }
        return 0;
    }

}
