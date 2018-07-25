package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import cussingfish.narrator.Game;
import cussingfish.narrator.Vote;

public class DayHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        Gson gson = new Gson();
        Vote[] result = Game.getGame().getVotingResult();
        String response = gson.toJson(result);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
