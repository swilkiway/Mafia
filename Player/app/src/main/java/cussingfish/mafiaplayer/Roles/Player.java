package cussingfish.mafiaplayer.Roles;

public class Player {
    public static void set(String name) {
        player = new Player(name);
    }
    public static Player get() {
        return player;
    }
    private static Player player;
    public Player(String name) {
        userName = name;
    }
    public Player() {

    }

    String userName = "";
    public Boolean isDead = false;
    public Boolean isInLove = false;
    public Boolean isEnfranchised = false;
    public void castDayVote() {

    }
}
