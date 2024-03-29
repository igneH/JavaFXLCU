package connectToLCU;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class LockfileContents {
    String leagueLockfilePath = "C:\\Riot Games\\League of Legends\\lockfile";
    String riotlockfilePath = System.getenv("riotlockfileLocation");

    public LockfileContents(){

    }

    private String[] readRiotlockfile(){
        return getStrings(riotlockfilePath);
    }

    private String[] readLeaguelockfile(){
        return getStrings(leagueLockfilePath);
    }

    private String[] getStrings(String leagueLockfilePath) {
        boolean fileExists = false;
        while (!fileExists){
            System.out.println("Looking for lockfile");
            if (Files.exists(Path.of(leagueLockfilePath))){
                fileExists = true;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try(BufferedReader br = new BufferedReader(new FileReader(leagueLockfilePath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            sb.append(line);
            br.close();
            return line.split(":");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public int getLeaguePort() throws IOException {
        return Integer.parseInt(readLeaguelockfile()[2]);
    }

    public String getLeaguePw() throws IOException {
        return readLeaguelockfile()[3];
    }

    public String getLeagueProtocol() throws IOException {
        return readLeaguelockfile()[4];
    }

    public int getRiotPort() throws IOException {
        return Integer.parseInt(readRiotlockfile()[2]);
    }

    public String getRiotPw() throws IOException {
        return readRiotlockfile()[3];
    }

    public String getRiotProtocol() throws IOException {
        return readRiotlockfile()[4];
    }
}
