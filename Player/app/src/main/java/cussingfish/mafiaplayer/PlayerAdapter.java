package cussingfish.mafiaplayer;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerHolder> {
    private ArrayList<String> players;
    private int lastSelectedPosition = -1;
    Context context;
    public PlayerAdapter(Context c, ArrayList<String> r) {
        context = c;
        players = r;
    }
    @Override
    public PlayerAdapter.PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new PlayerHolder(inflater.inflate(R.layout.item_player, parent, false));
    }
    @Override
    public void onBindViewHolder(PlayerAdapter.PlayerHolder holder, int position) {
        String option = players.get(position);
        holder.bind(option, position);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
public class PlayerHolder extends RecyclerView.ViewHolder {
    private String option;
    private TextView eTextView;
    private RadioButton eButton;
    public PlayerHolder(View view) {
        super(view);
        eTextView = view.findViewById(R.id.playerName);
        eButton = view.findViewById(R.id.playerSelect);
        eButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eButton.setChecked(true);
                lastSelectedPosition = getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }
    public void bind(String o, int position) {
        option = o;
        eTextView.setText(option);
        eButton.setChecked(lastSelectedPosition == position);
    }
}
}
