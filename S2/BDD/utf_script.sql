DROP TABLE IF EXISTS TD_collation;
CREATE TABLE TD_collation (
    texte VARCHAR(191) NOT NULL );

SET CHARACTER SET utf8mb4;
 
INSERT INTO TD_collation VALUES ('foo - bar');
INSERT INTO TD_collation VALUES ('foo 𝌆 bar');
INSERT INTO TD_collation VALUES ('foo 🎼 - 🎿 bar');
INSERT INTO TD_collation VALUES ('foo🦔 - 🦕 - 🦖 - 🥩 - 🧦bar');
INSERT INTO TD_collation VALUES ('⚡ Vente Flash ⚡ à ne pas manquer !');
INSERT INTO TD_collation VALUES ('🔥 LES SOLDES commencent ! 🔥  sur  📱');
SELECT * FROM TD_collation;