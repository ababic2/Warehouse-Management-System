package ba.unsa.etf.rpr.projekat.Controllers;

import ba.unsa.etf.rpr.projekat.Controllers.DashboardControllers.DashboardController;
import ba.unsa.etf.rpr.projekat.Controllers.ItemDetailsControllers.ItemDetailsController;
import ba.unsa.etf.rpr.projekat.Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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

import static ba.unsa.etf.rpr.projekat.Controllers.LogInController.currentUser;

public class HomeController {
    public Pane rightPane;

    public GridPane leftPane;

    public Label currentAccess;
    public Label currLoggedIn;
    public Label timeLabel;
    public Label dateLabel;
    public MenuBar help;
    public MenuItem about;
    public MenuItem learn;

    public Button btnDashboard;
    public Button btnItems;
    public Button btnFirms;
    public Button btnShipping;
    public Button btnAccounts;
    public Button btnAdmin;
    public Button btnLogOut;
    public Label accessLabel;
    public Label loggedLabel, name1, name2, name3;

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
        ctrlRightPane("/fxml/dashboard/dashboard.fxml","btnDashboard");
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
        views.put("btnDashboard", "/fxml/dashboard/dashboard.fxml");
        views.put("btnItems", "/fxml/itemDetails.fxml");
        views.put("btnFirms", "/fxml/firmDetails.fxml");
        views.put("btnAccounts", "/fxml/employeeAccount.fxml");
        views.put("btnAdmin", "/fxml/adminPanel.fxml");
        views.put("btnShipping", "/fxml/shipment.fxml");
    }

    @FXML
    void btnNavigator(ActionEvent event) {
        borderSelector(event); //Marking selected navigator button

        Button btn = (Button)event.getSource();

        String btnText = btn.getId();
        if(btnText.equals("btnLogOut")) logOut();
        else {

            try {
                ctrlRightPane(views.get(btnText), btnText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //When log out is clicked then log in will appear

    public void aboutClicked(ActionEvent e) {
        Parent root = null;
        try {
            Stage myStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
            myStage.setScene(new Scene(root));
            myStage.setResizable(false);
            myStage.show();
        } catch (IOException k) {
            k.printStackTrace();
        }
    }

    public void learnMoreClicked(ActionEvent e) {
        Parent root = null;
        try {
            Stage myStage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/fxml/learnMore.fxml"));
            myStage.setScene(new Scene(root));
            myStage.setResizable(false);
            myStage.show();
        } catch (IOException k) {
            k.printStackTrace();
        }
    }

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
        translateEn();
        ctrlRightPane(current.getKey(),current.getValue());
    }

    public void btnBsClicked(ActionEvent actionEvent) throws IOException {
        bs = true;
        System.out.println("BS");
        translateBs();
        ctrlRightPane(current.getKey(),current.getValue());
    }

    private void translateBs() {
        btnItems.setText("_Proizvodi");
        btnAccounts.setText("_Zaposlenici");
        btnLogOut.setText("_Odjava");
        btnShipping.setText("Otpre_manje");
        btnFirms.setText("_Firme");
        loggedLabel.setText("Logovan kao: ");
        accessLabel.setText("Nivo pristupa: ");
        name1.setText("Sistem");
        name2.setText("Upravjanja");
        name3.setText("Skladištem");
    }

    private void translateEn() {
        btnItems.setText("_Items");
        btnAccounts.setText("_Employee Accounts");
        btnLogOut.setText("_Log Out");
        btnShipping.setText("_Shipping");
        btnFirms.setText("_Firms");
        loggedLabel.setText("Logged in as: ");
        accessLabel.setText("Access Level: ");
        name1.setText("Warehouse");
        name2.setText("Management");
        name3.setText("System");
    }

    private void setNewStage(String URL, String name) throws IOException {
        FXMLLoader loader = null;
        ResourceBundle bundle = null;
        if(!bs) {
            switch (name) {
                case "btnAdmin":
                    bundle = ResourceBundle.getBundle("adminTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "btnDashboard":
                    bundle = ResourceBundle.getBundle("dashboardTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "btnItems":
                    bundle = ResourceBundle.getBundle("itemDetailsTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "btnAccounts":
                    bundle = ResourceBundle.getBundle("employeeDetailsTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "btnFirms":
                    bundle = ResourceBundle.getBundle("firmTranslation");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
                case "btnShipping":
                    bundle = ResourceBundle.getBundle("shipment");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;

            }
        } else {
            switch (name) {
                case "btnDashboard":
                    bundle = ResourceBundle.getBundle("dashboardTranslation_bs");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);

                    break;
                case "btnItems":
                    bundle = ResourceBundle.getBundle("itemDetailsTranslation_bs");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);

                    break;
                case "btnAccounts":
                    bundle = ResourceBundle.getBundle("employeeDetailsTranslation_bs");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);

                    break;
                case "btnAdmin":
                    bundle = ResourceBundle.getBundle("adminTranslation_bs");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);

                    break;
                case "btnFirms":
                    bundle = ResourceBundle.getBundle("firmTranslation_bs");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);

                    break;
                case "btnShipping":
                    bundle = ResourceBundle.getBundle("shipment_bs");
                    loader = new FXMLLoader(getClass().getResource(URL), bundle);
                    break;
            }
        }
        current = new Pair<>(URL, name);
        switch (name) {
            case "btnDashboard":
                DashboardModel dashBoardModel = new DashboardModel();
                loader.setController(new DashboardController(dashBoardModel));
                break;
            case "btnItems":
                ProductModel productModel = new ProductModel();
                loader.setController(new ItemDetailsController(productModel));
                break;
            case "btnAccounts":
                EmployeeAccountModel employeeAccountModel = new EmployeeAccountModel();
                loader.setController(new EmployeeDetailsController(employeeAccountModel));
                break;
            case "btnAdmin":
                AdminPanelModel adminPanelModel = new AdminPanelModel();
                loader.setController(new AdminPanelController(adminPanelModel));
                break;
            case "btnFirms":
                FirmModel firmModel = new FirmModel();
                loader.setController(new FirmDetailsController(firmModel));
                break;
            case "btnShipping":
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