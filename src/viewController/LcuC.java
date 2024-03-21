package viewController;

import connectToLCU.Methods;
import connectToLCU.Request;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import model.Calls;

import java.io.IOException;

public class LcuC {
    public Button btGetParticipants;
    public CheckBox cbOpenOPGG;

    private Request request;

    public static void show(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(LcuC.class.getResource("lcuV.fxml"));
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
        getParticipatns();
    }

    private void getParticipatns() {
        new Request().createGetRequest("/chat/v5/participants/");
    }
}
