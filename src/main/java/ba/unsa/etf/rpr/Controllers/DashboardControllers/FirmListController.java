package ba.unsa.etf.rpr.Controllers.DashboardControllers;

import ba.unsa.etf.rpr.DAL.DTO.Firm;
import ba.unsa.etf.rpr.Model.DashboardModel;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FirmListController implements Initializable {

    public TableView<Firm> firmTableView;
    public TableColumn<Firm, Integer> idColumn;
    public TableColumn<Firm, String> usernameColumn;
    public TableColumn<Firm, String> firmNameColumn;
    public TableColumn<Firm, String> mailColumn;
    public TableColumn<Firm, String> ownerColumn;
    public TableColumn<Firm, String> phoneColumn;
    public TableColumn<Firm, String> adessColumn;
    private DashboardModel dashboardModel;

    public FirmListController(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCells();
        firmTableView.setItems(dashboardModel.getFirms());
    }

    private void setCells() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("firmId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firmNameColumn.setCellValueFactory(new PropertyValueFactory<>("firmName"));

        mailColumn.setCellValueFactory(new PropertyValueFactory<>("firmEMail"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("firmPhone"));
        adessColumn.setCellValueFactory(new PropertyValueFactory<>("firmAdress"));
    }
}
