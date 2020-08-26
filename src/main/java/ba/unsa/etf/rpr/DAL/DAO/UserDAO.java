package ba.unsa.etf.rpr.DAL.DAO;

import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.HelpModel.Account;
import ba.unsa.etf.rpr.HelpModel.Reference;
import ba.unsa.etf.rpr.Interface.DAOInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAOInterface {

    private static UserDAO instance;
    private static Connection conn = null;

    private static PreparedStatement usernameStatement;
    private static PreparedStatement firmStatement;
    private static PreparedStatement countEmployees;
    private static PreparedStatement employeesStatement;
    private static PreparedStatement departmentNameStatement;

    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private Reference<Connection> connReference = new Reference<>(null);


    private UserDAO() {
        connectToBase(connReference);
        conn = connReference.get();
    }

    public static UserDAO getInstance() {
        if(instance == null) instance = new UserDAO();
        return instance;
    }

    public Integer getCount() {
        return count(countEmployees);
    }

    public ObservableList<Employee> getInfoList() {
        addToList(employeesStatement);
        return employees;
    }

    public Account getPasswordForUsername(String username) {
        Account account;
        try {
            usernameStatement.setString(1,username);
            ResultSet rs = usernameStatement.executeQuery();
            //if(!rs.next()) return null;
            if(rs.next()) {
                account = new Account(username, rs.getString("password"), rs.getString("access_level"));
                return account;
            } else {
                firmStatement.setString(1,username);
                ResultSet rs1 = firmStatement.executeQuery();
                if(rs1.next()) {
                    account = new Account(username, rs1.getString("password"), rs1.getString("access_level"));
                    return account;
                }
                else return null;
            }

        } catch (SQLException throwables) {
            System.out.println("IZUZETAK u passwordForUsername1");
            return null;
        }
    }

    private String getDepartmentName(int id) {
        try {
            departmentNameStatement.setInt(1,id);
            ResultSet rs = departmentNameStatement.executeQuery();
            if(rs.next()) {
                return rs.getString("department_name");
            } else return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void prepareStatements() throws SQLException {
        conn = connReference.get();
        usernameStatement = conn.prepareStatement("select password, access_level from employees where username = ?");
        firmStatement = conn.prepareStatement("select password, access_level from firms where username = ?");
        countEmployees = conn.prepareStatement("select count(employee_id) from employees");
        employeesStatement = conn.prepareStatement("select * from employees");
        departmentNameStatement = conn.prepareStatement("select department_name from departments where department_id = ?");
    }

    public void addToList(PreparedStatement statement) {
        try {
            if(employees.size() > 0) {
                employees.clear();
            }
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Employee modelEmployee =
                        new Employee(rs.getInt(1), rs.getString(2), rs.getString(3),
                                rs.getString(4),rs.getString(5), rs.getString(6),
                                rs.getString(7), rs.getInt(8), rs.getString(9),null);
                modelEmployee.setDepartmentName(getDepartmentName(rs.getInt(10)));
                employees.add(modelEmployee);
            }
        } catch (SQLException throwables) {
            throwables.getMessage();
        }
    }

}
