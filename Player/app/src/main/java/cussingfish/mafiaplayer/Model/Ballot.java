package cussingfish.mafiaplayer.Model;

import java.util.ArrayList;

public class Ballot {
    private int totalVotes;
    private ArrayList<Candidate> candidates;
    public Candidate[] getCandidates() {
        return candidates.toArray(new Candidate[candidates.size()]);
    }

    public int getTotalVotes() {
        return totalVotes;
    }
}
