package cussingfish.mafiaplayer.Model;

public class NightResults {
    public NightResults() { isNull = true; }
    private Player mafiaKilled;
    private Player daKilled;
    private Player bomberKilled;
    private String bodyguardSaved;
    private String daSaved;
    private String silenced;
    private Player[] alive;
    private Player lover;
    private int status;
    private boolean isNull;
    public Player getMafiaKilled() {
        return mafiaKilled;
    }
    public Player getDaKilled() { return daKilled; }
    public Player getBomberKilled() { return bomberKilled; }
    public String getBodyguardSaved() { return bodyguardSaved; }
    public String getDaSaved() { return daSaved; }
    public String getSilenced() { return silenced; }
    public Player[] getAlive() { return alive; }
    public Player getLover() { return lover; }
    public int getStatus() { return status; }
    public boolean getNull() { return isNull; }
    public boolean checkDead(String username) {
        return ((mafiaKilled != null && username.equals(mafiaKilled.getName()))
                || (daKilled != null && username.equals(daKilled.getName()))
                || (bomberKilled != null && username.equals(bomberKilled.getName()))
                || (lover != null && username.equals(lover.getName())));
    }
}
