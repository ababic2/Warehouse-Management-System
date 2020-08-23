package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label labelEmployees;
    public Label labelFirms;
    public Label labelItems;
    public Label labelLowStock;
    public Button btnEmployees;

    private UserDAO userDAO = UserDAO.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String label = userDAO.getNumberOfEmployees().toString();
        System.out.println(label);
        labelEmployees.setText(label);
    }


    public void btnEmployeesClicked(ActionEvent actionEvent) {

    }
}
