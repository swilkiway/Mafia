package cussingfish.narrator.Model;

import java.util.ArrayList;

public class StartResults {
    private String role;
    private String teammates[];
    private Player alive[];
    private boolean isNull = false;
    public void setRole(String r) { role = r; }
    public void setTeammates(ArrayList<String> t) { teammates = t.toArray(new String[t.size()]); }
    public void setAlive(ArrayList<Player> p) { alive = p.toArray(new Player[p.size()]); }
    public void setNull() { isNull = true; }
}
