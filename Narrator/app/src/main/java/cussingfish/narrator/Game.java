package cussingfish.narrator;

import java.util.ArrayList;
import java.util.Random;

import cussingfish.narrator.Model.Ballot;
import cussingfish.narrator.Model.DayResults;
import cussingfish.narrator.Model.NightResults;
import cussingfish.narrator.Model.Player;
import cussingfish.narrator.Model.Vote;

public class Game {
    /* other possible roles: official (two votes to a player), matchmaker (chooses two players
    to be in love), journalist (if killed, roles are no longer announced when others die), suspect
    (appears to be mafia if investigated by detectives) */
    enum ROLES { MAFIOSO, DETECTIVE, DOUBLE_AGENT, BODYGUARD, BOMBER, LAWYER, OFFICIAL, CIVILIAN, SUSPECT }
    enum STATUS { NO_WIN, CIVILIAN_WIN, MAFIA_WIN }
    enum JOURNALIST { DISABLED, ALIVE, DEAD }
    private final String W = "waiting";
    private final String D = "disabled"; //or dead
    private STATUS status;
    private JOURNALIST journalist;
    private boolean rolesReady;
    private ArrayList<String> roles;
    private int civilianSize;
    private ArrayList<Player> alive;
    private ArrayList<Player> dead;
    private ArrayList<String> mafia;
    private int mafiaVotes;
    private NightResults nightResults;
    private DayResults dayResults;
    private Ballot ballot;
    private String mafiaKilled = null;
    private String doubleAgentKilled = D;
    private String bomberKilled = D;
    private String bodyguardSaved = D;
    private String doubleAgentSaved = D;
    private String defended = D;
    private String enfranchised = D;
    private String silenced = D;

    private static Game game = null;

    private Game() {
        rolesReady = false;
        status = STATUS.NO_WIN;
        journalist = JOURNALIST.DISABLED;
        civilianSize = 0;
        mafiaVotes = 0;
        roles = new ArrayList<>();
        alive = new ArrayList<>();
        dead = new ArrayList<>();
        mafia = new ArrayList<>();
        nightResults = new NightResults();
        dayResults = new DayResults();
        ballot = new Ballot();
    }

    public static Game getGame() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public void addPlayer(String name) {
        Player p = new Player(name);
        alive.add(p);
    }

    public void setupRoles(int[] rolesList) {
        System.out.println("In setupGame");
        for (int i = 0; i < rolesList[ROLES.MAFIOSO.ordinal()]; i++) {
            roles.add("mafioso");
        }
        civilianSize = rolesList[ROLES.DETECTIVE.ordinal()];
        for (int i = 0; i < rolesList[ROLES.DETECTIVE.ordinal()]; i++) {
            roles.add("detective");
        }
        if (rolesList[ROLES.DOUBLE_AGENT.ordinal()] == 1) {
            civilianSize++;
            roles.add("double agent");
            doubleAgentSaved = W;
            doubleAgentKilled = W;
        }
        if (rolesList[ROLES.BODYGUARD.ordinal()] == 1) {
            civilianSize++;
            roles.add("bodyguard");
            bodyguardSaved = W;
        }
        if (rolesList[ROLES.BOMBER.ordinal()] == 1) {
            civilianSize++;
            roles.add("bomber");
            bomberKilled = W;
        }
        if (rolesList[ROLES.LAWYER.ordinal()] == 1) {
            civilianSize++;
            roles.add("lawyer");
            defended = W;
        }
        if (rolesList[ROLES.OFFICIAL.ordinal()] == 1) {
            civilianSize++;
            roles.add("official");
            enfranchised = W;
        }
        civilianSize += rolesList[ROLES.CIVILIAN.ordinal()];
        for (int i = 0; i < rolesList[ROLES.CIVILIAN.ordinal()]; i++) {
            roles.add("civilian");
        }
        civilianSize += rolesList[ROLES.SUSPECT.ordinal()];
        for (int i = 0; i < rolesList[ROLES.SUSPECT.ordinal()]; i++) {
            roles.add("suspect");
        }
        for (int i = 0; i < 100; i++) {
            Random rand = new Random(System.currentTimeMillis());
            int index = rand.nextInt(roles.size());
            String tmp = roles.get(index);
            roles.set(index, roles.get(0));
            roles.set(0, tmp);
        }
        for (int i = 0; i < roles.size(); i++) {
            alive.get(i).setRole(roles.get(i));
        }
        for (Player p : alive) {
            if (p.getRole().equals("mafioso")) {
                mafia.add(p.getName());
            }
        }
        rolesReady = true;
    }

    public String[] getRole(String player) {
        if (!rolesReady) { return null; }
        //used to store names of teammates if any are known
        ArrayList<String> role = new ArrayList<>();
        for (Player p : alive) {
            if (p.getName().equals(player)) {
                role.add(p.getRole());
                if (p.getRole().equals("mafioso")) {
                    role.addAll(mafia);
                }
                return role.toArray(new String[role.size()]);
            }
        }
        return null;
    }

    private boolean everyoneDone() {
        return ((mafia.size() == mafiaVotes) && !doubleAgentKilled.equals(W) && !bodyguardSaved.equals(W) &&
                !doubleAgentSaved.equals(W) && !bomberKilled.equals(W) && !defended.equals(W) &&
                !enfranchised.equals(W));
    }

    public void mafiaKillPlayer(String victim) {
        if (mafiaVotes == 0) {
            mafiaKilled = victim;
        } else {
            Random rand = new Random(System.currentTimeMillis());
            int tmp = rand.nextInt(2);
            if (tmp == 0) {
                mafiaKilled = victim;
            }
        }
        mafiaVotes++;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    public void daKillPlayer(String victim) {
        doubleAgentKilled = victim;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    public void bomberKillPlayer(String victim) {
        bomberKilled = victim;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    public String getMafiaKilled() {
        if (mafia.size() == mafiaVotes) {
            return mafiaKilled;
        }
        return null;
    }

    public void bodyguardSavePlayer(String saved) {
        bodyguardSaved = saved;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    public void lawyerDefendPlayer(String defendant) {
        defended = defendant;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    public void officialEnfranchisePlayer(String voter) {
        enfranchised = voter;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    public void blackmailerSilencePlayer(String quiet) {
        silenced = quiet;
//        if (everyoneDone()) {
//            resolveNight();
//        }
    }

    public void daSavePlayer(String saved) {
        doubleAgentSaved = saved;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    private void resolveNight() {
        if (!mafiaKilled.equals(bodyguardSaved) && !mafiaKilled.equals(doubleAgentSaved)) {
            Player p = findPlayer(mafiaKilled);
            nightResults.setMafiaKilled(p);
            removePlayer(p);
            if (p.getRole().equals("bomber")) {
                Player dead = findPlayer(bomberKilled);
                removePlayer(dead);
                nightResults.setBombed(dead);
                bomberKilled = null;
            } else {
                nightResults.setBombed(null);
            }
        } else {
            nightResults.setBodyguardSaved(mafiaKilled);
        }
        if (!doubleAgentKilled.equals("pass")) {
            if (!doubleAgentKilled.equals(bodyguardSaved)) {
                Player p = findPlayer(doubleAgentKilled);
                nightResults.setDaKilled(p);
                removePlayer(p);
                if (p.getRole().equals("bomber")) {
                    Player dead = findPlayer(bomberKilled);
                    removePlayer(dead);
                    nightResults.setBombed(dead);
                    bomberKilled = null;
                } else {
                    nightResults.setBombed(null);
                }
            } else {
                nightResults.setDaSaved(doubleAgentKilled);
            }
        }
        nightResults.setSilenced(silenced);
        nightResults.setStatus(status.ordinal());
        resetForNextNight();
    }

    private Player findPlayer(String name) {
        for (Player p : alive) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        for (Player p : dead) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    private void removePlayer(Player victim) {
        for (Player p : dead) {
            if (p.equals(victim)) {
                return;
            }
        }
        for (Player p : alive) {
            if (p.equals(victim)) {
                dead.add(p);
                alive.remove(p);
            }
        }
        if (victim.getRole().equals("mafioso")) {
            mafia.remove(victim.getName());
        } else {
            civilianSize--;
            disableRole(victim.getRole());
        }
        if (mafia.isEmpty()) {
            status = STATUS.CIVILIAN_WIN;
        } else if (civilianSize < mafia.size()) {
            status = STATUS.MAFIA_WIN;
        }
    }

    private void disableRole(String role) {
        switch (role) {
            case "bodyguard": bodyguardSaved = D; break;
            case "double agent": doubleAgentKilled = D; doubleAgentSaved = D; break;
            case "bomber": bomberKilled = D; break;
            case "lawyer": defended = D; break;
            case "official": enfranchised = D; break;
        }
    }

    private void resetForNextNight() {
        mafiaKilled = null;
        mafiaVotes = 0;
        if (!doubleAgentKilled.equals(D)) {
            doubleAgentKilled = W;
        }
        if (!bodyguardSaved.equals(D)) {
            bodyguardSaved = W;
        }
        if (!doubleAgentSaved.equals(D)) {
            doubleAgentSaved = W;
        }
        if (!bomberKilled.equals(D)) {
            bomberKilled = W;
        }
        if (!defended.equals(D)) {
            defended = W;
        }
        if (!enfranchised.equals(D)) {
            enfranchised = W;
        }
    }

    public NightResults getNightResults() {
        nightResults.setAlive(alive);
        return nightResults;
    }

    public String investigatePlayer(String guess) {
        for (Player p : alive) {
            if (p.getName().equals(guess)) {
                if (p.getRole().equals("mafioso") || p.getRole().equals("suspect")) {
                    return guess + " is a member of the mafia";
                } else {
                    return guess + " is an innocent civilian";
                }
            }
        }
        for (Player p : dead) {
            if (p.getName().equals(guess)) {
                if (p.getRole().equals("mafioso")) {
                    return guess + " is a member of the mafia";
                } else {
                    return guess + " is an innocent civilian";
                }
            }
        }
        return null;
    }

    public void votePlayer(String vote[]) {
        ballot.addVote(vote[0],vote[1]);
        if (vote[1].equals(enfranchised)) {
            ballot.addVote(vote[0], vote[1]);
        }
        if (ballot.getTotalVotes() == alive.size()) {
            resolveVoting();
        }
    }

    private void resolveVoting() {
        dayResults.setBallot(ballot);
        boolean isTied = false;
        Vote lynched = null;
        for (Vote v : ballot.getVotes()) {
            if (lynched == null) {
                lynched = v;
            } else if (v.getVotes() > lynched.getVotes()) {
                lynched = v;
                isTied = false;
            } else if (v.getVotes() == lynched.getVotes()) {
                isTied = true;
            }
        }
        if (!isTied) {
            if (lynched.getNominated().equals(defended)) {
                dayResults.setDefended(defended);
                dayResults.setBombed(null);
            } else {
                Player p = findPlayer(lynched.getNominated());
                dayResults.setLynched(p);
                removePlayer(p);
                if (p.getRole().equals("bomber")) {
                    Player dead = findPlayer(bomberKilled);
                    removePlayer(dead);
                    dayResults.setBombed(dead);
                } else {
                    dayResults.setBombed(null);
                }
            }
        }
        dayResults.setStatus(status.ordinal());
        clearVotes();
    }

    public DayResults getVotingResult() {
        dayResults.setAlive(alive);
        return dayResults;
    }

    private void clearVotes() {
        ballot = new Ballot();
    }
}