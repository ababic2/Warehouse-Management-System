package ba.unsa.etf.rpr.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class HomeController {
    public SplitPane splitPane;
    public Label loggedLabel;
//    public Label accessLabel;
    public Pane rightPane;
    public GridPane leftPane;
    private Button temp = null;
    private GridPane newRightPane = null;
    private static String username = "";
    private static String accessLevel = "";
    private HashMap<String, String> views = new HashMap<>();
    private TextField haha;


    @FXML
    public void initialize() throws IOException {
        loadViews();
        //username = LogInController.username;
//        accessLevel = LogInController.;
//        loggedLabel.setText(username.toUpperCase());
//        accessLabel.setText(accessLevel);
        ctrlRightPane("/fxml/dashboard.fxml");

    }

    private void loadViews() {
        views.put("Dashboard", "/fxml/dashboard.fxml");
        views.put("Items", "/fxml/itemDetails.fxml");
        views.put("Firms", "/fxml/firmDetails.fxml");
        views.put("Employee Accounts", "/fxml/employeeAccount.fxml");
    }

    @FXML
    void btnNavigator(ActionEvent event) {
        borderSelector(event); //Marking selected navigator button

        Button btn = (Button)event.getSource();


        String btnText = btn.getText();
        if(btnText.equals("Log Out")) logOut();
        else {

            try {
                ctrlRightPane(views.get(btnText));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //when log out is clicked then log in will appear
    private void logOut() {
        Stage current =  (Stage)loggedLabel.getScene().getWindow();
        current.close();

        try {
            // Setting login window
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/log_in.fxml"));

            Scene scene = new Scene(root);
            Stage logInPrompt = new Stage();
            logInPrompt.setScene(scene);
            logInPrompt.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ctrlRightPane(String URL) throws IOException {
        try {
            rightPane.getChildren().clear(); //Removing previous nodes
            newRightPane = FXMLLoader.load(getClass().getResource(URL));

            newRightPane.setPrefHeight(rightPane.getHeight());
            newRightPane.setPrefWidth(rightPane.getWidth());

            rightPane.getChildren().add(newRightPane);

            //Listener to monitor any window size change

            rightPane.layoutBoundsProperty().addListener((obs, oldValue, newValue) -> {
                autoResizePane();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void autoResizePane() {
//        newRightPane.setLayoutX(rightPane.getWidth());
//        newRightPane.setLayoutY(rightPane.getHeight());
        newRightPane.setPrefWidth(rightPane.getWidth());
        newRightPane.setPrefHeight(rightPane.getHeight());
        newRightPane.setMaxWidth(rightPane.getMaxWidth());
        newRightPane.setMaxHeight(rightPane.getMaxHeight());
        newRightPane.resize(rightPane.getWidth(),rightPane.getHeight());
    }

    /**
     * This method will mark selected navigator
     * from left navigation pane and will remove it if another
     * navition button is clicked.
     * @param event
     */
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
