package controllers.baloot;


import java.io.*;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import Baloot.BalootServer;
import Baloot.Exception.InvalidCreditValue;
import Baloot.Exception.UserNotExist;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import Baloot.Managers.UserManager;

@WebServlet(name = "BuylistServlet", value = "/buylist")
public class BuylistServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (BalootServer.getInstance().getLoggedInUser() == null) {
            response.sendRedirect("/login");
            return;
        }
        String username = BalootServer.getInstance().getLoggedInUser().getName();
        try {
            var user = BalootServer.getInstance().findUser(username);
            var buylist = BalootServer.getInstance().getUserBuyList(username);
            request.setAttribute("user", user);
            request.setAttribute("buylist", buylist);
            request.getRequestDispatcher("/buylist.jsp").forward(request, response);

        } catch (Exception exception) {
            request.setAttribute("errorMSG",exception.getMessage());
            request.getRequestDispatcher("400.jsp").forward(request, response);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");
        String username = BalootServer.getInstance().getLoggedInUser().getName();

        try {
            switch (action) {
                case "submit":
                    BalootServer.getInstance().handlePaymentUser(username);
                 //   request.getRequestDispatcher("/200.jsp").forward(request, response);
                    response.sendRedirect("/buylist");
                    break;
                case "remove":
                    int commodityID = Integer.valueOf(request.getParameter("commodityId"));
                    BalootServer.getInstance().removeFromBuyList(username,commodityID);
                    response.sendRedirect("/buylist");
                    break;
                case "discount":
                    String code = request.getParameter("discount");
                    BalootServer.getInstance().applyDiscountCode(username,code);
                    response.sendRedirect("/buylist");
                    break;
            }

        } catch (Exception exception) {
            request.setAttribute("errorMSG",exception.getMessage());
            request.getRequestDispatcher("400.jsp").forward(request, response);

        }
      //  response.sendRedirect("/buylist" );
    }

}
