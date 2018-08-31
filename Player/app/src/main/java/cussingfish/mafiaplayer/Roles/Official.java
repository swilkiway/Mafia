package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.Model.StartResults;

public class Official extends Civilian {
    private static boolean hasVotedSelf = false;
    public static boolean checkVotedSelf() { return hasVotedSelf; }
    public static void voteSelf() { hasVotedSelf = true; }
    public static void reset() { hasVotedSelf = false; }
}
