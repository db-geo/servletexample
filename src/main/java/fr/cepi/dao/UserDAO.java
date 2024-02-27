package fr.cepi.dao;

import fr.cepi.CepiException;
import fr.cepi.bean.Utilisateur;
import fr.cepi.service.CepiService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Classe d'accès à la table utilisateur en base de données
 */
public enum UserDAO {
    INSTANCE;
    /**
     * Interrogation d'un utilisateur par son login et vérification de son mot de passe
     *
     * @param login login de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @return l'utilisateur trouvé ou null s'il n'existe pas
     * @throws CepiException en cas d'erreur lors de l'exécution de la requête
     */
    public Utilisateur login(String login, String password) throws CepiException {
        // Sinon, on recherche l'utilisateur en base de données
        try {
            Connection con = CepiService.getConnection();
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM utilisateur WHERE login = ?")) {
                ps.setString(1, login);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Si on l'a trouvé, on vérifie son mot de passe
                        byte[] salt = rs.getBytes("salt");
                        byte[] hash = hashPassword(salt, password);
                        // Est-ce que le hash du mot de passe fourni est le même que celui enregistré en base ?
                        if (Arrays.compare(hash, rs.getBytes("password")) == 0) {
                            return new Utilisateur(rs.getString("nom"), rs.getString("login"),
                                    rs.getInt("id"));
                        }
                    }
                    return null;
                }
            }
        } catch (Exception e) {
            throw new CepiException("Erreur lors de l'interrogation d'un utilisateur", e);
        }
    }


    /**
     * Enregistrement d'un nouvel utilisateur en base de données
     *
     * @param user utilisateur à créer
     * @param password mot de passe
     * @throws CepiException en cas d'erreur lors de l'exécution de la requête
     */
    public void save(Utilisateur user, String password) throws CepiException {
        try {
            Connection con = CepiService.getConnection();
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO utilisateur(nom, login, salt, password) VALUES (?, ?, ?, ?)")) {
                ps.setString(1, user.getNom());
                ps.setString(2, user.getLogin());

                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);
                ps.setBytes(3, salt);
                ps.setBytes(4, hashPassword(salt, password));
                ps.execute();
            }
        } catch (Exception e) {
            throw new CepiException("Erreur lors de la création d'un utilisateur", e);
        }
    }


    /**
     * Supprime un utilisateur en base de données à partir de son login
     *
     * @param login de l'utilisateur à supprimer
     * @throws CepiException en cas d'erreur lors de l'exécution de la requête
     */
    public void delete(String login) throws CepiException {
        try {
            Connection con = CepiService.getConnection();
            try (PreparedStatement ps = con.prepareStatement("DELETE FROM utilisateur WHERE login = ?")) {
                ps.setString(1, login);
                ps.execute();
            }
        } catch (Exception e) {
            throw new CepiException("Erreur lors de la suppression d'un utilisateur", e);
        }
    }


    /**
     * Renvoie le hash d'un mot de passe avec du sel
     *
     * @param salt sel
     * @param password mot de passe
     * @return le hash du mot de passe
     * @throws NoSuchAlgorithmException En cas d'erreur de hash
     * @throws InvalidKeySpecException En cas d'erreur de hash
     */
    private byte[] hashPassword(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Augmenter iteration count pour plus de sécurité
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1024, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }
}
