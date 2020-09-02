package ba.unsa.etf.rpr.Model;

import ba.unsa.etf.rpr.DAL.DAO.CategoryDAO;
import ba.unsa.etf.rpr.DAL.DAO.DepartmentDAO;

public class AdminPanelModel {

    private CategoryDAO categoryDAO = CategoryDAO.getInstance();
    private DepartmentDAO departmentDAO = DepartmentDAO.getInstance();

    public void addCategoryToBase(String name) {
        categoryDAO.addCategory(name);
    }
    public void addDepartmentToBase(String name) {
        departmentDAO.addDepartment(name);
    }
}