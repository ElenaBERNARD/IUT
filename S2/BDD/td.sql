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