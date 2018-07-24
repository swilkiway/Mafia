package cussingfish.mafiaplayer.Roles;

public class DoubleAgent extends Player {
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
