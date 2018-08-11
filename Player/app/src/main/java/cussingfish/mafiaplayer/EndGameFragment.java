package cussingfish.mafiaplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EndGameFragment extends Fragment {

    private int status;
    private TextView winText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = this.getArguments().getInt("status");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_end_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        winText = view.findViewById(R.id.winText);
        if (status == 1) {
            winText.setText(R.string.civilian_win);
        } else {
            winText.setText(R.string.mafia_win);
        }
    }
}
