package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

import cussingfish.narrator.Game;
import cussingfish.narrator.Player;
import cussingfish.narrator.Vote;
import cussingfish.narrator.WebServer;

public class VoteHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        String vote[] = gson.fromJson(WebServer.readString(is), String[].class);
        Game.getGame().votePlayer(vote);
    }
}
