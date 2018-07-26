package cussingfish.mafiaplayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetupFragment extends Fragment {
    enum ROLES { MAFIOSO, DETECTIVE, DOUBLE_AGENT, BODYGUARD, BOMBER, CIVILIAN }
    private EditText mafiosi;
    private EditText detectives;
    private EditText doubleAgent;
    private EditText bodyguards;
    private EditText bomber;
    private EditText civilians;
    private TextView mafiosiText;
    private TextView detectiveText;
    private TextView doubleAgentText;
    private TextView bodyguardText;
    private TextView bomberText;
    private TextView civilianText;
    private TextView description;
    private Button submit;
    private int numMafiosi;
    private int numDetectives;
    private int numDoubleAgents;
    private int numBodyguards;
    private int numBombers;
    private int numCivilians;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = this.getArguments().getString("username");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mafiosi = view.findViewById(R.id.mafiosi);
        mafiosi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = s.toString();
                if (num.equals("")) {
                    numMafiosi = 0;
                } else {
                    numMafiosi = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        detectives = view.findViewById(R.id.detectives);
        detectives.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = s.toString();
                if (num.equals("")) {
                    numDetectives = 0;
                } else {
                    numDetectives = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        doubleAgent = view.findViewById(R.id.doubleAgent);
        doubleAgent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = s.toString();
                if (num.equals("")) {
                    numDoubleAgents = 0;
                } else {
                    numDoubleAgents = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bodyguards = view.findViewById(R.id.bodyguards);
        bodyguards.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = s.toString();
                if (num.equals("")) {
                    numBodyguards = 0;
                } else {
                    numBodyguards = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bomber = view.findViewById(R.id.bomber);
        bomber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = s.toString();
                if (num.equals("")) {
                    numBombers = 0;
                } else {
                    numBombers = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        civilians = view.findViewById(R.id.civilians);
        civilians.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = s.toString();
                if (num.equals("")) {
                    numCivilians = 0;
                } else {
                    numCivilians = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mafiosiText = view.findViewById(R.id.mafiosiText);
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
                int roles[] = getNums();
                SetupTask s = new SetupTask();
                s.execute(roles);
            }
        });
    }

    protected void setTextViewsClick() {
        mafiosiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.mafiosi_desc);
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

    public int[] getNums() {
        int nums[] = new int[6];
        nums[ROLES.MAFIOSO.ordinal()] = numMafiosi;
        nums[ROLES.DETECTIVE.ordinal()] = numDetectives;
        nums[ROLES.DOUBLE_AGENT.ordinal()] = numDoubleAgents;
        nums[ROLES.BODYGUARD.ordinal()] = numBodyguards;
        nums[ROLES.BOMBER.ordinal()] = numBombers;
        nums[ROLES.CIVILIAN.ordinal()] = numCivilians;
        return nums;
    }

    public class SetupTask extends AsyncTask<int[], String, String[]> {
        @Override
        protected String[] doInBackground(int[]... r) {
            ServerProxy.get().setup(r[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s[]) {
            getActivity().onBackPressed();
        }
    }
}
