package connectToLCU;

import java.io.*;
import java.util.Arrays;

public class LockfileContents {
    String leagueLockfilePath = "C:\\Riot Games\\League of Legends\\lockfile";

    public LockfileContents(){

    }

    private String[] readlockfile() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(leagueLockfilePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            sb.append(line);
            return line.split(":");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public int getPort() throws IOException {
        return Integer.parseInt(readlockfile()[2]);
    }

    public String getPw() throws IOException {
        return readlockfile()[3];
    }

    public String getProtocol() throws IOException {
        return readlockfile()[4];
    }
}
