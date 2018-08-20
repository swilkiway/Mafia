package cussingfish.narrator.Model;

import java.util.ArrayList;

public class Candidate {
    private String nominated;
    private ArrayList<String> voters;
    public Candidate(String n, String v) {
        nominated = n;
        voters = new ArrayList<>();
        voters.add(v);
    }
    public void addVote(String v) {
        voters.add(v);
    }
    public String[] getVoters() {
        return voters.toArray(new String[voters.size()]);
    }
    public int getVotes() {
        return voters.size();
    }
    public String getNominated() {
        return nominated;
    }
}
