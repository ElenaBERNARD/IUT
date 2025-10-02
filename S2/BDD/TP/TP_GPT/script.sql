DROP TABLE IF EXISTS Embauche, Grille_sal, Projet, Employe;

CREATE TABLE Employe (
    NumSS INT(5) PRIMARY KEY,
    NomE VARCHAR(20),
    PrenomE VARCHAR(20),
    VilleE VARCHAR(20),
    DateNaiss DATE
);

CREATE TABLE Projet (
    NumProj INT(3) PRIMARY KEY,
    NomProj VARCHAR(20),
    RespProj INT,
    VilleP VARCHAR(20),
    Budget DECIMAL(10,2),
    FOREIGN KEY (RespProj) REFERENCES Employe(NumSS)
);

CREATE TABLE Grille_sal (
    profil VARCHAR(20) PRIMARY KEY,
    salaire DECIMAL(7,2)
);

CREATE TABLE Embauche (
    NumSS INT,
    NumProj INT,
    DateEmb DATE DEFAULT (CURRENT_DATE),
    Profil VARCHAR(20),
    PRIMARY KEY (NumSS, NumProj, DateEmb),
    FOREIGN KEY (NumSS) REFERENCES Employe(NumSS),
    FOREIGN KEY (NumProj) REFERENCES Projet(NumProj),
    FOREIGN KEY (Profil) REFERENCES Grille_sal(profil)
);

INSERT INTO Employe (NumSS, NomE, PrenomE, VilleE, DateNaiss)
VALUES
(22334, 'Adam', 'Funk', 'Paris', STR_TO_DATE('01-12-1982', '%d-%m-%Y')),
(45566, 'Rachid', 'Allaoui', 'Lyon', STR_TO_DATE('13-04-1986', '%d-%m-%Y')),
(77889, 'Florent', 'Girac', 'Marseille', STR_TO_DATE('04-11-1990', '%d-%m-%Y')),
(90011, 'Mayla', 'Aoun', 'Lyon', STR_TO_DATE('26-03-1987', '%d-%m-%Y')),
(22233, 'Christine', 'Lara', 'Paris', STR_TO_DATE('09-08-1982', '%d-%m-%Y')),
(34445, 'Amel', 'Orlando', 'Lyon', STR_TO_DATE('14-02-1976', '%d-%m-%Y')),
(55666, 'Mohsen', 'Charef', 'Paris', STR_TO_DATE('28-05-1991', '%d-%m-%Y')),
(77788, 'Tim', 'Arabi', 'Marseille', STR_TO_DATE('08-06-1984', '%d-%m-%Y')),
(89990, 'Fernando', 'Lopez', 'Lyon', STR_TO_DATE('05-10-1993', '%d-%m-%Y')),
(11122, 'Ada', 'Tan Lee', 'Marseille', STR_TO_DATE('21-03-1994', '%d-%m-%Y')),
(11123, 'Franck', 'Morel', 'Lille', STR_TO_DATE('10-01-1945', '%d-%m-%Y'));

INSERT INTO Projet VALUES (123, 'ADOOP', 22334, 'Paris', 120000);
INSERT INTO Projet VALUES (757, 'SKALA', 45566, 'Lyon', 180000);
INSERT INTO Projet VALUES (890, 'BAJA', 22334, 'Paris', 24000);

INSERT INTO Grille_sal VALUES ('Admin', 80000);
INSERT INTO Grille_sal VALUES ('Deve', 45000);
INSERT INTO Grille_sal VALUES ('Tech', 35000);

INSERT INTO Embauche VALUES (77889, 123, STR_TO_DATE('01-03-2014', '%d-%m-%Y'), 'Deve');
INSERT INTO Embauche VALUES (90011, 123, STR_TO_DATE('01-05-2014', '%d-%m-%Y'), 'Tech');
INSERT INTO Embauche VALUES (22233, 757, STR_TO_DATE('01-03-2014', '%d-%m-%Y'), 'Deve');


-- EXERCICE 1

DROP PROCEDURE IF EXISTS DeleteOldEmployees;

DELIMITER //

CREATE PROCEDURE DeleteOldEmployees()
BEGIN
    DECLARE v_rows_deleted INT DEFAULT 0;

    -- Perform the delete operation
    DELETE FROM Employe
    WHERE YEAR(CURDATE()) - YEAR(DateNaiss) >= 70;

    -- Get the number of rows deleted
    SET v_rows_deleted = ROW_COUNT();

    -- Display the appropriate message
    IF v_rows_deleted > 0 THEN
        SELECT CONCAT(v_rows_deleted, ' employés ont été supprimés.') AS message;
    ELSE
        SELECT 'Aucun employé supprimé.' AS message;
    END IF;
END //
DELIMITER ;

CALL DeleteOldEmployees();
CALL DeleteOldEmployees();



-- EXERCICE 2

DROP PROCEDURE IF EXISTS DisplayProjectsAndEmployees;

DELIMITER $$

CREATE PROCEDURE DisplayProjectsAndEmployees()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_NumProj INT;
    DECLARE v_NomProj VARCHAR(20);
    DECLARE v_NumSS INT;
    DECLARE v_NomE VARCHAR(20);
    DECLARE v_PrenomE VARCHAR(20);
    DECLARE result TEXT DEFAULT '';
    DECLARE employee_count INT DEFAULT 0;

    -- Declare a cursor to select all projects
    DECLARE cur_projects CURSOR FOR
        SELECT NumProj, NomProj FROM Projet;

    -- Declare a cursor to select employees for a specific project
    DECLARE cur_employees CURSOR FOR
        SELECT e.NumSS, em.NomE, em.PrenomE
        FROM Embauche e JOIN Employe em ON e.NumSS = em.NumSS
        WHERE e.NumProj = v_NumProj;

    -- Declare a handler for the end of the projects cursor
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    -- Open the projects cursor
    OPEN cur_projects;

    -- Loop through each project
    project_loop: LOOP
        FETCH cur_projects INTO v_NumProj, v_NomProj;
        IF done THEN
            LEAVE project_loop;
        END IF;

        -- Append the project name to the result string
        SET result = CONCAT(result, 'Projet: ', v_NomProj, '\n');

        -- Reset employee count
        SET employee_count = 0;

        -- Open the employees cursor for the current project
        OPEN cur_employees;

        -- Loop through each employee for the current project
        employee_loop: LOOP
            FETCH cur_employees INTO v_NumSS, v_NomE, v_PrenomE;
            IF done THEN
                LEAVE employee_loop;
            END IF;

            -- Increment the employee count
            SET employee_count = employee_count + 1;

            -- Append the employee's name to the result string
            SET result = CONCAT(result, '    Employé: ', v_NomE, ' ', v_PrenomE, '\n');
        END LOOP;

        -- Close the employees cursor
        CLOSE cur_employees;

        -- Check if no employees were found
        IF employee_count = 0 THEN
            SET result = CONCAT(result, '    Personne n\'est affecté sur le projet\n');
        ELSE
            SET result = CONCAT(result, '    Nombre d\'employés: ', employee_count, '\n');
        END IF;

        -- Reset the done flag for the employee loop
        SET done = 0;
    END LOOP;

    -- Close the projects cursor
    CLOSE cur_projects;

    -- Display the final result string
    SELECT result AS 'Projects and Employees';
END$$

DELIMITER ;



CALL DisplayProjectsAndEmployees();
