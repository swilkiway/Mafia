package cussingfish.mafiaplayer.Roles;

import cussingfish.mafiaplayer.StartResults;

public class DoubleAgent extends Civilian {
    public static void set(String name, StartResults s) {
        doubleAgent = new DoubleAgent(name, s);
    }
    public static DoubleAgent get() {
        return doubleAgent;
    }
    private static DoubleAgent doubleAgent = null;
    private DoubleAgent(String name, StartResults s) {
        userName = name;
        startResults = s;
    }
    private boolean alreadyKilled = false;
    private boolean alreadySaved = false;
    public boolean hasAlreadyKilled() { return alreadyKilled; }
    public boolean hasAlreadySaved() { return alreadySaved; }

    public void killPlayer() {
        alreadyKilled = true;
    }

    public void savePlayer() {
        alreadySaved = true;
    }
}
