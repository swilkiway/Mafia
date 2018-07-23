package cussingfish.mafiaplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetupFragment extends Fragment {
    private EditText mafia;
    private EditText detectives;
    private EditText doubleAgents;
    private EditText bodyguards;
    private EditText bombers;
    private EditText civilians;
    private TextView mafiaText;
    private TextView detectiveText;
    private TextView doubleAgentText;
    private TextView bodyguardText;
    private TextView bomberText;
    private TextView civilianText;
    private TextView description;
    private Button submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mafia = view.findViewById(R.id.mafia);
        detectives = view.findViewById(R.id.detectives);
        doubleAgents = view.findViewById(R.id.doubleAgents);
        bodyguards = view.findViewById(R.id.bodyguards);
        bombers = view.findViewById(R.id.bombers);
        civilians = view.findViewById(R.id.civilians);
        mafiaText = view.findViewById(R.id.mafiaText);
        detectiveText = view.findViewById(R.id.detectiveText);
        doubleAgentText = view.findViewById(R.id.doubleAgentText);
        bodyguardText = view.findViewById(R.id.bodyguardText);
        bomberText = view.findViewById(R.id.bomberText);
        civilianText = view.findViewById(R.id.civilianText);
        description = view.findViewById(R.id.description);
        setTextViewsClick();
        submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void setTextViewsClick() {
        mafiaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.mafia_desc);
            }
        });
        detectiveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.detective_desc);
            }
        });
        doubleAgentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.double_agent_desc);
            }
        });
        bodyguardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.bodyguard_desc);
            }
        });
        bomberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.bomber_desc);
            }
        });
        civilianText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.civilian_desc);
            }
        });
    }
}
