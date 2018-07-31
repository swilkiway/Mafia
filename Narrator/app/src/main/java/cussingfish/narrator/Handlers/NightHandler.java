package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import cussingfish.narrator.Game;
import cussingfish.narrator.NightResults;
import cussingfish.narrator.Player;

public class NightHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        Gson gson = new Gson();
        NightResults results = Game.getGame().getNightResults();
        String response = gson.toJson(results);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
