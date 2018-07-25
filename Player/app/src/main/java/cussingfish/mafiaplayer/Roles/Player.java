package cussingfish.mafiaplayer.Roles;

public class Player {
    public static Player getPlayer() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    private static Player player;

    public String userName = "";
    public Boolean isDead = false;
    public Boolean isInLove = false;
    public Boolean isEnfranchised = false;
    public void castDayVote() {

    }
}
