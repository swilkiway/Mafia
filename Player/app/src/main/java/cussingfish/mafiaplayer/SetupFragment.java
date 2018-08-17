package cussingfish.mafiaplayer;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import cussingfish.mafiaplayer.Model.StartResults;
import cussingfish.mafiaplayer.Roles.Bodyguard;
import cussingfish.mafiaplayer.Roles.Bomber;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.Roles.Lawyer;
import cussingfish.mafiaplayer.Roles.Mafioso;

public class SetupFragment extends Fragment {
    enum ROLES { MAFIOSO, DETECTIVE, DOUBLE_AGENT, BODYGUARD, BOMBER, LAWYER, OFFICIAL, CIVILIAN, SUSPECT }
    private EditText mafiosi;
    private EditText detectives;
    private CheckBox doubleAgent;
    private CheckBox bodyguard;
    private CheckBox bomber;
    private CheckBox lawyer;
    private CheckBox official;
    private EditText civilians;
    private EditText suspects;
    private TextView mafiosiText;
    private TextView detectiveText;
    private TextView doubleAgentText;
    private TextView bodyguardText;
    private TextView bomberText;
    private TextView lawyerText;
    private TextView officialText;
    private TextView civilianText;
    private TextView suspectText;
    private TextView description;
    private Button submit;
    private int numMafiosi;
    private int numDetectives;
    private int hasDoubleAgent;
    private int hasBodyguard;
    private int hasBomber;
    private int hasLawyer;
    private int numCivilians;
    private int numSuspects;
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
        bodyguard = view.findViewById(R.id.bodyguards);
        bomber = view.findViewById(R.id.bomber);
        lawyer = view.findViewById(R.id.lawyer);
        official = view.findViewById(R.id.official);
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
        suspects = view.findViewById(R.id.suspects);
        suspects.addTextChangedListener(new TextWatcher() {
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
        lawyerText = view.findViewById(R.id.lawyerText);
        officialText = view.findViewById(R.id.officialText);
        civilianText = view.findViewById(R.id.civilianText);
        suspectText = view.findViewById(R.id.suspectText);
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
                description.setText(R.string.detectives_desc);
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
        lawyerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.lawyer_desc);
            }
        });
        officialText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.official_desc);
            }
        });
        civilianText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.civilian_desc);
            }
        });
        suspectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.suspects_desc);
            }
        });
    }

    public int[] getNums() {
        int nums[] = new int[]{0,0,0,0,0,0,0,0,0};
        nums[ROLES.MAFIOSO.ordinal()] = numMafiosi;
        nums[ROLES.DETECTIVE.ordinal()] = numDetectives;
        if (doubleAgent.isChecked()) {
            nums[ROLES.DOUBLE_AGENT.ordinal()] = 1;
        }
        if (bodyguard.isChecked()) {
            nums[ROLES.BODYGUARD.ordinal()] = 1;
        }
        if (bomber.isChecked()) {
            nums[ROLES.BOMBER.ordinal()] = 1;
        }
        if (lawyer.isChecked()) {
            nums[ROLES.LAWYER.ordinal()] = 1;
        }
        if (official.isChecked()) {
            nums[ROLES.OFFICIAL.ordinal()] = 1;
        }
        nums[ROLES.CIVILIAN.ordinal()] = numCivilians;
        nums[ROLES.SUSPECT.ordinal()] = numSuspects;
        return nums;
    }

    public class SetupTask extends AsyncTask<int[], String, StartResults> {
        @Override
        protected StartResults doInBackground(int[]... r) {
            ServerProxy.get().setup(r[0]);
            try {
                StartResults s = new StartResults();
                while (s.getNull()) {
                    Thread.sleep(1000);
                    s = ServerProxy.get().getRole(username);
                }
                return s;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(StartResults s) {
            createCharacter(username, s);
            getActivity().onBackPressed();
        }
        private void createCharacter(String name, StartResults s) {
            switch (s.getRole()) {
                case "mafioso":
                    Mafioso.instantiate(name, s); break;
                case "detective":
                    Detective.instantiate(name, s); break;
                case "double agent":
                    DoubleAgent.instantiate(name, s); break;
                case "bodyguard":
                    Bodyguard.instantiate(name, s); break;
                case "bomber":
                    Bomber.instantiate(name, s); break;
                case "lawyer":
                    Lawyer.instantiate(name, s); break;
                default:
                    Civilian.instantiate(name, s); break;
            }
        }
    }
}
