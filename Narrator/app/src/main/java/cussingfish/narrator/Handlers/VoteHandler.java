package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cussingfish.narrator.Game;
import cussingfish.narrator.Model.Vote;
import cussingfish.narrator.WebServer;

public class VoteHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        Vote v = gson.fromJson(WebServer.readString(is), Vote.class);
        Game.getGame().votePlayer(v);
        String response = gson.toJson("\n\n");
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
