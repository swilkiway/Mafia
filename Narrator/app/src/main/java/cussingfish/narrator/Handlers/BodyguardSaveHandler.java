package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

import cussingfish.narrator.Game;
import cussingfish.narrator.WebServer;

public class BodyguardSaveHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        String player = gson.fromJson(WebServer.readString(is), String.class);
        Game.getGame().bodyguardSavePlayer(player);
    }
}
