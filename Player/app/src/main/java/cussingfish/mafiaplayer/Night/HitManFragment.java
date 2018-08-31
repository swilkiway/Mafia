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

import java.util.ArrayList;
import java.util.Arrays;

import cussingfish.mafiaplayer.PlayerAdapter;
import cussingfish.mafiaplayer.R;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.HitMan;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;
import cussingfish.mafiaplayer.VoteAdapter;

public class HitManFragment extends Fragment {

    private RecyclerView playerList;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;
    private RecyclerView voteList;
    private VoteAdapter voteAdapter;
    private RecyclerView.LayoutManager voteManager;
    private Button submitButton;
    private TextView dayResults;
    private TextView teamList;
    private String victim;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hit_man, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerList = view.findViewById(R.id.playerList);
        playerManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(playerManager);
        dayResults = view.findViewById(R.id.dayResults);
        if (HitMan.getDayResults() != null) {
            playerAdapter = new PlayerAdapter(getActivity(), HitMan.getDayResults().getAlive());
            dayResults.setText(Utils.getVotingResults(getContext(), HitMan.getDayResults()));
            voteList = view.findViewById(R.id.voteList);
            voteManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            voteList.setLayoutManager(voteManager);
            voteAdapter = new VoteAdapter(getActivity(), HitMan.getDayResults().getBallot().getCandidates());
            voteList.setAdapter(voteAdapter);

        } else {
            playerAdapter = new PlayerAdapter(getActivity(), HitMan.getStartResults().getAlive());
            dayResults.setText(getString(R.string.hit_men_goal));
        }
        playerList.setAdapter(playerAdapter);
        teamList = view.findViewById(R.id.teamList);
        teamList.setText(Utils.getTeammates(getContext(), HitMan.getStartResults().getTeammates()));
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                victim = playerAdapter.getSelected();
                if (victim == null) {
                    Toast.makeText(getContext(), R.string.mafia_kill, Toast.LENGTH_SHORT).show();
                } else if (Arrays.asList(HitMan.getStartResults().getTeammates()).contains(victim)) {
                    Toast.makeText(getContext(), R.string.kill_other_mafia, Toast.LENGTH_SHORT).show();
                } else {
                    HitManTask b = new HitManTask();
                    b.execute(victim);
                }
            }
        });
    }
    public class HitManTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            ServerProxy.get().mafiaKill(r[0]);
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
