package Toolkit.Database;

import Control.Session;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

    public class Connection {

        private final java.sql.Connection con;
        private final Statement statement;

        private final String ip = "127.0.0.1";

        /**
         * Creates a connection to the MySQL Database
         * @param database the name of the database
         * @param user the database user
         * @param password the database userpassword
         * @throws SQLException
         */
        public Connection (String database, String user, String password) throws SQLException {
            con = DriverManager.getConnection("jdbc:mysql://"+ip+":"+ Session.PORT +"/" + database, user, password);
            statement = con.createStatement();
        }

        public void close() throws SQLException {
            con.close();
        }

        /**
         * Execute a query and get the ResultSet of it
         * @param queryString the search string
         * @return the ResultSet for the query
         * @throws SQLException
         */
        public ResultSet QueryResult(String queryString) throws SQLException {
            try {
                return statement.executeQuery(queryString);
            } catch (SQLException ex) {
                statement.execute(queryString);
            }
            return null;
        }

        /**
         * Print the result of the Query as a string, not System.out.println
         * @param queryString the search string
         * @return the string
         * @throws SQLException
         */
        public String Query(String queryString) throws SQLException {
            ResultSet rs = this.QueryResult(queryString);

            if(rs == null)
                return "No ResultSet given!";

            StringBuilder str = new StringBuilder();

            for (int i = 1; i < rs.getMetaData().getColumnCount()+1; i++) {
                str.append(rs.getMetaData().getColumnLabel(i) + (i < rs.getMetaData().getColumnCount() ? "\t|\t":""));
            }

            while(rs.next()) {
                str.append("\n");
                for (int i = 1; i < rs.getMetaData().getColumnCount()+1; i++) {
                    str.append(rs.getString(i) + (i < rs.getMetaData().getColumnCount() ? "\t|\t":""));
                }
            }

            return str.toString();
        }

    }
