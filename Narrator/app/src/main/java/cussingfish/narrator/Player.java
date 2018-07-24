package cussingfish.narrator;

public class Player {
    private String role;
    private String name;
    private int votes;
    private boolean alive;
    Player(String n) {
        name = n;
        role = null;
        votes = 0;
        alive = true;
    }
    public String getRole() { return role; }
    public void setRole(String r) {
        role = r;
    }
    public String getName() { return name; }
    public void setName(String n) {
        name = n;
    }
    public int getVotes() { return votes; }
    public void addVote() { votes++; }
    public void resetVotes() { votes = 0; }
    public void kill() { alive = false; }

    @Override
    public boolean equals(Object o) {
        return !(o instanceof Player) && name.equals(((Player)o).getName());
    }
}
