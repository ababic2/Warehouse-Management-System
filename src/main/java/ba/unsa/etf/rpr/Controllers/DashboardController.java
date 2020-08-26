package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.FirmDAO;
import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.Interface.ControllerInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable, ControllerInterface {
    //iskoristeno jednosmjerno povezivanje jer ima puno labela i bilo bi
    //puno ponavljanja koda, greske bla bla
    private SimpleStringProperty employeesLabel;
    private SimpleStringProperty firmLabel;
    private SimpleStringProperty itemsLabel;
    private SimpleStringProperty lowStockLabel;

    private UserDAO userDAO = UserDAO.getInstance();
    private FirmDAO firmDAO = FirmDAO.getInstance();
    private ProductDAO productDAO = ProductDAO.getInstance();

    public static boolean btnLowStock = false;

    public DashboardController() {
        btnLowStock = false;
        employeesLabel = new SimpleStringProperty("");
        firmLabel = new SimpleStringProperty("");
        itemsLabel = new SimpleStringProperty("");
        lowStockLabel = new SimpleStringProperty("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnLowStock = false;
        employeesLabel.set(userDAO.getCount().toString());
        firmLabel.set(firmDAO.getCount().toString());
        itemsLabel.set(productDAO.getCount().toString());
        lowStockLabel.set(productDAO.getCountLowStock().toString());
    }

    public void btnEmployeesClicked(ActionEvent actionEvent) {
        btnLowStock = false;
        openNewStage("/fxml/rightSidePane/listOfEmployees.fxml");
    }

    public void btnFirmsClicked(ActionEvent actionEvent) {
        btnLowStock = false;
        openNewStage("/fxml/rightSidePane/listOfFirms.fxml");
    }

    public void btnItemsClicked(ActionEvent actionEvent) {
        btnLowStock = false;
        openNewStage("/fxml/rightSidePane/listOfItems.fxml");
    }

    public void btnLowStockClicked(ActionEvent actionEvent) {
        btnLowStock = true;
        openNewStage("/fxml/rightSidePane/listOfItems.fxml");
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

}
