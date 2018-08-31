package cussingfish.mafiaplayer;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cussingfish.mafiaplayer.Night.BlackmailerFragment;
import cussingfish.mafiaplayer.Night.BodyguardFragment;
import cussingfish.mafiaplayer.Night.BomberFragment;
import cussingfish.mafiaplayer.Night.DetectiveFragment;
import cussingfish.mafiaplayer.Night.DoubleAgentFragment;
import cussingfish.mafiaplayer.Night.HitManFragment;
import cussingfish.mafiaplayer.Night.LawyerFragment;
import cussingfish.mafiaplayer.Night.MatchmakerFragment;
import cussingfish.mafiaplayer.Night.OfficialFragment;
import cussingfish.mafiaplayer.Night.PoisonerFragment;
import cussingfish.mafiaplayer.Night.SleepFragment;
import cussingfish.mafiaplayer.Roles.Civilian;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Fragment fragment;
        switch (Civilian.getStartResults().getRole()) {
            case "godfather":
            case "hit man":
                fragment = new HitManFragment(); break;
            case "detective":
                fragment = new DetectiveFragment(); break;
            case "double agent":
                fragment = new DoubleAgentFragment(); break;
            case "bodyguard":
                fragment = new BodyguardFragment(); break;
            case "bomber":
                fragment = new BomberFragment(); break;
            case "lawyer":
                fragment = new LawyerFragment(); break;
            case "official":
                fragment = new OfficialFragment(); break;
            case "matchmaker":
                fragment = new MatchmakerFragment(); break;
            case "blackmailer":
                fragment = new BlackmailerFragment(); break;
            case "poisoner":
                fragment = new PoisonerFragment(); break;
            default:
                fragment = new SleepFragment(); break;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    public class LeaveTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... s) {
            ServerProxy.get().leaveGame(s[0]);
            return null;
        }
    }
}
