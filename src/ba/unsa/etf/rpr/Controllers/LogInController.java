package ba.unsa.etf.rpr.Controllers;


import ba.unsa.etf.rpr.DAL.DTO.Employee;
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

    private Employee employee = null;
    private String password;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggerPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                requiredLabel.setVisible(false);

                if (is_Valid_Password(n) ) {
                    loggerPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    loggerPassword.getStyleClass().add("poljeIspravno");
//                    password = loggerPassword.getText();

                } else if(loggerPassword.getText().length() == 0)  {
                    loggerPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    loggerPassword.getStyleClass().removeAll("poljeIspravno");
                }
                else {
                    loggerPassword.getStyleClass().removeAll("poljeIspravno");
                    loggerPassword.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });
    }


    @FXML
    public void checkPasswordMask()
    {
        if (passwordMask.isSelected())
        {
            passShowField.setText(loggerPassword.getText());
            passShowField.setVisible(true);
            loggerPassword.setVisible(false);

            } else {
                loggerPassword.setText(passShowField.getText());
                loggerPassword.setVisible(true);
                passShowField.setVisible(false);
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

    public void onLogInButtonClicked(ActionEvent actionEvent) {
        if(loggerUsername.getText().length() == 0 || loggerPassword.getText().length() == 0) {
            requiredLabel.setVisible(true);
        }
//        else if(is_Valid_Password(password)) {
//             users.setCurrentEmployee(employee);
//        }
        else {
            requiredLabel.setVisible(false);
        }
    }
}
