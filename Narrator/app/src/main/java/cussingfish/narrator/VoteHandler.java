package cussingfish.narrator;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VoteHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        String vote = gson.fromJson(WebServer.readString(is), String.class);
        Game.getGame().votePlayer(vote);
    }
}
