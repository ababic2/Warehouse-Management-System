package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.Controllers.DashboardControllers.DashboardController;
import ba.unsa.etf.rpr.Controllers.ItemDetailsControllers.ItemDetailsController;
import ba.unsa.etf.rpr.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

import static ba.unsa.etf.rpr.Controllers.LogInController.currentUser;

public class HomeController {
    public SplitPane splitPane;
    public Pane rightPane;
    public GridPane leftPane;

    public Label currentAccess;
    public Label currLoggedIn;

    @FXML
    private Label loggedLabel;

    private Button temp = null;
    private GridPane newRightPane = null;
    private static String username = "";
    private static String accessLevel = "";
    private HashMap<String, String> views = new HashMap<>();

    @FXML
    public void initialize() throws IOException {
        loadViews();
        currLoggedIn.setText(currentUser.getUsername());
        currentAccess.setText(currentUser.getAccessLevel());
        ctrlRightPane("/fxml/dashboard/dashboard.fxml","Dashboard");
    }

    private void loadViews() {
        views.put("Dashboard", "/fxml/dashboard/dashboard.fxml");
        views.put("Items", "/fxml/itemDetails.fxml");
        views.put("Firms", "/fxml/firmDetails.fxml");
        views.put("Employee Accounts", "/fxml/employeeAccount.fxml");
        views.put("Admin", "/fxml/adminPanel.fxml");
    }

    @FXML
    void btnNavigator(ActionEvent event) {
        borderSelector(event); //Marking selected navigator button

        Button btn = (Button)event.getSource();

        String btnText = btn.getText();
        if(btnText.equals("Log Out")) logOut();
        else {

            try {
                ctrlRightPane(views.get(btnText), btnText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //When log out is clicked then log in will appear
    private void logOut() {
        Stage current =  (Stage)loggedLabel.getScene().getWindow();
        current.close();

        try {
            LogInModel logInModel = new LogInModel();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/log_in.fxml"));
            loader.setController(new LogInController(logInModel));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage logInPrompt = new Stage();
            logInPrompt.setScene(scene);
            logInPrompt.show();

        } catch (IOException e) {
        }
    }

    @FXML
    private void ctrlRightPane(String URL, String name) throws IOException {
        try {
            rightPane.getChildren().clear(); //Removing previous nodes
            setNewStage(URL, name);

            newRightPane.setPrefHeight(rightPane.getHeight());
            newRightPane.setPrefWidth(rightPane.getWidth());

            rightPane.getChildren().add(newRightPane);

            //Listener to monitor any window size change
            rightPane.layoutBoundsProperty().addListener((obs, oldValue, newValue) -> {
                autoResizePane();
            });

        } catch (IOException e) {}
    }

    private void setNewStage(String URL, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(URL));
        if(name.equals("Dashboard")) {
            DashboardModel dashBoardModel = new DashboardModel();
            loader.setController(new DashboardController(dashBoardModel));
        } else if(name.equals("Items")) {
            ProductModel productModel = new ProductModel();
            loader.setController(new ItemDetailsController(productModel));
        } else if(name.equals("Employee Accounts")) {
            EmployeeAccountModel employeeAccountModel = new EmployeeAccountModel();
            loader.setController(new EmployeeDetailsController(employeeAccountModel));
        } else if(name.equals("Admin")) {
            AdminPanelModel adminPanelModel = new AdminPanelModel();
            loader.setController(new AdminPanelController(adminPanelModel));
        } else if(name.equals("Firms")) {
            FirmModel firmModel = new FirmModel();
            loader.setController(new FirmDetailsController(firmModel));
        }
        newRightPane = loader.load();
    }

    private void autoResizePane() {
        newRightPane.setPrefWidth(rightPane.getWidth());
        newRightPane.setPrefHeight(rightPane.getHeight());
        newRightPane.setMaxWidth(rightPane.getMaxWidth());
        newRightPane.setMaxHeight(rightPane.getMaxHeight());
        newRightPane.resize(rightPane.getWidth(),rightPane.getHeight());
    }

    private void borderSelector(ActionEvent event) {
        Button btn = (Button)event.getSource();

        if(temp == null) {
            temp = btn; //Saving current button properties
        } else {
            temp.setStyle(""); //Resetting previous selected button properties
            temp = btn; //Saving current button properties
        }
        btn.setStyle("-fx-background-color: #455A64");
    }
}
