package ba.unsa.etf.rpr.DAL.DAO;

import ba.unsa.etf.rpr.DAL.DTO.Department;
import ba.unsa.etf.rpr.HelpModel.Reference;
import ba.unsa.etf.rpr.Interface.DAOInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO implements DAOInterface {

    private static DepartmentDAO instance;
    private Connection conn = null;

    private ObservableList<Department> departments = FXCollections.observableArrayList();
    private Reference<Connection> connReference = new Reference<>(null);

    private PreparedStatement departmentNameStatement;
    private PreparedStatement getDepStatement;
    private PreparedStatement getMaxId;
    private PreparedStatement addDepartmentStatement;

    private DepartmentDAO() {
        connectToBase(connReference);
        conn = connReference.get();
    }

    public Connection getConn() {
        return conn;
    }

    public static DepartmentDAO getInstance() {
        if(instance == null) instance = new DepartmentDAO();
        return instance;
    }

    @Override
    public void addToList(PreparedStatement statement) {
        departments.clear();
        try {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Department department = new Department(rs.getInt(1), rs.getString(2));
                departments.add(department);
            }
        } catch (SQLException exception) {
        }
    }

    public ObservableList<Department> getDepartments() {
        addToList(getDepStatement);
        return departments;
    }

    @Override
    public void prepareStatements() throws SQLException {
        conn = connReference.get();
        departmentNameStatement = conn.prepareStatement("select department_name from departments where department_id = ?");
        getDepStatement = conn.prepareStatement("select  * from departments");
        getMaxId = conn.prepareStatement("select max(department_id) from departments");
        addDepartmentStatement = conn.prepareStatement("insert into departments(department_id, department_name) values (?,?)");
    }

    public Department getDepartment(int id) {
        try {
            PreparedStatement departmentStatement = conn.prepareStatement("select * from departments where department_id = ?");
            departmentStatement.setInt(1,id);
            ResultSet rs = departmentStatement.executeQuery();
            Department department = new Department(rs.getInt(1), rs.getString(2));
            return department;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void addDepartment(String name) {
        try {
            ResultSet rs = getMaxId.executeQuery();
            int id = rs.getInt(1);
            addDepartmentStatement.setInt(1,id);
            addDepartmentStatement.setString(2, name);
            addDepartmentStatement.executeUpdate();
        } catch (SQLException exception) {
        }
    }
}