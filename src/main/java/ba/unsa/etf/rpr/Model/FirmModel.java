package ba.unsa.etf.rpr.Model;

import ba.unsa.etf.rpr.DAL.DAO.FirmDAO;
import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.DAL.DTO.Firm;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import java.sql.Connection;

public class FirmModel {

    private final FirmDAO firmDAO = FirmDAO.getInstance();
    private ObservableList<Firm> firms = firmDAO.getInfoList();
    private final ObjectProperty<Firm> currentFirm = new SimpleObjectProperty<>();

    public ObservableList<Firm> getFirms() {
        return firms;
    }

    public void setFirms(ObservableList<Firm> firms) {
        this.firms = firms;
    }

    public Firm getCurrentFirm() {
        return currentFirm.get();
    }

    public ObjectProperty<Firm> currentFirmProperty() {
        return currentFirm;
    }

    public void setCurrentFirm(Firm currentFirm) {
        this.currentFirm.set(currentFirm);
    }

    public void changeCurrent(int page) {
        currentFirm.set(firms.get(page));
    }

    public void deleteUserWithId(int id) {
        firmDAO.deleteUserWithId(id);

    }

    public void updateEmployee() {
        firmDAO.updateFirm(currentFirm.getValue().getFirmId(), currentFirm.getValue().getFirmName(),
                currentFirm.getValue().getOwner(), currentFirm.getValue().getFirmEMail(),
                currentFirm.getValue().getFirmPhone(), currentFirm.getValue().getFirmAdress());
    }


//    public void addEmployeeToBase(Employee employee) {
//        userDAO.addEmployee(employee.getEmployeeId(), employee.getFirstName(),
//                employee.getLastName(), employee.getUsername(), employee.getPassword(),
//                employee.getAccessLevelString(), employee.geteMail(), employee.getSalary(), employee.getHireDate(),
//                employee.getDepartment().getDepartmentId());
//    }

//    public int getMaxId() {
//        return userDAO.getMaxID();
//    }
//
//    public Connection getConn() {
//        return userDAO.getConn();
//    }
}