package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.Interface.DetailsInterface;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static ba.unsa.etf.rpr.Controllers.LogInController.currentUser;

public class ItemDetailsController implements Initializable, DetailsInterface {

    public Label itemIdLabel;
    public Label stockLabell;

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
        System.out.println("INITIALIZE");
        products.addAll(productDAO.getInfoList());
        System.out.println(products.get(0));
        bindingFieldsWithProperties();
        changeCurrent();
        setInitialProduct();
        setBinding();
        setFieldsDisableTo(true);
        setButtonsDisableTo();
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

    private boolean isEqual(int i) {
        return (products.get(i).getProductId() == currentProduct.getValue().getProductId()) &&
                (products.get(i).getPrice() == currentProduct.getValue().getPrice()) &&
                (products.get(i).getStock() == currentProduct.getValue().getStock()) &&
                (products.get(i).getName().equals(currentProduct.getValue().getName()));
    }

    @Override
    public void changeCurrent() {
        currentProduct.set(products.get(page));

    }

    @Override
    public void btnDeleteClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(itemIdLabel.getText());
        productDAO.deleteProductWithId(id);

        if(page == 0) goToNextPage();
        else goToPreviousPage();
    }

    @Override
    public void btnSearchClicked(ActionEvent actionEvent) {
        if(checkBox.isSelected()) {
            checkBoxSelected();
        } else {
            if (searchField.getText() != null) {
                Product product = findItemWithNameStream(searchField.getText());

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

                Product product = findItemWithIDStream(id);

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

    private Product findItemWithNameStream(String name) {
        List<Product> result = products.stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList());
        if(result.size() == 0) return null;
        return result.get(0);
    }

    private Product findItemWithIDStream(int id) {
        List<Product> list = products.stream().filter(s -> s.getProductId() == id).collect(Collectors.toList());
        if(list.size() == 0) return null;
        return list.get(0);
    }

    private void findPageAfterSearch() {
        for(int i = 0; i < products.size(); i++) {
            if(isEqual(i)) {
                page = i;
                break;
            }
        }
        if (page == products.size() - 1) {
            btnNext.setDisable(true);
            btnPrevious.setDisable(false);
        } else if (page == 0) {
            btnNext.setDisable(false);
            btnPrevious.setDisable(true);
        } else {
            btnPrevious.setDisable(false);
            btnNext.setDisable(false);
        }
    }

    @Override
    public void btnAddClicked(ActionEvent actionEvent) {
        openNewStage("/fxml/addItem.fxml");
    }

    @Override
    public void btnEditClicked(ActionEvent actionEvent) {
        setFieldsDisableTo(!disable);
        disable = !disable;
    }

    @Override
    public void btnNextClicked(ActionEvent actionEvent) {
        goToNextPage();
    }

    @Override
    public void btnPreviousClicked(ActionEvent actionEvent) {
        goToPreviousPage();

    }

    private void goToNextPage() {
        page++;
        if (page == products.size() - 1) {
            btnNext.setDisable(true);
        }
        btnPrevious.setDisable(false);
        changeCurrent();
    }

    private void goToPreviousPage() {
        page--;
        if (page == 0) {
            btnPrevious.setDisable(true);
        }
        btnNext.setDisable(false);
        changeCurrent();
    }

    public int getItemIDLabel() {
        return itemIDLabel.get();
    }

    public void btnIncreaseStockClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(itemIdLabel.getText());
        int newStock = Integer.parseInt(stockLabell.getText());
        newStock++;
        productDAO.increaseStockOfProduct(id, newStock);
        currentProduct.getValue().setStock(newStock);
        stock.setValue(newStock);
    }

    public void btnDecreaseStock(ActionEvent actionEvent) {
        int id = Integer.parseInt(itemIdLabel.getText());
        int newStock = Integer.parseInt(stockLabell.getText());
        newStock--;
        productDAO.increaseStockOfProduct(id, newStock);
        currentProduct.getValue().setStock(newStock);
        stock.setValue(newStock);
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
