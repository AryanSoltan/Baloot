package controllers.baloot;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import Baloot.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import Baloot.Managers.UserManager;
import ExternalServer.ExternalServer;

//todo filters............
//todo suggestions 3 or 5?
//todo sort based on rate or price?


@WebServlet(name = "HomeServlet", value = "")
public class HomeServlet extends HttpServlet {
    public void init() {
        String externalServerAddress = "http://5.253.25.110:5000";
        BalootServer balootServer =  BalootServer.getInstance();
        ExternalServer externalServer = new ExternalServer(externalServerAddress, balootServer);


    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserManager.isAnyBodyLoggedIn()) {
            User user =  BalootServer.getInstance().getLoggedInUser();
            request.setAttribute("loggedInUser",user);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else {
            response.sendRedirect("/login");
        }

    }

    }
