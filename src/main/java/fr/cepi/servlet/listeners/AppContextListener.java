package fr.cepi.servlet.listeners;

import fr.cepi.service.DatabaseService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Listener déclencher lors de l'initialisation deu contexte de l'application : configure le logger et initialise le
 * pool de connexions
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Initialisation du context");
        ServletContext ctx = servletContextEvent.getServletContext();

        String webAppPath = ctx.getRealPath("/");

        // Initialisation du pool de connexion
        try {
            Properties props = new Properties();
            props.load(new FileReader(webAppPath + "WEB-INF/db.properties"));
            setupDriver(props);
            DatabaseService.getConnection();
            System.out.println("Test connexion base de données : OK");
        } catch (Exception e) {
            System.err.println("Connexion base de données : KO. " + e.getMessage());
        }

        // initialisation de log4j2
        String log4jProp = webAppPath + "WEB-INF/log4j2.xml";
        try (LoggerContext ignored = Configurator.initialize(null,
                new ConfigurationSource(new FileInputStream(log4jProp)))) {
            System.out.println("Configuration log4j : OK");
        } catch (Exception e) {
            System.err.println("Connection log4j : KO. " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            destroyDriver();
        } catch (Exception ignored) {
        }
    }

    /**
     * Crée un driver JDBC pour un pool de connexion nommé
     * "jdbc:apache:commons:dbcp:MyPool"
     */
    public static void setupDriver(Properties properties) throws Exception {

        ConnectionFactory cf = new DriverManagerConnectionFactory(properties.getProperty("url"), properties);
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
        ObjectPool<PoolableConnection> op = new GenericObjectPool<>(pcf);
        pcf.setPool(op);
        Class.forName("org.apache.commons.dbcp2.PoolingDriver");
        Class.forName(properties.getProperty("driver"));
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.registerPool("MyPool", op);
        System.out.println("Initialisation pool de connexion : OK");
    }

    public static void destroyDriver() throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.closePool("MyPool");
    }

}
