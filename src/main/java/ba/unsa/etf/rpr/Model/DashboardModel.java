package ba.unsa.etf.rpr.Model;

import ba.unsa.etf.rpr.DAL.DAO.FirmDAO;
import ba.unsa.etf.rpr.DAL.DAO.ProductDAO;
import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import ba.unsa.etf.rpr.DAL.DTO.Employee;
import ba.unsa.etf.rpr.DAL.DTO.Firm;
import ba.unsa.etf.rpr.DAL.DTO.Product;
import javafx.collections.ObservableList;

public class DashboardModel {

    private UserDAO userDAO = UserDAO.getInstance();
    private FirmDAO firmDAO = FirmDAO.getInstance();
    private ProductDAO productDAO = ProductDAO.getInstance();

    public ObservableList<Employee> getEmployees() {
        return userDAO.getInfoList();
    }

    public ObservableList<Product> getProducts() { return productDAO.getInfoList(); }

    public ObservableList<Firm> getFirms() { return firmDAO.getInfoList(); }

    public ObservableList<Product> getLowStockProducts() {
        return productDAO.getInfoLowStockList();
    }

    public String getNumberOfEmployed() { return userDAO.getCount().toString();}

    public String getNumberOfFirms() {
        return firmDAO.getCount().toString();
    }

    public String getNumberOfItems() {
        return productDAO.getCount().toString();
    }

    public String getNumberOfLowStockItems() { return productDAO.getCountLowStock().toString();}
}