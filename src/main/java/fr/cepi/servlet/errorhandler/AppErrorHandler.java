package fr.cepi.servlet.errorhandler;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Gestionnaire des pages d'erreur
 */
@WebServlet("/AppErrorHandler")
public class AppErrorHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processError(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processError(request, response);
    }

    @SuppressWarnings("boxing")
    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String servletName = (String) request.getAttribute("jakarta.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "inconnu";
        }
        String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "inconnu";
        }

        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.write("<html><head><title>Exception/Error details</title></head><body>");
        if (statusCode != 500) {
            out.write("<h3>Details</h3>");
            out.write("<strong>Status Code</strong>:" + statusCode + "<br>");
            out.write("<strong>Requested URI</strong>:" + requestUri);
        } else {
            out.write("<h3>Exception details</h3>");
            out.write("<ul><li>Servlet name:" + servletName + "</li>");
            out.write("<li>Class name:" + throwable.getClass().getName() + "</li>");
            out.write("<li>Requested URI:" + requestUri + "</li>");
            out.write("<li>Exception message:" + throwable.getMessage() + "</li>");
            out.write("</ul>");
        }
        throwable.printStackTrace();

        out.write("<br><br>");
        out.write("<a href=\"" + request.getContextPath() + "\">Page d'accueil</a>");
        out.write("</body></html>");
    }
}
