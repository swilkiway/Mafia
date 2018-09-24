package cussingfish.narrator.Model;

import java.util.ArrayList;

public class DayResults {
    private Player lynched;
    private String defended;
    private Player alive[];
    private Ballot ballot;
    private Player bombed;
    private Player lover;
    private String diplomat;
    private boolean isTied;
    private int status;
    private boolean isNull = false;
    public void setLynched(Player p) { lynched = p; }
    public void setDefended(String s) { defended = s; }
    public void setAlive(ArrayList<Player> a) { alive = a.toArray(new Player[a.size()]); }
    public void setBallot(Ballot b) { ballot = b; }
    public void setBombed(Player p) { bombed = p; }
    public void setLover(Player l) { lover = l; }
    public void setDiplomat(String d) { diplomat = d; }
    public void setTied(boolean t) { isTied = t; }
    public boolean getTied() { return isTied; }
    public void setStatus(int s) { status = s; }
    public void setNull() { isNull = true; }
}
