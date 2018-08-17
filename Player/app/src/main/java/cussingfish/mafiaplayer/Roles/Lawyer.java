package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.Model.StartResults;

public class Lawyer extends Civilian {
    private static boolean hasDefendedSelf = false;
    public static boolean checkDefendedSelf() { return hasDefendedSelf; }
    public static void defendSelf() { hasDefendedSelf = true; }
}
