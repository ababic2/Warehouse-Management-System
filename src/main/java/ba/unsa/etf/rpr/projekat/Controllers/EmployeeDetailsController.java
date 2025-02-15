package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.DAL.DTO.Department;
import ba.unsa.etf.rpr.projekat.DAL.DTO.Employee;
import ba.unsa.etf.rpr.projekat.Interface.DetailsInterface;
import ba.unsa.etf.rpr.projekat.Model.EmployeeAccountModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeeDetailsController implements Initializable, DetailsInterface {

    public TextField nameField;
    public TextField mailField;
    public TextField salaryField;
//    public TextField dateField;

    public Button btnNext;
    public Button btnPrevious;
    public ToggleButton btnEdit;

    public Label idLabel;
    private Label nameErrorLabel;
    private Label salaryErrorLabel;

    public CheckBox checkBox;
    public ChoiceBox<Department> departmentChoice;
    public TextField searchField;
    public RadioButton radioEmployee;
    public RadioButton radioAdmin;
    public ToggleGroup toggle;
    public GridPane nameGrid, salaryGrid;

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty mail;
    private SimpleIntegerProperty salary;
    private SimpleStringProperty department;
    private SimpleStringProperty date;

    public DatePicker datePicker;

    //used for SimpleIntegerProperty to SimpleStringProperty
    private NumberStringConverter converter = new NumberStringConverter();

    private int page = 0;
    private boolean disable = true;
    private ChangeListener nameListener;
    private ChangeListener salaryListener;
    private ChangeListener mailListener;
    private ChangeListener dateListener;
    private int editClick = 0;

    private EmployeeAccountModel model;

    public EmployeeDetailsController(EmployeeAccountModel employeeAccountModel) {
        this();
        this.model = employeeAccountModel;
    }

    public EmployeeDetailsController() {
        id = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty("");
        mail = new SimpleStringProperty("");
        salary = new SimpleIntegerProperty(0);
        department = new SimpleStringProperty("");
//        date = new SimpleStringProperty("");
    }

    private String pattern = "dd-MM-yyyy";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }

    public String dateToString(LocalDate date) {
        if (date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        departmentChoice.setItems(model.getDepartments());
        datePicker.setValue(fromString(model.getEmployees().get(0).getHireDate()));
//
//        try {
//            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(model.getCurrentEmployee().getHireDate());
//            datePicker.setValue(new LocalDate.of());
//        } catch (ParseException e) {
//        }

        bindingFieldsWithProperties();
        model.changeCurrent(page);

        setInitialEmployee();
        setBinding();
        setFieldsBoxRadioDisableTo(true);

        setRadioButtons();

        setButtonsDisableTo();
        DetailsInterface.setButtonsNextPrev(model.getEmployees().size(), btnNext, btnPrevious, page);
        btnPrevious.setDisable(true);
    }

    private void setButtonsDisableTo() {
//        if(!currentUser.getAccessLevel().equals("ADMIN")) {
//            btnAdd.setDisable(true);
//            btnEdit.setDisable(true);
//
//        } else {
//            btnAdd.setDisable(false);
//            btnEdit.setDisable(false);
//        }
    } //zakomentarisano

    private void setBinding() {
        model.currentEmployeeProperty().addListener((obs, oldValue, newValue) -> {
            id.setValue(model.getCurrentEmployee().getEmployeeId());
            if(oldValue != null) {
                nameField.textProperty().unbindBidirectional(oldValue.firstNameProperty());
                mailField.textProperty().unbindBidirectional(oldValue.eMailProperty());
                salaryField.textProperty().unbindBidirectional(oldValue.salaryProperty());
               // dateField.textProperty().unbindBidirectional(oldValue.hireDateProperty());
            }
            nameField.textProperty().bindBidirectional(newValue.firstNameProperty());
            mailField.textProperty().bindBidirectional(newValue.eMailProperty());
            salaryField.textProperty().bindBidirectional(newValue.salaryProperty(), converter);
            //dateField.textProperty().bindBidirectional(newValue.hireDateProperty());
        });
    }

    private void setInitialEmployee() {
        id.setValue(model.getCurrentEmployee().getEmployeeId());
        name.setValue(model.getCurrentEmployee().getFirstName() +" " + model.getCurrentEmployee().getLastName());
        mail.setValue(model.getCurrentEmployee().geteMail());
        salary.setValue(model.getCurrentEmployee().getSalary());
       // date.setValue(model.getCurrentEmployee().getHireDate());
        departmentChoice.setValue(model.getDepartments().get(0));
        setRadioButtons();
    }

    private void setFieldsBoxRadioDisableTo(boolean var) {
        nameField.setDisable(var);
        mailField.setDisable(var);
        salaryField.setDisable(var);
//        dateField.setDisable(var);
        departmentChoice.setDisable(var);
        datePicker.setDisable(var);
        radioAdmin.setDisable(var);
        radioEmployee.setDisable(var);
    }

    private void bindingFieldsWithProperties() {
        nameField.textProperty().bindBidirectional(name);
        mailField.textProperty().bindBidirectional(mail);
        salaryField.textProperty().bindBidirectional(salary, converter);
//        dateField.textProperty().bindBidirectional(date);
    }

    public void btnPreviousClicked(ActionEvent actionEvent) {
        goToPreviousPage();
    }

    private void goToPreviousPage() {
        page--;
        DetailsInterface.setButtonsNextPrev(model.getEmployees().size(), btnNext, btnPrevious, page);
        model.changeCurrent(page);
        int id = model.getEmployees().get(page).getDepartment().getDepartmentId();
        departmentChoice.setValue(model.getDepartments().stream().filter(s -> s.getDepartmentId() == (id)).collect(Collectors.toList()).get(0));
        datePicker.setValue(fromString(model.getEmployees().get(page).getHireDate()));

        setRadioButtons();
    }

    public void btnNextClicked(ActionEvent actionEvent) {
        goToNextPage();
    }

    private void goToNextPage() {
        page++;
        DetailsInterface.setButtonsNextPrev(model.getEmployees().size(), btnNext, btnPrevious, page);
        model.changeCurrent(page);
        int id = model.getEmployees().get(page).getDepartment().getDepartmentId();
        departmentChoice.setValue(model.getDepartments().stream().filter(s -> s.getDepartmentId() == id).collect(Collectors.toList()).get(0));
        datePicker.setValue(fromString(model.getCurrentEmployee().getHireDate()));
        setRadioButtons();
    }

    private void setRadioButtons() {
        if(model.getCurrentEmployee().getAccessLevelString().equals("admin")) {
            radioAdmin.setSelected(true);
        } else {
            radioEmployee.setSelected(true);
        }
    }

    public void btnDeleteClicked(ActionEvent actionEvent) {
        int id = Integer.parseInt(idLabel.getText());

        model.deleteUserWithId(id);
        model.getEmployees().remove(page);

        if (page == 0) goToNextPage();
        else goToPreviousPage();
    }

    public void btnEditClicked(ActionEvent actionEvent) {
        setFieldsBoxRadioDisableTo(!disable);
        disable = !disable;
        editClick++;

        if (editClick == 1) {
            nameListener = (obs, oldValue, newValue) -> {
                if(!nameFormat((String) newValue)) {
                    try {
                        nameErrorLabel = FXMLLoader.load(getClass().getResource("/fxml/dynamic/itemNameErrorLabel.fxml"));
                        nameGrid.add(nameErrorLabel, 0, 2);
                        model.getEmployees().get(page).setFirstName((String) oldValue);
                    } catch (IOException e) {
                    }
                } else {
                    name.setValue((String) newValue);
                    model.getEmployees().get(page).setFirstName(name.getValue());
                }
            };
            nameField.textProperty().addListener(nameListener);

            salaryListener = (obs, oldValue, newValue) -> {
                if(!digitFormat(salaryField.getText())) {
                    try {
                        salaryErrorLabel = FXMLLoader.load(getClass().getResource("/fxml/dynamic/onlyNumberErrorLabel.fxml"));
                        salaryGrid.add(salaryErrorLabel, 0, 2);
                        model.getEmployees().get(page).setSalary(Integer.parseInt((String) oldValue));
                    } catch (IOException e) {
                    }
                }else {
                    salary.setValue(Integer.parseInt((String) newValue));
                    salaryGrid.getChildren().remove(salaryErrorLabel);
                    model.getEmployees().get(page).setSalary(salary.getValue());
                }
            };
            salaryField.textProperty().addListener(salaryListener);

            mailListener = (obs, oldValue, newValue) -> {
                mail.setValue((String) newValue);
                model.getEmployees().get(page).seteMail(mail.getValue());
            };
            mailField.textProperty().addListener(mailListener);
            datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
                model.getEmployees().get(page).setHireDate(dateToString(datePicker.getValue()));
            });

            departmentChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Department>() {
                @Override
                public void changed(ObservableValue<? extends Department> observableValue, Department department, Department t1) {
                    model.getEmployees().get(page).setDepartment(departmentChoice.getSelectionModel().getSelectedItem());
                }
            });


        toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                    if (toggle.getSelectedToggle() != null) {
                        if(toggle.getSelectedToggle().equals(radioAdmin)) {
                            model.getEmployees().get(page).setAccessLevelString("admin");
                        } else {
                            model.getEmployees().get(page).setAccessLevelString("employee");
                        }

                    }
                }
            });

        }
        else if (editClick == 2) {
            nameGrid.getChildren().remove(nameErrorLabel);
            salaryGrid.getChildren().remove(salaryErrorLabel);

            model.changeCurrent(page);
            model.updateEmployee();

            nameField.textProperty().removeListener(nameListener);
            mailField.textProperty().removeListener(mailListener);
            salaryField.textProperty().removeListener(salaryListener);
            editClick = 0;
        }
    }

    public void btnSearchClicked(ActionEvent actionEvent) {
        if (checkBox.isSelected()) {
            checkBoxSelected();
        } else {
            if (searchField.getText() != null) {
                List<Employee> list = model.getEmployees().stream().filter(s -> s.getFirstName().equals(searchField.getText())).collect(Collectors.toList());
                if (list.size() == 0) {
                    setAlertWindow("No result !");
                } else {
                    model.setCurrentEmployee(list.get(0));
                    findPageAfterSearch();
                }
            }
        }
    }

    private void checkBoxSelected() {
        if (searchField.getText() != null) {
            if (isNumeric(searchField.getText())) {
                int id = Integer.parseInt(searchField.getText());
                List<Employee> list = model.getEmployees().stream().filter((emp) -> emp.getEmployeeId() == id).collect(Collectors.toList());
                if (list.size() == 0) {
                    setAlertWindow("No result !");
                } else {
                    model.setCurrentEmployee(list.get(0));
                    findPageAfterSearch();
                }
            } else {
                setAlertWindow("ID must be numeric !");
            }
        }
    }

    private void findPageAfterSearch() {
        for(int i = 0; i < model.getEmployees().size(); i++) {
            if(model.getCurrentEmployee().compareTo(model.getEmployees().get(i)) == 0) {
                page = i;
                break;
            }
        }
        DetailsInterface.setButtonsNextPrev(model.getEmployees().size(), btnNext, btnPrevious, page);
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