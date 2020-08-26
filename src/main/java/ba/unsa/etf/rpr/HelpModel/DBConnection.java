package ba.unsa.etf.rpr.HelpModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;
    private static final String URL = "jdbc:sqlite:baza.db";

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
