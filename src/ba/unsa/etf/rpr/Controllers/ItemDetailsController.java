package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.Interface.ControllerInterface;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemDetailsController implements Initializable, ControllerInterface {

    public Label itemIdLabel;
    public TextField itemNameField;;
    public TextField itemTypeLabel;
    public TextField itemPriceLabel;
    public Label stockLabell;
    public Button btnNext;
    public Button btnPrevious;


    private SimpleIntegerProperty itemIDLabel;
    private SimpleStringProperty name;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty stock;
    private SimpleStringProperty type;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObjectProperty<Product> currentProduct = new SimpleObjectProperty<>();
    private int page = 0;
    private boolean disable = true;
    private ProductDAO productDAO = ProductDAO.getInstance();

    //used for SimpleIntegerProperty to SimpleStringProperty
    private NumberStringConverter converter = new NumberStringConverter();

    public ItemDetailsController() {
        itemIDLabel = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty("");
        price = new SimpleIntegerProperty(0);
        stock = new SimpleIntegerProperty(0);
        type = new SimpleStringProperty("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products.addAll(productDAO.getInfoList());
        bindingFields();
        btnPrevious.setDisable(true);
        changeCurrentProduct();
        initializeFirstProduct();
        setFields();
        //setDisableTo(true);
    }

    private void bindingFields() {
//        itemNameField.textProperty().bindBidirectional(name);
        itemPriceLabel.textProperty().bindBidirectional(price, converter);
        itemTypeLabel.textProperty().bindBidirectional(type);
    }

    private void initializeFirstProduct() {
        currentProduct.set(products.get(0));
        itemIDLabel.setValue(currentProduct.getValue().getProductId());
//        itemNameField.textProperty().bindBidirectional(currentProduct.getValue().nameProperty());
//        itemPriceLabel.textProperty().bindBidirectional(currentProduct.getValue().getPrice(), converter);
        itemTypeLabel.textProperty().bindBidirectional(currentProduct.getValue().getCategory().categoryNameProperty());
        stock.setValue(currentProduct.getValue().getStock());
    }

    private void setDisableTo(boolean var) {
        itemNameField.setDisable(var);
        itemTypeLabel.setDisable(var);
        itemPriceLabel.setDisable(var);
    }

    private void setFields() {
        itemIDLabel.setValue(currentProduct.getValue().getProductId());
        currentProduct.addListener((obs, oldValue, newValue) -> {
            if(oldValue != null) {
//                itemNameField.textProperty().unbindBidirectional(oldValue.nameProperty());
                itemPriceLabel.textProperty().unbindBidirectional(oldValue.priceProperty());
                itemTypeLabel.textProperty().unbindBidirectional(oldValue.getCategory().categoryNameProperty());
            }
            if(newValue == null) {
                currentProduct.set(products.get(0));
                itemIDLabel.setValue(currentProduct.getValue().getProductId());
//                itemNameField.textProperty().bindBidirectional(currentProduct.getValue().nameProperty());
                //itemPriceLabel.textProperty().bindBidirectional(currentProduct.getValue().getPrice(), converter);
                itemTypeLabel.textProperty().bindBidirectional(currentProduct.getValue().getCategory().categoryNameProperty());
                stock.setValue(currentProduct.getValue().getStock());
            }
            else {
//                itemNameField.textProperty().bindBidirectional(newValue.nameProperty());
                itemPriceLabel.textProperty().bindBidirectional(newValue.priceProperty(),converter);
                itemTypeLabel.textProperty().bindBidirectional(newValue.getCategory().categoryNameProperty());
            }
        });
        stock.setValue(currentProduct.getValue().getStock());
    }

    private void changeCurrentProduct() {
        currentProduct.set(products.get(page));
    }

    public void onSearchFieldClicked(ActionEvent actionEvent) {

    }

    public void btnEditClicked(ActionEvent actionEvent) {
        setDisableTo(!disable);
        disable = !disable;
    }

    public void btnDeleteClicked(ActionEvent actionEvent) {

    }

    public void btnNextClicked(ActionEvent actionEvent) {
        page++;
        if(page != 0) {
            btnPrevious.setDisable(false);
        }
        if (page == products.size() - 1) {
            btnNext.setDisable(true);
        }
        changeCurrentProduct();
        setFields();
    }

    public void btnPrevoiusClicked(ActionEvent actionEvent) {
            page--;
            if( page != products.size() - 1 ) {
                btnNext.setDisable(false);
            } else if (page == 0) {
                btnPrevious.setDisable(true);
            }
            changeCurrentProduct();
            setFields();
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getStock() {
        return stock.get();
    }

    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Product getCurrentProduct() {
        return currentProduct.get();
    }

    public ObjectProperty<Product> currentProductProperty() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct.set(currentProduct);
    }

    public void btnAddClicked(ActionEvent actionEvent) {

    }
}
