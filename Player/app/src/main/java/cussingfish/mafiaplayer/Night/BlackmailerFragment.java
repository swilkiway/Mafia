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

import java.util.Arrays;

import cussingfish.mafiaplayer.PlayerAdapter;
import cussingfish.mafiaplayer.R;
import cussingfish.mafiaplayer.Roles.Blackmailer;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;
import cussingfish.mafiaplayer.VoteAdapter;

public class BlackmailerFragment extends Fragment {

    private RecyclerView playerList;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;
    private Button submitButton;
    private TextView dayResults;
    private TextView teamList;
    private String victim;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blackmailer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerAdapter = Utils.setUpViews(getContext(), view, getString(R.string.blackmailer_goal));
        teamList = view.findViewById(R.id.teamList);
        teamList.setText(Utils.getTeammates(getContext(), Blackmailer.getStartResults().getTeammates()));
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                victim = playerAdapter.getSelected();
                if (victim == null) {
                    Toast.makeText(getContext(), R.string.mafia_kill, Toast.LENGTH_SHORT).show();
                } else if (Arrays.asList(Blackmailer.getStartResults().getTeammates()).contains(victim)) {
                    Toast.makeText(getContext(), R.string.kill_other_mafia, Toast.LENGTH_SHORT).show();
                } else {
                    BlackmailerTask b = new BlackmailerTask();
                    b.execute(victim);
                }
            }
        });
    }
    public class BlackmailerTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... r) {
            ServerProxy.get().blackmailerSilence(r[0]);
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
