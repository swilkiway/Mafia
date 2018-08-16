package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.StartResults;

public class Detective extends Civilian {
    public static void set(String name, StartResults s) {
        detective = new Detective(name, s);
    }
    public static Detective get() {
        return detective;
    }
    private static Detective detective = null;
    private Detective(String name, StartResults s) {
        userName = name;
        startResults = s;
    }

    public void investigate(String suspect) {

    }
}
