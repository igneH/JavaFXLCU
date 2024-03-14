package viewController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import model.Calls;

import java.io.IOException;

public class lcuC {
    public Button btGetParticipants;
    public CheckBox cbOpenOPGG;

    private Calls model;

    public void btGetParticipantsOnAction(ActionEvent actionEvent) {
        getParticipatns();
    }

    public static void show(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(lcuC.class.getResource("lcuV.fxml"));
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

    private void getParticipatns() {

    }
}
