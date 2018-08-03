package cussingfish.mafiaplayer.Roles;

public class Detective extends Civilian {
    public static void set(String name) {
        detective = new Detective(name);
    }
    public static Detective get() {
        return detective;
    }
    private static Detective detective = null;
    private Detective(String name) {
        userName = name;
    }

    public void investigate(String suspect) {

    }
}
