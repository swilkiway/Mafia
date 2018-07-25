package cussingfish.narrator;

public class Vote {
    private String voter;
    private String nominated;
    private String role;
    public Vote(String v, Player n) {
        voter = v;
        nominated = n.getName();
        role = n.getRole();
    }
}
