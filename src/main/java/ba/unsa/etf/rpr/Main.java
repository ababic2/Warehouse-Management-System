package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Controllers.LogInController;
import ba.unsa.etf.rpr.Model.LogInModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// --module-path /usr/share/javafx/lib --add-modules javafx.controls,javafx.fxml

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LogInModel logInModel = new LogInModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/log_in.fxml"));
        loader.setController(new LogInController(logInModel));
        Parent root = loader.load();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shipment.fxml"));
//        ShipmentController ctrl = new ShipmentController();
//        loader.setController(ctrl);
//        Parent root = loader.load();
        primaryStage.setTitle("Welcome");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) throws Exception {
         launch(args);
    }
}
