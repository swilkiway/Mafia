package cussingfish.mafiaplayer;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DayVoteActivity extends AppCompatActivity {

    private RecyclerView optionsList;
    private RecyclerView.Adapter optionsAdapter;
    private RecyclerView.LayoutManager optionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_vote);
        optionsList = (RecyclerView) findViewById(R.id.optionsList);
        optionsManager = new LinearLayoutManager(this);
        optionsList.setLayoutManager(optionsManager);
        optionsAdapter = new OptionsAdapter(options);
        optionsList.setAdapter(optionsAdapter);
    }

    public class OptionsAdapter extends RecyclerView.Adapter<OptionsHolder> {
        private ArrayList<String> options;
        public OptionsAdapter(ArrayList<String> r) {
            options = r;
        }
        @Override
        public OptionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(DayVoteActivity.this);
            return new OptionsHolder(inflater.inflate(R.layout.option_item, parent, false));
        }
        @Override
        public void onBindViewHolder(OptionsHolder holder, int position) {
            String option = options.get(position);
            holder.bind(option);
        }

        @Override
        public int getItemCount() {
            return options.size();
        }
    }
    public class OptionsHolder extends RecyclerView.ViewHolder {
        private String option;
        private TextView eTextView;
        private Button eButton;
        public OptionsHolder(View view) {
            super(view);
            eTextView = (TextView) view.findViewById(R.id.option_text);
            eButton = (Button) view.findViewById(R.id.option_button);
            eButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eButton.setEnabled(false);
                    mGuess.setMessage(mAnswer.getMessage());
                    mGuess.setUserName(User.get().getUserName());
                    GuessTask r = new GuessTask();
                    r.execute(mGuess);
                }
            });
        }
        public void bind(String o) {
            option = o;
            eTextView.setText(option);
        }
    }
}
