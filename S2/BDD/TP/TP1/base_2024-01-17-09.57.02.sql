-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: localhost    Database: BDD_drg0n
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ARTICLE`
--

DROP TABLE IF EXISTS `ARTICLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ARTICLE` (
  `idArticle` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(255) DEFAULT NULL,
  `prix` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idArticle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARTICLE`
--

LOCK TABLES `ARTICLE` WRITE;
/*!40000 ALTER TABLE `ARTICLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ARTICLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLIENT`
--

DROP TABLE IF EXISTS `CLIENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CLIENT` (
  `idClient` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `ville` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLIENT`
--

LOCK TABLES `CLIENT` WRITE;
/*!40000 ALTER TABLE `CLIENT` DISABLE KEYS */;
INSERT INTO `CLIENT` VALUES (1,'Mutz','Ruelisheim\r'),(2,'Rato','Besancon\r'),(3,'Goerig','Walheim\r'),(4,'Walter','Bretten\r'),(5,'PAUTOT','Belfort\r'),(6,'PETIT','Belfort\r'),(7,'SAINT DIZIER','Sevenans\r'),(8,'SALVI','Offemont\r'),(9,'TERRAT','Belfort\r'),(10,'TYRODE','Valdoie\r'),(11,'ALANKAYA','Bavilliers\r'),(12,'DAROSEY','Essert\r'),(13,'duguet','Belfort\r'),(14,'ESSENBURGER','Belfort\r'),(15,'JAOUEN','Sevenans\r'),(16,'molin','Belfort\r'),(17,'AMGHAR','Belfort\r'),(18,'BOUCHAUD','Belfort\r'),(19,'COTTARD','Belfort\r'),(20,'dirand','Valdoie\r'),(21,'LAMOTTE','Belfort\r'),(22,'METTEY','Belfort\r'),(23,'WOLF','Belfort\r'),(24,'BISMUTH','Belfort\r'),(25,'chaillet','Belfort\r'),(26,'DECOCK','Belfort');
/*!40000 ALTER TABLE `CLIENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMMANDE`
--

DROP TABLE IF EXISTS `COMMANDE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COMMANDE` (
  `idCommande` int NOT NULL AUTO_INCREMENT,
  `dateCommande` date DEFAULT NULL,
  `idClient` int DEFAULT NULL,
  PRIMARY KEY (`idCommande`),
  KEY `idClient` (`idClient`),
  CONSTRAINT `COMMANDE_ibfk_1` FOREIGN KEY (`idClient`) REFERENCES `CLIENT` (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMMANDE`
--

LOCK TABLES `COMMANDE` WRITE;
/*!40000 ALTER TABLE `COMMANDE` DISABLE KEYS */;
INSERT INTO `COMMANDE` VALUES (1,'2023-04-11',3),(2,'2023-12-11',2),(3,'2021-01-28',4),(4,'2023-09-19',1),(5,'2022-04-11',3),(6,'2023-12-11',2),(7,'2023-03-28',4),(8,'2023-12-09',1),(9,'2022-04-11',3),(10,'2023-12-11',2);
/*!40000 ALTER TABLE `COMMANDE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIGNE`
--

DROP TABLE IF EXISTS `LIGNE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LIGNE` (
  `idCommande` int NOT NULL,
  `idArticle` int NOT NULL,
  `quantite` int NOT NULL,
  PRIMARY KEY (`idCommande`,`idArticle`),
  KEY `idArticle` (`idArticle`),
  CONSTRAINT `LIGNE_ibfk_1` FOREIGN KEY (`idCommande`) REFERENCES `COMMANDE` (`idCommande`),
  CONSTRAINT `LIGNE_ibfk_2` FOREIGN KEY (`idArticle`) REFERENCES `ARTICLE` (`idArticle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIGNE`
--

LOCK TABLES `LIGNE` WRITE;
/*!40000 ALTER TABLE `LIGNE` DISABLE KEYS */;
/*!40000 ALTER TABLE `LIGNE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-17  9:57:02
