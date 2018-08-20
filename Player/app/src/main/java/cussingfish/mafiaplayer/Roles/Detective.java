package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.Model.StartResults;

public class Detective extends Civilian {
    private static boolean foundGossip = false;
    public static boolean checkFoundGossip() { return foundGossip; }
    public static void setFoundGossip() { foundGossip = true; }
}
