package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Model.AdminPanelModel;
import ba.unsa.etf.rpr.projekat.Model.EmployeeAccountModel;
import ba.unsa.etf.rpr.projekat.Reports.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

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

    public void btnAddEmployeeClicked(ActionEvent actionEvent) {
        EmployeeAccountModel logInModel = new EmployeeAccountModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_fxml/addEmployee.fxml"));
        loader.setController(new EmployeeAccountController(logInModel));
        Parent root = null;
        try {
            root = loader.load();
            Stage logInPrompt = new Stage();
            Scene scene = new Scene(root);
            logInPrompt.setScene(scene);
            logInPrompt.show();
        } catch (IOException e) {
        }

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
