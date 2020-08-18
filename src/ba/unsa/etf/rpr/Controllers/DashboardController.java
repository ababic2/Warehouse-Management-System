package ba.unsa.etf.rpr.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label countEmployees;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countEmployees.setText("5");
    }
}
