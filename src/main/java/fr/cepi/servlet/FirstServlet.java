package fr.cepi.servlet;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet basique
 */
@WebServlet(name = "firstServlet", value = "/firstServlet")
public class FirstServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Première servlet !";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<a href='Login'>Se connecter</a>");
        out.println("</body></html>");
    }


}