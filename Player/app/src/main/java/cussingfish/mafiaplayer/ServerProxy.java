package cussingfish.mafiaplayer;

import com.google.gson.Gson;

public class ServerProxy {
    //TODO: Implement serverhost on login page
    public static void setup(int roles[]) {
        Gson gson = new Gson();
        String json = gson.toJson(roles);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/setup";
        WebClient.getConnection(urlString, json, null);
    }
    public static void register(String username) {
        Gson gson = new Gson();
        String json = gson.toJson(username);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/register";
        WebClient.getConnection(urlString, json, null);
    }
    public static String getRole(String username) {
        Gson gson = new Gson();
        String json = gson.toJson(username);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/getrole";
        String response = WebClient.getConnection(urlString, json, null);
        return gson.fromJson(response, String.class);
    }
    public static void mafiaKill(String victim) {
        Gson gson = new Gson();
        String json = gson.toJson(victim);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/mafiakill";
        WebClient.getConnection(urlString, json, null);
    }
    public static void daKill(String victim) {
        Gson gson = new Gson();
        String json = gson.toJson(victim);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/dakill";
        WebClient.getConnection(urlString, json, null);
    }
    public static String guess(String suspect) {
        Gson gson = new Gson();
        String json = gson.toJson(suspect);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/guess";
        String response = WebClient.getConnection(urlString, null, null);
        return gson.fromJson(response, String.class);
    }
    public static void bodyguardSave(String saved) {
        Gson gson = new Gson();
        String json = gson.toJson(saved);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/bodyguardsave";
        WebClient.getConnection(urlString, json, null);
    }
    public static void daSave(String saved) {
        Gson gson = new Gson();
        String json = gson.toJson(saved);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/dasave";
        WebClient.getConnection(urlString, json, null);
    }
    public static void vote(String player) {
        Gson gson = new Gson();
        String json = gson.toJson(player);
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/vote";
        WebClient.getConnection(urlString, json, null);
    }
    public static Vote[] dayResult() {
        Gson gson = new Gson();
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/dayresult";
        String response = WebClient.getConnection(urlString, null, null);
        return gson.fromJson(response, Vote[].class);
    }
    public static String nightResult() {
        Gson gson = new Gson();
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/nightresult";
        String response = WebClient.getConnection(urlString, null, null);
        return gson.fromJson(response, String.class);
    }
    public static int checkStatus() {
        Gson gson = new Gson();
        String urlString = "http://" + "serverhost" + ":" + "7996" + "/checkstatus";
        String response = WebClient.getConnection(urlString, null, null);
        return gson.fromJson(response, int.class);
    }
}
