package fr.cepi.test;

import fr.cepi.CepiException;
import fr.cepi.bean.Utilisateur;
import fr.cepi.dao.UserDAO;
import fr.cepi.service.CepiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Classe de test de la classe UserDao */
public class UserDaoTest {


    /**
     * Initialisation du pool de connexion et création du DAO
     */
    @BeforeAll
    public static void init()  {
        try {
            CepiService.setupDriver("src/main/webapp/WEB-INF/db.properties");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test de création, login et suppression d'un utilisateur
     * @throws CepiException en cas d'erreur d'accès à la base de données
     */
    @Test
    public void test() throws CepiException {
        UserDAO.INSTANCE.save(new Utilisateur("a", "b"), "abc");
        Assertions.assertNotNull(UserDAO.INSTANCE.login("b", "abc"));
        Assertions.assertNull(UserDAO.INSTANCE.login("a", "abc"));
        Assertions.assertNull(UserDAO.INSTANCE.login("b", "aaa"));
        UserDAO.INSTANCE.delete("b");
        Assertions.assertNull(UserDAO.INSTANCE.login("b", "abc"));
    }
}
