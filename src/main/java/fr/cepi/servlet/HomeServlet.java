package fr.cepi.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Servlet pour la page d'accueil
 */
@WebServlet({"/Home"})
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LogManager.getLogger(HomeServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/home.jsp").forward(request,response);
    }
}
