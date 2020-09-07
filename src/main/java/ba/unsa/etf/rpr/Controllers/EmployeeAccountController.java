package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DTO.Department;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.Model.EmployeeAccountModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeAccountController implements Initializable {

    public Label errorLabel;
    public ChoiceBox<Department> departmentChoice;

    public TextField nameField;
    public TextField lastNameField;
    public TextField salaryField;
    public TextField hireDateField;
    public TextField mailField;
    public TextField usernameField;
    public TextField passwordField;

    public RadioButton radioAdmin;
    public RadioButton radioEmployee;
    public ToggleGroup group;

    private final EmployeeAccountModel model;

    public EmployeeAccountController(EmployeeAccountModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentChoice.setItems(model.getDepartments());
    }

    public void btnSaveClicked(ActionEvent actionEvent) {
        //no repeat of id
        int id = model.getMaxId();
        Employee employee;
        id++;
        if (anyFieldIsNull()) {
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
            String access;
            if(radioAdmin.isSelected()) access = "admin";
            else access = "employee";
            employee = new Employee(
                    id,
                    nameField.getText(),
                    lastNameField.getText(),
                    usernameField.getText(),
                    passwordField.getText(),
                    access,
                    mailField.getText(),
                    Integer.parseInt(salaryField.getText()),
                    hireDateField.getText(),
                    departmentChoice.getSelectionModel().getSelectedItem());
                    model.addEmployeeToBase(employee);
                    Stage current = (Stage) errorLabel.getScene().getWindow();
                    current.close();
            }
    }


    private boolean anyFieldIsNull() {
        return nameField.getText().equals("") && lastNameField.getText().equals("")
                && usernameField.getText().equals("") && passwordField.getText().equals("")
                && salaryField.getText().equals("") && hireDateField.getText().equals("")  &&
                mailField.getText().equals("");
    }
}
