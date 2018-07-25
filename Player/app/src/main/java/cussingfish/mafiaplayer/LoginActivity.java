package cussingfish.mafiaplayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    private Button setup;
    private Button wait;
    private TextView waitingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        waitingText = findViewById(R.id.waitingText);
        setup = findViewById(R.id.setup);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup.setVisibility(View.GONE);
                wait.setVisibility(View.GONE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SetupFragment fragment = new SetupFragment();
                ft.add(R.id.fragmentContainer, fragment);
                ft.commit();
            }
        });
        wait = findViewById(R.id.wait);
        wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup.setVisibility(View.GONE);
                wait.setVisibility(View.GONE);
                waitingText.setVisibility(View.VISIBLE);
            }
        });

    }
//    public class GuessTask extends AsyncTask<Guess, Guess, Guess> {
//        @Override
//        protected Guess doInBackground(Guess... gObj) {
//            ServerProxy.submitGuess(gObj[0]);
//            try {
//                boolean done = false;
//                while (!done) {
//                    Thread.sleep(1000);
//                    done = ServerProxy.testIfReady(GUESS_CODE).isReady();
//                }
//                return null;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Guess g) {
//            Intent intent = new Intent(GuessActivity.this, DisplayActivity.class);
//            startActivity(intent);
//        }
//    }
}
