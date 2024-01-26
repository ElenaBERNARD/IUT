-- compte login  (remplacer login et motdepasse) dans tout le script
-- mysql --user=login --password=motdepasse --host=localhost  --database=BDD_login

CREATE DATABASE BDD_login;
CREATE USER 'login' IDENTIFIED BY  'mdp';

GRANT ALL PRIVILEGES ON  *.* To 'drg0n';
FLUSH PRIVILEGES;