package cussingfish.narrator.Model;

import java.util.ArrayList;

public class NightResults {
    private Player mafiaKilled;
    private Player daKilled;
    private Player bomberKilled;
    private String bodyguardSaved;
    private String daSaved;
    private String defended;
    private String enfranchised;
    private String silenced;
    private Player[] alive;
    private int status;
    public void setMafiaKilled(Player p) {
        mafiaKilled = p;
    }
    public void setDaKilled(Player p) {
        daKilled = p;
    }
    public void setBombed(Player p) { bomberKilled = p; }
    public void setBodyguardSaved(String p) {
        bodyguardSaved = p;
    }
    public void setDaSaved(String p) {
        daSaved = p;
    }
    public void setDefended(String p) {
        defended = p;
    }
    public void setEnfranchised(String p) {
        enfranchised = p;
    }
    public void setSilenced(String p) {
        silenced = p;
    }
    public void setAlive(ArrayList<Player> a) { alive = a.toArray(new Player[a.size()]); }
    public void setStatus(int s) { status = s; }
}
