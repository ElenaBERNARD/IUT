DROP TABLE IF EXISTS utilisateurs;
CREATE TABLE utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    age INT,
    majeur INT
);

-- Création de la procédure stockée
DELIMITER //
CREATE PROCEDURE AjouterUtilisateur(IN nom_utilisateur VARCHAR(50), IN prenom_utilisateur VARCHAR(50), IN age_utilisateur INT)
BEGIN
    INSERT INTO utilisateurs (nom, prenom, age) VALUES (UPPER(nom_utilisateur), LOWER(prenom_utilisateur), age_utilisateur);
END //
DELIMITER ;

-- Appel de la procédure
CALL AjouterUtilisateur('Doe', 'John', 25);
CALL AjouterUtilisateur('Doe', 'Jane', 30);

-- Affichage du contenu de la table utilisateurs
SELECT * FROM utilisateurs;

DELIMITER //

CREATE FUNCTION CalculerMoyenne(a DECIMAL(5,2), b DECIMAL(5,2))
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN
    DECLARE moyenne DECIMAL(5,2);
    SET moyenne = (a + b) / 2;
    RETURN moyenne;
END //

DELIMITER ;


-- Utilisation de la fonction
SELECT CalculerMoyenne(10, 20) AS MoyenneSet1;
SELECT CalculerMoyenne(5, 16.3333) AS MoyenneSet2;

DELIMITER //
CREATE PROCEDURE VerifierAge(IN id_utilisateur INT, IN age_utilisateur INT)
BEGIN
    UPDATE utilisateurs SET age=age_utilisateur WHERE id=id_utilisateur;
    IF age_utilisateur >= 18 THEN
        UPDATE utilisateurs SET majeur=1 WHERE id=id_utilisateur;
    ELSE
        UPDATE utilisateurs SET majeur=0 WHERE id=id_utilisateur;
    END IF;
END //
DELIMITER ;

-- Appel de la procédure
CALL VerifierAge(1, 20);
CALL VerifierAge(2, 15);
SELECT * FROM utilisateurs;

CREATE TABLE nombres (
    valeur INT
);

-- Insertion des valeurs de 1 à 10
DELIMITER //
CREATE PROCEDURE RemplirNombres()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 10 DO
        INSERT INTO nombres (valeur) VALUES (i);
        SET i = i + 1;
    END WHILE;
END //
DELIMITER ;

-- Appel de la procédure
CALL RemplirNombres();

-- Affichage du contenu de la table
SELECT * FROM nombres;

DROP TABLE IF EXISTS historique_utilisateurs;
CREATE TABLE historique_utilisateurs (
    id_utilisateur INT AUTO_INCREMENT PRIMARY KEY,
    nom_utilisateur VARCHAR(50),
    action VARCHAR(50)
);

-- Création du déclencheur
DELIMITER //
CREATE TRIGGER after_insert_utilisateur
AFTER INSERT ON utilisateurs
FOR EACH ROW
BEGIN
    INSERT INTO historique_utilisateurs (id_utilisateur, nom_utilisateur, action)
    VALUES (NEW.id, NEW.nom, 'Ajout');
END //
DELIMITER ;


-- Ajout d'un utilisateur pour déclencher le déclencheur
INSERT INTO utilisateurs (nom, age) VALUES ('lionel', 28);

-- Affichage du contenu de la table historique_utilisateurs
SELECT * FROM historique_utilisateurs;
SELECT * FROM utilisateurs;

DELIMITER //

CREATE TRIGGER before_insert_utilisateurs
BEFORE INSERT ON utilisateurs
FOR EACH ROW
BEGIN
    SET NEW.nom = UPPER(NEW.nom);
    SET NEW.prenom = LOWER(NEW.prenom);
END //
DELIMITER ;

INSERT INTO utilisateurs (nom, prenom, age) VALUES ('Messi', 'Lionel', 28);


SELECT * FROM utilisateurs;