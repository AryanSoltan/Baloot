package controllers.baloot;

import Baloot.BalootServer;
import Baloot.Managers.CommodityManager;
import Baloot.Managers.UserManager;
import Baloot.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CreditServlet", value = "/credit")
public class CreditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (BalootServer.getInstance().getLoggedInUser() == null) {
            response.sendRedirect("/login");
            return;
        }
        User user =  BalootServer.getInstance().getLoggedInUser();
        request.setAttribute("loggedInUser",user);


        request.getRequestDispatcher("/credit.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (BalootServer.getInstance().getLoggedInUser() == null) {
            response.sendRedirect("/login");
            return;
        }

        try {
            String username = BalootServer.getInstance().getLoggedInUser().getName();
            String credit = request.getParameter("credit");
            BalootServer.getInstance().addCredit(username, credit);

         //   request.getRequestDispatcher("/200.jsp").forward(request, response);
            response.sendRedirect("/buylist");
        }
        catch (Exception exception){
            request.setAttribute("errorMSG",exception.getMessage());
            request.getRequestDispatcher("/400.jsp").forward(request, response);

        }

    }

}
