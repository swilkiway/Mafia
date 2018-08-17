package cussingfish.mafiaplayer.Model;

public class Player {
    private String role;
    private String name;
    Player(String n) {
        name = n;
        role = null;
    }
    public String getRole() { return role; }
    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        return !(o instanceof Player) && name.equals(((Player)o).getName());
    }
}
