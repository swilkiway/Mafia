package cussingfish.narrator.Model;

import java.util.ArrayList;

public class Ballot {
    private int totalVotes;
    private ArrayList<Candidate> candidates;
    public Ballot() {
        totalVotes = 0;
        candidates = new ArrayList<>();
    }
    public void addVote(String nominated, String voter) {
        totalVotes++;
        if (!candidates.isEmpty()) {
            for (Candidate c : candidates) {
                if (c.getNominated().equals(nominated)) {
                    c.addVote(voter);
                    return;
                }
            }
        }
        Candidate c = new Candidate(nominated, voter);
        candidates.add(c);
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public int getTotalVotes() {
        return totalVotes;
    }
}
