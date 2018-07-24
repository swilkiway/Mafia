package cussingfish.narrator;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private boolean rolesReady;
    private ArrayList<String> roles;
    private ArrayList<Player> alive;
    private ArrayList<Player> dead;
    private Player nightResults[];
    private ArrayList<Vote> votes;
    private String mafiaKilled = null;
    private String doubleAgentKilled = null;
    private String bodyguardSaved = null;
    private String doubleAgentSaved = null;
    private boolean detectiveGuess = false;

    private static Game game = null;

    private Game() {
        rolesReady = false;
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

    public void addPlayer(String name) {
        Player p = new Player(name);
        alive.add(p);
    }

    public void setupRoles(int[] rolesList) {
        for (int i = 0; i < rolesList[0]; i++) {
            roles.add("mafia");
        }
        for (int i = 0; i < rolesList[1]; i++) {
            roles.add("detective");
        }
        for (int i = 0; i < rolesList[2]; i++) {
            roles.add("double agent");
        }
        for (int i = 0; i < rolesList[3]; i++) {
            roles.add("bodyguard");
        }
        for (int i = 0; i < rolesList[4]; i++) {
            roles.add("bomber");
        }
        for (int i = 0; i < rolesList[5]; i++) {
            roles.add("civilian");
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
                doubleAgentSaved != null && detectiveGuess);
    }

    public void killPlayer(String victim) {
        if (mafiaKilled == null) {
            mafiaKilled = victim;
        } else if (doubleAgentKilled == null) {
            doubleAgentKilled = victim;
            if (everyoneDone()) {
                resolveNight();
            }
        }
    }

    public void savePlayer(String saved) {
        if (bodyguardSaved == null) {
            bodyguardSaved = saved;
        } else if (doubleAgentSaved == null) {
            doubleAgentSaved = saved;
            if (everyoneDone()) {
                resolveNight();
            }
        }
    }

    private void resolveNight() {
        nightResults[0] = findPlayer(mafiaKilled);
        if (!mafiaKilled.equals(bodyguardSaved) && !mafiaKilled.equals(doubleAgentSaved)) {
            nightResults[0].kill();
            removePlayer(mafiaKilled);
        }
        if (!doubleAgentKilled.equals("pass")) {
            nightResults[1] = findPlayer(doubleAgentKilled);
            if (!doubleAgentKilled.equals(bodyguardSaved)) {
                nightResults[1].kill();
                removePlayer(doubleAgentKilled);
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

    private void removePlayer(String victim) {
        for (Player p : alive) {
            if (p.getName().equals(victim)) {
                dead.add(p);
                alive.remove(p);
            }
        }
    }

    private void resetForNextNight() {
        mafiaKilled = null;
        doubleAgentKilled = null;
        bodyguardSaved = null;
        doubleAgentSaved = null;
        detectiveGuess = false;
    }

    public Player[] getNightResults() {
        return nightResults;
    }

    public String guessPlayer(String guess) {
        detectiveGuess = true;
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
            dead.add(lynched);
            alive.remove(lynched);
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