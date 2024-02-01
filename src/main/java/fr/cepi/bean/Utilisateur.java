package fr.cepi.bean;

import java.io.Serializable;

/**
 * Classe représentant un utilisateur de l'application
 */
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 6297385302078200511L;

    private int id;
    private String nom;
    private String login;


    public Utilisateur(String nom, String login) {
        this.nom = nom;
        this.login = login;
    }

    public Utilisateur(String nom, String login, int id) {
        this(nom, login);
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getNom() {
        return nom;
    }

    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
    }


    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", nom=" + nom + ", login=" + login + "]";
    }

}
