package cussingfish.narrator.Model;

public class Player {
    private String role;
    private String name;
    public Player(String n) {
        name = n;
        role = null;
    }
    public String getRole() { return role; }
    public void setRole(String r) {
        role = r;
    }
    public String getName() { return name; }
    public void setName(String n) {
        name = n;
    }

    @Override
    public boolean equals(Object o) {
        return !(o instanceof Player) && name.equals(((Player)o).getName());
    }
}
