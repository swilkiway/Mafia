package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import cussingfish.narrator.Game;

public class AgentHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        Gson gson = new Gson();
        String victim = Game.getGame().getMafiaKilled();
        String response = gson.toJson(victim);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
