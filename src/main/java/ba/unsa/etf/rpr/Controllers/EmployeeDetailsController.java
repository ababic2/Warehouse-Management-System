package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.HelpModel.Reference;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.ResourceBundle;

public class EmployeeDetailsController implements Initializable {


    public TextField nameField;
    public TextField mailField;
    public TextField salaryField;
    public TextField departmentField;
    public TextField dateField;

    public Button btnAdd;
    public Button btnNext;
    public Button btnPrevious;

    public Label idLabel;

    public CheckBox checkBox;

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty mail;
    private SimpleIntegerProperty salary;
    private SimpleStringProperty department;
    private SimpleStringProperty date;

    private UserDAO userDAO = UserDAO.getInstance();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    //used for SimpleIntegerProperty to SimpleStringProperty
    private NumberStringConverter converter = new NumberStringConverter();
    private ObjectProperty<Employee> currentEmployee;
    private int page = 0;


    public EmployeeDetailsController() {
        id = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty("");
        mail = new SimpleStringProperty("");
        salary = new SimpleIntegerProperty(0);
        department = new SimpleStringProperty("");
        date = new SimpleStringProperty("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employees.addAll(userDAO.getInfoList());
        bindingFieldsWithProperties();
        changeCurrent();
        setInitialEmployee();
        setBinding();
        setFieldsDisableTo(true);

        setButtonsDisableTo();

        setButtonsNextPrevious();
        btnPrevious.setDisable(true);
    }

    private void setInitialEmployee() {
        name.setValue(currentEmployee.getValue().getFirstName() +" " + currentEmployee.getValue().getLastName());
        mail.setValue(currentEmployee.getValue().geteMail());
        department.setValue(currentEmployee.getValue().getDepartmentName());
    }

    private void setFieldsDisableTo(boolean var) {
        nameField.setDisable(var);
        mailField.setDisable(var);
        salaryField.setDisable(var);
        departmentField.setDisable(var);
        dateField.setDisable(var);
    }

    private void changeCurrent() {
        currentEmployee.set(employees.get(page));
    }

    private void bindingFieldsWithProperties() {

        nameField.textProperty().bindBidirectional(name);
        mailField.textProperty().bindBidirectional(mail);
        salaryField.textProperty().bindBidirectional(salary, converter);
        dateField.textProperty().bindBidirectional(date);
        departmentField.textProperty().bindBidirectional(department);
    }

    public void btnPreviousClicked(ActionEvent actionEvent) {

    }

    public void btnNextClicked(ActionEvent actionEvent) {

    }

    public void btnEditClicked(ActionEvent actionEvent) {

    }

    public void btnDeleteClicked(ActionEvent actionEvent) {

    }

    public void btnAddClicked(ActionEvent actionEvent) {

    }

    public void btnSearchClicked(ActionEvent actionEvent) {

    }
}
