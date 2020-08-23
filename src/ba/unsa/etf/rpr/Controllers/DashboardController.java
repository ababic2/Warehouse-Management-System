package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    //iskoristeno jednosmjerno povezivanje jer ima puno labela i bilo bi
    //puno ponavljanja koda, greske bla bla
    private SimpleStringProperty employeesLabel;
    private SimpleStringProperty firmLabel;
    private SimpleStringProperty itemsLabel;
    private SimpleStringProperty lowStockLabel;

    private UserDAO userDAO = UserDAO.getInstance();

    public DashboardController() {
        employeesLabel = new SimpleStringProperty("");
        firmLabel = new SimpleStringProperty("");
        itemsLabel = new SimpleStringProperty("");
        lowStockLabel = new SimpleStringProperty("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String label = userDAO.getNumberOfEmployees().toString();
        System.out.println(label);
        employeesLabel.set(label);
    }

    public String getEmployeesLabel() {
        return employeesLabel.get();
    }

    public SimpleStringProperty employeesLabelProperty() {
        return employeesLabel;
    }

    public String getFirmLabel() {
        return firmLabel.get();
    }

    public SimpleStringProperty firmLabelProperty() {
        return firmLabel;
    }

    public String getItemsLabel() {
        return itemsLabel.get();
    }

    public SimpleStringProperty itemsLabelProperty() {
        return itemsLabel;
    }

    public String getLowStockLabel() {
        return lowStockLabel.get();
    }

    public SimpleStringProperty lowStockLabelProperty() {
        return lowStockLabel;
    }

    private void openNewStage(String url) {
        try {
            // Setting dashboard window
            Parent root = FXMLLoader.load(getClass().getResource(url));

            Scene scene = new Scene(root);
            Stage logInPrompt = new Stage();
            logInPrompt.setScene(scene);
            logInPrompt.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnEmployeesClicked(ActionEvent actionEvent) {
        openNewStage("/fxml/employeeList.fxml");
    }
}
