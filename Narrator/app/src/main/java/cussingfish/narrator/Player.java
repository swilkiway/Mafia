package cussingfish.narrator;

import android.support.annotation.NonNull;

public class Player {
    private String role = "";
    private String name = "";
    private int votes = 0;
    public String getRole() {
        return role;
    }
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

    @Override
    public boolean equals(Object o) {
        return !(o instanceof Player) && name.equals(((Player)o).getName());
    }
}
