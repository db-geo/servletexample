package fr.cepi.test;

import fr.cepi.bean.Utilisateur;
import fr.cepi.dao.UserDAO;
import fr.cepi.service.CepiService;
import fr.cepi.servlet.LoginServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Exemple de test des Servlets avec mockito
 */
public class TestLoginServlet extends Mockito {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher dispatcherLogin;
    @Mock
    ServletContext context;
    @Mock
    HttpSession session;
    @Mock
    ServletConfig config;

    @Test
    public void testLoginServlet() throws Exception {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {

            when(request.getSession()).thenReturn(session);

            CepiService.setupDriver("src/main/webapp/WEB-INF/db.properties");
            // Crée un user aaa
            CepiService.getConnection();
            UserDAO.INSTANCE.save(new Utilisateur("aaa", "aaa"), "bbb");
        }


        LoginServlet servlet = new LoginServlet();
        servlet.init(config);
        when(servlet.getServletContext()).thenReturn(context);

        when(servlet.getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp")).thenReturn(dispatcherLogin);

        // Avec des identifiants qui n'existent pas
        when(request.getParameter("login")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("pwd");
        servlet.doPost(request, response);

        verify(dispatcherLogin).forward(request, response);
        verify(request, atLeast(1)).getParameter("login");
        verify(request, atLeast(1)).getParameter("password");


        // Avec des identifiants qui existent
        when(request.getParameter("login")).thenReturn("aaa");
        when(request.getParameter("password")).thenReturn("bbb");
        servlet.doPost(request, response);

        Mockito.verify(response, Mockito.times(1)).sendRedirect("Home");
        verify(request, atLeast(1)).getParameter("login");
        verify(request, atLeast(1)).getParameter("password");

        // Cleanup
        UserDAO.INSTANCE.delete("aaa");

    }
}