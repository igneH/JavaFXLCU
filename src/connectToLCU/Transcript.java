package connectToLCU;

public class Transcript {
    private String username;
    private String password;
    private boolean persistLogin;

    public Transcript() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPersistLogin() {
        return persistLogin;
    }

    public void setPersistLogin(boolean persistLogin) {
        this.persistLogin = persistLogin;
    }
}
