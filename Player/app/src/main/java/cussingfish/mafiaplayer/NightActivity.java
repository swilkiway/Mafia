package cussingfish.mafiaplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cussingfish.mafiaplayer.Roles.Bomber;
import cussingfish.mafiaplayer.Roles.Mafioso;
import cussingfish.mafiaplayer.Roles.Player;

public class NightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);
        Fragment fragment = new SetupFragment();
        if (Player.getPlayer() instanceof Mafioso) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragmentContainer, fragment);
            ft.commit();
        }
        //TODO: consider changing this to GameActivity, and using day and night fragments for simplicity
    }
}
