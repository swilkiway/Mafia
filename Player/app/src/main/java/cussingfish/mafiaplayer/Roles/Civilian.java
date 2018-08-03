package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.DayResults;
import cussingfish.mafiaplayer.NightResults;

public class Civilian {
    public static void set(String name) {
        Civilian = new Civilian(name);
    }
    public static Civilian get() {
        return Civilian;
    }
    private static Civilian Civilian;
    public Civilian(String name) {
        userName = name;
        nightResults = new NightResults();
        dayResults = new DayResults();
    }
    public Civilian() {

    }

    public String userName = "";
    public Boolean isDead = false;
    public Boolean isInLove = false;
    public Boolean isEnfranchised = false;
    public NightResults nightResults;
    public DayResults dayResults;
    public void castDayVote() {

    }
    public void kill() {
        isDead = true;
    }
}
