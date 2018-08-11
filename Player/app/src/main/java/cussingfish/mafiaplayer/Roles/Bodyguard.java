package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.NightResults;

public class Bodyguard extends Civilian {
    public static void set(String name) {
        bodyguard = new Bodyguard(name);
    }
    public static Bodyguard get() {
        return bodyguard;
    }
    private static Bodyguard bodyguard = null;
    private Bodyguard(String name) {
        userName = name;
    }
    private boolean hasSavedSelf = false;
    public boolean checkSavedSelf() { return hasSavedSelf; }
    public void saveSelf() { hasSavedSelf = true; }
}