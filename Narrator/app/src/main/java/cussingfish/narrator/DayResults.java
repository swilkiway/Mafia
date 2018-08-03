package cussingfish.narrator;

import java.util.ArrayList;

public class DayResults {
    private Player lynched;
    private Player[] alive;
    private Vote[] ballot;
    private Player bombed;
    public void setLynched(Player p) { lynched = p; }
    public void setAlive(ArrayList<Player> a) { alive = a.toArray(new Player[a.size()]); }
    public void setBallot(ArrayList<Vote> b) { ballot = b.toArray(new Vote[b.size()]); }
    public void setBombed(Player p) { bombed = p; }
}
