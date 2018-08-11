package cussingfish.mafiaplayer;

public class DayResults {
    private Player lynched;
    private String defended;
    private Player[] alive;
    private Vote[] ballot;
    private Player bombed;
    private int status;
    public Player getLynched() { return lynched; }
    public String getDefended() { return defended; }
    public Player[] getAlive() { return alive; }
    public Vote[] getBallot() { return ballot; }
    public Player getBombed() { return bombed; }
    public int getStatus() { return status; }
}
