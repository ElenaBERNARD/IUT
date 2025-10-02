-- mysql --local-infile=1 --user=drg0n --password=2302 --host=localhost BDD_drg0n
-- mysqldump --host=localhost --user=drg0n --password=2302  BDD_drg0n --no-tablespaces > base.sql
-- mysqldump --host=localhost --user=drg0n --password=2302  BDD_drg0n --no-tablespaces > base_$(date +%Y-%m-%d-%H.%M.%S).sql
DROP TABLE IF EXISTS LIGNE;
DROP TABLE IF EXISTS COMMANDE;
DROP TABLE IF EXISTS ARTICLE;
DROP TABLE IF EXISTS CLIENT;

CREATE TABLE IF NOT EXISTS ARTICLE (
    idArticle INT NOT NULL AUTO_INCREMENT,
    designation VARCHAR(255),
    prix DECIMAL(10, 2),
    PRIMARY KEY (idArticle)
)CHARACTER SET 'utf8mb4';

CREATE TABLE IF NOT EXISTS CLIENT (
    idClient INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(100),
    ville VARCHAR(50),
    PRIMARY KEY (idClient)
)CHARACTER SET 'utf8mb4';

CREATE TABLE IF NOT EXISTS COMMANDE (
    idCommande INT NOT NULL AUTO_INCREMENT,
    dateCommande DATE,
    idClient INT,
    PRIMARY KEY (idCommande),
    FOREIGN KEY fk_commande_client (idClient) REFERENCES CLIENT(idClient)
);

CREATE TABLE IF NOT EXISTS LIGNE (
    idCommande INT,
    idArticle INT,
    quantite INT NOT NULL,
    PRIMARY KEY (idCommande, idArticle),
    FOREIGN KEY fk_ligne_commande (idCommande) REFERENCES COMMANDE(idCommande),
    FOREIGN KEY fk_ligne_article (idArticle) REFERENCES ARTICLE(idArticle)
);


LOAD DATA LOCAL INFILE 'data-article.csv' INTO TABLE ARTICLE FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'data-client.csv' INTO TABLE CLIENT FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'data-commande.csv' INTO TABLE COMMANDE FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'data-ligne.csv' INTO TABLE LIGNE FIELDS TERMINATED BY ',';


-- ALTER TABLE COMMANDE DROP FOREIGN KEY fk_commande_client;
-- ALTER TABLE LIGNE DROP FOREIGN KEY fk_ligne_commande;
-- ALTER TABLE LIGNE DROP FOREIGN KEY fk_ligne_article;

-- ALTER TABLE COMMANDE ADD CONSTRAINT fk_commande_client FOREIGN KEY (idClient)
-- REFERENCES CLIENT (idClient) ON DELETE CASCADE;
-- ALTER TABLE LIGNE ADD CONSTRAINT fk_ligne_commande FOREIGN KEY (idCommande)
-- REFERENCES COMMANDE (idCommande) ON DELETE CASCADE;
-- ALTER TABLE LIGNE ADD CONSTRAINT fk_ligne_article FOREIGN KEY (idArticle)
-- REFERENCES ARTICLE (idArticle) ON DELETE CASCADE;

-- SHOW CREATE table COMMANDE;
-- SHOW CREATE table LIGNE;

-- DELETE FROM CLIENT WHERE nom LIKE "Mutz";

-- Requete 1
SELECT nom
FROM CLIENT
WHERE (SUBSTRING(nom, 1, 1) IN ('M', 'm', 'E', 'e', 'D', 'd'))
AND ville LIKE 'Belfort%'
ORDER BY nom;

-- Requete 2
SELECT designation, prix
FROM ARTICLE
WHERE prix BETWEEN 6 AND 10
AND designation LIKE '%lég%';

-- Requete 3
SELECT CLIENT.nom, COMMANDE.dateCommande
FROM COMMANDE
INNER JOIN CLIENT
ON COMMANDE.idClient=CLIENT.idClient
WHERE CLIENT.nom LIKE 'Mutz';

-- Requete 4
SELECT CLIENT.nom, ARTICLE.designation, ARTICLE.prix, LIGNE.quantite, COMMANDE.idCommande
FROM CLIENT
INNER JOIN COMMANDE
ON CLIENT.idClient=COMMANDE.idClient
INNER JOIN LIGNE
ON COMMANDE.idCommande=LIGNE.idCommande
INNER JOIN ARTICLE
ON LIGNE.idArticle=ARTICLE.idArticle
WHERE CLIENT.nom LIKE 'Mutz';

-- Requete 5
SELECT CLIENT.nom, ARTICLE.designation, LIGNE.idCommande, LIGNE.quantite*ARTICLE.prix prix_total
FROM CLIENT
INNER JOIN COMMANDE
ON CLIENT.idClient=COMMANDE.idClient
INNER JOIN LIGNE
ON COMMANDE.idCommande=LIGNE.idCommande
INNER JOIN ARTICLE
ON LIGNE.idArticle=ARTICLE.idArticle
WHERE CLIENT.nom LIKE 'Mutz';

-- Requete 6
SELECT CLIENT.nom, LIGNE.idCommande, SUM(LIGNE.quantite*ARTICLE.prix) prix_total
FROM CLIENT
INNER JOIN COMMANDE
ON CLIENT.idClient=COMMANDE.idClient
INNER JOIN LIGNE
ON COMMANDE.idCommande=LIGNE.idCommande
INNER JOIN ARTICLE
ON LIGNE.idArticle=ARTICLE.idArticle
WHERE CLIENT.nom LIKE 'Mutz'
GROUP BY LIGNE.idCommande;

-- Requete 7
SELECT CLIENT.nom, LIGNE.idCommande, SUM(LIGNE.quantite*ARTICLE.prix) prix_total_HT, SUM(LIGNE.quantite*ARTICLE.prix)*0.2 TVA, SUM(LIGNE.quantite*ARTICLE.prix)*1.2 prix_total_TTC
FROM CLIENT
INNER JOIN COMMANDE
ON CLIENT.idClient=COMMANDE.idClient
INNER JOIN LIGNE
ON COMMANDE.idCommande=LIGNE.idCommande
INNER JOIN ARTICLE
ON LIGNE.idArticle=ARTICLE.idArticle
GROUP BY LIGNE.idCommande
ORDER BY prix_total_TTC DESC;

-- Requete 8
SELECT ARTICLE.designation, LIGNE.quantite, COMMANDE.dateCommande, COMMANDE.idCommande
FROM ARTICLE
INNER JOIN LIGNE
ON ARTICLE.idArticle=LIGNE.idArticle
INNER JOIN COMMANDE
ON COMMANDE.idCommande=LIGNE.idCommande;

-- Requete 9
SELECT A.designation, L.quantite, YEAR(C.dateCommande) AS anneeCommande, L.idCommande
FROM LIGNE L
INNER JOIN COMMANDE C 
ON L.idCommande = C.idCommande
INNER JOIN ARTICLE A 
ON L.idArticle = A.idArticle
WHERE YEAR(C.dateCommande) = 2023
AND L.idArticle IN (SELECT DISTINCT idArticle
FROM LIGNE
WHERE idCommande IN (
SELECT idCommande
FROM COMMANDE
WHERE YEAR(dateCommande) = 2024));