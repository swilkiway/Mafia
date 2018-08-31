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

import cussingfish.mafiaplayer.Model.DayResults;
import cussingfish.mafiaplayer.Night.BlackmailerFragment;
import cussingfish.mafiaplayer.Night.BodyguardFragment;
import cussingfish.mafiaplayer.Night.BomberFragment;
import cussingfish.mafiaplayer.Night.DetectiveFragment;
import cussingfish.mafiaplayer.Night.DoubleAgentFragment;
import cussingfish.mafiaplayer.Night.HitManFragment;
import cussingfish.mafiaplayer.Night.LawyerFragment;
import cussingfish.mafiaplayer.Night.OfficialFragment;
import cussingfish.mafiaplayer.Night.PoisonerFragment;
import cussingfish.mafiaplayer.Night.SleepFragment;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.DoubleAgent;

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
        playerAdapter = new PlayerAdapter(getActivity(), Civilian.getNightResults().getAlive());
        playerList.setAdapter(playerAdapter);
        nightResults = view.findViewById(R.id.nightResults);
        nightResults.setText(Utils.getNightResults(getContext(), Civilian.getNightResults()));
        submitButton = view.findViewById(R.id.submitButton);
        if (!Civilian.getDead() && !Civilian.getSilenced()) {
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
                DayResults d = new DayResults();
                while (d.getNull()) {
                    Thread.sleep(1000);
                    d = ServerProxy.get().dayResult();
                }
                Civilian.setDayResults(d);
                return d;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(DayResults s) {
            Civilian.setSilenced(false);
            if (s.checkDead(Civilian.getUserName())) {
                Civilian.kill();
            }
            Fragment fragment;
            if (s.getStatus() != 0) {
                fragment = new EndGameFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("status", s.getStatus());
                fragment.setArguments(bundle);
            } else if (Civilian.getDead()) {
                fragment = new SleepFragment();
            } else {
                switch (Civilian.getStartResults().getRole()) {
                    case "godfather":
                    case "hit man":
                        fragment = new HitManFragment(); break;
                    case "detective":
                        if (Detective.checkFoundGossip()) fragment = new SleepFragment();
                        else fragment = new DetectiveFragment();
                        break;
                    case "double agent":
                        if (DoubleAgent.hasAlreadyKilled() && DoubleAgent.hasAlreadySaved()) fragment = new SleepFragment();
                        else fragment = new DoubleAgentFragment();
                        break;
                    case "bodyguard":
                        fragment = new BodyguardFragment(); break;
                    case "bomber":
                        fragment = new BomberFragment(); break;
                    case "lawyer":
                        fragment = new LawyerFragment(); break;
                    case "official":
                        fragment = new OfficialFragment(); break;
                    case "blackmailer":
                        fragment = new BlackmailerFragment(); break;
                    case "poisoner":
                        fragment = new PoisonerFragment(); break;
                    default:
                        fragment = new SleepFragment(); break;
                }
            }
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentContainer, fragment);
            ft.commit();
        }
    }
}
