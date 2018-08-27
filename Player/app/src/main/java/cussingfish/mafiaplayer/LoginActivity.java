package cussingfish.mafiaplayer;

import android.content.Intent;
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
import cussingfish.mafiaplayer.Roles.Official;


public class LoginActivity extends AppCompatActivity {

    private boolean setupPlayer = false;
    private Button setup;
    private Button wait;
    private EditText enterUsername;
    private EditText enterHost;
    private TextView waitingText;
    private String username = "";
    private String hostIP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        waitingText = findViewById(R.id.waitingText);
        enterUsername = findViewById(R.id.enterUsername);
        enterUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = s.toString();
                if (username.equals("") || hostIP.equals("")) {
                    setup.setEnabled(false);
                    wait.setEnabled(false);
                } else {
                    setup.setEnabled(true);
                    wait.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        enterHost = findViewById(R.id.enterHost);
        enterHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hostIP = s.toString();
                if (username.equals("") || hostIP.equals("")) {
                    setup.setEnabled(false);
                    wait.setEnabled(false);
                } else {
                    setup.setEnabled(true);
                    wait.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setup = findViewById(R.id.setup);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup.setVisibility(View.GONE);
                wait.setVisibility(View.GONE);
                enterUsername.setVisibility(View.GONE);
                enterHost.setVisibility(View.GONE);
                setupPlayer = true;
                RegisterTask r = new RegisterTask();
                r.execute(username);
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                bundle.putString("hostIP",hostIP);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SetupFragment fragment = new SetupFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
                ft.commit();
            }
        });
        wait = findViewById(R.id.wait);
        wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup.setVisibility(View.GONE);
                wait.setVisibility(View.GONE);
                enterUsername.setVisibility(View.GONE);
                enterHost.setVisibility(View.GONE);
                waitingText.setVisibility(View.VISIBLE);
                RegisterTask r = new RegisterTask();
                r.execute(username);
            }
        });
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
                case "official":
                    Official.instantiate(name, s); break;
                default:
                    Civilian.instantiate(name, s); break;
            }
        }
    }
}
