package cussingfish.mafiaplayer.Roles;

public class Mafioso extends Player {
    public static void set(String username, String teammates[]) {
        mafioso = new Mafioso(username, teammates);
    }
    public static Mafioso get() {
        return mafioso;
    }
    private static Mafioso mafioso = null;
    private Mafioso(String u, String t[]) {
        userName = u;
        teammates = t;
    }
    private String teammates[];
    public String[] getTeammates() {
        return teammates;
    }
    public void snuffEm(String victim) {

    }
}
