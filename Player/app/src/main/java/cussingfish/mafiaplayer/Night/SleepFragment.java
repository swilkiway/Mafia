package cussingfish.mafiaplayer.Night;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cussingfish.mafiaplayer.DayFragment;
import cussingfish.mafiaplayer.NightResults;
import cussingfish.mafiaplayer.R;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.ServerProxy;
import cussingfish.mafiaplayer.Utils;

public class SleepFragment extends Fragment {

    private TextView dayResults;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        dayResults = view.findViewById(R.id.dayResults);
        dayResults.setText(Utils.getVotingResults(getContext(), Civilian.get().dayResults));
        SleepTask s = new SleepTask();
        s.execute();
    }

    public class SleepTask extends AsyncTask<String, NightResults, NightResults> {
        @Override
        protected NightResults doInBackground(String... r) {
            try {
                NightResults n = null;
                while (n == null) {
                    Thread.sleep(1000);
                    n = ServerProxy.get().nightResult();
                }
                Civilian.get().nightResults = n;
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(NightResults s) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            DayFragment fragment = new DayFragment();
            ft.replace(R.id.fragmentContainer, fragment).addToBackStack(null);
            ft.commit();
        }
    }
}
