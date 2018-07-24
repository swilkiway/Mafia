package cussingfish.narrator;

public class Vote {
    private String voter;
    private Player nominated;
    public Vote(String v, Player n) {
        voter = v;
        nominated = n;
    }
}
