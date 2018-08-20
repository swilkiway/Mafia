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
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;

public class MatchmakerFragment extends Fragment {
    private RecyclerView playerOneList;
    private RecyclerView playerTwoList;
    private PlayerAdapter playerOneAdapter;
    private PlayerAdapter playerTwoAdapter;
    private RecyclerView.LayoutManager playerOneManager;
    private RecyclerView.LayoutManager playerTwoManager;
    private Button submitButton;
    private TextView startResults;
    private String playerOne;
    private String playerTwo;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matchmaker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerOneList = view.findViewById(R.id.playerOneList);
        playerTwoList = view.findViewById(R.id.playerTwoList);
        playerOneManager = new LinearLayoutManager(getContext());
        playerTwoManager = new LinearLayoutManager(getContext());
        playerOneList.setLayoutManager(playerOneManager);
        playerTwoList.setLayoutManager(playerTwoManager);
        startResults = view.findViewById(R.id.startResults);
        playerOneAdapter = new PlayerAdapter(getActivity(), Civilian.getStartResults().getAlive());
        playerTwoAdapter = new PlayerAdapter(getActivity(), Civilian.getStartResults().getAlive());
        startResults.setText(getString(R.string.detectives_goal));
        playerOneList.setAdapter(playerOneAdapter);
        playerTwoList.setAdapter(playerTwoAdapter);
        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerOne = playerOneAdapter.getSelected();
                playerTwo = playerTwoAdapter.getSelected();
                if (playerOne == null) {
                    Toast.makeText(getContext(), R.string.matchmaker_one, Toast.LENGTH_SHORT).show();
                } else if (playerTwo == null) {
                    Toast.makeText(getContext(), R.string.matchmaker_two, Toast.LENGTH_SHORT).show();
                } else {
                    MatchmakerTask b = new MatchmakerTask();
                    b.execute(new String[] {playerOne, playerTwo});
                }
            }
        });
    }
    public class MatchmakerTask extends AsyncTask<String[], String, String> {
        @Override
        protected String doInBackground(String[]... r) {
            ServerProxy.get().matchmake(r[0]);
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
