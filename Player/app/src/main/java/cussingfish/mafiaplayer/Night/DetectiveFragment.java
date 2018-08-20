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

import cussingfish.mafiaplayer.PlayerAdapter;
import cussingfish.mafiaplayer.R;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.Mafioso;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;

public class DetectiveFragment extends Fragment {
    private RecyclerView playerList;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;
    private Button submitButton;
    private TextView dayResults;
    private String victim;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detective, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerList = view.findViewById(R.id.playerList);
        playerManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(playerManager);
        dayResults = view.findViewById(R.id.dayResults);
        if (Detective.getDayResults() != null) {
            playerAdapter = new PlayerAdapter(getActivity(), Detective.getDayResults().getAlive());
            dayResults.setText(Utils.getVotingResults(getContext(), Detective.getDayResults()));
        } else {
            playerAdapter = new PlayerAdapter(getActivity(), Detective.getStartResults().getAlive());
            dayResults.setText(getString(R.string.detectives_goal));
        }
        playerList.setAdapter(playerAdapter);
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                victim = playerAdapter.getSelected();
                if (victim == null) {
                    Toast.makeText(getContext(), R.string.investigate, Toast.LENGTH_SHORT).show();
                } else {
                    DetectiveTask b = new DetectiveTask();
                    b.execute(victim);
                }
            }
        });
    }
    public class DetectiveTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            return ServerProxy.get().investigate(r[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.contains("gossip")) {
                Detective.setFoundGossip();
            }
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            SleepFragment fragment = new SleepFragment();
            ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
            ft.commit();
        }
    }
}
