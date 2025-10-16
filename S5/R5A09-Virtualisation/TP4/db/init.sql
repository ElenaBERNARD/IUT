CREATE DATABASE IF NOT EXISTS TP4_Docker_DB;

USE TP4_Docker_DB;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO
    users (name)
VALUES
    ('Alice'),
    ('Bob'),
    ('Charlie');