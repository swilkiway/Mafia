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
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;
import cussingfish.mafiaplayer.VoteAdapter;

public class DoubleAgentFragment extends Fragment {
    private RecyclerView playerList;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;
    private RecyclerView voteList;
    private VoteAdapter voteAdapter;
    private RecyclerView.LayoutManager voteManager;
    private Button killButton;
    private Button passButton;
    private Button yesButton;
    private Button noButton;
    private String victim;
    private String mafiaVictim;
    private TextView dayResults;
    private TextView daSave;
    private TextView daKill;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_double_agent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerList = view.findViewById(R.id.killList);
        playerManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(playerManager);
        dayResults = view.findViewById(R.id.dayResults);
        if (DoubleAgent.getDayResults() != null) {
            playerAdapter = new PlayerAdapter(getActivity(), DoubleAgent.getDayResults().getAlive());
            dayResults.setText(Utils.getVotingResults(getContext(), DoubleAgent.getDayResults()));
            voteList = view.findViewById(R.id.voteList);
            voteManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            voteList.setLayoutManager(voteManager);
            voteAdapter = new VoteAdapter(getActivity(), DoubleAgent.getDayResults().getBallot().getCandidates());
            voteList.setAdapter(voteAdapter);
        } else {
            playerAdapter = new PlayerAdapter(getActivity(), DoubleAgent.getStartResults().getAlive());
            dayResults.setText(getString(R.string.double_agent_goal));
        }
        playerList.setAdapter(playerAdapter);
        daSave = view.findViewById(R.id.daSave);
        daKill = view.findViewById(R.id.daKill);
        yesButton = view.findViewById(R.id.yesButton);
        noButton = view.findViewById(R.id.noButton);
        killButton = view.findViewById(R.id.killButton);
        passButton = view.findViewById(R.id.passButton);
        if (!DoubleAgent.hasAlreadyKilled()) {
            killButton.setEnabled(true);
            passButton.setEnabled(true);
            killButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    victim = playerAdapter.getSelected();
                    if (victim == null) {
                        Toast.makeText(getContext(), R.string.da_kill, Toast.LENGTH_SHORT).show();
                    } else if (victim.equals(DoubleAgent.getUserName())) {
                        Toast.makeText(getContext(), R.string.kill_other, Toast.LENGTH_SHORT).show();
                    } else {
                        killButton.setEnabled(false);
                        passButton.setEnabled(false);
                        DoubleAgent.killPlayer();
                        KillTask b = new KillTask();
                        b.execute(victim);
                    }
                }
            });
            passButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    killButton.setEnabled(false);
                    passButton.setEnabled(false);
                    KillTask b = new KillTask();
                    b.execute("pass");
                }
            });
        } else {
            daKill.setText(R.string.da_already_killed);
            killButton.setVisibility(View.GONE);
            passButton.setVisibility(View.GONE);
        }
        if (!DoubleAgent.hasAlreadySaved()) {
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
                    DoubleAgent.savePlayer();
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
            if (DoubleAgent.hasAlreadySaved()) {
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
