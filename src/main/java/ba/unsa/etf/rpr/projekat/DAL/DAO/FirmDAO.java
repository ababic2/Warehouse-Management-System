package ba.unsa.etf.rpr.projekat.DAL.DAO;

import ba.unsa.etf.rpr.projekat.DAL.DTO.Firm;
import ba.unsa.etf.rpr.projekat.HelpModel.Reference;
import ba.unsa.etf.rpr.projekat.Interface.DAOInterface;
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
    private Reference<Connection> connReference = new Reference<>(null);

    private FirmDAO() {
        connectToBase(connReference);
        conn = connReference.get();
    }

    public static FirmDAO getInstance() {
        if(instance == null) instance = new FirmDAO();
        return instance;
    }

    public Integer getCount() {
        return count(countFirmsStatement);
    }

    public ObservableList<Firm> getInfoList() {
        addToList(firmsStatement);
        return firms;
    }

    public void prepareStatements() throws SQLException {
        conn = connReference.get();
        countFirmsStatement = conn.prepareStatement("select count(firm_id) from firms");
        firmsStatement = conn.prepareStatement("select * from firms");
    }

    @Override
    public void addToList(PreparedStatement statement) {
        if(firms.size() > 0) {
            firms.clear();
        }
        try {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Firm firm = new Firm (rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                firms.add(firm);
            }
        } catch (SQLException throwables) {

        }
    }

    public void updateFirm(int firmId, String firmName, String owner, String firmEMail, String firmPhone, String firmAdress) {
        try {
            PreparedStatement updateFirm =
                    conn.prepareStatement("update firms set firm_name = ?, owner = ?, firm_mail = ?, phone = ?, adress = ? where firm_id = ? ");
            updateFirm.setString(1, firmName);
            updateFirm.setString(2, owner);
            updateFirm.setString(3, firmEMail);
            updateFirm.setString(4, firmPhone);
            updateFirm.setString(5, firmAdress);
            updateFirm.setInt(6, firmId);
            updateFirm.executeUpdate();
        } catch (SQLException exception) {

        }
    }

    public void deleteUserWithId(int id) {
        try {
            PreparedStatement deleteStatement = conn.prepareStatement("delete from firms where firm_id = ?");
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException exception) {
        }
    }
}
