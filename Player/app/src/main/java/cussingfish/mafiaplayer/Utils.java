package cussingfish.mafiaplayer;

import android.content.Context;

import cussingfish.mafiaplayer.Roles.Civilian;

public class Utils {
    public static String getVotingResults(Context context, DayResults d) {
        String lynched = context.getString(R.string.lynched, d.getLynched().getName(), d.getLynched().getRole());
        if (lynched.equals(Civilian.get().userName)) {
            Civilian.get().kill();
        }
        StringBuilder votes = new StringBuilder();
        for (Vote v : d.getBallot()) {
            votes.append(context.getString(R.string.voted, v.getVoter(), v.getNominated()));
        }
        return lynched + votes.toString();
    }
    public static String getNightResults(Context context, NightResults n) {
        Player mafiaKilled = n.getMafiaKilled();
        String bodyguardSaved = n.getBodyguardSaved();
        Player daKilled = n.getDaKilled();
        String daSaved = n.getDaSaved();
        Player bomberKilled = n.getBomberKilled();
        StringBuilder results = new StringBuilder();
        if (mafiaKilled != null) {
            results.append(context.getString(R.string.mafia_killed, mafiaKilled.getName(), mafiaKilled.getRole()));
            if (mafiaKilled.getName().equals(Civilian.get().userName)) {
                Civilian.get().kill();
            }
        }
        if (bodyguardSaved != null) {
            results.append(context.getString(R.string.bodyguard_saved, bodyguardSaved));
        }
        if (daKilled != null) {
            results.append(context.getString(R.string.da_killed, daKilled.getName(), daKilled.getRole()));
            if (daKilled.getName().equals(Civilian.get().userName)) {
                Civilian.get().kill();
            }
        }
        if (daSaved != null) {
            results.append(context.getString(R.string.da_saved, daSaved));
        }
        if (bomberKilled != null) {
            results.append(context.getString(R.string.da_killed, bomberKilled.getName(), bomberKilled.getRole()));
            if (bomberKilled.getName().equals(Civilian.get().userName)) {
                Civilian.get().kill();
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