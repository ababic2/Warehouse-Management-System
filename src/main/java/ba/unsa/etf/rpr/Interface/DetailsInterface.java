/*
This interface is contract between ItemDetailsController,
EmployeeDetailsController and FirmDetailsController
because they all need to implement similar methods
*/

package ba.unsa.etf.rpr.Interface;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.regex.Pattern;

public interface DetailsInterface {
    default void openNewStage(String url) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(url));
            Scene scene = new Scene(root);
            Stage logInPrompt = new Stage();
            logInPrompt.setScene(scene);
            logInPrompt.show();

        } catch (IOException e) {
            e.getCause();
        }
    }

    // When searching by ID, make sure that entered ID is numeric
    default boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    void btnNextClicked(ActionEvent actionEvent);

    void btnPreviousClicked(ActionEvent actionEvent);

    void btnEditClicked(ActionEvent actionEvent);

    void btnSearchClicked(ActionEvent actionEvent);

    void btnDeleteClicked(ActionEvent actionEvent);

    default void setAlertWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    default int confirmDeleteAction() {
        return JOptionPane.showConfirmDialog(null,
                "Do you want to proceed?", "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION);
    }

    static void setButtonsNextPrev(int size, Button btnNext, Button btnPrevious, int page) {
        if(size == 1) {
            btnNext.setDisable(true);
            btnPrevious.setDisable(true);
        } else if (page == 0) {
            btnPrevious.setDisable(true);
            btnNext.setDisable(false);
        } else if(page == size - 1) {
            btnNext.setDisable(true);
            btnPrevious.setDisable(false);
        } else {
            btnNext.setDisable(false);
            btnPrevious.setDisable(false);
        }
    }
}
