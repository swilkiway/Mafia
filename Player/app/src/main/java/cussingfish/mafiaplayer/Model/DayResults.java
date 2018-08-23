package cussingfish.mafiaplayer.Model;

public class DayResults {
    public DayResults() { isNull = true; }
    private Player lynched;
    private String defended;
    private Player[] alive;
    private Ballot ballot;
    private Player bombed;
    private Player lover;
    private boolean isTied;
    private int status;
    private boolean isNull;
    public Player getLynched() { return lynched; }
    public String getDefended() { return defended; }
    public Player[] getAlive() { return alive; }
    public Ballot getBallot() { return ballot; }
    public Player getBombed() { return bombed; }
    public Player getLover() { return lover; }
    public boolean getTied() { return isTied; }
    public int getStatus() { return status; }
    public boolean getNull() { return isNull; }
}
