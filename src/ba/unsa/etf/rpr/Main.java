package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


// --module-path /usr/share/javafx/lib --add-modules javafx.controls,javafx.fxml

public class Main extends Application {
    public ImageView userImageView;


    @Override
    public void start(Stage primaryStage) throws Exception{
        //pokrece kreiranje objekata
        //Stage je prozor koji ce biti kreiran
        //Scene je sadrzaj prozora

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ll.fxml"));
        // start metoda kreira scenu prema prilozenom fxml fajlu
        //primaryStage.setTitle("Welcome");
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
