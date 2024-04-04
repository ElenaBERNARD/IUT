DROP TABLE IF EXISTS TD_telephone, TD_marque_tel, TD_type_tel;

CREATE TABLE IF NOT EXISTS TD_type_tel (
    type_id VARCHAR(2),
    type_nom VARCHAR(50),
    PRIMARY KEY(type_id)
);

CREATE TABLE IF NOT EXISTS TD_marque_tel (
    marque_id INT NOT NULL AUTO_INCREMENT,
    libelle VARCHAR(25),
    pays VARCHAR(25),
    PRIMARY KEY(marque_id)
);

CREATE TABLE IF NOT EXISTS TD_telephone (
    idTelephone INT NOT NULL AUTO_INCREMENT,
    type_id VARCHAR(2),
    marque_id INT,
    date_achat DATE,
    prix DECIMAL(10,2),
    proprietaire_id INT,
    couleur VARCHAR(50),
    PRIMARY KEY(idTelephone),
    CONSTRAINT fk_telephone_type_tel FOREIGN KEY (type_id) REFERENCES TD_type_tel(type_id),
    CONSTRAINT fk_telephone_marque_tel FOREIGN KEY (marque_id) REFERENCES TD_marque_tel(marque_id)
);

INSERT INTO TD_type_tel VALUES ('SP','Smartphone');
INSERT INTO TD_type_tel VALUES ('CL','clapet');
INSERT INTO TD_type_tel VALUES ('CO','COULISSANT');
INSERT INTO TD_type_tel VALUES ('IP','IPHONE');
INSERT INTO TD_type_tel VALUES ('AU','AUTRE');


INSERT INTO TD_marque_tel VALUES (NULL, 'société SAMSUNG','COREE');
INSERT INTO TD_marque_tel VALUES (NULL, 'entreprise SONY','JAPON');
INSERT INTO TD_marque_tel VALUES (NULL, 'groupe PHILIPS','PAYS BAS');
INSERT INTO TD_marque_tel VALUES (NULL, 'marque MOTOROLA','USA');
INSERT INTO TD_marque_tel VALUES (NULL, 'SOCIETE APPLE','USA');

INSERT INTO TD_telephone (idTelephone,type_id,marque_id,date_achat,prix,proprietaire_id,couleur) VALUES 
(1,'SP' ,1,STR_TO_DATE('15/01/2020', '%d/%m/%Y'),139.99,190120,'ROUGE');
INSERT INTO TD_telephone (idTelephone,type_id,marque_id,date_achat,prix,proprietaire_id) VALUES 
(NULL,'SP' ,2,STR_TO_DATE('14/03/2020', '%d/%m/%Y'), 99.99,190215);
INSERT INTO TD_telephone (idTelephone,type_id,marque_id,date_achat,prix,proprietaire_id,couleur) VALUES
(NULL,'CL' ,3,STR_TO_DATE('02/05/2020', '%d/%m/%Y'), 49.11,190001,'NOIR');
INSERT INTO TD_telephone
(idTelephone,type_id,marque_id,date_achat,prix,proprietaire_id,couleur) VALUES
(NULL,'CO' ,4,STR_TO_DATE('25/07/2020', '%d/%m/%Y'), 89.14,190222,'BLANC');
INSERT INTO TD_telephone
(idTelephone,type_id,marque_id,date_achat,prix,proprietaire_id) VALUES
(NULL,'IP' ,5,STR_TO_DATE('30/09/2020', '%d/%m/%Y'),359.49,190561);
INSERT INTO TD_telephone (idTelephone,type_id,marque_id,date_achat,prix,proprietaire_id,couleur) VALUES
(NULL,'CO' ,5,STR_TO_DATE('01/01/2021', '%d/%m/%Y'), 99.51,122120,'BLANC'),
(NULL,'SP' ,1,'2013-01-15',189,190622,'ROUGE'),
(NULL,NULL ,NULL,'2013-01-15',20,190623,'ROUGE'),
(NULL,NULL ,1,'2013-01-15',NULL,NULL,NULL);

SELECT IFNULL(concat(type_id, ' - ', IFNULL(couleur, 'NC')), 'NV') AS votreFonction
FROM TD_telephone;

SELECT 
COALESCE(concat(type_id, ' - ', COALESCE(couleur, 'NC')), 'NV') AS votreFocntion
FROM TD_telephone;

-- REQUETE 1

SELECT tel.idTelephone, m.libelle , t.libelle , tel.prix , tel.date_achat, DATE_ADD(tel.date_achat, INTERVAL 1 YEAR) AS date_fin_garantie 
FROM TD_telephone AS tel 
INNER JOIN TD_type_tel AS t 
ON tel.type_id = t.idType 
INNER JOIN TD_marque_tel AS m 
ON tel.marque_id = m.idMarque 
ORDER BY tel.date_achat;

-- REQUETE 2

SELECT tel.idTelephone , m.libelle , t.libelle , tel.prix , tel.date_achat , 
CASE 
    WHEN m.libelle LIKE ‘%SAMSUNG%’ THEN DATE_ADD(tel.date_achat, INTERVAL 2 YEAR) 
    WHEN m.libelle like ‘%APPLE%’ THEN DATE_ADD(tel.date_achat, INTERVAL 3 YEAR) 
    ELSE DATE_ADD(tel.date_achat, INTERVAL 1 YEAR) END 
    AS date_fin_garantie 
FROM TD_telephone AS tel 
INNER JOIN TD_type_tel AS t 
ON tel.type_id = t.idType 
INNER JOIN TD_marque_tel AS m 
ON tel.marque_id = m.idMarque 
ORDER BY tel.date_achat;

-- REQUETE 3

SELECT tel.proprietaire_id, m.libelle , t.libelle, tel.prix , tel.date_achat , DATEDIFF(CURDATE(), DATE_ADD(tel.date_achat, INTERVAL 2 YEAR)) as calcul_tmp, CASE WHEN m.libelle like ‘%SAMSUNG%’ THEN prix0.07 WHEN m.libelle like ‘%APPLE%’ THEN prix0.1 ELSE prix*0.05 END AS remise FROM TD_telephone AS tel INNER JOIN TD_type_tel AS t ON tel.type_id = t.idType INNER JOIN TD_marque_tel AS m ON tel.marque_id = m.idMarque WHERE prix IS NOT NULL AND DATEDIFF(CURDATE(), DATE_ADD(date_achat, INTERVAL 5 YEAR)) < 0 ORDER BY tel.date_achat;

SELECT tel.proprietaire_id, m.libelle , t.libelle, tel.prix , CONCAT( LPAD(CAST(DAY(date_achat) AS CHAR(2)),2,0),‘/’, LPAD(CAST(MONTH(date_achat) AS CHAR(2)),2,0),‘/’,YEAR(date_achat)) AS DATE_ACHAT_FR FROM TD_telephone AS tel INNER JOIN TD_type_tel AS t ON tel.type_id = t.idType INNER JOIN TD_marque_tel AS m ON tel.marque_id = m.idMarque ORDER BY tel.date_achat;