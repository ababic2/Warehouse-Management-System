package ba.unsa.etf.rpr.HelpModel;

public class Account {
    private String username;
    private String password;
    private String accessLevel;

    public Account() {
    }

    public Account(String username, String password, String accessLevel) {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
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

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
