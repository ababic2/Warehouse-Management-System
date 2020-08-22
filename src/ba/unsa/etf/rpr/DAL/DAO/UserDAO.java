package ba.unsa.etf.rpr.DAL.DAO;

import ba.unsa.etf.rpr.DAL.DBConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance;
    private Connection conn = null;

    private PreparedStatement usernameStatement;
    private PreparedStatement firmStatement;

    public static UserDAO getInstance() {
        if(instance == null) instance = new UserDAO();
        return instance;
    }
    private UserDAO() {
        try {//zbog baze, if its empty
            conn = DBConnection.getConnection();

            System.out.println("HEEEE");
            //conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            System.out.println("NEMAAA");
            usernameStatement = conn.prepareStatement("select password from employees where username = ?");
            firmStatement = conn.prepareStatement("select password from firms where username = ?");
        } catch (SQLException throwables) {
            regenerisiBazu();
            try {
                usernameStatement = conn.prepareStatement("select password from employees where username = ?");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                firmStatement = conn.prepareStatement("select password from firms where username = ?");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String passwordForUsername(String username) {
        ResultSet rs = null;
        try {
            usernameStatement.setString(1,username);
            rs = usernameStatement.executeQuery();
            //if(!rs.next()) return null;
            if (rs.getString("password") != null) {
                return rs.getString("password");
            }
        } catch (SQLException throwables) {
            System.out.println("IZUZETAK u passwordForUsername");
            try {
                firmStatement.setString(1,username);
                rs = firmStatement.executeQuery();
                if(rs.getString("password") != null) {
                    return rs.getString("password");
                } else
                    return null;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("IZUZETAK u passwordForUsername");
                return null;
            }
        }
        return null;
    }

    private void regenerisiBazu() {
        Scanner scanner = null;
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
            e.printStackTrace();
        }
    }
}
