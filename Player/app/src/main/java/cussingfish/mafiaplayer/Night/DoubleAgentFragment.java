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

import java.util.ArrayList;

import cussingfish.mafiaplayer.PlayerAdapter;
import cussingfish.mafiaplayer.R;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;

public class DoubleAgentFragment extends Fragment {
    private RecyclerView playerList;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;
    private Button submitButton;
    private Button yesButton;
    private Button noButton;
    private String victim;
    private String mafiaVictim;
    private TextView dayResults;
    private TextView daSave;
    private TextView daKill;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mafioso, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerList = view.findViewById(R.id.playerList);
        playerManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(playerManager);
        playerAdapter = new PlayerAdapter(getActivity(), Civilian.get().dayResults.getAlive());
        playerList.setAdapter(playerAdapter);
        dayResults = view.findViewById(R.id.dayResults);
        dayResults.setText(Utils.getVotingResults(getContext(), Civilian.get().dayResults));
        daSave = view.findViewById(R.id.daSave);
        daKill = view.findViewById(R.id.daKill);
        yesButton = view.findViewById(R.id.yesButton);
        noButton = view.findViewById(R.id.noButton);
        submitButton = view.findViewById(R.id.submitButton);
        if (!DoubleAgent.get().hasAlreadyKilled()) {
            submitButton.setEnabled(true);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DoubleAgent.get().killPlayer();
                    victim = playerAdapter.getSelected();
                    KillTask b = new KillTask();
                    b.execute(victim);
                }
            });
        } else {
            daKill.setText(R.string.da_already_killed);
        }
        if (!DoubleAgent.get().hasAlreadySaved()) {
            daSave.setVisibility(View.VISIBLE);
            daSave.setText(R.string.da_wait);
            AgentTask a = new AgentTask();
            a.execute();
        } else {
            daSave.setText(R.string.da_already_saved);
        }
    }
    public class AgentTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            try {
                String n = null;
                while (n == null) {
                    Thread.sleep(1000);
                    n = ServerProxy.get().daGetMafiaKilled();
                }
                return n;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            mafiaVictim = s;
            daSave.setText(getString(R.string.da_save, mafiaVictim));
            daSave.setVisibility(View.VISIBLE);
            yesButton.setEnabled(true);
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DoubleAgent.get().savePlayer();
                    SaveTask t = new SaveTask();
                    t.execute(mafiaVictim);
                }
            });
            noButton.setEnabled(true);
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveTask t = new SaveTask();
                    t.execute("pass");
                }
            });
        }
    }
    public class KillTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            ServerProxy.get().daKill(r[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (DoubleAgent.get().hasAlreadySaved()) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SleepFragment fragment = new SleepFragment();
                ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
                ft.commit();
            }
        }
    }
    public class SaveTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            ServerProxy.get().daSave(r[0]);
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
