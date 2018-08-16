package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.NightResults;
import cussingfish.mafiaplayer.StartResults;

public class Bomber extends Civilian {
    public static void set(String name, StartResults s) {
        bomber = new Bomber(name, s);
    }
    public static Bomber get() {
        return bomber;
    }
    private static Bomber bomber = null;
    private Bomber(String name, StartResults s) {
        userName = name;
        startResults = s;
    }

    private String target = "";
    private NightResults nightResults;

    public void explode() {

    }
    public void chooseTarget(String t) {
        target = t;
    }
    public void setNightResults(NightResults n) {
        nightResults = n;
    }

    public NightResults getNightResults() {
        return nightResults;
    }
}