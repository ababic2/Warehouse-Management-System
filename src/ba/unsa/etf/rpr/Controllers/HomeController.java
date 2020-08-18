package ba.unsa.etf.rpr.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
        //views.put("Dashboard", "/main/resources/fxml/logInFinal.fxml");
//        views.put("Items", "/main/resources/view/inventory.fxml");
//        views.put("Customers", "/main/resources/view/customer.fxml");
//        views.put("Sells", "/main/resources/view/sells.fxml");
//        views.put("Rentals", "/main/resources/view/rentals.fxml");
//        views.put("Accounts", "/main/resources/view/accounts.fxml");
//        views.put("Administrative", "/main/resources/view/administrator.fxml");
//        views.put("Update Due", "/main/resources/view/dueupdate.fxml");
    }

    @FXML
    void btnNavigator(ActionEvent event) {
        borderSelector(event); //Marking selected navigator button

        Button btn = (Button)event.getSource();

        // Getting navigation button label
        String btnText = btn.getText();

        // Checking which button is clicked from the map
        // and navigating to respective menu
        try {
            ctrlRightPane(views.get(btnText));
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
//            rightPane.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
//                // Some components of the scene will be resized automatically
//                autoResizePane();
//            });

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
