package ba.unsa.etf.rpr.Controllers.DashboardControllers;

import ba.unsa.etf.rpr.DAL.DTO.Category;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.Model.DashboardModel;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemListController implements Initializable {
    public TableView<Product> productsTableView;

    public TableColumn<Product,Integer> idColumn;
    public TableColumn<Product,String> nameColumn;
    public TableColumn<Product,Integer> priceColumn;
    public TableColumn<Product,Integer> stockColumn;
    public TableColumn<Product, Category> categoryColumn;
    private DashboardModel dashboardModel;

    public ItemListController(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCells();
        if(!DashboardController.btnLowStock) productsTableView.setItems(dashboardModel.getProducts());
        else productsTableView.setItems(dashboardModel.getLowStockProducts());
    }
    private void setCells() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    }
}