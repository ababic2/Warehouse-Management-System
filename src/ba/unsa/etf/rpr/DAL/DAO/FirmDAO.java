package ba.unsa.etf.rpr.DAL.DAO;

import ba.unsa.etf.rpr.DAL.DTO.Firm;
import ba.unsa.etf.rpr.Interface.DAOInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class FirmDAO implements DAOInterface {
    private static FirmDAO instance;
    private Connection conn = null;

//  private PreparedStatement usernameStatement;
    private ObservableList<Firm> firms = FXCollections.observableArrayList();




    @Override
    public Integer count() {
        return null;
    }

    @Override
    public void addToList() {

    }
}
