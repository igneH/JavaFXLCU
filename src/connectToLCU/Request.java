package connectToLCU;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class Request {

    static LockfileContents lockfileContents = new LockfileContents();

    public Request() {
    }

    private static String connectionString() throws IOException {
        return STR."\{lockfileContents.getRiotProtocol()}://127.0.0.1:\{lockfileContents.getRiotPort()}";
    }

    public void createGetRequest(String apiCall){
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,trustAllCerts,new SecureRandom());
            String credentials = "riot:" + lockfileContents.getRiotPw();
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("User-Agent", "LeagueOfLegendsClient")
                    .header("Accept", "application/json")
                    .header("Authorization", STR."Basic \{encodedCredentials}")
                    .uri(new URI(connectionString()+apiCall))
                    .build();

            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("participants: "+response.statusCode());
            System.out.println(response.body());

            Gson gson = new Gson();

            JsonObject participantsJson = gson.fromJson(response.body(), JsonObject.class);
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

        }catch (KeyManagementException| NoSuchAlgorithmException | IOException | InterruptedException | URISyntaxException e){
            e.printStackTrace();
        }
    }
    private static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };
}
