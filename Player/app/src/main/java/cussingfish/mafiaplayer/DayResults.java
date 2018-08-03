package cussingfish.mafiaplayer;

public class DayResults {
    private Player lynched;
    private Player[] alive;
    private Vote[] ballot;
    private Player bombed;
    public Player getLynched() { return lynched; }
    public Player[] getAlive() { return alive; }
    public Vote[] getBallot() { return ballot; }
    public Player getBombed() { return bombed; }
}
