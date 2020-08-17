package ba.unsa.etf.rpr.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;

public class Controller {

    //public Label label;
    private SimpleStringProperty username;
    private int counter = 0;

    public String getUsername() {
        return username.get();
    }

    public Controller() {
        username = new SimpleStringProperty("");
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void clickMe(ActionEvent actionEvent) {
        counter++;
        //label.setText(counter+"");
        username.set(counter + "");
    }

}
