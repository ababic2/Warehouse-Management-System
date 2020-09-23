package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.Model.EmployeeAccountModel;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(ApplicationExtension.class)
class EmployeeDetailsControllerTest {
    Stage theStage;
    EmployeeDetailsController ctrl;
    EmployeeAccountModel model;
    UserDAO dao = UserDAO.getInstance();

    @Start
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle =ResourceBundle.getBundle("employeeDetailsTranslation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/employeeAccount.fxml"),bundle);
        model = new EmployeeAccountModel();
        loader.setController(new EmployeeDetailsController(model));
        Parent root = loader.load();
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }
//    @Test
//    void btnDeleteClicked(FxRobot robot) {
//        int size = dao.getInfoList().size();
//        robot.clickOn("#btnDelete");
//        int s = dao.getInfoList().size();
//        size--;
//        assertEquals(size, s);
//    }

    @Test
    void changeDepartment(FxRobot robot) {
        robot.clickOn("#btnEdit");
        robot.clickOn("#departmentChoice");
        robot.clickOn("3, Safety");
        robot.clickOn("#btnEdit");

        Employee employee = dao.getInfoList().get(0);
        assertEquals("Safety", employee.getDepartment().getDepartmentName());
    }

    @Test
    void editName(FxRobot robot) {

        robot.clickOn("#btnNext");

        Employee employee = model.getCurrentEmployee();
        String name = employee.getFirstName();

        robot.clickOn("#btnEdit");
        robot.clickOn("#nameField");
        robot.write("abc");
        robot.clickOn("#btnEdit");

        name += "abc";
        employee = model.getCurrentEmployee();

        assertEquals(name, employee.getFirstName());
    }

    @Test
    void nameValidation(FxRobot robot) {

        Employee employee = model.getCurrentEmployee();
        String name = employee.getFirstName();

        robot.clickOn("#btnEdit");
        robot.clickOn("#nameField");
        robot.write("1");
        robot.clickOn("#btnEdit");

        name += "1";
        employee = model.getCurrentEmployee();

        assertFalse(employee.getFirstName().equals(name));
    }

    @Test
    void salaryValidation(FxRobot robot) {

        robot.clickOn("#btnNext");
        Employee employee = model.getCurrentEmployee();
        String salary = String.valueOf(employee.getSalary());

        robot.clickOn("#btnEdit");
        robot.clickOn("#salaryField");
        robot.write("a");
        robot.clickOn("#btnEdit");

        salary += "a";
        employee = model.getCurrentEmployee();

        assertFalse(String.valueOf(employee.getSalary()).equals(salary));
    }

}