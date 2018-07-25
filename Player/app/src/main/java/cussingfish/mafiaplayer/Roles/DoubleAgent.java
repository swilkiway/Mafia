package cussingfish.mafiaplayer.Roles;

public class DoubleAgent extends Player {
    public static DoubleAgent get() {
        if (DoubleAgent == null) {
            DoubleAgent = new DoubleAgent();
        }
        return DoubleAgent;
    }
    private static DoubleAgent DoubleAgent = null;
    private DoubleAgent() { }
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
