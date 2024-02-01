package fr.cepi;

import fr.cepi.servlet.LoginServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CepiException extends Exception {

    static Logger logger = LogManager.getLogger(LoginServlet.class);

    public CepiException() {
    }

    public CepiException(String message) {
        super(message);
        logger.error(message);
    }

    public CepiException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

    public CepiException(Throwable cause) {
        super(cause);
        logger.error(cause);
    }

    public CepiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
