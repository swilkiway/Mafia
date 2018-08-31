package cussingfish.narrator.Model;

import java.util.ArrayList;

public class NightResults {
    private Player mafiaKilled;
    private Player daKilled;
    private Player bomberKilled;
    private String bodyguardSaved;
    private String daSaved;
    private String silenced;
    private Player poisoned;
    private Player lover;
    private Player[] alive;
    private int status;
    private boolean isNull = false;
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
    public void setSilenced(String p) {
        silenced = p;
    }
    public void setPoisoned(Player p) { poisoned = p; }
    public void setLover(Player l) { lover = l; }
    public void setAlive(ArrayList<Player> a) { alive = a.toArray(new Player[a.size()]); }
    public void setStatus(int s) { status = s; }
    public void setNull() { isNull = true; }
}
