package cussingfish.narrator;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class WebServer {
    public static void main(String args[]) throws IOException {
        int port = Integer.parseInt(args[0]);
        System.out.println("Started server on " + args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), port);
        server.createContext("/setup", new SetupHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/kill", new KillHandler());
        server.createContext("/guess", new GuessHandler());
        server.createContext("/save", new SaveHandler());
        server.createContext("/vote", new VoteHandler());

        server.setExecutor(null);
        server.start();
    }
    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
