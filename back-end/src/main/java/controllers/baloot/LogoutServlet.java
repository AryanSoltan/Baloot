package controllers.baloot;
import Baloot.BalootServer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import Baloot.Managers.UserManager;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (BalootServer.getInstance().getLoggedInUser() == null) {
            response.sendRedirect("/login");
        } else {
            BalootServer.getInstance().logOut();
            response.sendRedirect("/login");
        }
    }


}
