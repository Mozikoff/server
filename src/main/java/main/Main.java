package main;

import accounts.*;
import chat.WebSocketChatServlet;
import dbservice.DBService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import servlets.ServerStatisticsServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            logger.error("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.parseInt(portString);

        logger.info("Starting server on http://127.0.0.1:" + portString);

        AccountServerI accountServer = new AccountServer(10);
        AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ServerManager:type=AccountServerController");
        mbs.registerMBean(serverStatistics, name);

        DBService dbService = new DBService();
        AccountService accountService = new AccountService(dbService);

        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        SignInServlet signInServlet = new SignInServlet(accountService);
        ServerStatisticsServlet statisticsServlet = new ServerStatisticsServlet(accountServer);
        WebSocketChatServlet chatServlet = new WebSocketChatServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signUpServlet), SignUpServlet.PAGE_URL);
        context.addServlet(new ServletHolder(signInServlet), SignInServlet.PAGE_URL);
        context.addServlet(new ServletHolder(statisticsServlet), ServerStatisticsServlet.PAGE_URL);
        context.addServlet(new ServletHolder(chatServlet), WebSocketChatServlet.PAGE_URL);
        JettyWebSocketServletContainerInitializer.configure(context, null);

        Server server = new Server(port);
        server.setHandler(context);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
