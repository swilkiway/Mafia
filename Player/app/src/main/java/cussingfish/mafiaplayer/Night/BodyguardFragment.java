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

import cussingfish.mafiaplayer.Player;
import cussingfish.mafiaplayer.PlayerAdapter;
import cussingfish.mafiaplayer.R;
import cussingfish.mafiaplayer.Roles.Bodyguard;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Mafioso;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;
import cussingfish.mafiaplayer.Vote;

public class BodyguardFragment extends Fragment {
    private RecyclerView playerList;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;
    private Button submitButton;
    private String saved;
    private TextView dayResults;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bodyguard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerList = view.findViewById(R.id.playerList);
        playerManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(playerManager);dayResults = view.findViewById(R.id.dayResults);
        if (Bodyguard.get().dayResults != null) {
            playerAdapter = new PlayerAdapter(getActivity(), Bodyguard.get().dayResults.getAlive());
            dayResults.setText(Utils.getVotingResults(getContext(), Bodyguard.get().dayResults));
        } else {
            playerAdapter = new PlayerAdapter(getActivity(), Bodyguard.get().startResults.getAlive());
            dayResults.setText(getString(R.string.bodyguard_goal));
        }
        playerList.setAdapter(playerAdapter);
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved = playerAdapter.getSelected();
                if (saved.equals(Bodyguard.get().userName)) {
                    if (!Bodyguard.get().checkSavedSelf()) {
                        Bodyguard.get().saveSelf();
                        BodyguardTask b = new BodyguardTask();
                        b.execute(saved);
                    } else {
                        Toast.makeText(getContext(), R.string.choose_other, Toast.LENGTH_LONG).show();
                    }
                } else {
                    BodyguardTask b = new BodyguardTask();
                    b.execute(saved);
                }
            }
        });
    }

    public class BodyguardTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            ServerProxy.get().bodyguardSave(r[0]);
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
