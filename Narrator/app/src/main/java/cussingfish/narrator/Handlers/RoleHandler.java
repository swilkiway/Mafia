package cussingfish.narrator.Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cussingfish.narrator.Game;
import cussingfish.narrator.WebServer;

public class RoleHandler implements HttpHandler {
    public void handle(HttpExchange h) throws IOException {
        InputStream is = h.getRequestBody();
        Gson gson = new Gson();
        String player = gson.fromJson(WebServer.readString(is), String.class);
        String role = Game.getGame().getRole(player);
        if (role == null) { return; }
        String response = gson.toJson(role);
        h.sendResponseHeaders(200, 0);
        OutputStream os = h.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
