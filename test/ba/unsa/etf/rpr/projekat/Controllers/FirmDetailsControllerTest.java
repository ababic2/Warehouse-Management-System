package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAL.DAO.FirmDAO;
import ba.unsa.etf.rpr.projekat.DAL.DTO.Firm;
import ba.unsa.etf.rpr.projekat.Model.FirmModel;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class FirmDetailsControllerTest {
    Stage theStage;
    FirmDetailsController ctrl;
    FirmModel model;
    FirmDAO dao = FirmDAO.getInstance();

    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle =ResourceBundle.getBundle("firmTranslation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/firmDetails.fxml"),bundle);
        model = new FirmModel();
        loader.setController(new FirmDetailsController(model));
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

        Firm firm = model.getCurrentFirm();
        String name = firm.getFirmName();

        robot.clickOn("#btnEdit");
        robot.clickOn("#nameField");
        robot.write("abc");
        robot.clickOn("#btnEdit");

        name += "abc";
        firm = model.getCurrentFirm();

        assertEquals(name, firm.getFirmName());
    }

    @Test
    void ownerEdit(FxRobot robot) {

        Firm firm = model.getCurrentFirm();
        String name = firm.getOwner();

        robot.clickOn("#btnEdit");
        robot.clickOn("#ownerField");
        robot.write("abc");
        robot.clickOn("#btnEdit");

        name += "abc";
        firm = model.getCurrentFirm();

        assertEquals(name, firm.getOwner());
    }

    @Test
    void eMailEdit(FxRobot robot) {
        Firm firm = model.getCurrentFirm();
        String name = firm.getFirmEMail();

        robot.clickOn("#btnEdit");
        robot.clickOn("#mailField");
        robot.write("abc");
        robot.clickOn("#btnEdit");

        name += "abc";
        firm = model.getCurrentFirm();

        assertEquals(name, firm.getFirmEMail());
    }

}