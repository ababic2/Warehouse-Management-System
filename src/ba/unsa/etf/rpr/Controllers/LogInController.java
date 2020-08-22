package ba.unsa.etf.rpr.Controllers;


import ba.unsa.etf.rpr.DAL.DAO.UserDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    public PasswordField loggerPassword;
    public TextField loggerUsername;


    public CheckBox passwordMask;
    public TextField passShowField;
    public Button buttonLogIn;
    public Label requiredLabel;
    private boolean isMaskChoosen = false;

    private String passFromBase = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggerPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                requiredLabel.setVisible(false);
                changeLabelBackground(n);
            }
        });
    }

    private void lookForUsernameAndPassInBase(String username, String password) {
        UserDAO userDAO = UserDAO.getInstance();
        passFromBase = userDAO.passwordForUsername(username);
        if(passFromBase == null) {
            sendMessageForWrongUsername();
        } else if(!passFromBase.equals(password)) {
            sendMessageForWrongPassword();
        } else {
            System.out.println("OKE username i pass");
            //openDashboard();
        }
    }

    private void sendMessageForWrongPassword() {
        System.out.println("Wrongpass");
    }

    private void sendMessageForWrongUsername() {
        System.out.println("wronguser");
    }

    private void changeLabelBackground(String n) {
        if (is_Valid_Password(n) ) {
            loggerPassword.getStyleClass().removeAll("poljeNijeIspravno");
            loggerPassword.getStyleClass().add("poljeIspravno");

        } else if(loggerPassword.getText().length() == 0)  {
            loggerPassword.getStyleClass().removeAll("poljeNijeIspravno");
            loggerPassword.getStyleClass().removeAll("poljeIspravno");
        }
        else {
            loggerPassword.getStyleClass().removeAll("poljeIspravno");
            loggerPassword.getStyleClass().add("poljeNijeIspravno");
        }
    }


    @FXML
    private void checkPasswordMask()
    {
        if (passwordMask.isSelected())
        {
            passShowField.setText(loggerPassword.getText());
            passShowField.setVisible(true);
            loggerPassword.setVisible(false);
            isMaskChoosen = true;

            } else {
                loggerPassword.setText(passShowField.getText());
                loggerPassword.setVisible(true);
                passShowField.setVisible(false);
                isMaskChoosen = false;
            }
        }

    private static boolean is_Valid_Password(String password) {

        if (password.length() > 0 && password.length() < 8 ) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }
        return (charCount >= 2 && numCount >= 2);
    }

    private static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    private static boolean is_Numeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    //provjeri da li ima taj username i pass u bazi, i vidi koji je accesslevel ako da otvori dash inace alert
    public void onLogInButtonClicked(ActionEvent actionEvent) {
        if(loggerUsername.getText().length() == 0 || loggerPassword.getText().length() == 0) {
            requiredLabel.setVisible(true);
        } else {
            requiredLabel.setVisible(false);

            if(isMaskChoosen) {
                lookForUsernameAndPassInBase(loggerUsername.getText(),passShowField.getText());
            } else {
                lookForUsernameAndPassInBase(loggerUsername.getText(), loggerPassword.getText());
            }
        }

    }
}
