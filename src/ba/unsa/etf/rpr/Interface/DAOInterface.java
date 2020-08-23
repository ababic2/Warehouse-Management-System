package ba.unsa.etf.rpr.Interface;

import Exceptions.FailedBaseRegeneration;
import ba.unsa.etf.rpr.HelpModel.DBConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public interface DAOInterface {
    default void baseRegeneration() throws FailedBaseRegeneration {
            Scanner scanner = null;
            Connection conn = DBConnection.getConnection();

        try {
                scanner = new Scanner(new FileInputStream("baza.db.sql"));
                String statement = "";
                while(scanner.hasNext()) {
                    statement += scanner.nextLine();
                    if(statement.charAt(statement.length() - 1) == ';') {
                        try {
                            Statement stmt = conn.createStatement();
                            stmt.execute(statement);
                            statement = "";
                        } catch (SQLException throwables) {
                        }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                throw new FailedBaseRegeneration("Failed base regeneration!");
            }
        }
    default Integer count(PreparedStatement statement) {
            try {
                ResultSet rs = statement.executeQuery();
                return rs.getInt(1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
    }

    void addToList();
    void prepareStatements() throws SQLException;
}
