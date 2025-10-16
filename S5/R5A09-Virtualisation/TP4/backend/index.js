const express = require("express");
const mysql = require("mysql2/promise");

const app = express();
const PORT = 3000;

// Connexion MariaDB
const dbConfig = {
    host: "db", // le nom du conteneur DB
    user: "root",
    password: "root",
    database: "TP4_Docker_DB" // nom de la base de données
};

app.get("/users", async (req, res) => {
    try {
        const conn = await mysql.createConnection(dbConfig);
        const [rows] = await conn.execute("SELECT * FROM users");
        res.json(rows);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.listen(PORT, () => {
    console.log(`API running on port ${PORT}`);
});