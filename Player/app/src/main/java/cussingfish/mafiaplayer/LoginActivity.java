package cussingfish.mafiaplayer;

import android.content.Intent;
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

import cussingfish.mafiaplayer.Roles.Bodyguard;
import cussingfish.mafiaplayer.Roles.Bomber;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.Roles.Mafioso;


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
    public class RegisterTask extends AsyncTask<String, String, String[]> {
        @Override
        protected String[] doInBackground(String... s) {
            String name = username;
            ServerProxy.get().setHostIP(hostIP);
            ServerProxy.get().register(username);
            if (!setupPlayer) {
                try {
                    String role[] = null;
                    while (role == null) {
                        Thread.sleep(1000);
                        role = ServerProxy.get().getRole(username);
                    }
                    return role;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s[]) {
            if (!setupPlayer) {
                createCharacter(username, s);
                Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                startActivity(intent);
            }
        }
        private void createCharacter(String name, String role[]) {
            switch (role[0]) {
                case "mafioso":
                    Mafioso.set(name, getTeammates(role)); break;
                case "detective":
                    Detective.set(name); break;
                case "double agent":
                    DoubleAgent.set(name); break;
                case "bodyguard":
                    Bodyguard.set(name); break;
                case "bomber":
                    Bomber.set(name); break;
                default:
                    Civilian.set(name); break;
            }
        }
        private String[] getTeammates(String role[]) {
            String team[] = new String[role.length - 1];
            System.arraycopy(role, 1, team, 0, role.length - 1);
            return team;
        }
    }
}
