package fr.cepi.servlet;

import fr.cepi.servlet.filters.AuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Servlet pour la déconnexion d'un utilisateur
 */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger logger = LogManager.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    logger.info("JSESSIONID=" + cookie.getValue());
                    break;
                }
            }
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info("Logout de l'utilisateur " + session.getAttribute(AuthenticationFilter.SESSION_USER_KEY));
            session.invalidate();
        }
        // redirection vers la page d'accueil
        response.sendRedirect("Login");
    }

}
