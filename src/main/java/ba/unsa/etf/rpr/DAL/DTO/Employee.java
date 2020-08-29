package ba.unsa.etf.rpr.DAL.DTO;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee extends Object implements Comparable {

    private SimpleIntegerProperty employeeId; //ne mijenja se
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty eMail;
    private SimpleIntegerProperty salary;
    private SimpleStringProperty hireDate;

    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty accessLevelString;

    private Department department;

    @Override
    public int compareTo(Object o) {
        Employee employee = (Employee) o;
        if(this.getFirstName().equals(employee.getFirstName()) &&
            this.getLastName().equals(employee.getLastName()) &&
            this.getEmployeeId() == employee.getEmployeeId())return  0;
        return -1;
    }

    private enum AccessLevel{ADMIN, EMPLOYEE;};
    private AccessLevel accessLevel;

    public Employee() {
    }

    public Employee(int employeeId, String firstName, String lastName,
                    String username, String password, String accessLevel,
                    String eMail, int salary, String hireDate, Department department
                    ) {
        this.employeeId = new SimpleIntegerProperty(employeeId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.eMail = new SimpleStringProperty(eMail);
        this.salary = new SimpleIntegerProperty(salary);
        this.hireDate = new SimpleStringProperty(hireDate);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.department = department;
        if(accessLevel.toLowerCase().equals("admin")) {
            this.accessLevel = AccessLevel.ADMIN;
        } else {
            this.accessLevel = AccessLevel.EMPLOYEE;
        }
        this.accessLevelString = new SimpleStringProperty(accessLevel);
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public SimpleIntegerProperty employeeIdProperty() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId.set(employeeId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String geteMail() {
        return eMail.get();
    }

    public SimpleStringProperty eMailProperty() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail.set(eMail);
    }

    public int getSalary() {
        return salary.get();
    }

    public SimpleIntegerProperty salaryProperty() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
    }

    public String getHireDate() {
        return hireDate.get();
    }

    public SimpleStringProperty hireDateProperty() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate.set(hireDate);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getAccessLevelString() {
        return accessLevelString.get();
    }

    public SimpleStringProperty accessLevelStringProperty() {
        return accessLevelString;
    }

    public void setAccessLevelString(String accessLevelString) {
        this.accessLevelString.set(accessLevelString);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", eMail=" + eMail +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", username=" + username +
                ", password=" + password +
                ", departmentName='" + department.getDepartmentName() + '\'' +
                ", accessLevel=" + accessLevel +
                '}';
    }
}