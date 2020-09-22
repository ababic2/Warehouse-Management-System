package ba.unsa.etf.rpr.Controllers.ItemDetailsControllers;

import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.Interface.DetailsInterface;
import ba.unsa.etf.rpr.Model.ProductModel;
import ba.unsa.etf.rpr.Reports.ShipReport;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import net.sf.jasperreports.engine.JRException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public Button btnRefresh;
    public Button btnIncreaseStock;
    public Button btnDecreaseStock;

    public CheckBox checkBox;

    private SimpleIntegerProperty itemIDLabel;
    private SimpleStringProperty name;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty stock;
    private SimpleStringProperty type;

    private ProductModel model;

    private boolean disable = true;
    private int page = 0;

    //used for SimpleIntegerProperty to SimpleStringProperty
    private NumberStringConverter converter = new NumberStringConverter();
    private int editClick = 0;

    private ChangeListener<String> nameListener = null;
    private ChangeListener<String> priceListener = null;
    private ChangeListener<String> stockListener = null;

    public ItemDetailsController(ProductModel m) {
        this();
        model = m;
    }

    public ItemDetailsController() {
        itemIDLabel = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty("");
        price = new SimpleIntegerProperty(0);
        stock = new SimpleIntegerProperty(0);
        type = new SimpleStringProperty("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        products.addAll(productDAO.getInfoList());
        bindingFieldsWithProperties();
        model.changeCurrent(page);

        setInitialProduct();
        setBinding();
        setFieldsDisableTo(true);
        setButtonsDisableTo();
        DetailsInterface.setButtonsNextPrev(model.getSizeOfList(), btnNext, btnPrevious, page);
        btnPrevious.setDisable(true);
    }

    private void setButtonsDisableTo() { /// bit će prepravkaaa
//
//        if(!currentUser.getAccessLevel().equals("EMPLOYEE")) {
//            btnIncreaseStock.setDisable(true);
//            btnDecreaseStock.setDisable(true);
//
//        } else {
//            btnIncreaseStock.setDisable(false);
//            btnDecreaseStock.setDisable(false);K
//        }
    }

    private void setInitialProduct() {
        itemIDLabel.setValue(model.getCurrentProduct().getProductId());
        name.setValue(model.getCurrentProduct().getName());
        price.setValue(model.getCurrentProduct().getPrice());
        stock.setValue(model.getCurrentProduct().getStock());
        type.setValue(model.getCurrentProduct().getCategory().getCategoryName());
    }

    private void setBinding() {
        model.currentProductProperty().addListener((obs, oldValue, newValue) -> {
            itemIDLabel.setValue(model.getCurrentProduct().getProductId());
            stock.setValue(model.getCurrentProduct().getStock());
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
    public void btnDeleteClicked(ActionEvent actionEvent) {
        //makeShipmentReport();
        int id = Integer.parseInt(itemIdLabel.getText());
        ArrayList<Product> product = (ArrayList<Product>) model.getProducts().stream().filter(o-> o.getProductId() == id).collect(Collectors.toList());
        model.deleteProductWithId(id);
        model.removeProductOnPage(page);

        if (page == 0) goToNextPage();
        else goToPreviousPage();

        writeToFile(product.get(0), product.get(0).getStock());
    }

    public void makeShipmentReport() {
        try {
            new ShipReport().showReport(model.getConn(), "/reports/itemsReport/itemShipReport.jrxml",model.getCurrentProduct().getProductId());
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void btnSearchClicked(ActionEvent actionEvent) {
        if(checkBox.isSelected()) {
            checkBoxSelected();
        } else {
            if (searchField.getText() != null) {
                List<Product> list = model.getProducts().stream().filter(s -> s.getName().equals(searchField.getText())).collect(Collectors.toList());
                if (list.size() == 0) {
                    setAlertWindow("No result !");
                } else {
                    model.setCurrentProduct(list.get(0));
                    findPageAfterSearch();
                }
            }
        }
    }

    private void checkBoxSelected() {
        if (searchField.getText() != null) {
            if (isNumeric(searchField.getText())) {
                int id = Integer.parseInt(searchField.getText());
                List<Product> list = model.getProducts().stream().filter(s -> s.getProductId() == id).collect(Collectors.toList());
                if (list.size() == 0) {
                    setAlertWindow("No result !");
                } else {
                    model.setCurrentProduct(list.get(0));
                    findPageAfterSearch();
                }
            } else {
                setAlertWindow("ID must be numeric !");
            }
        }
    }

    private void findPageAfterSearch() {
        for(int i = 0; i < model.getSizeOfList(); i++) {
            if(model.getCurrentProduct().compareTo(model.getProducts().get(i)) == 0) {
                page = i;
                break;
            }
        }
        DetailsInterface.setButtonsNextPrev(model.getSizeOfList(), btnNext, btnPrevious, page);
    }

    public void btnAddClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_fxml/addItem.fxml"));
            loader.setController(new AddItemController(model));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage logInPrompt = new Stage();
            logInPrompt.setScene(scene);
            logInPrompt.show();
        } catch (IOException e) {
            e.getCause();
        }
    }

    private void goToPreviousPage() {
        page--;
        DetailsInterface.setButtonsNextPrev(model.getSizeOfList(), btnNext, btnPrevious, page);
        model.changeCurrent(page);
    }

    private void goToNextPage() {
        page++;
        DetailsInterface.setButtonsNextPrev(model.getSizeOfList(), btnNext, btnPrevious, page);
        model.changeCurrent(page);
    }

    @Override
    public void btnEditClicked(ActionEvent actionEvent) {

        setFieldsDisableTo(!disable);
        disable = !disable;
        editClick++;

        if(editClick == 1) {
            nameListener = (obs, oldValue, newValue) -> {
                name.setValue((String) newValue);
                model.getProducts().get(page).setName(name.getValue());
            };
            itemNameField.textProperty().addListener(nameListener);

            priceListener = (obs, oldValue, newValue) -> {
                price.setValue(Integer.parseInt((String) newValue));
                model.getProducts().get(page).setPrice(price.getValue());
            };
            itemPriceLabel.textProperty().addListener(priceListener);

            stockListener = (obs, oldValue, newValue) -> {
                stock.setValue(Integer.parseInt((String) newValue));
                model.getProducts().get(page).setStock(stock.getValue());
            };
            stockLabel.textProperty().addListener(stockListener);
        } else if (editClick == 2) {
            model.changeCurrent(page);
            model.updateProductInBase();
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

    private void writeToFile(Product product, int num) {
        try {
            String data = product.toString() + " " + num + " " + currentUser.getUsername() + "\n";

            File file = new File("Ship.txt");

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("New File Created Now");
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(file, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();
            fileWritter.close();

            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void btnPreviousClicked(ActionEvent actionEvent) { goToPreviousPage();
    }

    public void btnIncreaseStockClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(itemIdLabel.getText());
        int newStock = Integer.parseInt(stockLabel.getText());
        newStock++;
        model.changeStock(id, newStock);
        model.getCurrentProduct().setStock(newStock);
        stock.setValue(newStock);
    }

    public void btnDecreaseStock(ActionEvent actionEvent) {
        int id = Integer.parseInt(itemIdLabel.getText());
        ArrayList<Product> product = (ArrayList<Product>) model.getProducts().stream().filter(o-> o.getProductId() == id).collect(Collectors.toList());
        int newStock = Integer.parseInt(stockLabel.getText());
        newStock--;
        model.changeStock(id, newStock);
        model.getCurrentProduct().setStock(newStock);
        stock.setValue(newStock);
        writeToFile(product.get(0), 1);

    }

    public void btnRefreshClicked(ActionEvent actionEvent) {
        model.addProductToList(model.getAddedProduct());
        DetailsInterface.setButtonsNextPrev(model.getSizeOfList(), btnNext, btnPrevious, page);
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
}