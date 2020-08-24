package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.Interface.ControllerInterface;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemDetailsController implements Initializable, ControllerInterface {

    public Label itemIdLable;
    public TextField itemNameLabel;
    public TextField itemTypeLabel;
    public TextField itemPriceLabel;
    public Label stockLabell;

    private SimpleIntegerProperty itemIDLabel;
    private SimpleStringProperty nameLabel;
    private SimpleIntegerProperty priceLabel;
    private SimpleIntegerProperty stockLabel;
    private SimpleIntegerProperty typeLabel;

    private int page = 1;
    private ProductDAO productDAO = ProductDAO.getInstance();

    public ItemDetailsController(SimpleIntegerProperty itemIDLabel,
                                 SimpleStringProperty nameLabel, SimpleIntegerProperty priceLabel,
                                 SimpleIntegerProperty stockLabel, SimpleIntegerProperty typeLabel) {
        this.itemIDLabel = itemIDLabel;
        this.nameLabel = nameLabel;
        this.priceLabel = priceLabel;
        this.stockLabel = stockLabel;
        this.typeLabel = typeLabel;


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDisableTo(true);
        setFields();
    }

    private void setDisableTo(boolean var) {
        itemNameLabel.setDisable(var);
        itemTypeLabel.setDisable(var);
        itemPriceLabel.setDisable(var);
    }

    private void setFields() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.addAll(productDAO.getInfoList());
        Product currentProduct = products.get(page);

    }

    public void onSearchFieldClicked(ActionEvent actionEvent) {

    }

    public void btnEditClicked(ActionEvent actionEvent) {

    }

    public void btnDeleteClicked(ActionEvent actionEvent) {

    }

    public int getItemIDLabel() {
        return itemIDLabel.get();
    }

    public SimpleIntegerProperty itemIDLabelProperty() {
        return itemIDLabel;
    }

    public void setItemIDLabel(int itemIDLabel) {
        this.itemIDLabel.set(itemIDLabel);
    }

    public String getNameLabel() {
        return nameLabel.get();
    }

    public SimpleStringProperty nameLabelProperty() {
        return nameLabel;
    }

    public void setNameLabel(String nameLabel) {
        this.nameLabel.set(nameLabel);
    }

    public int getPriceLabel() {
        return priceLabel.get();
    }

    public SimpleIntegerProperty priceLabelProperty() {
        return priceLabel;
    }

    public void setPriceLabel(int priceLabel) {
        this.priceLabel.set(priceLabel);
    }

    public int getStockLabel() {
        return stockLabel.get();
    }

    public SimpleIntegerProperty stockLabelProperty() {
        return stockLabel;
    }

    public void setStockLabel(int stockLabel) {
        this.stockLabel.set(stockLabel);
    }

    public int getTypeLabel() {
        return typeLabel.get();
    }

    public SimpleIntegerProperty typeLabelProperty() {
        return typeLabel;
    }

    public void setTypeLabel(int typeLabel) {
        this.typeLabel.set(typeLabel);
    }
}
