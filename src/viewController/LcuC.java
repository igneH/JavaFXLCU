package viewController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Calls;

import java.io.IOException;

public class LcuC {
    private final Calls calls = new Calls();

    public Button btGetParticipants;
    public CheckBox cbOpenOPGG;
    public TextField tfUsername;
    public CheckBox cbMultipleClients;
    public PasswordField pfPassword;
    public Button btLogin;
    public CheckBox cbAutoAccept;

    public static void show(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(LcuC.class.getResource("LcuV.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setTitle("LCU Shit");
            stage.setScene(scene);
            stage.show();

        }
        catch (IOException e){
            e.printStackTrace();
            Platform.exit();
        }
    }

    public void btGetParticipantsOnAction(ActionEvent actionEvent) {
        showLobby();
    }

    public void btLoginOnAction(ActionEvent actionEvent) {
        login();
    }


    private void showLobby(){
        calls.getParticipants();
    }

    private void login(){
        calls.login(tfUsername.getText(), pfPassword.getText(), cbMultipleClients.isSelected());
    }

    @FXML
    public void initialize(){
        tfUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButtonState(btLogin, tfUsername.getText(), pfPassword.getText());
        });
        pfPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            updateButtonState(btLogin, tfUsername.getText(), pfPassword.getText());
        });
        cbAutoAccept.selectedProperty().addListener((observable, oldValue, newValue) -> {
            calls.autoacceptQueue(cbAutoAccept.isSelected());
        });
        btLogin.setDisable(true);
        cbMultipleClients.setSelected(false);
        cbOpenOPGG.setSelected(true);
    }
    private void updateButtonState(Button button, String username, String password){
        button.setDisable(username.isEmpty() || password.isEmpty());
    }
}
