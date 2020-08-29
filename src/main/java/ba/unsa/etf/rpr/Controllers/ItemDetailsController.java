package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.HelpModel.Reference;
import ba.unsa.etf.rpr.Interface.DetailsInterface;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static ba.unsa.etf.rpr.Controllers.LogInController.currentUser;

public class ItemDetailsController implements Initializable, DetailsInterface {

    public Label itemIdLabel;
    public Label stockLabel;

    public TextField itemNameField;;
    public TextField itemTypeLabel;
    public TextField itemPriceLabel;
    public TextField searchField;

    public Button btnAdd;
    public Button btnNext;
    public Button btnPrevious;
    public Button btnIncreaseStock;
    public Button btnDecreaseStock;

    public CheckBox checkBox;

    private SimpleIntegerProperty itemIDLabel;
    private SimpleStringProperty name;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty stock;
    private SimpleStringProperty type;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObjectProperty<Product> currentProduct = new SimpleObjectProperty<>();
    private ProductDAO productDAO = ProductDAO.getInstance();
    private boolean disable = true;
    private int page = 0;

    public Reference<Product> addedProduct = new Reference<>(null);

    //used for SimpleIntegerProperty to SimpleStringProperty
    private NumberStringConverter converter = new NumberStringConverter();

    private int editClick = 0;
    private ChangeListener<String> nameListener = null;
    private ChangeListener<String> priceListener = null;
    private ChangeListener<String> stockListener = null;

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
        bindingFieldsWithProperties();
        changeCurrent();
        setInitialProduct();
        setBinding();
        setFieldsDisableTo(true);

        setButtonsDisableTo();

        DetailsInterface.setButtonsNextPrev(products.size(), btnNext, btnPrevious, page);

        btnPrevious.setDisable(true);
    }

    private void setButtonsDisableTo() {

        if(!currentUser.getAccessLevel().equals("EMPLOYEE")) {
            btnIncreaseStock.setDisable(true);
            btnDecreaseStock.setDisable(true);

        } else {
            btnIncreaseStock.setDisable(false);
            btnDecreaseStock.setDisable(false);
        }
    }

    private void setInitialProduct() {
        itemIDLabel.setValue(currentProduct.getValue().getProductId());
        name.setValue(currentProduct.getValue().getName());
        price.setValue(currentProduct.getValue().getPrice());
        stock.setValue(currentProduct.getValue().getStock());
        type.setValue(currentProduct.getValue().getCategory().getCategoryName());
    }

    private void setBinding() {
        currentProduct.addListener((obs, oldValue, newValue) -> {
            System.out.println("BINDAM");
            itemIDLabel.setValue(currentProduct.getValue().getProductId());
            stock.setValue(currentProduct.getValue().getStock());
            if(oldValue != null) {
                itemNameField.textProperty().unbindBidirectional(oldValue.nameProperty());
                itemPriceLabel.textProperty().unbindBidirectional(oldValue.priceProperty());
                itemTypeLabel.textProperty().unbindBidirectional(oldValue.getCategory().categoryNameProperty());
            }
            itemNameField.textProperty().bindBidirectional(newValue.nameProperty());
            itemPriceLabel.textProperty().bindBidirectional(newValue.priceProperty(),converter);
            itemTypeLabel.textProperty().bindBidirectional(newValue.getCategory().categoryNameProperty());
        });
    }

    private void bindingFieldsWithProperties() {
        itemNameField.textProperty().bindBidirectional(name);
        itemPriceLabel.textProperty().bindBidirectional(price, converter);
        itemTypeLabel.textProperty().bindBidirectional(type);
    }

    private void setFieldsDisableTo(boolean var) {
        itemNameField.setDisable(var);
        itemTypeLabel.setDisable(var);
        itemPriceLabel.setDisable(var);
    }

    @Override
    public void changeCurrent() {
        currentProduct.set(products.get(page));
    }

    @Override
    public void btnDeleteClicked(ActionEvent actionEvent) {

            int id = Integer.parseInt(itemIdLabel.getText());
            productDAO.deleteProductWithId(id);

            products.remove(page);

            if (page == 0) goToNextPage();
            else goToPreviousPage();
    }

    @Override
    public void btnSearchClicked(ActionEvent actionEvent) {
        if(checkBox.isSelected()) {
            checkBoxSelected();
        } else {
            if (searchField.getText() != null) {
                Product product = products.stream().filter(s -> s.getName().equals(searchField.getText())).collect(Collectors.toList()).get(0);
                if (product == null) {
                    setAlertWindow("No result !");
                } else {
                    currentProduct.set(product);
                    findPageAfterSearch();
                }
            }
        }
    }

    private void checkBoxSelected() {
        if (searchField.getText() != null) {
            if (isNumeric(searchField.getText())) {
                int id = Integer.parseInt(searchField.getText());

                Product product = products.stream().filter(s -> s.getProductId() == id).collect(Collectors.toList()).get(0);

                if (product == null) {
                    setAlertWindow("No result !");
                } else {
                    currentProduct.set(product);
                    findPageAfterSearch();
                }
            } else {
                setAlertWindow("ID must be numeric !");
            }
        }
    }

    private void findPageAfterSearch() {
        for(int i = 0; i < products.size(); i++) {
            if(currentProduct.getValue().compareTo(products.get(i)) == 0) {
                page = i;
                break;
            }
        }
        DetailsInterface.setButtonsNextPrev(products.size(), btnNext, btnPrevious, page);
    }

    @Override
    public void btnAddClicked(ActionEvent actionEvent) {
        openNewStage("/fxml/addItem.fxml");
    }

    private void goToPreviousPage() {
        page--;
        DetailsInterface.setButtonsNextPrev(products.size(), btnNext, btnPrevious, page);
        changeCurrent();
    }

    private void goToNextPage() {
        page++;
        DetailsInterface.setButtonsNextPrev(products.size(), btnNext, btnPrevious, page);
        changeCurrent();
    }

    @Override
    public void btnEditClicked(ActionEvent actionEvent) {

        setFieldsDisableTo(!disable);
        disable = !disable;
        editClick++;

    if(editClick == 1) {
        nameListener = (obs, oldValue, newValue) -> {
            name.setValue((String) newValue);
            products.get(page).setName(name.getValue());
        };
        itemNameField.textProperty().addListener(nameListener);

        priceListener = (obs, oldValue, newValue) -> {
            price.setValue(Integer.parseInt((String) newValue));
            products.get(page).setPrice(price.getValue());
        };
        itemPriceLabel.textProperty().addListener(priceListener);

        stockListener = (obs, oldValue, newValue) -> {
            stock.setValue(Integer.parseInt((String) newValue));
            products.get(page).setStock(stock.getValue());
        };
        stockLabel.textProperty().addListener(stockListener);
    } else if (editClick == 2) {

            System.out.println("HELLOOOOO");
            changeCurrent();
            System.out.println("1");

            productDAO.updateProduct(currentProduct.getValue().getProductId(), currentProduct.getValue().getName(),
                    currentProduct.getValue().getPrice(), currentProduct.getValue().getStock());

            System.out.println("2");

            itemNameField.textProperty().removeListener(nameListener);
            itemPriceLabel.textProperty().removeListener(priceListener);
            stockLabel.textProperty().removeListener(stockListener);
            editClick = 0;
        }
    }

    @Override
    public void btnNextClicked(ActionEvent actionEvent) {
        goToNextPage();
    }

    @Override
    public void btnPreviousClicked(ActionEvent actionEvent) { goToPreviousPage();
    }

    public void btnIncreaseStockClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(itemIdLabel.getText());
        int newStock = Integer.parseInt(stockLabel.getText());
        newStock++;
        productDAO.increaseStockOfProduct(id, newStock);
        currentProduct.getValue().setStock(newStock);
        stock.setValue(newStock);
    }

    public void btnDecreaseStock(ActionEvent actionEvent) {
        int id = Integer.parseInt(itemIdLabel.getText());
        int newStock = Integer.parseInt(stockLabel.getText());
        newStock--;
        productDAO.increaseStockOfProduct(id, newStock);
        currentProduct.getValue().setStock(newStock);
        stock.setValue(newStock);
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
}