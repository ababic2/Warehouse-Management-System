package ba.unsa.etf.rpr.Controllers;

import ba.unsa.etf.rpr.HelpModel.CurrentUser;
import ba.unsa.etf.rpr.Model.LogInModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    public PasswordField loggerPassword;
    public TextField loggerUsername;
    public TextField passShowField;

    public CheckBox passwordMask;
    public Button buttonLogIn;
    public Button buttonSignUp;
    public Label requiredLabel;
    public Label wrongUsernameField;
    public Label wrongPassField;
    public GridPane usernameGrid;
    public GridPane passwordGrid;
    private Label usernameErrorLabel;
    private Label passwordErrorLabel;
    private LogInModel logInModel;
    private boolean isMaskChoosen = false;
    private String accessLevel;
    public static CurrentUser currentUser = new CurrentUser();

    public LogInController(LogInModel logInModel) {
        this.logInModel = logInModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggerPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                usernameGrid.getChildren().remove(usernameErrorLabel);
                passwordGrid.getChildren().remove(passwordErrorLabel);
                requiredLabel.setVisible(false);
                changeLabelBackground(n);
            }
        });
    }

    private void checkPassAndUsername(String username, String password) {
        if(!logInModel.lookForUsernameInBase(username)) {
            sendMessageForWrongUsername();
        } else if(!logInModel.lookForPassInBaseForUsername(username, password)) {
            sendMessageForWrongPassword();
        } else {
                logInModel.setUpAccountForUser(username);
                accessLevel = logInModel.setUpAccountForUser(username).getAccessLevel();
                //ovo će zamijeniti currentUser-a
                currentUser.setUsername(loggerUsername.getText());
                currentUser.setAccessLevel(accessLevel);
                openNewStage("/fxml/home.fxml");
            }
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    private void openNewStage(String url) {
        Stage current = (Stage) loggerUsername.getScene().getWindow();
        current.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource(url));
            Scene scene = new Scene(root);
            Stage logInPrompt = new Stage();
            logInPrompt.setScene(scene);
            logInPrompt.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageForWrongPassword() {
        try {
            passwordErrorLabel= FXMLLoader.load(getClass().getResource("/fxml/dynamic/passwordErrorLabel.fxml"));
            passwordGrid.add(passwordErrorLabel,0,2);
        } catch (IOException e) {
        }
    }

    private void sendMessageForWrongUsername() {
        try {
            usernameErrorLabel = FXMLLoader.load(getClass().getResource("/fxml/dynamic/usernameErrorLabel.fxml"));
            usernameGrid.add(usernameErrorLabel,0,2);
        } catch (IOException e) {
        }
    }

    private void changeLabelBackground(String n) {
        if (logInModel.is_Valid_Password(n)) {
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

    //provjeri da li ima taj username i pass u bazi, i vidi koji je accesslevel ako da otvori dash inace alert
    public void onLogInButtonClicked(ActionEvent actionEvent) {
        if(loggerUsername.getText().length() == 0 || loggerPassword.getText().length() == 0) {
            requiredLabel.setVisible(true);
            usernameGrid.getChildren().remove(usernameErrorLabel);
            passwordGrid.getChildren().remove(passwordErrorLabel);
        } else {
            requiredLabel.setVisible(false);
            usernameGrid.getChildren().remove(usernameErrorLabel);
            passwordGrid.getChildren().remove(passwordErrorLabel);

            if(isMaskChoosen) {
                checkPassAndUsername(loggerUsername.getText(),passShowField.getText());
            } else {
                checkPassAndUsername(loggerUsername.getText(), loggerPassword.getText());
            }
        }

    }

    public void onSignUpButtonClicked(ActionEvent actionEvent) {
        if(accessLevel.equals("Firm")) {
            openNewStage("/fxml/signUpFormForFirm");
        } else if(accessLevel.equals("Employee") || accessLevel.equals("Admin")) {
            openNewStage("");
        }
    }
}
