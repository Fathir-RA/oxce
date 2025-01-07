-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: oxcee
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pesantiket`
--

DROP TABLE IF EXISTS `pesantiket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pesantiket` (
  `id_tiket` int(11) NOT NULL,
  `id_penyelam` int(11) NOT NULL,
  `id_destinasi` int(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `id_pembayaran` int(11) DEFAULT NULL,
  `tanggal` date NOT NULL,
  `quantity` int(11) NOT NULL,
  `waktu` int(11) NOT NULL,
  PRIMARY KEY (`id_tiket`),
  KEY `id_penyelam` (`id_penyelam`) USING BTREE,
  KEY `id_pembayaran` (`id_pembayaran`) USING BTREE,
  CONSTRAINT `pesantiket_ibfk_1` FOREIGN KEY (`id_penyelam`) REFERENCES `penyelam` (`id_penyelam`),
  CONSTRAINT `pesantiket_ibfk_2` FOREIGN KEY (`id_pembayaran`) REFERENCES `pembayaran` (`id_pembayaran`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesantiket`
--

LOCK TABLES `pesantiket` WRITE;
/*!40000 ALTER TABLE `pesantiket` DISABLE KEYS */;
INSERT INTO `pesantiket` VALUES (9014,123,3,1000000,NULL,'2024-12-27',4,2),(42421,123,1,500000,NULL,'2024-12-27',4,1),(76440,777,1,500000,NULL,'2025-01-02',2,1);
/*!40000 ALTER TABLE `pesantiket` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-07 10:37:48
