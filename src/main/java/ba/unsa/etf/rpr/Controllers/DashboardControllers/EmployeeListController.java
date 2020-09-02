package ba.unsa.etf.rpr.Controllers.DashboardControllers;

import ba.unsa.etf.rpr.DAL.DTO.Department;
import ba.unsa.etf.rpr.Model.DashboardModel;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeListController implements Initializable {

    public TableView<Employee> employeeTableView;
    public TableColumn<Employee, Integer> idColumn;
    public TableColumn<Employee, String> usernameColumn;
    public TableColumn<Employee, String> firstNameColumn;
    public TableColumn<Employee, String> lastNameColumn;
    public TableColumn<Employee, String> accessColumn;
    public TableColumn<Employee, String> mailColumn;
    public TableColumn<Employee, String> salaryColumn;
    public TableColumn<Employee, String> hireDateColumn;
    public TableColumn<Employee, Department> depColumn;
    private DashboardModel dashboardModel;

    public EmployeeListController(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCells();
        employeeTableView.setItems(dashboardModel.getEmployees());
    }

    private void setCells() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        accessColumn.setCellValueFactory(new PropertyValueFactory<>("accessLevelString"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("eMail"));

        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        depColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
    }
}
