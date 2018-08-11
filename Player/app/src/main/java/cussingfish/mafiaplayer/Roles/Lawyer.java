package cussingfish.mafiaplayer.Roles;

public class Lawyer extends Civilian {
    public static void set(String name) {
        lawyer = new Lawyer(name);
    }
    public static Lawyer get() {
        return lawyer;
    }
    private static Lawyer lawyer = null;
    private Lawyer(String name) { userName = name; }
    private boolean hasDefendedSelf = false;
    public boolean checkDefendedSelf() { return hasDefendedSelf; }
    public void defendSelf() { hasDefendedSelf = true; }
}
