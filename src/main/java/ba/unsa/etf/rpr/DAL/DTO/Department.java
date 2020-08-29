package ba.unsa.etf.rpr.DAL.DTO;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Department {

    private SimpleIntegerProperty departmentId;
    private SimpleStringProperty departmentName;

    public Department() {
    }

    public Department(int departmentId, String departmentName) {
        this.departmentId = new SimpleIntegerProperty(departmentId);
        this.departmentName = new SimpleStringProperty(departmentName);
    }

    public int getDepartmentId() {
        return departmentId.get();
    }

    public SimpleIntegerProperty departmentIdProperty() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId.set(departmentId);
    }

    public String getDepartmentName() {
        return departmentName.get();
    }

    public SimpleStringProperty departmentNameProperty() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    @Override
    public String toString() {
        return getDepartmentId() + ", " + getDepartmentName();
    }
}
