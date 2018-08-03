package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.NightResults;

public class Bomber extends Civilian {
    public static void set(String name) {
        bomber = new Bomber(name);
    }
    public static Bomber get() {
        return bomber;
    }
    private static Bomber bomber = null;
    private Bomber(String name) {
        userName = name;
        nightResults = new NightResults();
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