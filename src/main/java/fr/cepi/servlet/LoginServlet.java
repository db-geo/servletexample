package fr.cepi.servlet;

import fr.cepi.CepiException;
import fr.cepi.bean.Utilisateur;
import fr.cepi.dao.UserDAO;
import fr.cepi.servlet.filters.AuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/** Servlet pour la page d'authentification */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // récupérations des paramètres de la requêtes : ici les champs input du formulaire
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        // Quelques contrôles
        String errorMsg = null;
        if (login == null || login.isEmpty()) {
            errorMsg = "Le login est obligatoire";
        }
        if (password == null || password.isEmpty()) {
            errorMsg = "Le mot de passe est obligatoire";
        }
        // S'il y a des erreurs, on met le message en attribut de la requête et on renvoie sur la page de login
        if (errorMsg != null) {
            request.setAttribute("errorMessage", errorMsg);
            getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } else {
            // Sinon, on recherche l'utilisateur en base de données
            try {
                Utilisateur user = new UserDAO().login(login, password);
                if (user!=null) {
                    // Si on l'a trouvé, on l'indique dans le log
                    logger.info("Utilisateur trouvé :" + user);
                    // Puis, on met l'objet utilisateur dans la session
                    HttpSession session = request.getSession();
                    session.setAttribute(AuthenticationFilter.SESSION_USER_KEY, user);
                    // et on renvoie sur Home
                    response.sendRedirect("Home");
                } else {
                    // Sinon, message d'erreur dans la requête pour affichage dans la vue login.jsp.
                    logger.error("Utilisateur introuvable : " + login);
                    request.setAttribute("errorMessage", "Combinaison incorrecte.");
                    getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                }
            } catch (CepiException e) {
                // Sinon renvoie sur la vue login.jsp avec un message d'erreur
                request.setAttribute("errorMessage", "Erreur technique : veuillez contacter l'administrateur de l'application.");
                getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            }
        }
    }

}
