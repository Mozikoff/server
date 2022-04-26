package servlets;

import accounts.AccountService;
import dbservice.dataSets.UsersDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;
    private final String NO_LOGIN_OR_PASS_PARAMS_MESSAGE = "Wrong request. No 'login' or 'password' parameters!";

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        if (Objects.isNull(request.getParameter("login")) || Objects.isNull(request.getParameter("password"))) {
            response.getWriter().println(NO_LOGIN_OR_PASS_PARAMS_MESSAGE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UsersDataSet newUser = new UsersDataSet(request.getParameter("login"));
        accountService.addNewUser(newUser);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
