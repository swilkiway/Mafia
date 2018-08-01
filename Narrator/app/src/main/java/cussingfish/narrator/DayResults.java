package cussingfish.narrator;

import java.util.ArrayList;

public class DayResults {
    private Player lynched;
    private Player[] alive;
    public void setLynched(Player p) { lynched = p; }
    public void setAlive(ArrayList<Player> a) { alive = a.toArray(new Player[a.size()]); }
}
