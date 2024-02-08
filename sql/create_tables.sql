-- Création de la table des utilisateurs : à faire sur la base tst
CREATE TABLE utilisateur
(
    id       INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nom      VARCHAR(20) NOT NULL,
    login    VARCHAR(20) NOT NULL UNIQUE,
    salt     BYTEA NOT NULL,
    password BYTEA NOT NULL
);

GRANT ALL ON ALL TABLES IN SCHEMA "public" TO tst;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA "public" TO tst;
