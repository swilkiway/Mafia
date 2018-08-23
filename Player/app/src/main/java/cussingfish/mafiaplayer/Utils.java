package cussingfish.mafiaplayer;

import android.content.Context;

import cussingfish.mafiaplayer.Model.DayResults;
import cussingfish.mafiaplayer.Model.NightResults;
import cussingfish.mafiaplayer.Model.Player;
import cussingfish.mafiaplayer.Roles.Civilian;

public class Utils {
    public static String getVotingResults(Context context, DayResults d) {
        StringBuilder results = new StringBuilder();
        Player lynched = d.getLynched();
        String defended = d.getDefended();
        Player bomberKilled = d.getBombed();
        boolean isTied = d.getTied();
        Player lover = d.getLover();
        if (isTied) {
            results.append(context.getString(R.string.tied));
        } else if (lynched != null) {
            results.append(context.getString(R.string.lynched, d.getLynched().getName(), d.getLynched().getRole()));
            if (lynched.getName().equals(Civilian.getUserName())) {
                Civilian.kill();
            }
        } else if (defended != null) {
            results.append(context.getString(R.string.lawyer_defended, d.getDefended()));
        }
        if (bomberKilled != null) {
            results.append(context.getString(R.string.bomber_killed, bomberKilled.getName(), bomberKilled.getRole()));
            if (bomberKilled.getName().equals(Civilian.getUserName())) {
                Civilian.kill();
            }
        }
        if (lover != null) {
            results.append(context.getString(R.string.lover_died, lover.getName(), lover.getRole()));
            if (lover.getName().equals(Civilian.getUserName())) {
                Civilian.kill();
            }
        }
        return results.toString();
    }

    public static String getNightResults(Context context, NightResults n) {
        Player mafiaKilled = n.getMafiaKilled();
        String bodyguardSaved = n.getBodyguardSaved();
        Player daKilled = n.getDaKilled();
        String daSaved = n.getDaSaved();
        Player bomberKilled = n.getBomberKilled();
        Player lover = n.getLover();
        StringBuilder results = new StringBuilder();
        if (mafiaKilled != null) {
            results.append(context.getString(R.string.mafia_killed, mafiaKilled.getName(), mafiaKilled.getRole()));
            if (mafiaKilled.getName().equals(Civilian.getUserName())) {
                Civilian.kill();
            }
        }
        if (bodyguardSaved != null) {
            results.append(context.getString(R.string.bodyguard_saved, bodyguardSaved));
        }
        if (daKilled != null) {
            results.append(context.getString(R.string.da_killed, daKilled.getName(), daKilled.getRole()));
            if (daKilled.getName().equals(Civilian.getUserName())) {
                Civilian.kill();
            }
        }
        if (daSaved != null) {
            results.append(context.getString(R.string.da_saved, daSaved));
        }
        if (bomberKilled != null) {
            results.append(context.getString(R.string.bomber_killed, bomberKilled.getName(), bomberKilled.getRole()));
            if (bomberKilled.getName().equals(Civilian.getUserName())) {
                Civilian.kill();
            }
        }
        if (lover != null) {
            results.append(context.getString(R.string.lover_died, lover.getName(), lover.getRole()));
            if (lover.getName().equals(Civilian.getUserName())) {
                Civilian.kill();
            }
        }
        return results.toString();
    }

    public static String getTeammates(Context context, String team[]) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.mafia_team));
        for (String t : team) {
            sb.append(t);
            sb.append("\n");
        }
        return sb.toString();
    }
}
