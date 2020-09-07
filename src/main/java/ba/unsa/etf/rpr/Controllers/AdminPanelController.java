package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.Model.AdminPanelModel;
import ba.unsa.etf.rpr.Reports.Report;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;

public class AdminPanelController {

    private AdminPanelModel model;
    public TextField categoryNameField;
    public TextField departmentNameField;
    private int addCategory = 0, addDepartment = 0;

    public AdminPanelController(AdminPanelModel model) {
        this.model = model;
    }

    public void btnAddCategoryClicked(ActionEvent actionEvent) {
        addCategory++;
        if(addCategory == 1) {
            categoryNameField.setDisable(false);
            categoryNameField.setVisible(true);
        } else {
            if(!categoryNameField.getText().equals("")) {
                model.addCategoryToBase(categoryNameField.getText());
            }
            categoryNameField.setVisible(false);
            categoryNameField.setDisable(true);
            categoryNameField.setText("");
            addCategory = 0;
        }
    }

    public void btnAddDepartmentClicked(ActionEvent actionEvent) {
        addDepartment++;
        if(addDepartment == 1) {
            departmentNameField.setDisable(false);
            departmentNameField.setVisible(true);
        } else {
            if(!departmentNameField.getText().equals("")) {
                model.addDepartmentToBase(departmentNameField.getText());
            }
            departmentNameField.setVisible(false);
            departmentNameField.setDisable(true);
            departmentNameField.setText("");
            addDepartment = 0;
        }
    }

    public void btnAddEmployee(ActionEvent actionEvent) {
    }

    public void btnItemsReportClicked(ActionEvent actionEvent) {
        try {
            new Report().showReport(model.getConn(), "/reports/itemsReport/itemsReport.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void btnEmployeesReportClicked(ActionEvent actionEvent){
        try {
            new Report().showReport(model.getConn(), "/reports/employeesReport/employeesReport.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
    public void btnFirmsReportClicked(ActionEvent actionEvent){
        try {
            new Report().showReport(model.getConn(), "/reports/firmsReport/firmsReport.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

}
