package cussingfish.narrator;

import java.util.ArrayList;

public class Game {
    private ArrayList<String> roles;
    private ArrayList<Player> alive;
    private ArrayList<Player> dead;
    private int votes;
    private String mafiaKilled = null;
    private String doubleAgentKilled = null;
    private String bodyguardSaved = null;
    private String doubleAgentSaved = null;

    private static Game game = null;

    private Game() {
        roles = new ArrayList<>();
        alive = new ArrayList<>();
        dead = new ArrayList<>();
        votes = 0;
    }

    public static Game getGame() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public void setupRoles(int[] rolesList) {
        for (int i = 0; i < rolesList[0]; i++) {
            roles.add("mafia");
        }
    }

    public String addPlayer(String name) {
        return "";
    }

    public String[] killPlayer(String victim) {
        if (mafiaKilled == null) {
            mafiaKilled = victim;
        } else if (doubleAgentKilled == null) {
            doubleAgentKilled = victim;
            if (bodyguardSaved != null && doubleAgentSaved != null) {
                return resolveNight();
            }
        }
        return null;
    }

    public String[] savePlayer(String saved) {
        if (bodyguardSaved == null) {
            bodyguardSaved = saved;
        } else if (doubleAgentSaved == null) {
            doubleAgentSaved = saved;
            if (mafiaKilled != null && doubleAgentKilled != null) {
                return resolveNight();
            }
        }
        return null;
    }

    private String[] resolveNight() {
        String[] results = new String[2];
        if (mafiaKilled.equals(bodyguardSaved) || mafiaKilled.equals(doubleAgentSaved)) {
            results[0] = mafiaKilled + " was saved last night by a mysterious do-gooder";
        } else {
            results[0] = mafiaKilled + " was knocked off by the mafia in the middle of the night";
            removePlayer(mafiaKilled);
        }
        if (!doubleAgentKilled.equals("pass")) {
            if (doubleAgentKilled.equals(bodyguardSaved)) {
                results[1] = doubleAgentKilled + "was saved last night by a mysterious do-gooder";
            } else {
                results[1] = doubleAgentKilled + " was knocked off by the mafia in the middle of the night";
                removePlayer(doubleAgentKilled);
            }
        } else {
            results[1] = null;
        }
        resetForNextNight();
        return results;
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
    }

    public String guessPlayer(String guess) {
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

    public String votePlayer(String vote) {
        for (Player p : alive) {
            if (p.getName().equals(vote)) {
                p.addVote();
                votes++;
            }
        }
        if (votes == alive.size()) {
            return resolveVoting();
        }
        return null;
    }

    private String resolveVoting() {
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
        if (isTied) {
            return "No one could agree on who to lynch, so everyone survived the day";
        } else {
            dead.add(lynched);
            alive.remove(lynched);
            clearVotes();
            return "The majority voted to lynch " + lynched.getName() + ", who was a " + lynched.getRole();
        }
    }

    private void clearVotes() {
        for (Player p : alive) {
            p.resetVotes();
        }
        votes = 0;
    }
}