package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAL.DTO.Department;
import ba.unsa.etf.rpr.projekat.DAL.DTO.Employee;
import ba.unsa.etf.rpr.projekat.Model.EmployeeAccountModel;
import ba.unsa.etf.rpr.projekat.Reports.EmploymentContract;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

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
    public Button btnCon;
    public ToggleGroup group;

    private final EmployeeAccountModel model;

    public EmployeeAccountController(EmployeeAccountModel model) {
        this.model = model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentChoice.setItems(model.getDepartments());
        btnCon.setDisable(true);
    }

    private Employee employee;

    public void btnSaveClicked(ActionEvent actionEvent) {
        //no repeat of id
        int id = model.getMaxId();
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
                    btnCon.setDisable(false);
            }
    }

    public void btnContractClicked(ActionEvent actionEvent) {
        if(!anyFieldIsNull()) {
            try {
                new EmploymentContract().showReport(model.getConn(), "/reports/employeesReport/employeeContract.jrxml",employee.getEmployeeId());
            } catch (JRException e1) {
                e1.printStackTrace();
            }
        }
        Stage current = (Stage) errorLabel.getScene().getWindow();
        current.close();
    }


    private boolean anyFieldIsNull() {
        return nameField.getText().equals("") && lastNameField.getText().equals("")
                && usernameField.getText().equals("") && passwordField.getText().equals("")
                && salaryField.getText().equals("") && hireDateField.getText().equals("")  &&
                mailField.getText().equals("");
    }
}
