package ba.unsa.etf.rpr.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Users {
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private ObjectProperty<Employee> currentEmployee = new SimpleObjectProperty<>();

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

//    public void setEmployees(ObservableList<Employee> employees) {
//        this.employees = employees;
//    }

    public Employee getCurrentEmployee() {
        return currentEmployee.get();
    }

    public ObjectProperty<Employee> currentEmployeeProperty() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee.set(currentEmployee);
    }

//    public void ispisiKnjige() {
//        System.out.println("Knjige su:");
//        for (Knjiga k : knjige)
//            System.out.println(k);
//    }

//    void napuni() {
//        employees.add(new Knjiga("Meša Selimović", "Tvrđava", "abcd", 500));
//        knjige.add(new Knjiga("Ivo Andrić", "Travnička hronika", "abcd", 500));
//        knjige.add(new Knjiga("J. K. Rowling", "Harry Potter", "abcd", 500));
//        //trenutnaKnjiga.set(null);
//    }
}
