package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.DAL.DAO.FirmDAO;
import ba.unsa.etf.rpr.DAL.DTO.Firm;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class firmListController implements Initializable {

    public TableView<Firm> firmTableView;
    public TableColumn<Firm, Integer> idColumn;
    public TableColumn<Firm, String> usernameColumn;
    public TableColumn<Firm, String> firmNameColumn;
    public TableColumn<Firm, String> mailColumn;
    public TableColumn<Firm, String> ownerColumn;
    public TableColumn<Firm, String> phoneColumn;
    public TableColumn<Firm, String> adessColumn;
    private FirmDAO firmDAO = FirmDAO.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCells();
        firmTableView.setItems(firmDAO.getInfoList());
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
