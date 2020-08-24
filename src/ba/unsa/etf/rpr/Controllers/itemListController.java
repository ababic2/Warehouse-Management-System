package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DTO.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class itemListController implements Initializable {
    public TableView<Product> productsTableView;

    public TableColumn<Product,Integer> idColumn;
    public TableColumn<Product,String> nameColumn;
    public TableColumn<Product,Integer>  priceColumn;
    public TableColumn<Product,Integer>  stockColumn;
    public TableColumn<Product,String> categoryColumn;

    private ProductDAO productDAO = ProductDAO.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCells();
        productsTableView.setItems(productDAO.getInfoList());
    }
    private void setCells() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        accessColumn.setCellValueFactory(new PropertyValueFactory<>("accessLevelString"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("eMail"));

        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        depColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
    }
}
