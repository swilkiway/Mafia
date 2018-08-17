package cussingfish.mafiaplayer.Model;

import java.util.ArrayList;

public class Ballot {
    private int totalVotes;
    private ArrayList<Vote> votes;
    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }
}
