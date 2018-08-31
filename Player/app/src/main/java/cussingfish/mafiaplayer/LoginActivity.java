package cussingfish.mafiaplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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


public class LoginActivity extends AppCompatActivity {

    private boolean setupPlayer = false;
    private CheckBox setup;
    private Button submit;
    private EditText enterUsername;
    private EditText enterHost;
    private TextView waitingText;
    private LinearLayout setupView;
    private String username = "";
    private String hostIP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        waitingText = findViewById(R.id.waitingText);
        enterUsername = findViewById(R.id.enterUsername);
        String storedUsername = sharedPref.getString("storedUsername", "");
        enterUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = s.toString();
                if (username.equals("") || hostIP.equals("")) {
                    submit.setEnabled(false);
                } else {
                    submit.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        enterHost = findViewById(R.id.enterHost);
        String storedHost = sharedPref.getString("storedHost", "");
        enterHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hostIP = s.toString();
                if (username.equals("") || hostIP.equals("")) {
                    submit.setEnabled(false);
                } else {
                    submit.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setup = findViewById(R.id.setup);
        setupView = findViewById(R.id.setupView);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.GONE);
                enterUsername.setVisibility(View.GONE);
                enterHost.setVisibility(View.GONE);
                setupView.setVisibility(View.GONE);
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.putString("storedUsername", username);
                editor.putString("storedHost", hostIP);
                editor.apply();
                if (!setup.isChecked()) {
                    waitingText.setVisibility(View.VISIBLE);
                }
                RegisterTask r = new RegisterTask();
                r.execute(username);
                if (setup.isChecked()) {
                    setupPlayer = true;
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("hostIP", hostIP);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    SetupFragment fragment = new SetupFragment();
                    fragment.setArguments(bundle);
                    ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
                    ft.commit();
                }
            }
        });
        if (!storedUsername.equals("") && !storedHost.equals("")) {
            enterUsername.setText(storedUsername);
            enterHost.setText(storedHost);
            submit.setEnabled(true);
        }
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
            Intent intent = new Intent(LoginActivity.this, GameActivity.class);
            startActivity(intent);
        }
    }
    public class RegisterTask extends AsyncTask<String, String, StartResults> {
        @Override
        protected StartResults doInBackground(String... s) {
            String name = username;
            ServerProxy.get().setBaseUrl(hostIP);
            ServerProxy.get().register(username);
            if (!setupPlayer) {
                try {
                    StartResults r = new StartResults();
                    while (r.getNull()) {
                        Thread.sleep(1000);
                        r = ServerProxy.get().getRole(username);
                    }
                    return r;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(StartResults s) {
            if (!setupPlayer) {
                createCharacter(username, s);
                Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                startActivity(intent);
            }
        }
        private void createCharacter(String name, StartResults s) {
            switch (s.getRole()) {
                case "godfather":
                case "hit man":
                    HitMan.instantiate(name, s); break;
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
                case "official":
                    Official.instantiate(name, s); break;
                case "matchmaker":
                    Matchmaker.instantiate(name, s); break;
                case "blackmailer":
                    Blackmailer.instantiate(name, s); break;
                case "poisoner":
                    Poisoner.instantiate(name, s); break;
                default:
                    Civilian.instantiate(name, s); break;
            }
        }
    }
}
