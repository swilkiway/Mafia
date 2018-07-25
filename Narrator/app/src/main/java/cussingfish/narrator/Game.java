package cussingfish.narrator;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    /* other possible roles: ballot box stuffer (two votes to a player), matchmaker (chooses two players
    to be in love), thief (role blocker), leper (infects those who interact with him) */
    enum ROLES { MAFIA, DETECTIVE, DOUBLE_AGENT, BODYGUARD, BOMBER, CIVILIAN }
    enum STATUS { NO_WIN, CIVILIAN_WIN, MAFIA_WIN }
    private STATUS status;
    private boolean rolesReady;
    private ArrayList<String> roles;
    private int civilianSize;
    private int mafiaSize;
    private ArrayList<Player> alive;
    private ArrayList<Player> dead;
    private Player nightResults[];
    private ArrayList<Vote> votes;
    private String mafiaKilled = null;
    private String doubleAgentKilled = null;
    private String bodyguardSaved = null;
    private String doubleAgentSaved = null;

    private static Game game = null;

    private Game() {
        rolesReady = false;
        status = STATUS.NO_WIN;
        civilianSize = 0;
        mafiaSize = 0;
        roles = new ArrayList<>();
        alive = new ArrayList<>();
        dead = new ArrayList<>();
        nightResults = new Player[2];
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
        mafiaSize = rolesList[ROLES.MAFIA.ordinal()];
        for (int i = 0; i < rolesList[ROLES.MAFIA.ordinal()]; i++) {
            roles.add("mafia");
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
        rolesReady = true;
    }

    public String getRole(String player) {
        if (!rolesReady) { return null; }
        for (Player p : alive) {
            if (p.getName().equals(player)) {
                return p.getRole();
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

    public void daSavePlayer(String saved) {
        doubleAgentSaved = saved;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    private void resolveNight() {
        nightResults[0] = findPlayer(mafiaKilled);
        if (!mafiaKilled.equals(bodyguardSaved) && !mafiaKilled.equals(doubleAgentSaved)) {
            nightResults[0].kill();
            removePlayer(findPlayer(mafiaKilled));
        }
        if (!doubleAgentKilled.equals("pass")) {
            nightResults[1] = findPlayer(doubleAgentKilled);
            if (!doubleAgentKilled.equals(bodyguardSaved)) {
                nightResults[1].kill();
                removePlayer(findPlayer(doubleAgentKilled));
            }
        } else {
            nightResults[1] = null;
        }
        resetForNextNight();
    }

    private Player findPlayer(String name) {
        for (Player p : alive) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    private void removePlayer(Player victim) {
        for (Player p : alive) {
            if (p.equals(victim)) {
                dead.add(p);
                alive.remove(p);
            }
        }
        if (victim.getRole().equals("mafia")) {
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

    public Player[] getNightResults() {
        return nightResults;
    }

    public String guessPlayer(String guess) {
        if (everyoneDone()) {
            resolveNight();
        }
        for (Player p : alive) {
            if (p.getName().equals(guess)) {
                 if (p.getRole().equals("mafia")) {
                     return guess + " is a member of the mafia";
                 } else {
                     return guess + " is an innocent civilian";
                 }
            }
        }
        for (Player p : dead) {
            if (p.getName().equals(guess)) {
                if (p.getRole().equals("mafia")) {
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
            clearVotes();
        }
    }

    public ArrayList<Vote> getVotingResult() {
        return votes;
    }

    private void clearVotes() {
        for (Player p : alive) {
            p.resetVotes();
        }
    }
}