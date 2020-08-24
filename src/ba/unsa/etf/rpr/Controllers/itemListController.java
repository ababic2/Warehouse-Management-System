package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DTO.Category;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static ba.unsa.etf.rpr.Controllers.DashboardController.btnLowStock;

public class itemListController implements Initializable {
    public TableView<Product> productsTableView;

    public TableColumn<Product,Integer> idColumn;
    public TableColumn<Product,String> nameColumn;
    public TableColumn<Product,Integer> priceColumn;
    public TableColumn<Product,Integer> stockColumn;
    public TableColumn<Product,Category> categoryColumn;

    private ProductDAO productDAO = ProductDAO.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCells();
        if(!btnLowStock) productsTableView.setItems(productDAO.getInfoList());
        else productsTableView.setItems(productDAO.getInfoLowStockList());
    }
    private void setCells() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
}
