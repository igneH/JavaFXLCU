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
import java.util.Objects;

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

        //startRiotClient();

        Gson gson = new Gson();
        String loginBody = gson.toJson(transcript);
        boolean riotClientComReady = false;
        while (!riotClientComReady){
            String test = new Request().putRequest(Methods.RIOT, "/rso-auth/v1/session/credentials", loginBody);
            if (!Objects.equals(test, "{\"errorCode\":\"RESOURCE_NOT_FOUND\",\"httpStatus\":404,\"message\":\"Invalid URI format\"}")){
                riotClientComReady = true;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }

    public void autoacceptQueue(boolean selected){
        if (selected){

        }
    }

    private void startRiotClient() {
        ProcessBuilder builder = new ProcessBuilder(
                "C:\\Riot Games\\Riot Client\\RiotClientServices.exe"); //,"--launch-product=league_of_legends", "--launch-patchline=live", "--allow-multiple-clients"
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
