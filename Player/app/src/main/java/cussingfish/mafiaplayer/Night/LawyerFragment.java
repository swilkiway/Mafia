package cussingfish.mafiaplayer.Night;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cussingfish.mafiaplayer.PlayerAdapter;
import cussingfish.mafiaplayer.R;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Lawyer;
import cussingfish.mafiaplayer.Roles.Mafioso;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;
import cussingfish.mafiaplayer.VoteAdapter;

public class LawyerFragment extends Fragment {
    private RecyclerView playerList;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;
    private RecyclerView voteList;
    private VoteAdapter voteAdapter;
    private RecyclerView.LayoutManager voteManager;
    private Button submitButton;
    private String saved;
    private TextView dayResults;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lawyer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerList = view.findViewById(R.id.playerList);
        playerManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(playerManager);
        dayResults = view.findViewById(R.id.dayResults);
        if (Lawyer.getDayResults() != null) {
            playerAdapter = new PlayerAdapter(getActivity(), Lawyer.getDayResults().getAlive());
            dayResults.setText(Utils.getVotingResults(getContext(), Lawyer.getDayResults()));
            voteList = view.findViewById(R.id.voteList);
            voteManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            voteList.setLayoutManager(voteManager);
            voteAdapter = new VoteAdapter(getActivity(), Lawyer.getDayResults().getBallot().getCandidates());
            voteList.setAdapter(voteAdapter);
        } else {
            playerAdapter = new PlayerAdapter(getActivity(), Lawyer.getStartResults().getAlive());
            dayResults.setText(getString(R.string.lawyer_goal));
        }
        playerList.setAdapter(playerAdapter);
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved = playerAdapter.getSelected();
                if (saved == null) {
                    Toast.makeText(getContext(), R.string.defend, Toast.LENGTH_SHORT).show();
                } else if (saved.equals(Lawyer.getUserName())) {
                    if (!Lawyer.checkDefendedSelf()) {
                        Lawyer.defendSelf();
                        LawyerTask b = new LawyerTask();
                        b.execute(saved);
                    } else {
                        Toast.makeText(getContext(), R.string.choose_other, Toast.LENGTH_LONG).show();
                    }
                } else {
                    LawyerTask b = new LawyerTask();
                    b.execute(saved);
                }
            }
        });
    }

    public class LawyerTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            ServerProxy.get().lawyerDefend(r[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            SleepFragment fragment = new SleepFragment();
            ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
            ft.commit();
        }
    }
}
