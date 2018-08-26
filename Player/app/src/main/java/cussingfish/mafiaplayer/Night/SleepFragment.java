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
import android.widget.TextView;

import cussingfish.mafiaplayer.DayFragment;
import cussingfish.mafiaplayer.EndGameFragment;
import cussingfish.mafiaplayer.Model.NightResults;
import cussingfish.mafiaplayer.R;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;
import cussingfish.mafiaplayer.VoteAdapter;

public class SleepFragment extends Fragment {

    private TextView dayResults;
    private RecyclerView voteList;
    private VoteAdapter voteAdapter;
    private RecyclerView.LayoutManager voteManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        dayResults = view.findViewById(R.id.dayResults);
        if (Civilian.getDayResults() != null) {
            dayResults.setText(Utils.getVotingResults(getContext(), Civilian.getDayResults()));
            voteList = view.findViewById(R.id.voteList);
            voteManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            voteList.setLayoutManager(voteManager);
            voteAdapter = new VoteAdapter(getActivity(), Civilian.getDayResults().getBallot().getCandidates());
            voteList.setAdapter(voteAdapter);
        } else {
            dayResults.setText(getString(R.string.civilian_goal));
        }
        SleepTask s = new SleepTask();
        s.execute();
    }

    public class SleepTask extends AsyncTask<String, NightResults, NightResults> {
        @Override
        protected NightResults doInBackground(String... r) {
            try {
                NightResults n = new NightResults();
                while (n.getNull()) {
                    Thread.sleep(1000);
                    n = ServerProxy.get().nightResult();
                }
                Civilian.setNightResults(n);
                return n;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(NightResults s) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment;
            if (s.getStatus() == 0) {
                fragment = new DayFragment();
            } else {
                fragment = new EndGameFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("status", s.getStatus());
                fragment.setArguments(bundle);
            }
            ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
            ft.commit();
        }
    }
}
