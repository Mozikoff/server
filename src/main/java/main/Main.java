package main;

import accounts.AccountService;
import dbservice.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();
        AccountService accountService = new AccountService(dbService);

        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        SignInServlet signInServlet = new SignInServlet(accountService);

        ServletContextHandler contex = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contex.addServlet(new ServletHolder(signUpServlet), "/signup");
        contex.addServlet(new ServletHolder(signInServlet), "/signin");

        Server server = new Server(8080);
        server.setHandler(contex);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
