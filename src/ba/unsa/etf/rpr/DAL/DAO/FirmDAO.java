package ba.unsa.etf.rpr.DAL.DAO;

import Exceptions.FailedBaseRegeneration;
import ba.unsa.etf.rpr.DAL.DTO.Firm;
import ba.unsa.etf.rpr.HelpModel.DBConnection;
import ba.unsa.etf.rpr.Interface.DAOInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirmDAO implements DAOInterface {
    private static FirmDAO instance;

    private PreparedStatement countFirmsStatement;
    private PreparedStatement firmsStatement;

    private Connection conn = null;
    private ObservableList<Firm> firms = FXCollections.observableArrayList();

    private FirmDAO() {
        try {
            conn = DBConnection.getConnection();
            prepareStatements();
        } catch (SQLException throwables) {
            try {
                baseRegeneration();
                try {
                    prepareStatements();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (FailedBaseRegeneration failedBaseRegeneration) {
                failedBaseRegeneration.getMessage();
            }
        }
    }

    public static FirmDAO getInstance() {
        if(instance == null) instance = new FirmDAO();
        return instance;
    }

    public void prepareStatements() throws SQLException {
        countFirmsStatement = conn.prepareStatement("select count(firm_id) from firms");
        firmsStatement = conn.prepareStatement("select * from firms");
    }

    public Integer getCount() {
        System.out.println(count(countFirmsStatement).toString());
        return count(countFirmsStatement);
    }

    @Override
    public void addToList() {
        if(firms.size() > 0) {
            firms.clear();
        }
        try {
            ResultSet rs = firmsStatement.executeQuery();
            while(rs.next()) {
                Firm firm = new Firm (rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                firms.add(firm);
            }
        } catch (SQLException throwables) {

        }
    }

    public ObservableList<Firm> getInfoList() {
        addToList();
        return firms;
    }
}
