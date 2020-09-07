package ba.unsa.etf.rpr.DAL.DAO;

import ba.unsa.etf.rpr.DAL.DTO.Department;
import ba.unsa.etf.rpr.HelpModel.Account;
import ba.unsa.etf.rpr.Interface.DAOInterface;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.HelpModel.Reference;
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
    private DepartmentDAO departmentDAO = DepartmentDAO.getInstance();


    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private Reference<Connection> connReference = new Reference<>(null);


    private UserDAO() {
        connectToBase(connReference);
        conn = connReference.get();
    }

    public static UserDAO getInstance() {
        if (instance == null) instance = new UserDAO();
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
            usernameStatement.setString(1, username);
            ResultSet rs = usernameStatement.executeQuery();
            //if(!rs.next()) return null;
            if (rs.next()) {
                account = new Account(username, rs.getString("password"), rs.getString("access_level"));
                return account;
            } else {
                firmStatement.setString(1, username);
                ResultSet rs1 = firmStatement.executeQuery();
                if (rs1.next()) {
                    account = new Account(username, rs1.getString("password"), rs1.getString("access_level"));
                    return account;
                } else return null;
            }

        } catch (SQLException throwables) {
            System.out.println("IZUZETAK u passwordForUsername1");
            return null;
        }
    }

    public void prepareStatements() throws SQLException {
        conn = connReference.get();
        usernameStatement = conn.prepareStatement("select password, access_level from employees where username = ?");
        firmStatement = conn.prepareStatement("select password, access_level from firms where username = ?");
        countEmployees = conn.prepareStatement("select count(employee_id) from employees");
        employeesStatement = conn.prepareStatement("select * from employees");
    }

    public void addToList(PreparedStatement statement) {
        try {
            if (employees.size() > 0) {
                employees.clear();
            }
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee modelEmployee =
                        new Employee(rs.getInt(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5), rs.getString(6),
                                rs.getString(7), rs.getInt(8), rs.getString(9), null);
                modelEmployee.setDepartment(departmentDAO.getDepartment(rs.getInt(10)));
                employees.add(modelEmployee);
            }
        } catch (SQLException throwables) {
        }
    }

    public void deleteUserWithId(int id) {
        try {
            PreparedStatement deleteStatement = conn.prepareStatement("delete from employees where employee_id = ?");
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException exception) {
        }

    }

    public void updateEmployee(int employeeId, String firstName, String mail, int salary, Department department, String hireDate, String accessLevel) {
        try {
            PreparedStatement updateEmployee =
                    conn.prepareStatement("update employees set first_name = ?, e_mail = ?, salary = ?, department_id = ?, hire_date = ?, access_level = ? where employee_id = ? ");
            updateEmployee.setString(1, firstName);
            updateEmployee.setString(2, mail);
            updateEmployee.setInt(3, salary);
            updateEmployee.setInt(4, department.getDepartmentId());
            updateEmployee.setString(5, hireDate);
            updateEmployee.setString(6, accessLevel);
            updateEmployee.setInt(7, employeeId);
            updateEmployee.executeUpdate();
        } catch (SQLException exception) {

        }

    }


    public void addEmployee(int employeeId, String firstName, String lastName, String username, String password,
                            String accessLevelString, String mail,int salary, String hireDate, int departmentId) {
        PreparedStatement addEmployeeStatement = null;
        try {
            addEmployeeStatement = conn.prepareStatement("insert into employees(employee_id, first_name, last_name, username, password, access_level, e_mail,salary,hire_date,department_id) values (?,?,?,?,?,?,?,?,?,?)");
            addEmployeeStatement.setInt(1,employeeId);
            addEmployeeStatement.setString(2,firstName);
            addEmployeeStatement.setString(3,lastName);
            addEmployeeStatement.setString(4,username);
            addEmployeeStatement.setString(5,password);
            addEmployeeStatement.setString(6,accessLevelString);
            addEmployeeStatement.setString(7, mail);
            addEmployeeStatement.setInt(8,salary);
            addEmployeeStatement.setString(9,hireDate);
            addEmployeeStatement.setInt(10,departmentId);
            addEmployeeStatement.executeUpdate();
        } catch (SQLException exception) {

        }

    }

    public int getMaxID() {
        try {
            PreparedStatement maxIdStatement = conn.prepareStatement("select max(employee_id) from employees");
            ResultSet rs = maxIdStatement.executeQuery();
            return rs.getInt(1);
        } catch (SQLException exception) {
            return 0;
        }
    }

    public Connection getConn() {
        return conn;
    }
}