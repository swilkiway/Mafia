package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.StartResults;

public class Mafioso extends Civilian {
    public static void set(String username, StartResults s) {
        mafioso = new Mafioso(username, s);
    }
    public static Mafioso get() { return mafioso; }
    private static Mafioso mafioso = null;
    private Mafioso(String u, StartResults s) {
        userName = u;
        teammates = s.getTeammates();
        startResults = s;
    }
    private String teammates[];
    public String[] getTeammates() {
        return teammates;
    }
    public void snuffEm(String victim) {

    }
}
