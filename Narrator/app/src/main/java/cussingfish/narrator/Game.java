package cussingfish.narrator;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    /* other possible roles: official (two votes to a player), matchmaker (chooses two players
    to be in love), journalist (if killed, roles are no longer announced when others die), suspect
    (appears to be mafia if investigated by detectives) */
    enum ROLES { MAFIOSO, DETECTIVE, DOUBLE_AGENT, BODYGUARD, BOMBER, CIVILIAN }
    enum STATUS { NO_WIN, CIVILIAN_WIN, MAFIA_WIN }
    enum JOURNALIST { DISABLED, ALIVE, DEAD }
    private STATUS status;
    private JOURNALIST journalist;
    private boolean rolesReady;
    private ArrayList<String> roles;
    private int civilianSize;
    private int mafiaSize;
    private ArrayList<Player> alive;
    private ArrayList<Player> dead;
    private ArrayList<String> mafia;
    private NightResults nightResults;
    private ArrayList<Vote> votes;
    private String mafiaKilled = null;
    private String doubleAgentKilled = null;
    private String bomberKilled = null;
    private String bodyguardSaved = null;
    private String doubleAgentSaved = null;
    private String defended = null;
    private String enfranchised = null;
    private String silenced = null;

    private static Game game = null;

    private Game() {
        rolesReady = false;
        status = STATUS.NO_WIN;
        civilianSize = 0;
        mafiaSize = 0;
        roles = new ArrayList<>();
        alive = new ArrayList<>();
        dead = new ArrayList<>();
        mafia = new ArrayList<>();
        nightResults = new NightResults();
        votes = new ArrayList<>();
    }

    public static Game getGame() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public int getStatus() {
        return status.ordinal();
    }

    public void addPlayer(String name) {
        Player p = new Player(name);
        alive.add(p);
    }

    public void setupRoles(int[] rolesList) {
        System.out.println("In setupGame");
        mafiaSize = rolesList[ROLES.MAFIOSO.ordinal()];
        for (int i = 0; i < rolesList[ROLES.MAFIOSO.ordinal()]; i++) {
            roles.add("mafioso");
        }
        civilianSize = rolesList[ROLES.DETECTIVE.ordinal()];
        for (int i = 0; i < rolesList[ROLES.DETECTIVE.ordinal()]; i++) {
            roles.add("detective");
        }
        civilianSize += rolesList[ROLES.DOUBLE_AGENT.ordinal()];
        for (int i = 0; i < rolesList[ROLES.DOUBLE_AGENT.ordinal()]; i++) {
            roles.add("double agent");
        }
        civilianSize += rolesList[ROLES.BODYGUARD.ordinal()];
        for (int i = 0; i < rolesList[ROLES.BODYGUARD.ordinal()]; i++) {
            roles.add("bodyguard");
        }
        civilianSize += rolesList[ROLES.BOMBER.ordinal()];
        for (int i = 0; i < rolesList[ROLES.BOMBER.ordinal()]; i++) {
            roles.add("bomber");
        }
        civilianSize += rolesList[ROLES.CIVILIAN.ordinal()];
        for (int i = 0; i < rolesList[ROLES.CIVILIAN.ordinal()]; i++) {
            roles.add("civilian");
            civilianSize++;
        }
        for (int i = 0; i < 100; i++) {
            Random rand = new Random();
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
        //used to store names of teammates if any are known
        ArrayList<String> role = new ArrayList<>();
        if (!rolesReady) { return null; }
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
        return (mafiaKilled != null && doubleAgentKilled != null && bodyguardSaved != null &&
                doubleAgentSaved != null);
    }

    public void mafiaKillPlayer(String victim) {
        if (mafiaKilled == null) {
            mafiaKilled = victim;
        } else {
            Random rand = new Random();
            int tmp = rand.nextInt(2);
            if (tmp == 0) {
                mafiaKilled = victim;
            }
        }
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
    }

    public String getMafiaKilled() {
        return mafiaKilled;
    }

    public void bodyguardSavePlayer(String saved) {
        if (bodyguardSaved == null) {
            bodyguardSaved = saved;
        } else {
            Random rand = new Random();
            int tmp = rand.nextInt(2);
            if (tmp == 0) {
                bodyguardSaved = saved;
            }
        }
        if (everyoneDone()) {
            resolveNight();
        }
    }

    public void lawyerDefendPlayer(String defendant) {
        defended = defendant;
//        if (everyoneDone()) {
//            resolveNight();
//        }
    }

    public void blackmailerSilencePlayer(String quiet) {
        silenced = quiet;
//        if (everyoneDone()) {
//            resolveNight();
//        }
    }

    public void officialEnfranchisePlayer(String voter) {
        enfranchised = voter;
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
            p.kill();
            removePlayer(p);
        } else {
            nightResults.setBodyguardSaved(mafiaKilled);
        }
        if (!doubleAgentKilled.equals("pass")) {
            if (!doubleAgentKilled.equals(bodyguardSaved)) {
                Player p = findPlayer(doubleAgentKilled);
                nightResults.setDaKilled(p);
                p.kill();
                removePlayer(p);
            } else {
                nightResults.setDaSaved(doubleAgentKilled);
            }
        }
        nightResults.setDefended(defended);
        nightResults.setEnfranchised(enfranchised);
        nightResults.setSilenced(silenced);
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
            mafiaSize--;
        } else {
            civilianSize--;
        }
        if (mafiaSize == 0) {
            status = STATUS.CIVILIAN_WIN;
        } else if (civilianSize < mafiaSize) {
            status = STATUS.MAFIA_WIN;
        }
    }

    private void resetForNextNight() {
        mafiaKilled = null;
        doubleAgentKilled = null;
        bodyguardSaved = null;
        doubleAgentSaved = null;
    }

    public NightResults getNightResults() {
        return nightResults;
    }

    public String guessPlayer(String guess) {
        if (everyoneDone()) {
            resolveNight();
        }
        for (Player p : alive) {
            if (p.getName().equals(guess)) {
                 if (p.getRole().equals("mafioso")) {
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
        Vote v;
        for (Player p : alive) {
            if (p.getName().equals(vote[1])) {
                v = new Vote(vote[0], p);
                votes.add(v);
            }
        }
        if (votes.size() == alive.size()) {
            resolveVoting();
        }
    }

    private void resolveVoting() {
        boolean isTied = false;
        Player lynched = null;
        for (Player p : alive) {
            if (lynched == null) {
                lynched = p;
            } else if (p.getVotes() > lynched.getVotes()) {
                lynched = p;
                isTied = false;
            } else if (p.getVotes() == lynched.getVotes()) {
                isTied = true;
            }
        }
        if (!isTied) {
            removePlayer(lynched);
            if (bomberKilled != null) {
                Player dead = findPlayer(bomberKilled);
                removePlayer(dead);
                votes.add(new Vote("BOOM!", dead));
                bomberKilled = null;
            }
            clearVotes();
        }
    }

    public Vote[] getVotingResult() {
        return votes.toArray(new Vote[votes.size()]);
    }

    private void clearVotes() {
        for (Player p : alive) {
            p.resetVotes();
        }
    }
}