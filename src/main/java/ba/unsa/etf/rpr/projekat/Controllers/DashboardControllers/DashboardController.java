package ba.unsa.etf.rpr.projekat.Controllers.DashboardControllers;

import ba.unsa.etf.rpr.projekat.Model.DashboardModel;
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

    private SimpleStringProperty employeesLabel;
    private SimpleStringProperty firmLabel;
    private SimpleStringProperty itemsLabel;
    private SimpleStringProperty lowStockLabel;

    public static boolean btnLowStock = false;

    private DashboardModel dashboardModel;

    public DashboardController(DashboardModel dashboardModel) {
        this();
        this.dashboardModel = dashboardModel;
    }

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
        employeesLabel.set(dashboardModel.getNumberOfEmployed());
        firmLabel.set(dashboardModel.getNumberOfFirms());
        itemsLabel.set(dashboardModel.getNumberOfItems());
        lowStockLabel.set(dashboardModel.getNumberOfLowStockItems());
    }

    private void setScene(FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage logInPrompt = new Stage();
        logInPrompt.setScene(scene);
        logInPrompt.show();
    }

    public void btnEmployeesClicked(ActionEvent actionEvent) {
        btnLowStock = false;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard/listOfEmployees.fxml"));
            loader.setController(new EmployeeListController(dashboardModel));
            setScene(loader);
        } catch (IOException e) {}
    }

    public void btnFirmsClicked(ActionEvent actionEvent) {
        btnLowStock = false;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard/listOfFirms.fxml"));
            loader.setController(new FirmListController(dashboardModel));
            setScene(loader);
        } catch (IOException e) {}
    }

    public void btnItemsClicked(ActionEvent actionEvent) {
        btnLowStock = false;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard/listOfItems.fxml"));
            loader.setController(new ItemListController(dashboardModel));
            setScene(loader);
        } catch (IOException e) {}
    }

    public void btnLowStockClicked(ActionEvent actionEvent) {
        btnLowStock = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard/listOfItems.fxml"));
            loader.setController(new ItemListController(dashboardModel));
            setScene(loader);
        } catch (IOException e) {}
    }

    public String getEmployeesLabel() {
        return employeesLabel.get();
    }

    public SimpleStringProperty employeesLabelProperty() {
        return employeesLabel;
    }

    public String getFirmLabel() { return firmLabel.get(); }

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