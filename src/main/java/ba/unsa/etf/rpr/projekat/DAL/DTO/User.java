package ba.unsa.etf.rpr.projekat.DAL.DTO;

import ba.unsa.etf.rpr.projekat.Enum.AccessLevel;
import javafx.beans.property.SimpleStringProperty;

public class User {

    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty accessLevelString;
    private AccessLevel accessLevel;

    public User() {
    }

    public User(String username, String password, String accessLevel) {
        setInConstructor(username, password, accessLevel);
    }

    public void setInConstructor(String username, String password, String accessLevel) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        if(accessLevel.toLowerCase().equals("admin")) {
            this.accessLevel = AccessLevel.ADMIN;
        } else {
            this.accessLevel = AccessLevel.EMPLOYEE;
        }
        this.accessLevelString = new SimpleStringProperty(accessLevel);
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
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

    public String getAccessLevelString() {
        return accessLevelString.get();
    }

    public SimpleStringProperty accessLevelStringProperty() {
        return accessLevelString;
    }

    public void setAccessLevelString(String accessLevelString) {
        this.accessLevelString.set(accessLevelString);
    }
}
