package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import cussingfish.narrator.Game;

public class StatusHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        Gson gson = new Gson();
        int status = Game.getGame().getStatus();
        String response = gson.toJson(status);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
