package ba.unsa.etf.rpr.projekat.Controllers.ItemDetailsControllers;

import ba.unsa.etf.rpr.projekat.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.projekat.DAL.DTO.Product;
import ba.unsa.etf.rpr.projekat.Model.ProductModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)

class ItemDetailsControllerTest {
    Stage theStage;
    ItemDetailsController ctrl;
    ProductModel model;
    UserDAO dao = UserDAO.getInstance();

    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle =ResourceBundle.getBundle("itemDetailsTranslation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/itemDetails.fxml"),bundle);
        model = new ProductModel();
        loader.setController(new ItemDetailsController(model));
        Parent root = loader.load();
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    void editName(FxRobot robot) {

        robot.clickOn("#btnNext");

        Product product = model.getCurrentProduct();
        String name = product.getName();

        robot.clickOn("#editButton");
        robot.clickOn("#itemNameField");
        robot.write("abc");
        robot.clickOn("#editButton");

        name += "abc";
        product = model.getCurrentProduct();

        assertEquals(name, product.getName());
    }

    @Test
    void priceEdit(FxRobot robot) {

        Product product = model.getCurrentProduct();
        String name = String.valueOf(product.getPrice());

        robot.clickOn("#editButton");
        robot.clickOn("#itemPriceLabel");
        robot.write("1");
        robot.clickOn("#editButton");

        name += "1";
        product = model.getCurrentProduct();

        assertTrue(String.valueOf(product.getPrice()).equals(name));
    }
}