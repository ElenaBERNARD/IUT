-- mysql --local-infile=1 --user=drg0n --password=2302 --host=localhost BDD_drg0n
-- mysqldump --host=localhost --user=drg0n --password=2302  BDD_drg0n --no-tablespaces > base.sql
-- mysqldump --host=localhost --user=drg0n --password=2302  BDD_drg0n --no-tablespaces > base_$(date +%Y-%m-%d-%H.%M.%S).sql

DROP TABLE IF EXISTS classement, skieur, comporte, specialite, competition, station;

CREATE TABLE IF NOT EXISTS station (
    idStation INT PRIMARY KEY AUTO_INCREMENT,
    nomStation VARCHAR(100),
    altitude INT,
    pays VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS competition (
    idCompetition INT PRIMARY KEY AUTO_INCREMENT,
    libelleCompet VARCHAR(100),
    dateComp DATE,
    station_id INT,
    FOREIGN KEY (station_id) REFERENCES station(idStation)
);

CREATE TABLE specialite (
    idSpecialite INT PRIMARY KEY AUTO_INCREMENT,
    libelleSpecialite VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS comporte (
    competition_id INT,
    specialite_id INT,
    PRIMARY KEY (competition_id, specialite_id),
    FOREIGN KEY (competition_id) REFERENCES competition(idCompetition),
    FOREIGN KEY (specialite_id) REFERENCES specialite(idSpecialite)
);

CREATE TABLE IF NOT EXISTS skieur (
    idSkieur INT PRIMARY KEY AUTO_INCREMENT,
    nomSkieur VARCHAR(100),
    specialite_id INT,
    station_id INT,
    FOREIGN KEY (specialite_id) REFERENCES specialite(idSpecialite),
    FOREIGN KEY (station_id) REFERENCES station(idStation)
);

CREATE TABLE IF NOT EXISTS classement (
    skieur_id INT,
    competition_id INT,
    classement INT,
    PRIMARY KEY (skieur_id, competition_id),
    FOREIGN KEY (skieur_id) REFERENCES skieur(idSkieur),
    FOREIGN KEY (competition_id) REFERENCES competition(idCompetition)
);

LOAD DATA LOCAL INFILE 'donnees/STATION.csv' INTO TABLE station FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'donnees/COMPETITION.csv' INTO TABLE competition FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'donnees/SPECIALITE.csv' INTO TABLE specialite FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'donnees/COMPORTE.csv' INTO TABLE comporte FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'donnees/SKIEUR.csv' INTO TABLE skieur FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'donnees/CLASSEMENT.csv' INTO TABLE classement FIELDS TERMINATED BY ',';

-- Requete 1
SELECT COUNT(DISTINCT skieur_id)
FROM classement;

-- Requete 2
SELECT nomSkieur, nomStation
FROM skieur
INNER JOIN station
ON idStation=station_id
ORDER BY nomStation;

-- Requete 3
SELECT skieur.nomSkieur, classement.classement, competition.libelleCompet
FROM classement
INNER JOIN skieur
ON skieur_id=idSkieur
INNER JOIN competition
ON competition_id=idCompetition
WHERE competition.libelleCompet LIKE '%compet%'
ORDER BY competition.libelleCompet;

-- Requete 4
SELECT competition.libelleCompet, skieur.nomSkieur 
FROM classement
INNER JOIN skieur
ON skieur_id=idSkieur
INNER JOIN competition
ON competition_id=idCompetition
INNER JOIN station
ON competition.station_id=idStation
WHERE classement.classement=1
AND station.nomStation LIKE 'Tignes';

-- Requete 5
SELECT station.idStation, station.nomStation, COUNT(competition.station_id) nbrDeCompet
FROM competition
INNER JOIN station
ON station_id=idStation
GROUP BY idStation
ORDER BY station.nomStation;

-- Requete 6
SELECT skieur.idSkieur, skieur.nomSkieur, COUNT(skieur.idSkieur) NbreDeVictoires 
FROM classement
INNER JOIN skieur
ON skieur_id=idSkieur
INNER JOIN competition
ON competition_id=idCompetition
INNER JOIN station
ON competition.station_id=idStation
WHERE classement.classement=1
AND station.nomStation LIKE 'Tignes'
GROUP BY skieur.idSkieur;

-- Requete 7
SELECT DISTINCT s.nomSkieur
FROM classement c
INNER JOIN skieur s
ON c.skieur_id=s.idSkieur
WHERE c.classement = 1
AND s.idSkieur NOT IN (
    SELECT c.skieur_id
    FROM classement c
    WHERE c.classement != 1
);



DELETE FROM classement;
DELETE FROM comporte;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE competition;
SET FOREIGN_KEY_CHECKS = 1;

LOAD DATA LOCAL INFILE 'donnees/COMPETITIONv2.csv' INTO TABLE competition FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'donnees/COMPORTEv2.csv' INTO TABLE comporte FIELDS TERMINATED BY ',';
LOAD DATA LOCAL INFILE 'donnees/CLASSEMENTv2.csv' INTO TABLE classement FIELDS TERMINATED BY ',';



-- Requete 8
SELECT DISTINCT sk.nomSkieur, cl.classement, st.nomStation, club.nomStation
FROM classement cl
INNER JOIN skieur sk
ON cl.skieur_id=sk.idSkieur
INNER JOIN competition cp
ON cl.competition_id=cp.idCompetition
INNER JOIN station st
ON cp.station_id=st.idStation
INNER JOIN station club
ON sk.station_id=club.idStation
WHERE sk.idSkieur NOT IN (
    SELECT c.skieur_id
    FROM classement c
    WHERE c.classement > 3
)
AND st.nomStation LIKE 'Tignes';

-- Requete 9
SELECT sk.nomSkieur
FROM classement cl
INNER JOIN skieur sk
ON cl.skieur_id=sk.idSkieur
INNER JOIN competition cp
ON cl.competition_id=cp.idCompetition
INNER JOIN station st
ON cp.station_id=st.idStation
INNER JOIN station club
ON sk.station_id=club.idStation
WHERE sk.idSkieur NOT IN (
    SELECT c.skieur_id
    FROM classement c
    WHERE c.classement > 3
)
AND st.nomStation LIKE club.nomStation
GROUP BY sk.nomSkieur;

-- Requete 11
SELECT st.nomStation
FROM (
    SELECT st.nomStation, COUNT(sk.idSkieur) nbSkieur
    FROM skieur sk
    INNER JOIN station st
    ON sk.station_id=st.idStation
    GROUP BY sk.station_id
) st
WHERE nbSkieur > 1;

-- Requete 12
SELECT sk.nomSkieur
FROM skieur sk
JOIN (SELECT specialite_id
FROM skieur
WHERE nomSkieur LIKE 'paul') p
WHERE sk.specialite_id = p.specialite_id
AND sk.nomSkieur NOT LIKE 'paul';
