package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.NightResults;
import cussingfish.mafiaplayer.StartResults;

public class Bodyguard extends Civilian {
    public static void set(String name, StartResults s) {
        bodyguard = new Bodyguard(name, s);
    }
    public static Bodyguard get() {
        return bodyguard;
    }
    private static Bodyguard bodyguard = null;
    private Bodyguard(String name, StartResults s) {
        userName = name;
        startResults = s;
    }
    private boolean hasSavedSelf = false;
    public boolean checkSavedSelf() { return hasSavedSelf; }
    public void saveSelf() { hasSavedSelf = true; }
}