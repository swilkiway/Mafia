package cussingfish.mafiaplayer.Roles;

public class Official extends Player {
    public static Official get() {
        if (Official == null) {
            Official = new Official();
        }
        return Official;
    }
    private static Official Official = null;
    private Official() { }
    public void enfranchise() {
    }
}
