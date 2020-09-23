package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Model.Shipment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ShipmentController implements Initializable {
    public TableView<Shipment>shipmentTableView;
    public TableColumn<Shipment, String> nameColumn;
    public TableColumn<Shipment, String> priceColumn;
    public TableColumn<Shipment, String> orderDateColumn;
    public TableColumn<Shipment, String> quantityColumn;
    public TableColumn<Shipment, String> employeeColumn;
    private ObservableList<Shipment> shipments = FXCollections.observableArrayList();

    public ShipmentController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCells();
        readFile();
        shipmentTableView.setItems(shipments);
    }

    private void readFile() {
        String data = "";
        ArrayList<String> arrOfStr = new ArrayList<>();
        try {
            File myObj = new File("Ship.txt");
            Scanner myReader = null;
            myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                if(!data.equals(""))
                    arrOfStr.add(data);
            }
            myReader.close();

            for(int i = 0; i < arrOfStr.size(); i++) {
                String[] result = arrOfStr.get(i).split(" ");

                String name = "";
                int j = 0;
                for(j = 0; j < result.length; j++) {
                    if (nameFormat(result[j])) {
                        if(j > 0) {
                            name += " ";
                        }
                        name += result[j];
                    } else {
                        break;
                    }
                }
                Shipment shipment = new Shipment(name, Integer.parseInt(result[j]),Integer.parseInt(result[j+2]),result[j+1],result[j+3]);
                shipments.add(shipment);
            }
        }catch (FileNotFoundException e) {
        }
    }

    private boolean nameFormat(String s) {
        return s.matches("^[A-Za-z]+$");
    }


    private void setCells() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
    }
}
