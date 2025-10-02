-- mysql --local-infile=1 --user=drg0n --password=2302 --host=localhost BDD_drg0n

DROP TABLE IF EXISTS TD_EMPLOYE,TD_DEPARTEMENT;

CREATE TABLE  TD_DEPARTEMENT (
    idDept INT,
    nom VARCHAR(255),
    lieu VARCHAR(255),
    PRIMARY KEY(idDept)
);

INSERT INTO TD_DEPARTEMENT (idDept,nom,lieu)  VALUES (10,"recherche","Besançon");
INSERT INTO TD_DEPARTEMENT (idDept,nom,lieu)  VALUES (20,"vente","Montbéliard");
INSERT INTO TD_DEPARTEMENT (idDept,nom,lieu)  VALUES (30,"direction","Belfort");
INSERT INTO TD_DEPARTEMENT (idDept,nom,lieu)  VALUES (40,"fabrication","Sochaux");

CREATE TABLE  TD_EMPLOYE (
    idEmploye INT,
    nom VARCHAR(255),
    fonction VARCHAR(255),
    idResponsable INT,
    date_embauche date,
    salaire NUMERIC(8,2),
    prime NUMERIC(8,2),
    departement_id INT,
    PRIMARY KEY(idEmploye)
);
INSERT INTO TD_EMPLOYE(nom,idEmploye,fonction,idResponsable,date_embauche,salaire,prime,departement_id) VALUES 
('MARTIN',16712,'directeur',25717,'2000-05-23',8000,NULL,30);
INSERT INTO TD_EMPLOYE(nom,idEmploye,fonction,idResponsable,date_embauche,salaire,prime,departement_id) VALUES 
('DUPONT',17574,'administratif',16712,'2005-05-03',1800,NULL,30),
('DUPOND',26691,'commercial',27047,'1998-04-04',5000,500,20),
('LAMBERT',25012,'administratif',27047,'2001-03-14',NULL,2400,20),
('JOUBERT',25717,'president',NULL,'1992-08-10',10000,NULL,30),
('LEBRETON',16034,'commercial',27047,'2001-06-01',3000,0,20),
('MARTIN',17147,'commercial',27047,'2005-05-03',4000,500,20),
('PAQUEL',27546,'commercial',27047,'1993-09-03',4400,1000,20),
('LEFEBVRE',25935,'commercial',27047,'1994-01-11',4700,400,20),
('GARDARIN',15155,'ingenieur',24533,'1995-03-22',4800,NULL,10),
('SIMON',26834,'ingenieur',24533,'1998-10-04',4000,NULL,10),
('DELOBEL',16278,'ingenieur',24533,'2004-11-16',4200,NULL,10),
('ADIBA',25067,'ingenieur',24533,'1997-10-05',6000,NULL,10),
('CODD',24533,'directeur',25717,'1985-11-12',11000,NULL,10),
('LAMERE',27047,'directeur',25717,'1999-09-07',9000,NULL,20),
('BALIN',17232,'administratif',24533,'1997-10-03',2700,NULL,10),
('BARA',24831,'administratif',16712,'1998-11-10',3000,NULL,30),
('toto','00001','livreur de pizzas',NULL,NULL,NULL,NULL,NULL);

SELECT e.nom AS nom, e.fonction AS fonction, d.nom AS nomDepartement
FROM TD_EMPLOYE e
INNER JOIN TD_DEPARTEMENT d
ON e.departement_id=d.idDept
ORDER BY nom ASC;

SELECT COUNT(e.departement_id) AS NombreEmployes, d.nom AS nomDepartement
FROM TD_EMPLOYE e
INNER JOIN TD_DEPARTEMENT d
ON e.departement_id=d.idDept
GROUP BY e.departement_id
ORDER BY nomDepartement ASC;

SELECT COUNT(e.departement_id) AS NombreEmployes, d.nom AS nomDepartement, d.lieu AS lieu
FROM TD_EMPLOYE e
INNER JOIN TD_DEPARTEMENT d
ON e.departement_id=d.idDept
GROUP BY e.departement_id
ORDER BY nomDepartement ASC;

SELECT e.nom AS nomEmploye, e.fonction AS fonctionEmploye, r.nom AS nomResponsable, r.fonction AS fonctionResponsable
FROM TD_EMPLOYE e
INNER JOIN TD_EMPLOYE r
ON r.idEmploye=e.idResponsable
ORDER BY  r.fonction ASC, e.nom ASC;

SELECT e.nom AS nomEmploye, e.fonction AS fonctionEmploye, r.nom AS nomResponsable, r.fonction AS fonctionResponsable
FROM TD_EMPLOYE e
INNER JOIN TD_EMPLOYE r
ON r.idEmploye=e.idResponsable
WHERE r.idResponsable is NULL
ORDER BY  r.fonction ASC, e.nom ASC;

SELECT E1.nom,E1.fonction,E1.salaire
FROM TD_EMPLOYE AS E1 
INNER JOIN TD_EMPLOYE AS E2 ON E1.salaire>E2.salaire
WHERE E2.nom like "SIMON";

SELECT e.nom, e.fonction, e.salaire, e.idResponsable
FROM  TD_EMPLOYE AS e
WHERE e.fonction = (
    SELECT fonction
    FROM TD_EMPLOYE
    WHERE nom = 'CODD');

SELECT e.nom, e.fonction, e.salaire
FROM  TD_EMPLOYE AS e
WHERE e.salaire > (
    SELECT AVG(salaire)
    FROM TD_EMPLOYE);