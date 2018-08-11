package cussingfish.mafiaplayer;

import com.google.gson.Gson;

public class ServerProxy {
    public static ServerProxy get() {
        if (serverProxy == null) {
            serverProxy = new ServerProxy();
        }
        return serverProxy;
    }
    private static ServerProxy serverProxy = null;
    private ServerProxy() { }
    private String hostIP = null;
    public void setHostIP(String ip) {
        hostIP = ip;
    }
    public void setup(int roles[]) {
        Gson gson = new Gson();
        String json = gson.toJson(roles);
        String urlString = "http://" + hostIP + ":" + "7996" + "/setup";
        WebClient.getConnection(urlString, json);
    }
    public void register(String username) {
        Gson gson = new Gson();
        String json = gson.toJson(username);
        String urlString = "http://" + hostIP + ":" + "7996" + "/register";
        WebClient.getConnection(urlString, json);
    }
    public String[] getRole(String username) {
        Gson gson = new Gson();
        String json = gson.toJson(username);
        String urlString = "http://" + hostIP + ":" + "7996" + "/getrole";
        String response = WebClient.getConnection(urlString, json);
        return gson.fromJson(response, String[].class);
    }
    public void mafiaKill(String victim) {
        Gson gson = new Gson();
        String json = gson.toJson(victim);
        String urlString = "http://" + hostIP + ":" + "7996" + "/mafiakill";
        WebClient.getConnection(urlString, json);
    }
    public void daKill(String victim) {
        Gson gson = new Gson();
        String json = gson.toJson(victim);
        String urlString = "http://" + hostIP + ":" + "7996" + "/dakill";
        WebClient.getConnection(urlString, json);
    }
    public void bomberKill(String target) {
        Gson gson = new Gson();
        String json = gson.toJson(target);
        String urlString = "http://" + hostIP + ":" + "7996" + "/bomberkill";
        WebClient.getConnection(urlString, json);
    }
    public String investigate(String suspect) {
        Gson gson = new Gson();
        String json = gson.toJson(suspect);
        String urlString = "http://" + hostIP + ":" + "7996" + "/detectiveinvestigate";
        String response = WebClient.getConnection(urlString, json);
        return gson.fromJson(response, String.class);
    }
    public void bodyguardSave(String saved) {
        Gson gson = new Gson();
        String json = gson.toJson(saved);
        String urlString = "http://" + hostIP + ":" + "7996" + "/bodyguardsave";
        WebClient.getConnection(urlString, json);
    }
    public void daSave(String saved) {
        Gson gson = new Gson();
        String json = gson.toJson(saved);
        String urlString = "http://" + hostIP + ":" + "7996" + "/dasave";
        WebClient.getConnection(urlString, json);
    }
    public String daGetMafiaKilled() {
        Gson gson = new Gson();
        String urlString = "http://" + hostIP + ":" + "7996" + "/doubleagent";
        String response = WebClient.getConnection(urlString, null);
        return gson.fromJson(response, String.class);
    }
    public void lawyerDefend(String player) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        String urlString = "http://" + hostIP + ":" + "7996" + "/lawyerdefend";
        WebClient.getConnection(urlString, json);
    }
    public void officialVote(String player) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        String urlString = "http://" + hostIP +":" + "7996" + "/officialenfranchise";
        WebClient.getConnection(urlString, json);
    }
    public void vote(String player) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        String urlString = "http://" + hostIP + ":" + "7996" + "/vote";
        WebClient.getConnection(urlString, json);
    }
    public DayResults dayResult() {
        Gson gson = new Gson();
        String urlString = "http://" + hostIP + ":" + "7996" + "/dayresult";
        String response = WebClient.getConnection(urlString, null);
        return gson.fromJson(response, DayResults.class);
    }
    public NightResults nightResult() {
        Gson gson = new Gson();
        String urlString = "http://" + hostIP + ":" + "7996" + "/nightresult";
        String response = WebClient.getConnection(urlString, null);
        return gson.fromJson(response, NightResults.class);
    }
    public int checkStatus() {
        Gson gson = new Gson();
        String urlString = "http://" + hostIP + ":" + "7996" + "/checkstatus";
        String response = WebClient.getConnection(urlString, null);
        return gson.fromJson(response, int.class);
    }
}
