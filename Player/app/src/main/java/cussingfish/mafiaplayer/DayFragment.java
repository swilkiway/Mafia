package cussingfish.mafiaplayer;

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
import cussingfish.mafiaplayer.Night.BodyguardFragment;
import cussingfish.mafiaplayer.Night.BomberFragment;
import cussingfish.mafiaplayer.Night.DetectiveFragment;
import cussingfish.mafiaplayer.Night.DoubleAgentFragment;
import cussingfish.mafiaplayer.Night.MafiosiFragment;
import cussingfish.mafiaplayer.Night.SleepFragment;
import cussingfish.mafiaplayer.Roles.Bodyguard;
import cussingfish.mafiaplayer.Roles.Bomber;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.Roles.Mafioso;

public class DayFragment extends Fragment {
    private RecyclerView playerList;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;
    private Button submitButton;
    private String vote;
    private TextView nightResults;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerList = view.findViewById(R.id.playerList);
        playerManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(playerManager);
        playerAdapter = new PlayerAdapter(getActivity(), Civilian.get().nightResults.getAlive());
        playerList.setAdapter(playerAdapter);
        nightResults = view.findViewById(R.id.nightResults);
        nightResults.setText(Utils.getNightResults(getContext(), Civilian.get().nightResults));
        submitButton = view.findViewById(R.id.submitButton);
        if (!Civilian.get().isDead) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitButton.setEnabled(false);
                    vote = playerAdapter.getSelected();
                    VoteTask b = new VoteTask();
                    b.execute(vote);
                }
            });
        } else {
            submitButton.setVisibility(View.GONE);
            WaitTask w = new WaitTask();
            w.execute();
        }
    }

    public class VoteTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            ServerProxy.get().vote(r[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            WaitTask w = new WaitTask();
            w.execute();
        }
    }

    public class WaitTask extends AsyncTask<String, String, DayResults> {
        @Override
        protected DayResults doInBackground(String... r) {
            try {
                DayResults d = null;
                while (d == null) {
                    Thread.sleep(1000);
                    d = ServerProxy.get().dayResult();
                }
                Civilian.get().dayResults = d;
                return d;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(DayResults s) {
            Fragment fragment;
            if (s.getStatus() != 0) {
                fragment = new EndGameFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("status", s.getStatus());
                fragment.setArguments(bundle);
            } else if (Civilian.get().isDead) {
                fragment = new SleepFragment();
            } else if (Mafioso.get() != null) {
                fragment = new MafiosiFragment();
            } else if (Detective.get() != null) {
                fragment = new DetectiveFragment();
            } else if (DoubleAgent.get() != null) {
                if (DoubleAgent.get().hasAlreadySaved() && DoubleAgent.get().hasAlreadyKilled()) {
                    fragment = new SleepFragment();
                } else {
                    fragment = new DoubleAgentFragment();
                }
            } else if (Bodyguard.get() != null) {
                fragment = new BodyguardFragment();
            } else if (Bomber.get() != null) {
                fragment = new BomberFragment();
            } else {
                fragment = new SleepFragment();
            }
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentContainer, fragment);
            ft.commit();
        }
    }
}
