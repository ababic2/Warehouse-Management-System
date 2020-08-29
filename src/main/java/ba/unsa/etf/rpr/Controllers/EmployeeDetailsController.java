package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.Interface.DetailsInterface;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static ba.unsa.etf.rpr.Controllers.LogInController.currentUser;

public class EmployeeDetailsController implements Initializable, DetailsInterface {


    public TextField nameField;
    public TextField mailField;
    public TextField salaryField;
    public TextField departmentField;
    public TextField dateField;

    public Button btnAdd;
    public Button btnNext;
    public Button btnPrevious;
    public ToggleButton btnEdit;

    public Label idLabel;

    public CheckBox checkBox;
    public TextField searchField;

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
    private ObjectProperty<Employee> currentEmployee =  new SimpleObjectProperty<>();
    private int page = 0;
    private boolean disable = true;
    private ChangeListener nameListener;
    private ChangeListener salaryListener;
    private ChangeListener mailListener;
    private ChangeListener dateListener;


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
        System.out.println(employees.get(page));
        setInitialEmployee();
        setBinding();
        setFieldsDisableTo(true);

        setButtonsDisableTo();

        DetailsInterface.setButtonsNextPrev(employees.size(), btnNext, btnPrevious, page);

        btnPrevious.setDisable(true);
    }

    private void setButtonsDisableTo() {
        if(!currentUser.getAccessLevel().equals("ADMIN")) {
            btnAdd.setDisable(true);
            btnEdit.setDisable(true);

        } else {
            btnAdd.setDisable(false);
            btnEdit.setDisable(false);
        }
    }

    private void setBinding() {
        currentEmployee.addListener((obs, oldValue, newValue) -> {
            id.setValue(currentEmployee.getValue().getEmployeeId());
            if(oldValue != null) {
                nameField.textProperty().unbindBidirectional(name);
                mailField.textProperty().unbindBidirectional(mail);
                salaryField.textProperty().unbindBidirectional(salary);
                dateField.textProperty().unbindBidirectional(date);
                departmentField.textProperty().unbindBidirectional(department);
            }
            nameField.textProperty().bindBidirectional(newValue.firstNameProperty());
            mailField.textProperty().bindBidirectional(newValue.eMailProperty());
            salaryField.textProperty().bindBidirectional(newValue.salaryProperty(), converter);
            dateField.textProperty().bindBidirectional(newValue.hireDateProperty());
            departmentField.textProperty().bindBidirectional(newValue.getDepartment().departmentNameProperty());
        });
    }

    private void setInitialEmployee() {
        id.setValue(currentEmployee.getValue().getEmployeeId());
        name.setValue(currentEmployee.getValue().getFirstName() +" " + currentEmployee.getValue().getLastName());
        mail.setValue(currentEmployee.getValue().geteMail());
        salary.setValue(currentEmployee.getValue().getSalary());
        department.setValue(currentEmployee.getValue().getDepartment().getDepartmentName());
        date.setValue(currentEmployee.getValue().getHireDate());
    }

    private void setFieldsDisableTo(boolean var) {
        nameField.setDisable(var);
        mailField.setDisable(var);
        salaryField.setDisable(var);
        departmentField.setDisable(var);
        dateField.setDisable(var);
    }

    public void changeCurrent() {
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
        goToPreviousPage();
    }

    private void goToPreviousPage() {
        page--;
        DetailsInterface.setButtonsNextPrev(employees.size(), btnNext, btnPrevious, page);
        changeCurrent();
    }

    public void btnNextClicked(ActionEvent actionEvent) {
        goToNextPage();
    }

    private void goToNextPage() {
        page++;
        DetailsInterface.setButtonsNextPrev(employees.size(), btnNext, btnPrevious, page);
        changeCurrent();
    }

    public void btnDeleteClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(idLabel.getText());
        userDAO.deleteUserWithId(id);

        employees.remove(page);

        if (page == 0) goToNextPage();
        else goToPreviousPage();
    }

    private int editClick = 0;

    public void btnEditClicked(ActionEvent actionEvent) {
        setFieldsDisableTo(!disable);
        disable = !disable;
        editClick++;

        if (editClick == 1) {
            nameListener = (obs, oldValue, newValue) -> {
                name.setValue((String) newValue);
                employees.get(page).setFirstName(name.getValue());
            };
            nameField.textProperty().addListener(nameListener);

            salaryListener = (obs, oldValue, newValue) -> {
                salary.setValue(Integer.parseInt((String) newValue));
                employees.get(page).setSalary(salary.getValue());
            };
            salaryField.textProperty().addListener(salaryListener);

            mailListener = (obs, oldValue, newValue) -> {
                mail.setValue((String) newValue);
                employees.get(page).seteMail(mail.getValue());
            };
            mailField.textProperty().addListener(mailListener);

            dateListener = (obs, oldValue, newValue) -> {
                date.setValue((String) newValue);
                employees.get(page).setHireDate(date.getValue());
            };
            dateField.textProperty().addListener(dateListener);

            departmentListener = (obs, oldValue, newValue) -> {
                department.setValue((String) newValue);
                employees.get(page).seteMail(mail.getValue());
            };
            mailField.textProperty().addListener(mailListener);
        } else if (editClick == 2) {
            changeCurrent();
            userDAO.updateProduct(currentEmployee.getValue().getEmployeeId(), currentEmployee.getValue().getName(),
                    currentProduct.getValue().getPrice(), currentProduct.getValue().getStock());

            nameField.textProperty().removeListener(nameListener);
            salaryField.textProperty().removeListener(salaryListener);
            mailField.textProperty().removeListener(mailListener);
            editClick = 0;
        }
    }

    public void btnAddClicked(ActionEvent actionEvent) {

    }

    public void btnSearchClicked(ActionEvent actionEvent) {
        if (checkBox.isSelected()) {
            checkBoxSelected();
        } else {
            if (searchField.getText() != null) {
                Employee employee = employees.stream().filter(s -> s.getFirstName().equals(searchField.getText())).collect(Collectors.toList()).get(0);
                if (employee == null) {
                    setAlertWindow("No result !");
                } else {
                    currentEmployee.set(employee);
                    findPageAfterSearch();
                }
            }
        }
    }

    private void checkBoxSelected() {
        if (searchField.getText() != null) {
            if (isNumeric(searchField.getText())) {
                int id = Integer.parseInt(searchField.getText());
                Employee employee = employees.stream().filter((emp) -> emp.getEmployeeId() == id).collect(Collectors.toList()).get(0);
                if (employee == null) {
                    setAlertWindow("No result !");
                } else {
                    currentEmployee.set(employee);
                    findPageAfterSearch();
                }
            } else {
                setAlertWindow("ID must be numeric !");
            }
        }
    }

    private void findPageAfterSearch() {
        for(int i = 0; i < employees.size(); i++) {
            if(currentEmployee.getValue().compareTo(employees.get(i)) == 0) {
                page = i;
                break;
            }
        }
        DetailsInterface.setButtonsNextPrev(employees.size(), btnNext, btnPrevious, page);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public String getMail() {
        return mail.get();
    }

    public SimpleStringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public int getSalary() {
        return salary.get();
    }

    public SimpleIntegerProperty salaryProperty() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
