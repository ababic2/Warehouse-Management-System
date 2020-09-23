package ba.unsa.etf.rpr.projekat.Interface;

import ba.unsa.etf.rpr.projekat.Exceptions.FailedBaseRegeneration;
import ba.unsa.etf.rpr.projekat.HelpModel.DBConnection;
import ba.unsa.etf.rpr.projekat.HelpModel.Reference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public interface DAOInterface {

    default void baseRegeneration() throws FailedBaseRegeneration {
        Scanner scanner = null;
        Connection conn = DBConnection.getConnection();

        try {
            scanner = new Scanner(new FileInputStream(new File(String.valueOf(getClass().getResource("/sql/baza.db.sql")))));
            String statement = "";
            while (scanner.hasNext()) {
                statement += scanner.nextLine();
                if (statement.charAt(statement.length() - 1) == ';') {
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
        ResultSet rs = null;
        try {
            rs = statement.executeQuery();
            Integer result = rs.getInt(1);

            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    void addToList(PreparedStatement statement);

    void prepareStatements() throws SQLException;

    default void connectToBase(Reference<Connection> conn) {
        Connection newConn = null;
        try {
            newConn = DBConnection.getConnection();
            conn.set(newConn);
            prepareStatements();
        } catch (SQLException throwables) {
            try {
                baseRegeneration();
                try {
                    prepareStatements();
                } catch (SQLException e) {
                }
            } catch (FailedBaseRegeneration failedBaseRegeneration) {
                failedBaseRegeneration.getMessage();
            }
        }
    }
}
