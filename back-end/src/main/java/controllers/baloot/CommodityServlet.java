package controllers.baloot;
import Baloot.BalootServer;
import Baloot.Comment;
import Baloot.Commodity;
import Baloot.Managers.CommodityManager;
import Baloot.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Baloot.Managers.UserManager;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CommodityServlet", value = "/commodities/*")
public class CommodityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (BalootServer.getInstance().getLoggedInUser() == null) {
            response.sendRedirect("/login");
            return;
        }
        String[] pathParts = request.getPathInfo().split("/");
        String commodityId = pathParts[1];
        try {
            var commodity = BalootServer.getInstance().getCommodityById(Integer.valueOf(commodityId));
            var similarCommodities = BalootServer.getInstance().getSuggestedCommodities(Integer.valueOf(commodityId));
            request.setAttribute("commodity",commodity);
            request.setAttribute("suggestions",similarCommodities);

        } catch (Exception exception) {

            request.getRequestDispatcher("/404.jsp").forward(request, response);

        }
        User user =  BalootServer.getInstance().getLoggedInUser();
        request.setAttribute("loggedInUser",user);
        request.getRequestDispatcher("/commodity.jsp?commodityId=" + commodityId).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (BalootServer.getInstance().getLoggedInUser() == null) {
            response.sendRedirect("/login");
            return;
        }
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        int commodityId =  Integer.valueOf(pathParts[1]);
        String username = BalootServer.getInstance().getLoggedInUser().getName();

        String action = request.getParameter("action");
        try{
        switch (action) {
            case "rate": //todo commodityController
                String rate = request.getParameter("quantity");
                BalootServer.getInstance().rateCommodity(username,commodityId, rate);
                break;

            case "add":  //todo commodityController
                BalootServer.getInstance().addCommidityToUserBuyList(username,commodityId);
                break;

            case "comment" : //todo commentController
                String useremail = BalootServer.getInstance().getLoggedInUser().getEmail();
                String commentText = request.getParameter("comment");

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                LocalDate localDate = LocalDate.now();
                String currDate = dtf.format(localDate);
                Comment comment = new Comment(useremail,commodityId,commentText,currDate);
                BalootServer.getInstance().addComment(comment);
                break;

            case "like" : //todo commentController
                Integer commentId = Integer.valueOf(request.getParameter("comment_id"));
                BalootServer.getInstance().addRatingToComment(commentId,username,1);
                break;

            case "dislike" : //todo commentController
                commentId = Integer.valueOf(request.getParameter("comment_id"));
                BalootServer.getInstance().addRatingToComment(commentId,username,-1);
                break;

        }


        }
        catch(Exception exception)
        {
            request.setAttribute("errorMSG",exception.getMessage());
            request.getRequestDispatcher("/400.jsp").forward(request, response);
        }
     //   request.getRequestDispatcher("/commodities/" + pathParts[1]).forward(request, response);
        response.sendRedirect("/commodities/" + pathParts[1]);

    }
}