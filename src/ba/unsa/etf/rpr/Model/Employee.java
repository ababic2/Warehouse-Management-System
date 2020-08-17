package ba.unsa.etf.rpr.Model;

import javafx.beans.property.SimpleStringProperty;

public class Employee {

    private SimpleStringProperty username;
    private SimpleStringProperty pass;
    private SimpleStringProperty email;
    private enum access {ADMIN, EMPLOYEE};

    public Employee() {
    }

    public Employee(SimpleStringProperty username, SimpleStringProperty pass, SimpleStringProperty email) {
        this.username = username;
        this.pass = pass;
        this.email = email;
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

    public String getPass() {
        return pass.get();
    }

    public SimpleStringProperty passProperty() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
