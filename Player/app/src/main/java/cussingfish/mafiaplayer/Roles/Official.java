package cussingfish.mafiaplayer.Roles;

public class Official extends Player {
    public static void set(String name) {
        official = new Official(name);
    }
    public static Official get() {
        return official;
    }
    private static Official official = null;
    private Official(String name) {
        userName = name;
    }
    public void enfranchise() {
    }
}
