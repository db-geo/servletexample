package fr.cepi.servlet;

import fr.cepi.CepiException;
import fr.cepi.bean.Utilisateur;
import fr.cepi.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Servlet pour l'enregistrement d'un nouvel utilisateur
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger logger = LogManager.getLogger(RegisterServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération des valeurs des champs du formulaire
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String nom = request.getParameter("nom");
        // Quelques contrôles
        String errorMsg = null;
        if (login == null || login.isEmpty()) {
            errorMsg = "Le login est obligatoire.";
        }
        if (password == null || password.isEmpty()) {
            errorMsg = "Le mot de passe est obligatoire";
        }
        if (nom == null || nom.isEmpty()) {
            errorMsg = "Le nom est obligatoire";
        }
        // S'il y a des erreurs, on met le message en attribut de la requête et on renvoie sur la page de login
        if (errorMsg != null) {
            request.setAttribute("errorMessage", errorMsg);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
        } else {
            try {
                Utilisateur user = new Utilisateur(nom, login);
                UserDAO.INSTANCE.save(user, password);
                logger.info("Utilisateur enregistré avec le login " + login);
                // On affiche la page d'accueil
                request.setAttribute("message",
                        "Enregistrement effectué avec succès, veuillez vous identifier.");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            } catch (CepiException e) {
                // Sinon, log de l'erreur et renvoi sur la vue login.jsp avec un message d'erreur
                request.setAttribute("errorMessage", "Erreur technique : veuillez contacter l'administrateur de l'application.");
                getServletContext().getRequestDispatcher("/WEB-INF/WEB-INF/jsp/login.jsp").forward(request, response);
            }
        }
    }
}
