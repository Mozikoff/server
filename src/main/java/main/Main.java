package main;

import accounts.AccountService;
import chat.WebSocketChatServlet;
import dbservice.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();
        AccountService accountService = new AccountService(dbService);

        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        SignInServlet signInServlet = new SignInServlet(accountService);
        WebSocketChatServlet chatServlet = new WebSocketChatServlet();

        ServletContextHandler contex = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contex.addServlet(new ServletHolder(signUpServlet), "/signup");
        contex.addServlet(new ServletHolder(signInServlet), "/signin");
        contex.addServlet(new ServletHolder(chatServlet), "/chat");
        JettyWebSocketServletContainerInitializer.configure(contex, null);

        Server server = new Server(8080);
        server.setHandler(contex);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
