package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.FirmDAO;
import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DTO.Category;
import ba.unsa.etf.rpr.DAL.DTO.Firm;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static ba.unsa.etf.rpr.Controllers.ItemDetailsController.addedProduct;

public class AddItemController implements Initializable {

    public TextField nameProduct;
    public TextField priceProduct;
    public TextField stockProduct;
    public ChoiceBox<Category> categoryChoice;
    public ChoiceBox<Firm> firmChoice;
    public Label errorLabel;

    private ObservableList<Category> categories = FXCollections.observableArrayList();
    private ObservableList<Firm> firms = FXCollections.observableArrayList();

    ProductDAO productDAO = ProductDAO.getInstance();
    //    CategoryDAO categoryDAO = CategoryDAO.getInstance();
    FirmDAO firmDAO = FirmDAO.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categories.addAll(productDAO.getCategories());
        categoryChoice.setItems(categories);

        firms.addAll(firmDAO.getInfoList());
        firmChoice.setItems(firms);
    }

    public void btnSaveClicked(ActionEvent actionEvent) {
        //no repeat of id
        int id = productDAO.maxID();
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
                productDAO.addProduct(product);
                Stage current = (Stage) errorLabel.getScene().getWindow();
                addedProduct.set(product);
                current.close();
            }
        }
    }

    private boolean checkIfThereIsAlreadyInBase(Product product) {
        return productDAO.checkIfAlreadyExist(product);
    }
}
