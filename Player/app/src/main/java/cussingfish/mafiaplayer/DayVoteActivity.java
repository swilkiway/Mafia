package cussingfish.mafiaplayer;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DayVoteActivity extends AppCompatActivity {

    private RecyclerView optionsList;
    private RecyclerView.Adapter optionsAdaper;
    private RecyclerView.LayoutManager optionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_vote);
        optionsList = (RecyclerView) findViewById(R.id.optionsList);
    }
}
