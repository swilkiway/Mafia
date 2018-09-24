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
import cussingfish.mafiaplayer.Roles.Blackmailer;
import cussingfish.mafiaplayer.Roles.Bodyguard;
import cussingfish.mafiaplayer.Roles.Bomber;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.Roles.Lawyer;
import cussingfish.mafiaplayer.Roles.HitMan;
import cussingfish.mafiaplayer.Roles.Matchmaker;
import cussingfish.mafiaplayer.Roles.Official;
import cussingfish.mafiaplayer.Roles.Poisoner;

public class SetupFragment extends Fragment {
    enum ROLES { HIT_MAN, DETECTIVE, DOUBLE_AGENT, BODYGUARD, BOMBER, LAWYER, OFFICIAL, MATCHMAKER,
        TOWN_GOSSIP, BLACKMAILER, GODFATHER, POISONER, DIPLOMAT, HERMIT, CIVILIAN, SUSPECT }
    private EditText hitMen;
    private EditText detectives;
    private CheckBox doubleAgent;
    private CheckBox bodyguard;
    private CheckBox bomber;
    private CheckBox lawyer;
    private CheckBox official;
    private CheckBox matchmaker;
    private CheckBox gossip;
    private CheckBox blackmailer;
    private CheckBox godfather;
    private CheckBox poisoner;
    private CheckBox diplomat;
    private CheckBox hermit;
    private EditText civilians;
    private EditText suspects;
    private TextView hitMenText;
    private TextView detectiveText;
    private TextView doubleAgentText;
    private TextView bodyguardText;
    private TextView bomberText;
    private TextView lawyerText;
    private TextView officialText;
    private TextView matchmakerText;
    private TextView gossipText;
    private TextView civilianText;
    private TextView suspectText;
    private TextView blackmailerText;
    private TextView godfatherText;
    private TextView poisonerText;
    private TextView diplomatText;
    private TextView hermitText;
    private TextView description;
    private Button submit;
    private int numHitMen;
    private int numDetectives;
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
        hitMen = view.findViewById(R.id.hitMen);
        hitMen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String num = s.toString();
                if (num.equals("")) {
                    numHitMen = 0;
                } else {
                    numHitMen = Integer.parseInt(s.toString());
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
        matchmaker = view.findViewById(R.id.matchmaker);
        gossip = view.findViewById(R.id.gossip);
        blackmailer = view.findViewById(R.id.blackmailer);
        godfather = view.findViewById(R.id.godfather);
        poisoner = view.findViewById(R.id.poisoner);
        diplomat = view.findViewById(R.id.diplomat);
        hermit = view.findViewById(R.id.hermit);
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
                    numSuspects = 0;
                } else {
                    numSuspects = Integer.parseInt(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setTextViewsClick(view);
        submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setEnabled(false);
                int roles[] = getNums();
                SetupTask s = new SetupTask();
                s.execute(roles);
            }
        });
    }

    protected void setTextViewsClick(View view) {
        hitMenText = view.findViewById(R.id.hitMenText);
        detectiveText = view.findViewById(R.id.detectiveText);
        doubleAgentText = view.findViewById(R.id.doubleAgentText);
        bodyguardText = view.findViewById(R.id.bodyguardText);
        bomberText = view.findViewById(R.id.bomberText);
        lawyerText = view.findViewById(R.id.lawyerText);
        officialText = view.findViewById(R.id.officialText);
        matchmakerText = view.findViewById(R.id.matchmakerText);
        gossipText = view.findViewById(R.id.gossipText);
        civilianText = view.findViewById(R.id.civilianText);
        suspectText = view.findViewById(R.id.suspectText);
        blackmailerText = view.findViewById(R.id.blackmailerText);
        godfatherText = view.findViewById(R.id.godfatherText);
        poisonerText = view.findViewById(R.id.poisonerText);
        diplomatText = view.findViewById(R.id.diplomatText);
        hermitText = view.findViewById(R.id.hermitText);
        description = view.findViewById(R.id.description);
        hitMenText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.hit_men_desc);
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
        matchmakerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.matchmaker_desc);
            }
        });
        gossipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.gossip_desc);
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
        blackmailerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.blackmailer_desc);
            }
        });
        godfatherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.godfather_desc);
            }
        });
        poisonerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.poisoner_desc);
            }
        });
        diplomatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.diplomat_desc);
            }
        });
        hermitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setText(R.string.hermit_desc);
            }
        });
    }

    public int[] getNums() {
        int nums[] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        nums[ROLES.HIT_MAN.ordinal()] = numHitMen;
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
        if (matchmaker.isChecked()) {
            nums[ROLES.MATCHMAKER.ordinal()] = 1;
        }
        if (gossip.isChecked()) {
            nums[ROLES.TOWN_GOSSIP.ordinal()] = 1;
        }
        if (blackmailer.isChecked()) {
            nums[ROLES.BLACKMAILER.ordinal()] = 1;
        }
        if (godfather.isChecked()) {
            nums[ROLES.GODFATHER.ordinal()] = 1;
        }
        if (poisoner.isChecked()) {
            nums[ROLES.POISONER.ordinal()] = 1;
        }
        if (diplomat.isChecked()) {
            nums[ROLES.DIPLOMAT.ordinal()] = 1;
        }
        if (hermit.isChecked()) {
            nums[ROLES.HERMIT.ordinal()] = 1;
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
            Utils.createCharacter(username, s);
            getActivity().onBackPressed();
        }
    }
}
