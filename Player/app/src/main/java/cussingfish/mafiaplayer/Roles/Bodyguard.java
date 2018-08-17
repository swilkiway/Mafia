package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.Model.StartResults;

public class Bodyguard extends Civilian {
    private static boolean hasSavedSelf = false;
    public static boolean checkSavedSelf() { return hasSavedSelf; }
    public static void saveSelf() { hasSavedSelf = true; }
}