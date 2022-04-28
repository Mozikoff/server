package servlets;

import accounts.AccountServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServerStatisticsServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ServerStatisticsServlet.class.getName());
    public final static String PAGE_URL = "/admin";
    private final AccountServerI accountServer;

    public ServerStatisticsServlet(AccountServerI accountServer) {
        this.accountServer = accountServer;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        logger.info("Users limit: " + accountServer.getUsersLimit());
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(accountServer.getUsersLimit());
    }
}
