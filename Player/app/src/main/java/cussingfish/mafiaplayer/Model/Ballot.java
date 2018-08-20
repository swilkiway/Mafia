package cussingfish.mafiaplayer.Model;

import java.util.ArrayList;

public class Ballot {
    private int totalVotes;
    private ArrayList<Candidate> candidates;
    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public int getTotalVotes() {
        return totalVotes;
    }
}
