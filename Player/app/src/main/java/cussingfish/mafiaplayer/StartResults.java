package cussingfish.mafiaplayer;

import java.util.ArrayList;

public class StartResults {
    public StartResults() {
        isNull = true;
    }
    private String role;
    private String teammates[];
    private Player alive[];
    private boolean isNull;
    public String getRole() { return role; }
    public String[] getTeammates() { return teammates; }
    public Player[] getAlive() { return alive; }
    public boolean getNull() { return isNull; }
}
