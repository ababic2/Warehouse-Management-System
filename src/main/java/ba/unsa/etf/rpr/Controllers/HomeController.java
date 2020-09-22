package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.Controllers.DashboardControllers.DashboardController;
import ba.unsa.etf.rpr.Controllers.ItemDetailsControllers.ItemDetailsController;
import ba.unsa.etf.rpr.Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import static ba.unsa.etf.rpr.Controllers.LogInController.currentUser;

public class HomeController {
    //    public SplitPane splitPane;
    public Pane rightPane;

    public GridPane leftPane;

    public Label currentAccess;
    public Label currLoggedIn;
    public Label timeLabel;
    public Label dateLabel;

    @FXML
    private Label loggedLabel;

    private Button temp = null;
    private GridPane newRightPane = null;
    private GridPane newLeftPane = null;
    private static String username = "";
    private static String accessLevel = "";
    private HashMap<String, String> views = new HashMap<>();

    private Pair<String, String> current;
    public boolean bs = false;

    @FXML
    public void initialize() throws IOException, ParseException {
        loadViews();
        currLoggedIn.setText(currentUser.getUsername());
        currentAccess.setText(currentUser.getAccessLevel());
        setDate();
        timeThread();
        ctrlRightPane("/fxml/dashboard/dashboard.fxml","Dashboard");
    }

    private void timeThread() {
        Runnable clock = new Runnable() {
            @Override
            public void run() {
                runClock();
            }
        };
        Thread thread = new Thread(clock);
        thread.setDaemon(true);
        thread.start();
    }

    private void runClock() {
        while (true) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
                    timeLabel.setText(time);
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDate() throws IOException, ParseException {
        URL url = new URL("https://www.worldtimeserver.com/");
        HttpURLConnection httpCon =
                (HttpURLConnection) url.openConnection();
        long date = httpCon.getDate();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = format.format( new Date(date) );
        dateLabel.setText(dateString);
    }

    private void loadViews() {
        views.put("Dashboard", "/fxml/dashboard/dashboard.fxml");
        views.put("Items", "/fxml/itemDetails.fxml");
        views.put("Firms", "/fxml/firmDetails.fxml");
        views.put("Employee Accounts", "/fxml/employeeAccount.fxml");
        views.put("Admin", "/fxml/adminPanel.fxml");
        views.put("Shipping", "/fxml/shipment.fxml");
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

    public void btnEnClicked(ActionEvent actionEvent) throws IOException {
        bs = false;
        //  translate();
        ctrlRightPane(current.getKey(),current.getValue());
    }

    public void btnBsClicked(ActionEvent actionEvent) throws IOException {
        bs = true;
        System.out.println("BS");
        // translate();
        ctrlRightPane(current.getKey(),current.getValue());
    }

    private void translate() throws IOException {
        ResourceBundle bundle = null;
        FXMLLoader loader = null;
        System.out.println("HERE");
        if(!bs) {
            System.out.println("EN_JE");
            bundle = ResourceBundle.getBundle("homeTranslate");
            loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"), bundle);
            newLeftPane = loader.load();
        } else {
            bundle = ResourceBundle.getBundle("homeTranslate_bs");
            loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"), bundle);
            newLeftPane = loader.load();
            System.out.println("BSBUNDLE");
        }
        leftPane.getChildren().clear();
        leftPane.getChildren().add(newLeftPane);
    }

    private void setNewStage(String URL, String name) throws IOException {
        FXMLLoader loader = null;
        ResourceBundle bundle = null;
        if(!bs) {
            switch (name) {
                case "Admin":
                    bundle = ResourceBundle.getBundle("adminTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "Dashboard":
                    bundle = ResourceBundle.getBundle("dashboardTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "Items":
                    bundle = ResourceBundle.getBundle("itemDetailsTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "Employee Accounts":
                    bundle = ResourceBundle.getBundle("employeeDetailsTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "Firms":
                    bundle = ResourceBundle.getBundle("firmTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                default:
                    bundle = ResourceBundle.getBundle("shipment");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
            }
        } else {
            switch (name) {
                case "Dashboard":
                    bundle = ResourceBundle.getBundle("dashboardTranslation_bs");
                    break;
                case "Items":
                    bundle = ResourceBundle.getBundle("itemDetailsTranslation_bs");
                    break;
                case "Employee Accounts":
                    bundle = ResourceBundle.getBundle("employeeDetailsTranslation_bs");
                    break;
                case "Admin":
                    bundle = ResourceBundle.getBundle("adminTranslation_bs");
                    break;
                case "Firms":
                    bundle = ResourceBundle.getBundle("firmTranslation_bs");
                    break;
                case "Shipping":
                    bundle = ResourceBundle.getBundle("shipment_bs");
                    break;
            }
            loader = new FXMLLoader(getClass().getResource(URL), bundle);
        }
        current = new Pair<>(URL, name);
        switch (name) {
            case "Dashboard":
                DashboardModel dashBoardModel = new DashboardModel();
                loader.setController(new DashboardController(dashBoardModel));
                break;
            case "Items":
                ProductModel productModel = new ProductModel();
                loader.setController(new ItemDetailsController(productModel));
                break;
            case "Employee Accounts":
                EmployeeAccountModel employeeAccountModel = new EmployeeAccountModel();
                loader.setController(new EmployeeDetailsController(employeeAccountModel));
                break;
            case "Admin":
                AdminPanelModel adminPanelModel = new AdminPanelModel();
                loader.setController(new AdminPanelController(adminPanelModel));
                break;
            case "Firms":
                FirmModel firmModel = new FirmModel();
                loader.setController(new FirmDetailsController(firmModel));
                break;
            default:
                loader.setController(new ShipmentController());
                break;
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