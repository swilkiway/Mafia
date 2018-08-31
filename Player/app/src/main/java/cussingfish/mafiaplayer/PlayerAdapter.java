package cussingfish.mafiaplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import cussingfish.mafiaplayer.Model.Player;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerHolder> {
    private Player players[];
    private int lastSelectedPosition = -1;
    private Context context;
    public PlayerAdapter(Context c, Player r[]) {
        context = c;
        players = r;
    }
    @Override
    @NonNull
    public PlayerAdapter.PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new PlayerHolder(inflater.inflate(R.layout.item_player, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.PlayerHolder holder, int position) {
        String option = players[position].getName();
        holder.bind(option, position);
    }
    public String getSelected() {
        if (lastSelectedPosition != -1) {
            return players[lastSelectedPosition].getName();
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return players.length;
    }
    class PlayerHolder extends RecyclerView.ViewHolder {
        private String option;
        private TextView eTextView;
        private RadioButton eButton;
        PlayerHolder(View view) {
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
        void bind(String o, int position) {
            option = o;
            eTextView.setText(option);
            eButton.setChecked(lastSelectedPosition == position);
        }
    }
}
