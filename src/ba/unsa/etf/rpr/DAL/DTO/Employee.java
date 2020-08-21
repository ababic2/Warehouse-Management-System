package ba.unsa.etf.rpr.DAL.DTO;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String eMail;
    private int salary;
    private String hireDate;

    private String username;
    private String password;

    private enum accessLevel{ADMIN, EMPLOYEE};
    private Department department;

    public Employee() {
    }

    public Employee(int employeeId, String firstName, String lastName,
                    String eMail, int salary, String hireDate, String username,
                    String password, Department department) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.salary = salary;
        this.hireDate = hireDate;
        this.username = username;
        this.password = password;
        this.department = department;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}