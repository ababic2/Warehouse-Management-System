package ba.unsa.etf.rpr.HelpModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:/home/ababic/IdeaProjects/JavaFX/baza.db";

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
