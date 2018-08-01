package cussingfish.narrator;

import java.util.ArrayList;

public class NightResults {
    private Player mafiaKilled;
    private Player daKilled;
    private String bodyguardSaved;
    private String daSaved;
    private String defended;
    private String enfranchised;
    private String silenced;
    private Player[] alive;
    public void setMafiaKilled(Player p) {
        mafiaKilled = p;
    }
    public void setDaKilled(Player p) {
        daKilled = p;
    }
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
}
