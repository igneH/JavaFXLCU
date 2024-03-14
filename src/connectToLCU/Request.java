package connectToLCU;

import jdk.internal.util.xml.impl.Input;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class Request {
    private final String apiCall;
    private final String method;

    static LockfileContents lockfileContents = new LockfileContents();

    public Request(String method, String apiCall) {
        this.apiCall = apiCall;
        this.method = method;
    }

    private static String connectionString() throws IOException {
        return lockfileContents.getProtocol() + "://127.0.0.1:" + lockfileContents.getPort();
    }

    public void createRequest() throws IOException {
        String encoding = Base64.getEncoder().encodeToString(("riot:"+lockfileContents.getPw()).getBytes());
        URL url = new URL(connectionString()+ apiCall);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "LeagueOfLegendsClient");
        conn.setRequestProperty("Authorization", "Basic " + encoding);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        System.out.println(conn);
        InputStream content = conn.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        String line;
        while ((line = in.readLine()) != null){
            System.out.println(line);
        }
    }
}
