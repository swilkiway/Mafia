package cussingfish.mafiaplayer;

import com.google.gson.Gson;

import cussingfish.mafiaplayer.Model.DayResults;
import cussingfish.mafiaplayer.Model.NightResults;
import cussingfish.mafiaplayer.Model.StartResults;
import cussingfish.mafiaplayer.Model.Vote;
import cussingfish.mafiaplayer.Roles.Civilian;

public class ServerProxy {
    public static ServerProxy get() {
        if (serverProxy == null) {
            serverProxy = new ServerProxy();
        }
        return serverProxy;
    }
    private static ServerProxy serverProxy = null;
    private ServerProxy() { }
    private String baseUrl = null;
    public void setBaseUrl(String ip) {
        baseUrl = "http://" + ip + ":" + "7996";
    }
    public void setup(int roles[]) {
        Gson gson = new Gson();
        String json = gson.toJson(roles);
        String urlString = baseUrl + "/setup";
        WebClient.getConnection(urlString, json);
    }
    public void register(String username) {
        Gson gson = new Gson();
        String json = gson.toJson(username);
        String urlString = baseUrl + "/register";
        WebClient.getConnection(urlString, json);
    }
    public StartResults getRole(String username) {
        Gson gson = new Gson();
        String json = gson.toJson(username);
        String urlString = baseUrl + "/getrole";
        String response = WebClient.getConnection(urlString, json);
        return gson.fromJson(response, StartResults.class);
    }
    public void mafiaKill(String victim) {
        Gson gson = new Gson();
        String json = gson.toJson(victim);
        String urlString = baseUrl + "/mafiakill";
        WebClient.getConnection(urlString, json);
    }
    public void daKill(String victim) {
        Gson gson = new Gson();
        String json = gson.toJson(victim);
        String urlString = baseUrl + "/dakill";
        WebClient.getConnection(urlString, json);
    }
    public void bomberKill(String target) {
        Gson gson = new Gson();
        String json = gson.toJson(target);
        String urlString = baseUrl + "/bomberkill";
        WebClient.getConnection(urlString, json);
    }
    public String investigate(String suspect) {
        Gson gson = new Gson();
        String json = gson.toJson(suspect);
        String urlString = baseUrl + "/detectiveinvestigate";
        String response = WebClient.getConnection(urlString, json);
        return gson.fromJson(response, String.class);
    }
    public void bodyguardSave(String saved) {
        Gson gson = new Gson();
        String json = gson.toJson(saved);
        String urlString = baseUrl + "/bodyguardsave";
        WebClient.getConnection(urlString, json);
    }
    public void daSave(String saved) {
        Gson gson = new Gson();
        String json = gson.toJson(saved);
        String urlString = baseUrl + "/dasave";
        WebClient.getConnection(urlString, json);
    }
    public String daGetMafiaKilled() {
        Gson gson = new Gson();
        String urlString = baseUrl + "/doubleagent";
        String response = WebClient.getConnection(urlString, "");
        return gson.fromJson(response, String.class);
    }
    public void lawyerDefend(String player) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        String urlString = baseUrl + "/lawyerdefend";
        WebClient.getConnection(urlString, json);
    }
    public void officialVote(String player) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        String urlString = baseUrl + "/officialenfranchise";
        WebClient.getConnection(urlString, json);
    }
    public void matchmake(String players[]) {
        Gson gson = new Gson();
        String json = gson.toJson(players);
        String urlString = baseUrl + "/matchmake";
        WebClient.getConnection(urlString, json);
    }
    public void blackmailerSilence(String victim) {
        Gson gson = new Gson();
        String json = gson.toJson(victim);
        String urlString = baseUrl + "/blackmailersilence";
        WebClient.getConnection(urlString, json);
    }
    public void poisonerKill(String victim) {
        Gson gson = new Gson();
        String json = gson.toJson(victim);
        String urlString = baseUrl + "/poisonerkill";
        WebClient.getConnection(urlString, json);
    }
    public void vote(String player) {
        Gson gson = new Gson();
        Vote vote = new Vote();
        vote.setNominated(player);
        vote.setVoter(Civilian.getUserName());
        String json = gson.toJson(vote);
        String urlString = baseUrl + "/vote";
        WebClient.getConnection(urlString, json);
    }
    public DayResults dayResult() {
        Gson gson = new Gson();
        String urlString = baseUrl + "/dayresult";
        String response = WebClient.getConnection(urlString, "");
        return gson.fromJson(response, DayResults.class);
    }
    public NightResults nightResult() {
        Gson gson = new Gson();
        String urlString = baseUrl + "/nightresult";
        String response = WebClient.getConnection(urlString, "");
        return gson.fromJson(response, NightResults.class);
    }

    public void leaveGame(String name) {
        Gson gson = new Gson();
        String json = gson.toJson(name);
        String urlString = baseUrl + "/leavegame";
        WebClient.getConnection(urlString, json);
    }
}
