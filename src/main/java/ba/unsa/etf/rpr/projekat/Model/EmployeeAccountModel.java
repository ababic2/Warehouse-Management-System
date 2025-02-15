package ba.unsa.etf.rpr.projekat.Model;

import ba.unsa.etf.rpr.projekat.DAL.DAO.DepartmentDAO;
import ba.unsa.etf.rpr.projekat.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.projekat.DAL.DTO.Department;
import ba.unsa.etf.rpr.projekat.DAL.DTO.Employee;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class EmployeeAccountModel {

    private final UserDAO userDAO = UserDAO.getInstance();
    private final DepartmentDAO departmentDAO = DepartmentDAO.getInstance();
    private ObservableList<Employee> employees = userDAO.getInfoList();
    private final ObjectProperty<Employee> currentEmployee = new SimpleObjectProperty<>();
    private ObservableList<Department> departments = departmentDAO.getDepartments();

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ObservableList<Employee> employees) {
        this.employees = employees;
    }

    public ObservableList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ObservableList<Department> departments) {
        this.departments = departments;
    }

    public Employee getCurrentEmployee() {
        return currentEmployee.get();
    }

    public ObjectProperty<Employee> currentEmployeeProperty() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee.set(currentEmployee);
    }

    public void changeCurrent(int page) {
        currentEmployee.set(employees.get(page));
    }

    public void deleteUserWithId(int id) {
        userDAO.deleteUserWithId(id);

    }

    public void updateEmployee() {
        userDAO.updateEmployee(currentEmployee.getValue().getEmployeeId(), currentEmployee.getValue().getFirstName(),
                currentEmployee.getValue().geteMail(), currentEmployee.getValue().getSalary(),
                currentEmployee.getValue().getDepartment(), currentEmployee.getValue().getHireDate(),
                currentEmployee.getValue().getAccessLevelString());
    }


    public void addEmployeeToBase(Employee employee) {
        userDAO.addEmployee(employee.getEmployeeId(), employee.getFirstName(),
                employee.getLastName(), employee.getUsername(), employee.getPassword(),
                employee.getAccessLevelString(), employee.geteMail(), employee.getSalary(), employee.getHireDate(),
                employee.getDepartment().getDepartmentId());
    }

    public int getMaxId() {
        return userDAO.getMaxID();
    }

    public Connection getConn() {
        return userDAO.getConn();
    }
}
