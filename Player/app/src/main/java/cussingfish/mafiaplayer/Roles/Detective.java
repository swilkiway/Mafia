package cussingfish.mafiaplayer.Roles;

public class Detective extends Player {
    public static Detective get() {
        if (Detective == null) {
            Detective = new Detective();
        }
        return Detective;
    }
    private static Detective Detective = null;
    private Detective() { }

    public void investigate(String suspect) {

    }
}
