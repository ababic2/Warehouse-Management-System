package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


// --module-path /usr/share/javafx/lib --add-modules javafx.controls,javafx.fxml

public class Main  extends Application {
    public ImageView userImageView;


    @Override
    public void start(Stage primaryStage) throws Exception{
        //pokrece kreiranje objekata
        //Stage je prozor koji ce biti kreiran
        //Scene je sadrzaj prozora
        // start metoda kreira scenu prema prilozenom fxml fajlu

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/log_in.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

//    public static void check() {
//        UserDAO userDAO = UserDAO.getInstance();
//        System.out.println("OOOO");
//        System.out.println(userDAO.passwordForUsername("viva"));
//
//    }

    public static void main(String[] args) {
        launch(args);
//        System.out.println("here i am");
//        check();
    }
}
