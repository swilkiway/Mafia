package cussingfish.mafiaplayer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cussingfish.mafiaplayer.Night.BodyguardFragment;
import cussingfish.mafiaplayer.Night.BomberFragment;
import cussingfish.mafiaplayer.Night.DetectiveFragment;
import cussingfish.mafiaplayer.Night.DoubleAgentFragment;
import cussingfish.mafiaplayer.Night.LawyerFragment;
import cussingfish.mafiaplayer.Night.MafiosiFragment;
import cussingfish.mafiaplayer.Night.MatchmakerFragment;
import cussingfish.mafiaplayer.Night.OfficialFragment;
import cussingfish.mafiaplayer.Night.SleepFragment;
import cussingfish.mafiaplayer.Roles.Bodyguard;
import cussingfish.mafiaplayer.Roles.Bomber;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.Roles.Lawyer;
import cussingfish.mafiaplayer.Roles.Mafioso;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Fragment fragment;
        switch (Civilian.getStartResults().getRole()) {
            case "mafioso":
                fragment = new MafiosiFragment(); break;
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
            default:
                fragment = new SleepFragment(); break;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            LeaveTask l = new LeaveTask();
            l.execute(Civilian.getUserName());
        }
    }

    public class LeaveTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... s) {
            ServerProxy.get().leaveGame(s[0]);
            return null;
        }
    }
}
