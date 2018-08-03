package cussingfish.mafiaplayer;

public class NightResults {
    private Player mafiaKilled;
    private Player daKilled;
    private Player bomberKilled;
    private String bodyguardSaved;
    private String daSaved;
    private String defended;
    private String enfranchised;
    private String silenced;
    private Player[] alive;
    public Player getMafiaKilled() {
        return mafiaKilled;
    }
    public Player getDaKilled() { return daKilled; }
    public Player getBomberKilled() { return bomberKilled; }
    public String getBodyguardSaved() { return bodyguardSaved; }
    public String getDaSaved() { return daSaved; }
    public String getDefended() {
        return defended;
    }
    public String getEnfranchised() { return enfranchised; }
    public String getSilenced() { return silenced; }
    public Player[] getAlive() { return alive; }
}
