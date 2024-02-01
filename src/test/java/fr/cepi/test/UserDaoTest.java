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

    private static UserDAO dao;

    /**
     * Initialisation du pool de connexion et création du DAO
     */
    @BeforeAll
    public static void init()  {
        try {
            CepiService.setupDriver("src/main/webapp/WEB-INF/db.properties");
            dao = new UserDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test de création, login et suppression d'un utilisateur
     * @throws CepiException
     */
    @Test
    public void test() throws CepiException {
        dao.save(new Utilisateur("a", "b"), "abc");
        Assertions.assertNotNull(dao.login("b", "abc"));
        Assertions.assertNull(dao.login("a", "abc"));
        Assertions.assertNull(dao.login("b", "aaa"));
        dao.delete("b");
        Assertions.assertNull(dao.login("b", "abc"));
    }
}
