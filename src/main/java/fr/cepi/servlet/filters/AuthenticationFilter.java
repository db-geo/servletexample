package fr.cepi.servlet.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.util.List;

/**
 * Filtre appliqué sur toutes les URL : vérifie que l'utilisateur est authentifié pour accéder à toutes les pages sauf
 * /, /Login et /Registrer. <br/>
 * L'accès aux fichiers du répertoire css est également permis sans être authentifié.
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public static final String SESSION_USER_KEY = "utilisateur";
    private final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig fConfig) {
        logger.info("Initialisation du filtre d'Authentification");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        logger.info("URI : " + uri);
        HttpSession session = req.getSession(false);
        // Extraction de la partie suivant le contexte de l'applicaiton dans l'URL, sans le / final
        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("/+$", "");
        // OK pour fichiers du réperotire css
        if (path.startsWith("/css/")) {
            // poursuit par le prochain filtre
            chain.doFilter(request, response);
            return;
        }
        // Est-ce que l'utilisateur est authentifié ? Oui si sa session contient une entrée user
        boolean isAuthenticated = session != null && session.getAttribute(SESSION_USER_KEY) != null;
        List<String> authorizedServlets = List.of("", "/manifest.json", "/Login", "/Register");
        // Redirection vers la page de login s'il n'est pas authentifié et devrait l'être pour accéder à la page demandée
        if (!authorizedServlets.contains(path) && !isAuthenticated) {
            System.out.println("Session null");
            // Affichage de la page de connexion
            req.getServletContext().getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        } else {
            // poursuit par le prochain filtre
            chain.doFilter(request, response);
        }
    }
}
