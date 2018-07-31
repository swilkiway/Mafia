package cussingfish.mafiaplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DayFragment extends Fragment {
    private ArrayList<String> players;
    private RecyclerView playerList;
    private RecyclerView.Adapter playerAdapter;
    private RecyclerView.LayoutManager playerManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        playerList = view.findViewById(R.id.playerList);
        playerManager = new LinearLayoutManager(getContext());
        playerList.setLayoutManager(playerManager);
        playerAdapter = new PlayerAdapter(players);
        playerList.setAdapter(playerAdapter);
    }

    public class PlayerAdapter extends RecyclerView.Adapter<DayFragment.PlayerHolder> {
        private ArrayList<String> players;
        public PlayerAdapter(ArrayList<String> r) {
            players = r;
        }
        @Override
        public DayFragment.PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            return new playerHolder(inflater.inflate(R.layout.option_item, parent, false));
            return null;
        }
        @Override
        public void onBindViewHolder(DayFragment.PlayerHolder holder, int position) {
            String option = players.get(position);
            holder.bind(option);
        }

        @Override
        public int getItemCount() {
            return players.size();
        }
    }
    public class PlayerHolder extends RecyclerView.ViewHolder {
        private String option;
        private TextView eTextView;
        private Button eButton;
        public PlayerHolder(View view) {
            super(view);
//            eTextView = (TextView) view.findViewById(R.id.option_text);
//            eButton = (Button) view.findViewById(R.id.option_button);
            eButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eButton.setEnabled(false);
//                    mGuess.setMessage(mAnswer.getMessage());
//                    mGuess.setUserName(User.get().getUserName());
//                    GuessTask r = new GuessTask();
//                    r.execute(mGuess);
                }
            });
        }
        public void bind(String o) {
            option = o;
            eTextView.setText(option);
        }
    }
}
