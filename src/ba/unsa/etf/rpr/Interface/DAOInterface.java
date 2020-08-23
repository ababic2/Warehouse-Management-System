package ba.unsa.etf.rpr.Interface;

import ba.unsa.etf.rpr.DAL.DBConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public interface DAOInterface {
    default void baseRegeneration() {
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
                            throwables.printStackTrace();
                        }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Exception when regenerating base !");
            }
        }
    Integer count();
    void addToList();
}
