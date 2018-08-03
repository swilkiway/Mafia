package cussingfish.mafiaplayer.Roles;

public class DoubleAgent extends Civilian {
    public static void set(String name) {
        doubleAgent = new DoubleAgent(name);
    }
    public static DoubleAgent get() {
        return doubleAgent;
    }
    private static DoubleAgent doubleAgent = null;
    private DoubleAgent(String name) {
        userName = name;
    }
    private boolean alreadyKilled = false;
    private boolean alreadySaved = false;
    public boolean hasAlreadyKilled() { return alreadyKilled; }
    public boolean hasAlreadySaved() { return alreadySaved; }

    public void killPlayer(String victim) {
        alreadyKilled = true;
    }

    public void savePlayer(String saved) {
        alreadySaved = true;
    }
}
