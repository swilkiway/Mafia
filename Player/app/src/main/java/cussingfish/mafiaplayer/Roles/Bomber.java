package cussingfish.mafiaplayer.Roles;

public class Bomber extends Player {
    private String target = "";
    public static Bomber get() {
        if (bomber == null) {
            bomber = new Bomber();
        }
        return bomber;
    }
    private static Bomber bomber = null;
    private Bomber() { }
    public void explode() {

    }
    public void chooseTarget(String t) {
        target = t;
    }
}