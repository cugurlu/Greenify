package greenify.client.controller;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import greenify.client.Application;
import greenify.client.rest.UserService;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Class that controls the actions for the login screen.
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;

    public void initialize() {
        loginButton.setSkin(new LoginButtonSkin(loginButton));
        signUpButton.setSkin(new LoginButtonSkin(signUpButton));
    }

    /**
     * Handles when the user clicks on the login button.
     * it checks if the username and password fields are filled
     * and gives alerts if they aren't filled in.
     * @param event the click of the login button
     * @throws IOException an exception for logging in the user
     */
    @FXML
    protected void handleLoginButtonAction(ActionEvent event)
            throws IOException, NoSuchAlgorithmException {
        Window owner = loginButton.getScene().getWindow(); //get the current window
        if (usernameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
                    "Please enter your username");
            //checks if the username field is filled,
            // and gives an alert if it is not
            return;
        } else {
            System.out.println("Username is " + usernameField.getText());
        }
        if (passwordField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
                    "Please enter a password");
            //checks if the password field is filled,
            // and gives an alert if it is not.
            return;
        } else {
            System.out.println("Password is " + passwordField.getText());
        }
        //log the user in with the userService method
        try {
            userService.loginUser(usernameField.getText(), passwordField.getText());
        } catch (HttpClientErrorException ex) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Log-in Error!",
                    "Your username or password is incorrect!");
            return;
        }
        Stage current = (Stage) owner;
        //after logging in, close the login window
        current.close();
        //open the other dashboard window
        openDashboard();

    }

    /**
     * Opens the dashboard stage.
     * @throws IOException exception if fxml file can't be found
     * @author sem
     */
    private void openDashboard() throws IOException {
        //load the fxml file
        Parent dash = Application.load(this.getClass().getClassLoader()
                .getResource("fxml/dashboard.fxml"));
        Scene scene = new Scene(dash);
        //add the stylesheet for the CSS
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("stylesheets/dashboardStyle.css")).toExternalForm());
        Stage appStage = new Stage();
        appStage.setScene(scene);
        //set the title
        appStage.setTitle("Greenify - " + usernameField.getText());
        appStage.show();
    }

    /**
     * Class for showing the alerts.
     */
    static class AlertHelper {
        /**
         * alerts for the login screen.
         * @param alertType the type of alert
         * @param owner the owner (window) of the alert
         * @param title the title given to the displayed alert
         * @param message the message displayed in the alert
         */
        static void showAlert(Alert.AlertType alertType,
                              Window owner,
                              String title,
                              String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.initOwner(owner);
            alert.show();
        }
    }

    /**
     * The method handles the clicking of the register button.
     * it then opens the register window where the user can register
     * (handled by RegisterWindowController)
     * @param event User clicks on the button
     * @throws Exception when the fxml file couldn't be found
     */
    public void handleRegisterButtonAction(ActionEvent event) throws Exception {
        Parent registerWindow = Application.load(this.getClass().getClassLoader()
                .getResource("fxml/RegisterWindow.fxml"));
        Scene registerScene = new Scene(registerWindow);
        registerScene.getStylesheets().add(this.getClass().getClassLoader()
                .getResource("stylesheets/registerWindowStyle.css").toExternalForm());
        Stage registerStage = new Stage();
        registerStage.setScene(registerScene);
        registerStage.setTitle("Enter register credentials");
        registerStage.show();
    }

    @SuppressWarnings("Duplicates")
    public class LoginButtonSkin extends ButtonSkin {
        /**
         * method for the skin of login button.
         * @param button clicking
         */
        public LoginButtonSkin(Button button) {
            super(button);
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(140));
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);
            scaleUp.setNode(button);
            button.setOnMouseEntered(e -> scaleUp.playFromStart());

            ScaleTransition scaleMiddleDown = new ScaleTransition(Duration.millis(50));
            scaleMiddleDown.setFromX(1.1);
            scaleMiddleDown.setFromY(1.1);
            scaleMiddleDown.setToX(1.05);
            scaleMiddleDown.setToY(1.05);
            scaleMiddleDown.setNode(button);
            button.setOnMousePressed(e -> scaleMiddleDown.playFromStart());

            ScaleTransition scaleMiddleUp = new ScaleTransition(Duration.millis(50));
            scaleMiddleUp.setFromX(1.05);
            scaleMiddleUp.setFromY(1.05);
            scaleMiddleUp.setToX(1.1);
            scaleMiddleUp.setToY(1.1);
            scaleMiddleUp.setNode(button);
            button.setOnMouseReleased(e -> scaleMiddleUp.playFromStart());

            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200));
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);
            scaleDown.setNode(button);
            button.setOnMouseExited(e -> scaleDown.playFromStart());
        }
    }

}

