package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import connectToLCU.Methods;
import connectToLCU.Request;
import connectToLCU.Transcript;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class Calls {
    Transcript transcript = new Transcript();
    public Calls() {
    }

    public void getParticipants(){
        try {
            String link = new Request().getRequest(Methods.RIOT,"/chat/v5/participants/");
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
    public void login(String username, String password, boolean persistLogin){
        transcript.setPassword(password);
        transcript.setUsername(username);
        transcript.setPersistLogin(persistLogin);

        startRiotClient();

        Gson gson = new Gson();
        String loginBody = gson.toJson(transcript);
        new Request().putRequest(Methods.RIOT, "/rso-auth/v1/session/credentials", loginBody);
    }

    private void startRiotClient() {
        try {
            ProcessBuilder process = new ProcessBuilder("C:\\Riot Games\\Riot Client\\RiotClientServices.exe", "--launch-product=league_of_legends", "--launch-patchline=live", "--allow-multiple-clients");
            Process process1 = process.start();
            process1.waitFor();
           Thread.sleep(1000);
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
