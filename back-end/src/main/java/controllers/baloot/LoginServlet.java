package controllers.baloot;


import java.io.*;

import Baloot.BalootServer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import Baloot.Managers.UserManager;



@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            BalootServer.getInstance().logIn(username,password);
            response.sendRedirect("http://localhost:8080/");
          //  request.getRequestDispatcher("/").forward(request, response);
        } catch (Exception exception) {
            request.setAttribute("errorMSG",exception.getMessage().toString());

            request.getRequestDispatcher("/400.jsp").forward(request, response);
        }
    }
}
