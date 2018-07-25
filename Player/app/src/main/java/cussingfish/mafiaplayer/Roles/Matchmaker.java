package cussingfish.mafiaplayer.Roles;

public class Matchmaker extends Player {
    public static Matchmaker get() {
        if (Matchmaker == null) {
            Matchmaker = new Matchmaker();
        }
        return Matchmaker;
    }
    private static Matchmaker Matchmaker = null;
    private Matchmaker() { }
    public void matchmake() {

    }
}
