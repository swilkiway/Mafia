package cussingfish.mafiaplayer.Roles;

public class Bomber extends Player {
    public static void set(String name) {
        bomber = new Bomber(name);
    }
    public static Bomber get() {
        return bomber;
    }
    private static Bomber bomber = null;
    private Bomber(String name) {
        userName = name;
    }

    private String target = "";

    public void explode() {

    }
    public void chooseTarget(String t) {
        target = t;
    }
}