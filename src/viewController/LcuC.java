package viewController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

    private void getParticipatns(){
        try {
            String link = new Request().createGetRequest(Methods.RIOT,"/chat/v5/participants/");
            Gson gson = new Gson();

            JsonObject participantsJson = gson.fromJson(link, JsonObject.class);
            JsonArray participants = participantsJson.getAsJsonArray("participants");

            StringBuilder multisearchString = new StringBuilder("https://www.op.gg/multisearch/EUW?summoners=");

            for (JsonElement element : participants){
                JsonObject participant = element.getAsJsonObject();
                if (participant.get("activePlatform") != null && !participant.get("activePlatform").isJsonNull()) {
                    String gameName = participant.get("game_name").getAsString();
                    String gameTag = participant.get("game_tag").getAsString();
                    System.out.println(STR."\{gameName}#\{gameTag}");
                    multisearchString.append(STR."\{gameName}%23\{gameTag}%2C");
                }
            }

            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(multisearchString.toString()));
        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

    }
}
