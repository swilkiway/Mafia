package cussingfish.mafiaplayer;

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
import cussingfish.mafiaplayer.Night.SleepFragment;
import cussingfish.mafiaplayer.Roles.Bodyguard;
import cussingfish.mafiaplayer.Roles.Bomber;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.Roles.Lawyer;
import cussingfish.mafiaplayer.Roles.Mafioso;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Fragment fragment;
        if (Mafioso.get() != null) {
            fragment = new MafiosiFragment();
        } else if (Detective.get() != null) {
            fragment = new DetectiveFragment();
        } else if (DoubleAgent.get() != null) {
            fragment = new DoubleAgentFragment();
        } else if (Bodyguard.get() != null) {
            fragment = new BodyguardFragment();
        } else if (Bomber.get() != null) {
            fragment = new BomberFragment();
        } else if (Lawyer.get() != null) {
            fragment = new LawyerFragment();
        } else {
            fragment = new SleepFragment();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragmentContainer, fragment);
        ft.commit();
    }
}
