package cussingfish.mafiaplayer.Model;

public class DayResults {
    private Player lynched;
    private String defended;
    private Player[] alive;
    private Vote[] ballot;
    private Player bombed;
    private int status;
    private boolean isNull;
    public Player getLynched() { return lynched; }
    public String getDefended() { return defended; }
    public Player[] getAlive() { return alive; }
    public Vote[] getBallot() { return ballot; }
    public Player getBombed() { return bombed; }
    public int getStatus() { return status; }
    public boolean getNull() { return isNull; }
}
