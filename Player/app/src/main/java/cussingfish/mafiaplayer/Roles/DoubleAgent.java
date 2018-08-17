package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.Model.StartResults;

public class DoubleAgent extends Civilian {
    private static boolean alreadyKilled = false;
    private static boolean alreadySaved = false;
    public static boolean hasAlreadyKilled() { return alreadyKilled; }
    public static boolean hasAlreadySaved() { return alreadySaved; }
    public static void killPlayer() {
        alreadyKilled = true;
    }
    public static void savePlayer() {
        alreadySaved = true;
    }
}
