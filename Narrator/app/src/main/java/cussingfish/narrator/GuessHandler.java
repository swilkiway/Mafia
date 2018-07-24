package cussingfish.narrator;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GuessHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        String player = gson.fromJson(WebServer.readString(is), String.class);
        String alignment = Game.getGame().guessPlayer(player);
        String response = gson.toJson(alignment);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    private Game game;
}
