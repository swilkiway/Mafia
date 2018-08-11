package cussingfish.narrator;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import cussingfish.narrator.Handlers.AgentHandler;
import cussingfish.narrator.Handlers.BodyguardSaveHandler;
import cussingfish.narrator.Handlers.BomberKillHandler;
import cussingfish.narrator.Handlers.DAKillHandler;
import cussingfish.narrator.Handlers.DASaveHandler;
import cussingfish.narrator.Handlers.DayHandler;
import cussingfish.narrator.Handlers.DetectiveHandler;
import cussingfish.narrator.Handlers.LawyerHandler;
import cussingfish.narrator.Handlers.MafiaKillHandler;
import cussingfish.narrator.Handlers.NightHandler;
import cussingfish.narrator.Handlers.OfficialHandler;
import cussingfish.narrator.Handlers.RegisterHandler;
import cussingfish.narrator.Handlers.RoleHandler;
import cussingfish.narrator.Handlers.SetupHandler;
import cussingfish.narrator.Handlers.StatusHandler;
import cussingfish.narrator.Handlers.VoteHandler;

public class WebServer {
    public static void main(String args[]) throws IOException {
        int port = Integer.parseInt(args[0]);
        System.out.println("Started server on " + args[0]);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), port);
        server.createContext("/setup", new SetupHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/getrole", new RoleHandler());
        server.createContext("/mafiakill", new MafiaKillHandler());
        server.createContext("/dakill", new DAKillHandler());
        server.createContext("/bomberkill", new BomberKillHandler());
        server.createContext("/detectiveinvestigate", new DetectiveHandler());
        server.createContext("/bodyguardsave", new BodyguardSaveHandler());
        server.createContext("/dasave", new DASaveHandler());
        server.createContext("/lawyerdefend", new LawyerHandler());
        server.createContext("/officialenfranchise", new OfficialHandler());
        server.createContext("/vote", new VoteHandler());
        server.createContext("/dayresult", new DayHandler());
        server.createContext("/nightresult", new NightHandler());
        server.createContext("/doubleagent", new AgentHandler());
        server.createContext("/checkstatus", new StatusHandler());

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
