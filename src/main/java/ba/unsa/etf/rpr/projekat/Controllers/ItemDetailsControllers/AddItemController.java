package ba.unsa.etf.rpr.projekat.Controllers.ItemDetailsControllers;

import ba.unsa.etf.rpr.projekat.DAL.DTO.Category;
import ba.unsa.etf.rpr.projekat.DAL.DTO.Firm;
import ba.unsa.etf.rpr.projekat.DAL.DTO.Product;
import ba.unsa.etf.rpr.projekat.Model.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

    public TextField nameProduct;
    public TextField priceProduct;
    public TextField stockProduct;
    public ChoiceBox<Category> categoryChoice;
    public ChoiceBox<Firm> firmChoice;
    public GridPane itemNameGrid, itemPriceGrid, stockGrid;
    public Label errorLabel;
    private Label nameErrorLabel;
    private Label priceErrorLabel;
    private Label stockErrorLabel;


    private ProductModel model;

    public AddItemController(ProductModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoice.setItems(model.getCategories());
        firmChoice.setItems(model.getFirms());
    }

    public void btnSaveClicked(ActionEvent actionEvent) throws IOException {
        //no repeat of id
        int id = model.getMaxId();
        Product product = null;
        id++;
        if (nameProduct.getText() == null || priceProduct.getText() == null ||
                stockProduct.getText() == null || categoryChoice.getSelectionModel().getSelectedItem() == null ||
                firmChoice.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setVisible(true);
        }else  if(!nameFormat(nameProduct.getText())) {
            nameErrorLabel= FXMLLoader.load(getClass().getResource("/fxml/dynamic/itemNameErrorLabel.fxml"));
            itemNameGrid.add(nameErrorLabel,0,2);
        } else if(!digitFormat(priceProduct.getText())) {
            priceErrorLabel = FXMLLoader.load(getClass().getResource("/fxml/dynamic/onlyNumberErrorLabel.fxml"));
            itemPriceGrid.add(priceErrorLabel,0,2);
            itemNameGrid.getChildren().remove(nameErrorLabel);
        }else if(!digitFormat(stockProduct.getText())) {
            stockErrorLabel = FXMLLoader.load(getClass().getResource("/fxml/dynamic/onlyNumberErrorLabel.fxml"));
            stockGrid.add(stockErrorLabel,0,2);
            itemPriceGrid.getChildren().remove(priceErrorLabel);
        } else {
            errorLabel.setVisible(false);
            stockGrid.getChildren().remove(stockErrorLabel);
            product = new Product(id, nameProduct.getText(),
                    Integer.parseInt(priceProduct.getText()),
                    Integer.parseInt(stockProduct.getText()),
                    categoryChoice.getSelectionModel().getSelectedItem(),
                    firmChoice.getSelectionModel().getSelectedItem());

            if (checkIfThereIsAlreadyInBase(product)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Already exist ! Try increasing stock of product or create new one.");
                alert.showAndWait();
            } else {
                model.addProductToBase(product);
                Stage current = (Stage) errorLabel.getScene().getWindow();
                model.setAddedProduct(product);
                model.addProductToList(product);
                current.close();
            }
        }
    }


    private boolean digitFormat(String text) {
        return text.matches("^[0-9]+$");
    }

    private boolean nameFormat(String s) {
        String[] result = s.split(" ");
        for(int i = 0; i<result.length; i++) {
            if(!result[i].matches("^[A-Za-z]+$")) return false;
        }
        return true;
    }

    private boolean checkIfThereIsAlreadyInBase(Product product) {
        return model.checkIfProductAlreadyExist(product);
    }
}
