package cussingfish.mafiaplayer.Roles;

public class Matchmaker extends Civilian {
    public static void set(String name) {
        matchmaker = new Matchmaker(name);
    }
    public static Matchmaker get() {
        return matchmaker;
    }
    private static Matchmaker matchmaker = null;
    private Matchmaker(String name) {
        userName = name;
    }
    public void matchmake() {

    }
}
