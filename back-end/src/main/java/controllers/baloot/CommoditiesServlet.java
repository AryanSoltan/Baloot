package controllers.baloot;
import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Managers.CommodityManager;
import Baloot.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import Baloot.Managers.UserManager;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CommoditiesServlet", value = "/commodities")
public class CommoditiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (BalootServer.getInstance().getLoggedInUser() == null) {
            response.sendRedirect("/login");
            return;
        }
        User user =  BalootServer.getInstance().getLoggedInUser();
        ArrayList<Commodity> commodities = BalootServer.getInstance().getFilteredCommodities();
        request.setAttribute("loggedInUser",user);
        request.setAttribute("commodities",commodities);
        request.getRequestDispatcher("/commodities.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (BalootServer.getInstance().getLoggedInUser() == null) {
            response.sendRedirect("/login");
            return;
        }
        String action = request.getParameter("action");

        switch (action) {
            case "search_by_category" :
                BalootServer.getInstance().setSearchFilters(request.getParameter("search"),"category");
                break;

            case "search_by_name" :
                BalootServer.getInstance().setSearchFilters(request.getParameter("search"),"name");
                break;

            case "clear" :
                BalootServer.getInstance().clearFilters();
                break;
            case "sort_by_rate" :
                BalootServer.getInstance().setSortFilters("rate");
                break;
        }
        response.sendRedirect("/commodities");
    }
}