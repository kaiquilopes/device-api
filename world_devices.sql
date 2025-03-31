-- MySQL dump 10.13  Distrib 9.2.0, for Linux (x86_64)
--
-- Host: localhost    Database: world_devices
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `state` enum('AVAILABLE','IN_USE','INACTIVE') NOT NULL,
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (1,'iPhone 14','Apple','AVAILABLE','2025-03-31 12:31:50'),(2,'Galaxy S21','Samsung','AVAILABLE','2025-03-31 12:31:50'),(3,'Pixel 6','Google','IN_USE','2025-03-31 12:31:50'),(4,'ThinkPad X1','Lenovo','AVAILABLE','2025-03-31 12:31:50'),(5,'MacBook Pro','Apple','IN_USE','2025-03-31 12:31:50'),(6,'Surface Pro 7','Microsoft','INACTIVE','2025-03-31 12:31:50'),(7,'Galaxy S7','Samsung','AVAILABLE','2025-03-31 12:31:50'),(8,'iPad Air','Apple','IN_USE','2025-03-31 12:31:50'),(9,'Echo Dot','Amazon','AVAILABLE','2025-03-31 12:31:50'),(10,'PlayStation 5','Sony','IN_USE','2025-03-31 12:31:50'),(11,'Xbox Series X','Microsoft','INACTIVE','2025-03-31 12:31:50'),(12,'GoPro Hero9','GoPro','IN_USE','2025-03-31 12:31:50'),(13,'Kindle Paperwhite','Amazon','AVAILABLE','2025-03-31 12:31:50'),(14,'iPhone 15 Pro','Apple','AVAILABLE','2025-03-31 12:31:50'),(15,'Nintendo Switch','Nintendo','AVAILABLE','2025-03-31 12:31:50');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-31 12:37:36
