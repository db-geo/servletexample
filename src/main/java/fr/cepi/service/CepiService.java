package fr.cepi.service;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe proposant des services transverses pour l'application
 */
public class CepiService {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:apache:commons:dbcp:MyPool");
    }

    /**
     * Crée un driver JDBC pour un pool de connexion nommé
     * "jdbc:apache:commons:dbcp:MyPool"
     */
    public static void setupDriver(String propFile) throws Exception {

        Properties properties = new Properties();
        properties.load(new FileReader(propFile));
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

    /**
     * Initialise le système de logging à partir d'un fichier de configuration
     *
     * @param propFile chemin du fichier de configuration
     */
    public static void setupLogger(String propFile) throws IOException {
        try (LoggerContext ignored = Configurator.initialize(null,
                new ConfigurationSource(new FileInputStream(propFile)))) {
        }

    }
}
