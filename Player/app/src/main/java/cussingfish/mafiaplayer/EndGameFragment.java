package cussingfish.mafiaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cussingfish.mafiaplayer.Roles.Civilian;

public class EndGameFragment extends Fragment {

    private int status;
    private TextView winText;
    private Button restart;

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
        restart = view.findViewById(R.id.restart);
        if (status == 1) {
            winText.setText(R.string.civilian_win);
        } else {
            winText.setText(R.string.mafia_win);
        }
//        restart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Civilian.reset();
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });
    }
}
