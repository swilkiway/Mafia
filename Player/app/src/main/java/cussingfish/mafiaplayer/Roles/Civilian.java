package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.DayResults;
import cussingfish.mafiaplayer.NightResults;
import cussingfish.mafiaplayer.StartResults;

public class Civilian {
    public static void set(String name, StartResults s) {
        Civilian = new Civilian(name, s);
    }
    public static Civilian get() {
        return Civilian;
    }
    private static Civilian Civilian;
    public Civilian(String name, StartResults s) {
        userName = name;
        startResults = s;
    }
    public Civilian() {

    }

    public String userName = "";
    public Boolean isDead = false;
    public Boolean isInLove = false;
    public Boolean isEnfranchised = false;
    public NightResults nightResults;
    public DayResults dayResults;
    public StartResults startResults;
    public void kill() {
        isDead = true;
    }
}
