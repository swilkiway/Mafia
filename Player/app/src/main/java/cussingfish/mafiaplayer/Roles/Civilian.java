package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.Model.DayResults;
import cussingfish.mafiaplayer.Model.NightResults;
import cussingfish.mafiaplayer.Model.StartResults;

public class Civilian {
    public static void instantiate(String name, StartResults s) {
        userName = name;
        dayResults = null;
        nightResults = null;
        startResults = s;
    }
    private static String userName = "";
    public static String getUserName() { return userName; }
    public static void setUserName(String u) { userName = u; }
    private static boolean isDead = false;
    public static boolean getDead() { return isDead; }
    public static void kill() {
        isDead = true;
    }
    private static boolean isSilenced = false;
    public static boolean getSilenced() { return isSilenced; }
    public static void setSilenced(boolean s) { isSilenced = s; }
    private static NightResults nightResults;
    public static NightResults getNightResults() { return nightResults; }
    public static void setNightResults(NightResults n) { nightResults = n; }
    private static DayResults dayResults;
    public static DayResults getDayResults() { return dayResults; }
    public static void setDayResults(DayResults d) { dayResults = d; }
    private static StartResults startResults;
    public static StartResults getStartResults() { return startResults; }
    public static void setStartResults(StartResults s) { startResults = s; }
    public static void reset() {
        isDead = false;
        startResults = null;
        nightResults = null;
        dayResults = null;
        Bodyguard.reset();
        Detective.reset();
        DoubleAgent.reset();
        Lawyer.reset();
        Official.reset();
    }
}
