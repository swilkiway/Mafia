package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

import cussingfish.narrator.Game;
import cussingfish.narrator.WebServer;

public class SetupHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        System.out.println("in handler");
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        int roles[] = gson.fromJson(WebServer.readString(is), int[].class);
        Game.getGame().setupRoles(roles);
    }
}
