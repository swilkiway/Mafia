package cussingfish.mafiaplayer.Roles;

public class Bodyguard extends Player {
    public static Bodyguard get() {
        if (bodyguard == null) {
            bodyguard = new Bodyguard();
        }
        return bodyguard;
    }
    private static Bodyguard bodyguard = null;
    private Bodyguard() { }
    public void guardBody() {

    }
}