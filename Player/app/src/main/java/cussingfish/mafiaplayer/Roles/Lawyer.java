package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.StartResults;

public class Lawyer extends Civilian {
    public static void set(String name, StartResults s) {
        lawyer = new Lawyer(name, s);
    }
    public static Lawyer get() {
        return lawyer;
    }
    private static Lawyer lawyer = null;
    private Lawyer(String name, StartResults s) {
        userName = name;
        startResults = s;
    }
    private boolean hasDefendedSelf = false;
    public boolean checkDefendedSelf() { return hasDefendedSelf; }
    public void defendSelf() { hasDefendedSelf = true; }
}
