package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DTO.Category;
import ba.unsa.etf.rpr.DAL.DTO.Firm;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import ba.unsa.etf.rpr.Model.ProductModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

    public TextField nameProduct;
    public TextField priceProduct;
    public TextField stockProduct;
    public ChoiceBox<Category> categoryChoice;
    public ChoiceBox<Firm> firmChoice;
    public Label errorLabel;

    private ProductModel model;

    public AddItemController(ProductModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryChoice.setItems(model.getCategories());
        firmChoice.setItems(model.getFirms());
    }

    public void btnSaveClicked(ActionEvent actionEvent) {
        //no repeat of id
        int id = model.getMaxId();
        Product product;
        id++;
        if (nameProduct.getText() == null || priceProduct.getText() == null ||
                stockProduct.getText() == null || categoryChoice.getSelectionModel().getSelectedItem() == null ||
                firmChoice.getSelectionModel().getSelectedItem() == null) {
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
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
                //addedProduct.set(product);
                current.close();
            }
        }
    }

    private boolean checkIfThereIsAlreadyInBase(Product product) {
        return model.checkIfProductAlreadyExist(product);
    }
}
