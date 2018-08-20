package cussingfish.mafiaplayer.Model;

import java.util.ArrayList;

public class Candidate {
    private String nominated;
    private ArrayList<String> voters;
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
