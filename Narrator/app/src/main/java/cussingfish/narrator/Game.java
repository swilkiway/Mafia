package cussingfish.narrator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import cussingfish.narrator.Model.Ballot;
import cussingfish.narrator.Model.Candidate;
import cussingfish.narrator.Model.DayResults;
import cussingfish.narrator.Model.NightResults;
import cussingfish.narrator.Model.Player;
import cussingfish.narrator.Model.StartResults;
import cussingfish.narrator.Model.Vote;

public class Game {
    /* journalist (if killed, roles are no longer announced when others die), apprentice (takes role
     * of first person killed) */
    enum ROLES { HIT_MAN, DETECTIVE, DOUBLE_AGENT, BODYGUARD, BOMBER, LAWYER, OFFICIAL, TOWN_GOSSIP,
        BLACKMAILER, GODFATHER, POISONER, DIPLOMAT, HERMIT, CIVILIAN, SUSPECT }
    enum STATUS { NO_WIN, CIVILIAN_WIN, MAFIA_WIN }
    enum JOURNALIST { DISABLED, ALIVE, DEAD }
    private final String W = "waiting";
    private final String D = "disabled"; //or dead
    private final String B = "blocked";
    private STATUS status;
    private JOURNALIST journalist;
    private boolean rolesReady;
    private boolean dayReady;
    private boolean nightReady;
    private ArrayList<String> roles;
    private int civilianSize;
    private ArrayList<Player> alive;
    private ArrayList<Player> dead;
    private ArrayList<String> mafia;
    private int mafiaVotes;
    private int hitMenSize;
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
    private String poisoned = D;
    private String lovers[];
    private Player lover;
    private int votesNeeded;

    private static Game game = null;

    private Game() {
        rolesReady = false;
        dayReady = false;
        nightReady = false;
        status = STATUS.NO_WIN;
        journalist = JOURNALIST.DISABLED;
        civilianSize = 0;
        mafiaVotes = 0;
        hitMenSize = 0;
        roles = new ArrayList<>();
        alive = new ArrayList<>();
        dead = new ArrayList<>();
        mafia = new ArrayList<>();
        nightResults = new NightResults();
        dayResults = new DayResults();
        ballot = new Ballot();
        lovers = null;
        lover = null;
        votesNeeded = 0;
    }

    public static Game getGame() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public void addPlayer(String name) {
        Player p = new Player(name);
        if (!alive.contains(p)) {
            System.out.println("added " + name);
            alive.add(p);
        }
    }

    public void setupRoles(int[] rolesList) {
        if (rolesReady) { return; }
        for (int i = 0; i < rolesList[ROLES.HIT_MAN.ordinal()]; i++) {
            roles.add("hit man");
            hitMenSize++;
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
        if (rolesList[ROLES.TOWN_GOSSIP.ordinal()] == 1) {
            civilianSize++;
            roles.add("town gossip");
        }
        if (rolesList[ROLES.BLACKMAILER.ordinal()] == 1) {
            roles.add("blackmailer");
            silenced = W;
        }
        if (rolesList[ROLES.GODFATHER.ordinal()] == 1) {
            roles.add("godfather");
            hitMenSize++;
        }
        if (rolesList[ROLES.POISONER.ordinal()] == 1) {
            roles.add("poisoner");
            poisoned = W;
        }
        if (rolesList[ROLES.DIPLOMAT.ordinal()] == 1) {
            civilianSize++;
            roles.add("diplomat");
        }
        if (rolesList[ROLES.HERMIT.ordinal()] == 1) {
            civilianSize++;
            roles.add("hermit");
        }
        civilianSize += rolesList[ROLES.CIVILIAN.ordinal()];
        for (int i = 0; i < rolesList[ROLES.CIVILIAN.ordinal()]; i++) {
            roles.add("civilian");
        }
        civilianSize += rolesList[ROLES.SUSPECT.ordinal()];
        for (int i = 0; i < rolesList[ROLES.SUSPECT.ordinal()]; i++) {
            roles.add("suspect");
        }
        while (roles.size() < alive.size()) {
            civilianSize++;
            roles.add("civilian");
        }
        Collections.shuffle(roles);
        for (int i = 0; i < alive.size(); i++) {
            alive.get(i).setRole(roles.get(i));
        }
        for (Player p : alive) {
            if (p.getRole().equals("hit man") || p.getRole().equals("blackmailer") ||
                    p.getRole().equals("godfather") || p.getRole().equals("poisoner")) {
                mafia.add(p.getName());
            }
        }
        rolesReady = true;
    }

    public StartResults getRole(String player) {
        StartResults s = new StartResults();
        if (!rolesReady) {
            s.setNull();
            return s;
        }
        s.setAlive(alive);
        for (Player p : alive) {
            if (p.getName().equals(player)) {
                s.setRole(p.getRole());
                if (p.getRole().equals("hit man") || p.getRole().equals("blackmailer") ||
                        p.getRole().equals("godfather") || p.getRole().equals("poisoner")) {
                    s.setTeammates(mafia);
                }
                return s;
            }
        }
        return null;
    }

    private boolean everyoneDone() {
        return ((hitMenSize == mafiaVotes) && !doubleAgentKilled.equals(W) && !bodyguardSaved.equals(W) &&
                !doubleAgentSaved.equals(W) && !bomberKilled.equals(W) && !defended.equals(W) &&
                !enfranchised.equals(W) && !silenced.equals(W) && !poisoned.equals(W));
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
        if (hitMenSize == mafiaVotes) {
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

    public void poisonPlayer(String victim) {
        poisoned = victim;
        if (everyoneDone()) {
            resolveNight();
        }
    }

    private void resolveNight() {
        clearVotes();
        dayReady = false;
        if (mafiaKilled.equals(doubleAgentSaved)) {
            nightResults.setDaSaved(doubleAgentSaved);
        } else if (mafiaKilled.equals(bodyguardSaved)) {
            nightResults.setBodyguardSaved(bodyguardSaved);
        } else {
            setMafiaKilled();
        }
        if (!doubleAgentKilled.equals("pass") && !doubleAgentKilled.equals(D)) {
            if (!doubleAgentKilled.equals(bodyguardSaved)) {
                setDaKilled();
            } else {
                nightResults.setBodyguardSaved(doubleAgentKilled);
            }
        }
        if (enfranchised.equals(silenced)) {
            if (!enfranchised.equals(D)) {
                enfranchised = B;
                silenced = B;
            }
            nightResults.setSilenced(null);
        } else {
            nightResults.setSilenced(silenced);
        }
        if (checkPoisoned()) {
            setPoisoned();
        } else {
            nightResults.setPoisoned(null);
        }
        nightResults.setLover(lover);
        nightResults.setStatus(status.ordinal());
        nightResults.setAlive(alive);
        nightReady = true;
    }

    private void setMafiaKilled() {
        Player p = findPlayer(mafiaKilled);
        if (p.getRole().equals("hermit")) {
            nightResults.setHermit(p.getName());
        } else {
            nightResults.setMafiaKilled(p);
            removePlayer(p);
            if (p.getRole().equals("bomber")) {
                setBomberKilled();
            }
        }
    }

    private void setDaKilled() {
        Player p = findPlayer(doubleAgentKilled);
        nightResults.setDaKilled(p);
        removePlayer(p);
        if (p.getRole().equals("bomber")) {
            setBomberKilled();
        }
    }

    private void setBomberKilled() {
        Player dead = findPlayer(bomberKilled);
        removePlayer(dead);
        nightResults.setBombed(dead);
        bomberKilled = null;
    }

    private void setPoisoned() {
        Player p = findPlayer(poisoned);
        nightResults.setPoisoned(p);
        removePlayer(p);
        if (p.getRole().equals("bomber")) {
            setBomberKilled();
        }
    }

    private boolean checkPoisoned() {
        return (!poisoned.equals(mafiaKilled) && !poisoned.equals(bodyguardSaved) && !poisoned.equals(doubleAgentKilled)
                && !poisoned.equals(doubleAgentSaved) && !poisoned.equals(bomberKilled) && !poisoned.equals(defended)
                && !poisoned.equals(enfranchised) && !poisoned.equals(silenced));
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

    public void removePlayer(String name) {
        removePlayer(findPlayer(name));
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
                if (lover == null && lovers != null) {
                    checkLovers(p.getName());
                }
                break;
            }
        }
        if (victim.getRole().equals("hit man") || victim.getRole().equals("godfather")) {
            mafia.remove(victim.getName());
            hitMenSize--;
        } else if (victim.getRole().equals("blackmailer") || victim.getRole().equals("poisoner")) {
            mafia.remove(victim.getName());
            disableRole(victim.getRole());
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

    private void checkLovers(String player) {
        if (lovers[0].equals(player)) {
            removePlayer(lovers[1]);
            lovers = null;
        } else if (lovers[1].equals(player)) {
            removePlayer(lovers[1]);
            lovers = null;
        }
    }

    private boolean checkAlive(String name) {
        for (Player p : alive) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void disableRole(String role) {
        switch (role) {
            case "bodyguard": bodyguardSaved = D; break;
            case "double agent": doubleAgentKilled = D; doubleAgentSaved = D; break;
            case "bomber": bomberKilled = D; break;
            case "lawyer": defended = D; break;
            case "official": enfranchised = D; break;
            case "blackmailer": silenced = D; break;
            case "poisoner": poisoned = D; break;
        }
    }

    private void resetForNextNight() {
        nightResults = new NightResults();
        mafiaKilled = W;
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
        if (!poisoned.equals(D)) {
            poisoned = W;
        }
        lover = null;
    }

    public NightResults getNightResults() {
        if (nightReady) {
            return nightResults;
        } else {
            NightResults n = new NightResults();
            n.setNull();
            return n;
        }
    }

    private void calculateVotesNeeded() {
        votesNeeded = alive.size();
        if (!silenced.equals(D) && checkAlive(silenced) && !silenced.equals(B)) {
            votesNeeded--;
        }
        if (!enfranchised.equals(D) && checkAlive(enfranchised) && !enfranchised.equals(B)) {
            votesNeeded++;
        }
    }

    public String investigatePlayer(String guess) {
        for (Player p : alive) {
            if (p.getName().equals(guess)) {
                if (p.getRole().equals("hit man") || p.getRole().equals("suspect") ||
                        p.getRole().equals("blackmailer") || p.getRole().equals("poisoner")) {
                    return guess + " is a member of the mafia";
                } else if (p.getRole().equals("town gossip")) {
                    return guess + " is the town gossip! Better lie low for a while";
                } else {
                    return guess + " is an innocent civilian";
                }
            }
        }
        for (Player p : dead) {
            if (p.getName().equals(guess)) {
                if (p.getRole().equals("hit man") || p.getRole().equals("suspect") ||
                        p.getRole().equals("blackmailer") || p.getRole().equals("poisoner")) {
                    return guess + " is a member of the mafia";
                } else if (p.getRole().equals("town gossip")) {
                    return guess + " is the town gossip! Better lie low for a while";
                } else {
                    return guess + " is an innocent civilian";
                }
            }
        }
        return null;
    }

    public void matchmake(String players[]) {
        lovers = players;
    }

    public void votePlayer(Vote vote) {
        calculateVotesNeeded();
        ballot.addVote(vote.getNominated(), vote.getVoter());
        if (vote.getVoter().equals(enfranchised)) {
            ballot.addVote(vote.getNominated(), vote.getVoter());
        }
        System.out.println("Total votes: " + ballot.getTotalVotes());
        System.out.println("Players alive: " + alive.size());
        if (ballot.getTotalVotes() == votesNeeded) {
            resolveVoting();
        }
    }

    private void resolveVoting() {
        resetForNextNight();
        nightReady = false;
        dayResults.setBallot(ballot);
        dayResults.setTied(false);
        Candidate lynched = null;
        for (Candidate c : ballot.getCandidates()) {
            if (lynched == null) {
                lynched = c;
            } else if (c.getVotes() > lynched.getVotes()) {
                lynched = c;
                dayResults.setTied(false);
            } else if (c.getVotes() == lynched.getVotes()) {
                dayResults.setTied(true);
            }
        }
        if (!dayResults.getTied()) {
            if (lynched.getNominated().equals(defended)) {
                dayResults.setDefended(defended);
            } else {
                Player p = findPlayer(lynched.getNominated());
                if (p.getRole().equals("diplomat")) {
                    dayResults.setDiplomat(p.getName());
                } else {
                    dayResults.setLynched(p);
                    removePlayer(p);
                    if (p.getRole().equals("bomber")) {
                        Player dead = findPlayer(bomberKilled);
                        removePlayer(dead);
                        dayResults.setBombed(dead);
                    }
                }
            }
        }
        dayResults.setLover(lover);
        dayResults.setStatus(status.ordinal());
        dayResults.setAlive(alive);
        dayReady = true;
    }

    public DayResults getVotingResult() {
        if (dayReady) {
            return dayResults;
        } else {
            DayResults d = new DayResults();
            d.setNull();
            return d;
        }
    }

    private void clearVotes() {
        dayResults = new DayResults();
        ballot = new Ballot();
        if (!defended.equals(D)) {
            defended = W;
        }
        if (!enfranchised.equals(D)) {
            enfranchised = W;
        }
        if (!silenced.equals(D)) {
            silenced = W;
        }
        lover = null;
    }
}