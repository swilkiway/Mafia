package cussingfish.mafiaplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import cussingfish.mafiaplayer.Model.Candidate;
import cussingfish.mafiaplayer.Model.Player;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteHolder> {
    private Candidate candidates[];
    Context context;
    public VoteAdapter(Context c, Candidate r[]) {
        context = c;
        candidates = r;
    }
    @Override
    public VoteAdapter.VoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new VoteHolder(inflater.inflate(R.layout.item_vote, parent, false));
    }
    @Override
    public void onBindViewHolder(VoteAdapter.VoteHolder holder, int position) {
        String someGuysName = candidates[position].getNominated();
        String theJerksThatVotedForHim = "";
        for (int i = 0; i < candidates[position].getVoters().length; i++) {
            theJerksThatVotedForHim += candidates[position].getVoters()[i];
            if (i < candidates[position].getVoters().length - 1) {
                theJerksThatVotedForHim += "\n";
            }
        }
        holder.bind(someGuysName, theJerksThatVotedForHim);
    }

    @Override
    public int getItemCount() {
        return candidates.length;
    }
    public class VoteHolder extends RecyclerView.ViewHolder {
        private String option;
        private TextView ePlayerName;
        private TextView eVotersNames;
        public VoteHolder(View view) {
            super(view);
            ePlayerName = view.findViewById(R.id.playerName);
            eVotersNames = view.findViewById(R.id.votersNames);

        }
        public void bind(String o, String p) {
            ePlayerName.setText(o);
            eVotersNames.setText(p);
        }
    }
}
