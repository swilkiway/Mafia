package cussingfish.narrator.Model;

import java.util.ArrayList;

public class Ballot {
    private int totalVotes;
    private ArrayList<Vote> votes;
    public void addVote(String nominated, String voter) {
        totalVotes++;
        for (Vote v : votes) {
            if (v.getNominated().equals(nominated)) {
                v.addVote(voter);
                return;
            }
        }
        Vote v = new Vote(nominated, voter);
        votes.add(v);
    }

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }
}
