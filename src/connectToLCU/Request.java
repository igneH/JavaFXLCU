package connectToLCU;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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

    static LockfileContents lockfileContents;

    public Request() {
    }

    private static String connectionString(Methods service) throws IOException {
        lockfileContents = new LockfileContents();
        if(service == Methods.RIOT){
            return STR."\{lockfileContents.getRiotProtocol()}://127.0.0.1:\{lockfileContents.getRiotPort()}";
        }
        return STR."\{lockfileContents.getRiotProtocol()}://127.0.0.1:\{lockfileContents.getLeaguePort()}";
    }

    public String getRequest(Methods service, String apiCall){
        lockfileContents = new LockfileContents();
        try {
            SSLContext sslContext = setSSLContext();
            String credentials;
            if (service == Methods.RIOT) {
                credentials = STR."riot:\{lockfileContents.getRiotPw()}";
            }else {
                credentials = STR."riot:\{lockfileContents.getLeaguePw()}";
            }
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("User-Agent", "LeagueOfLegendsClient")
                    .header("Accept", "application/json")
                    .header("Authorization", STR."Basic \{encodedCredentials}")
                    .uri(new URI(connectionString(service)+apiCall))
                    .build();

            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(STR."GetRequest: \{response.statusCode()}\n\{response.body()}");

            return response.body();
        }
        catch (IOException | InterruptedException | URISyntaxException e){
            e.printStackTrace();
        }
        return "failed";
    }

    public String putRequest(Methods service, String apiCall, String body){
        lockfileContents = new LockfileContents();
        try{
            SSLContext sslContext = setSSLContext();
            String credentials;
            if (service == Methods.RIOT) {
                credentials = STR."riot:\{lockfileContents.getRiotPw()}";
            }else {
                credentials = STR."riot:\{lockfileContents.getLeaguePw()}";
            }
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .header("User-Agent", "LeagueOfLegendsClient")
                    .header("Accept", "application/json")
                    .header("Authorization", STR."Basic \{encodedCredentials}")
                    .uri(new URI(connectionString(service)+apiCall))
                    .build();

            System.out.println(request.uri());
            System.out.println(request.bodyPublisher().toString());

            HttpClient httpClient = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(STR."PutRequest: \{response.statusCode()}\n\{response.body()}");

            return response.body();
        } catch (InterruptedException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "ned gfunkt";
    }

    public String getRequestSub(Methods service, String apiCall, Events event) {
        SSLContext sslContext = setSSLContext();
        String credentials;
        try {
            if (service == Methods.RIOT) {
                credentials = STR."riot:\{lockfileContents.getRiotPw()}";
            } else {
                credentials = STR."riot:\{lockfileContents.getLeaguePw()}";
            }
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("User-Agent", "LeagueOfLegendsClient")
                    .header("Accept", "application/json")
                    .header("Authorization", STR."Basic \{encodedCredentials}")
                    .uri(new URI(connectionString(service)+apiCall))
                    .build();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final TrustManager[] trustAllCerts = new TrustManager[]{
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

    private SSLContext setSSLContext(){
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,trustAllCerts,new SecureRandom());
            return sslContext;
        }catch(NoSuchAlgorithmException | KeyManagementException e){
            e.printStackTrace();
        }
        return null;
    }
}
