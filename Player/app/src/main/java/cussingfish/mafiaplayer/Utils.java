package cussingfish.mafiaplayer;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cussingfish.mafiaplayer.Model.DayResults;
import cussingfish.mafiaplayer.Model.NightResults;
import cussingfish.mafiaplayer.Model.Player;
import cussingfish.mafiaplayer.Model.StartResults;
import cussingfish.mafiaplayer.Roles.Blackmailer;
import cussingfish.mafiaplayer.Roles.Bodyguard;
import cussingfish.mafiaplayer.Roles.Bomber;
import cussingfish.mafiaplayer.Roles.Civilian;
import cussingfish.mafiaplayer.Roles.Detective;
import cussingfish.mafiaplayer.Roles.DoubleAgent;
import cussingfish.mafiaplayer.Roles.HitMan;
import cussingfish.mafiaplayer.Roles.Lawyer;
import cussingfish.mafiaplayer.Roles.Matchmaker;
import cussingfish.mafiaplayer.Roles.Official;
import cussingfish.mafiaplayer.Roles.Poisoner;

public class Utils {
    public static String getVotingResults(Context context, DayResults d) {
        StringBuilder results = new StringBuilder();
        Player lynched = d.getLynched();
        String defended = d.getDefended();
        Player bomberKilled = d.getBombed();
        String diplomat = d.getDiplomat();
        boolean isTied = d.getTied();
        Player lover = d.getLover();
        if (isTied) {
            results.append(context.getString(R.string.tied));
        } else if (lynched != null) {
            results.append(context.getString(R.string.lynched, d.getLynched().getName(), d.getLynched().getRole()));
        } else if (defended != null) {
            results.append(context.getString(R.string.lawyer_defended, d.getDefended()));
        }
        if (bomberKilled != null) {
            results.append(context.getString(R.string.bomber_killed, bomberKilled.getName(), bomberKilled.getRole()));
        }
        if (lover != null) {
            results.append(context.getString(R.string.lover_died, lover.getName(), lover.getRole()));
        }
        if (diplomat != null) {
            results.append(context.getString(R.string.diplomat_lynched, diplomat));
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
        Player poisoned = n.getPoisoned();
        String hermit = n.getHermit();
        String silenced = n.getSilenced();
        StringBuilder results = new StringBuilder();
        if (mafiaKilled != null) {
            results.append(context.getString(R.string.mafia_killed, mafiaKilled.getName(), mafiaKilled.getRole()));
        }
        if (daKilled != null) {
            results.append(context.getString(R.string.da_killed, daKilled.getName(), daKilled.getRole()));
        }
        if (bomberKilled != null) {
            results.append(context.getString(R.string.bomber_killed, bomberKilled.getName(), bomberKilled.getRole()));
        }
        if (poisoned != null) {
            results.append(context.getString(R.string.poisoner_killed, poisoned.getName(), poisoned.getRole()));
        }
        if (lover != null) {
            results.append(context.getString(R.string.lover_died, lover.getName(), lover.getRole()));
        }
        if (bodyguardSaved != null) {
            results.append(context.getString(R.string.bodyguard_saved, bodyguardSaved));
        }
        if (daSaved != null) {
            results.append(context.getString(R.string.da_saved, daSaved));
        }
        if (hermit != null) {
            results.append(context.getString(R.string.hermit_targeted, hermit));
        }
        if (silenced != null) {
            results.append(context.getString(R.string.silenced, silenced));
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

    public static void createCharacter(String name, StartResults s) {
        switch (s.getRole()) {
            case "godfather":
            case "hit man":
                HitMan.instantiate(name, s); break;
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
            case "matchmaker":
                Matchmaker.instantiate(name, s); break;
            case "blackmailer":
                Blackmailer.instantiate(name, s); break;
            case "poisoner":
                Poisoner.instantiate(name, s); break;
            default:
                Civilian.instantiate(name, s); break;
        }
    }

    //works for all night fragments (except the SleepFragment)
    public static PlayerAdapter setUpViews(Context context, View view, String goal) {
        RecyclerView playerList = view.findViewById(R.id.playerList);
        RecyclerView.LayoutManager playerManager = new LinearLayoutManager(context);
        playerList.setLayoutManager(playerManager);
        TextView dayResults = view.findViewById(R.id.dayResults);
        PlayerAdapter playerAdapter;
        if (Civilian.getDayResults() != null) {
            playerAdapter = new PlayerAdapter(context, Civilian.getDayResults().getAlive());
            dayResults.setText(Utils.getVotingResults(context, Civilian.getDayResults()));
            setUpVotesView(context, view);
        } else {
            playerAdapter = new PlayerAdapter(context, Civilian.getStartResults().getAlive());
            dayResults.setText(goal);
        }
        playerList.setAdapter(playerAdapter);
        return playerAdapter;
    }

    //keep in a separate function for the SleepFragment
    public static void setUpVotesView(Context context, View view) {
        RecyclerView voteList = view.findViewById(R.id.voteList);
        RecyclerView.LayoutManager voteManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        voteList.setLayoutManager(voteManager);
        VoteAdapter voteAdapter = new VoteAdapter(context, Civilian.getDayResults().getBallot().getCandidates());
        voteList.setAdapter(voteAdapter);
    }
}
