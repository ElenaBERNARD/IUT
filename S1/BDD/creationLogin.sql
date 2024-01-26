-- compte login  (remplacer login et motdepasse) dans tout le script
-- mysql --user=drg0n --password=2302 --host=localhost  --database=BDD_drg0n

CREATE DATABASE BDD_drg0n;
CREATE USER 'drg0n' IDENTIFIED BY  '2302';

GRANT ALL PRIVILEGES ON  *.* To 'drg0n';
FLUSH PRIVILEGES;