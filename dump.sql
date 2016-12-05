-- MySQL dump 10.13  Distrib 5.7.14, for Win64 (x86_64)
--
-- Host: localhost    Database: Hotel
-- ------------------------------------------------------
-- Server version	5.7.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `archive`
--

DROP TABLE IF EXISTS `archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archive` (
  `archive_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`archive_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive`
--

LOCK TABLES `archive` WRITE;
/*!40000 ALTER TABLE `archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `booking_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,'2016-11-21 06:26:00'),(2,'2016-11-21 06:32:06');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_archive`
--

DROP TABLE IF EXISTS `booking_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_archive` (
  `booking_id` bigint(20) DEFAULT NULL,
  `archive_id` bigint(20) DEFAULT NULL,
  KEY `booking_id` (`booking_id`),
  KEY `archive_id` (`archive_id`),
  CONSTRAINT `booking_archive_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `booking_archive_ibfk_2` FOREIGN KEY (`archive_id`) REFERENCES `archive` (`archive_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_archive`
--

LOCK TABLES `booking_archive` WRITE;
/*!40000 ALTER TABLE `booking_archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking_archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_customer`
--

DROP TABLE IF EXISTS `booking_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_customer` (
  `booking_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  KEY `booking_id` (`booking_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `booking_customer_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `booking_customer_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_customer`
--

LOCK TABLES `booking_customer` WRITE;
/*!40000 ALTER TABLE `booking_customer` DISABLE KEYS */;
INSERT INTO `booking_customer` VALUES (1,11),(2,11);
/*!40000 ALTER TABLE `booking_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_payment`
--

DROP TABLE IF EXISTS `booking_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_payment` (
  `booking_id` bigint(20) DEFAULT NULL,
  `payment_id` bigint(20) DEFAULT NULL,
  KEY `booking_id` (`booking_id`),
  KEY `payment_id` (`payment_id`),
  CONSTRAINT `booking_payment_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `booking_payment_ibfk_2` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_payment`
--

LOCK TABLES `booking_payment` WRITE;
/*!40000 ALTER TABLE `booking_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_period`
--

DROP TABLE IF EXISTS `booking_period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_period` (
  `booking_id` bigint(20) DEFAULT NULL,
  `period_id` bigint(20) DEFAULT NULL,
  KEY `booking_id` (`booking_id`),
  KEY `period_id` (`period_id`),
  CONSTRAINT `booking_period_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `booking_period_ibfk_2` FOREIGN KEY (`period_id`) REFERENCES `period` (`period_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_period`
--

LOCK TABLES `booking_period` WRITE;
/*!40000 ALTER TABLE `booking_period` DISABLE KEYS */;
INSERT INTO `booking_period` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `booking_period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_room`
--

DROP TABLE IF EXISTS `booking_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking_room` (
  `booking_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `guests` int(11) DEFAULT '0',
  KEY `booking_id` (`booking_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `booking_room_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `booking_room_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_room`
--

LOCK TABLES `booking_room` WRITE;
/*!40000 ALTER TABLE `booking_room` DISABLE KEYS */;
INSERT INTO `booking_room` VALUES (1,48,0),(2,23,2);
/*!40000 ALTER TABLE `booking_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(35) NOT NULL,
  `last_name` varchar(35) DEFAULT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `username_constraint` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Hotel','Manager','admin','admin',42),(2,'Luke','Skywalker','lskywalker','starwar',30),(3,'John','Smith','jsmith','js',23),(4,'Bruce','Almighty','balmighty','sovereign',19),(5,'Harry','Potter','hpotter','magicman',47),(6,'Crystal','Logan','clogan','test',26),(7,'Guy','Fieri','gfieri','tripled',29),(8,'Hillary','Cosby','hcosby','sleuth',34),(9,'Sarah','Johnson','sjohnson','summersurf',31),(10,'Bob','Jenkins','bjenkins','oldmanjenkins',80),(11,'Troy','Nguyen','tnguyen','111',18);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER CustomerInsertTrigger
BEFORE INSERT ON Customer
FOR EACH ROW
  BEGIN
    IF NEW.age < 18 OR NEW.age > 200 THEN
      SET NEW.age = 18;
    END IF;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER GuestInsertTrigger
BEFORE INSERT ON Customer
FOR EACH ROW
  BEGIN
    IF NEW.age < 0 OR NEW.age > 200 THEN
      SET NEW.age = 0;
    END IF;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `details`
--

DROP TABLE IF EXISTS `details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `details` (
  `details_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `price` decimal(5,2) DEFAULT NULL,
  `room_type` varchar(20) DEFAULT NULL,
  `floor` int(11) DEFAULT '2',
  `capacity` int(11) DEFAULT '1',
  `beds` int(11) DEFAULT '1',
  `bathrooms` int(11) DEFAULT '1',
  `has_windows` tinyint(1) DEFAULT '0',
  `smoking_allowed` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`details_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1019 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `details`
--

LOCK TABLES `details` WRITE;
/*!40000 ALTER TABLE `details` DISABLE KEYS */;
INSERT INTO `details` VALUES (1001,49.99,'Single',2,1,1,1,0,1),(1002,49.99,'Single',2,1,1,1,0,0),(1003,49.99,'Single',3,1,1,1,0,1),(1004,49.99,'Single',3,1,1,1,0,0),(1005,79.99,'Double',2,2,1,1,0,1),(1006,79.99,'Double',2,2,1,1,0,0),(1007,79.99,'Double',3,2,1,1,0,1),(1008,79.99,'Double',3,2,1,1,0,0),(1009,129.99,'Family',4,4,2,1,0,0),(1010,149.99,'Family',4,4,2,1,1,0),(1011,129.99,'Family',5,4,2,1,0,0),(1012,149.99,'Family',5,4,2,1,1,0),(1013,129.99,'Family',6,4,2,1,0,0),(1014,149.99,'Family',6,4,2,1,1,0),(1015,199.99,'Standard Suite',7,4,2,2,1,0),(1016,199.99,'Standard Suite',8,4,2,2,1,0),(1017,249.99,'Executive Suite',9,4,4,2,1,0),(1018,249.99,'Executive Suite',10,4,4,2,1,0);
/*!40000 ALTER TABLE `details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `payment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_type` enum('cash','visa','mastercard','paypal','prepaid','check') NOT NULL,
  `amount` decimal(7,2) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `period`
--

DROP TABLE IF EXISTS `period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `period` (
  `period_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`period_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `period`
--

LOCK TABLES `period` WRITE;
/*!40000 ALTER TABLE `period` DISABLE KEYS */;
INSERT INTO `period` VALUES (1,'2016-11-21 14:00:00','2016-11-22 10:00:00'),(2,'2016-11-22 14:00:00','2016-11-23 10:00:00');
/*!40000 ALTER TABLE `period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `room_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `room_number` int(11) NOT NULL,
  PRIMARY KEY (`room_id`),
  UNIQUE KEY `room_number_constraint` (`room_number`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,101),(2,102),(3,103),(4,104),(5,105),(6,106),(7,107),(8,108),(9,109),(10,110),(11,201),(12,202),(13,203),(14,204),(15,205),(16,206),(17,207),(18,208),(19,209),(20,210),(22,301),(23,302),(24,303),(25,304),(26,305),(27,306),(28,307),(29,308),(30,309),(31,310),(32,401),(33,402),(34,403),(35,404),(36,405),(37,406),(38,407),(39,408),(40,409),(41,410),(42,501),(43,502),(44,503),(45,504),(46,505),(47,506),(48,507),(49,508),(50,509),(51,510);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_details`
--

DROP TABLE IF EXISTS `room_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_details` (
  `room_id` bigint(20) DEFAULT NULL,
  `details_id` bigint(20) DEFAULT NULL,
  KEY `room_id` (`room_id`),
  KEY `details_id` (`details_id`),
  CONSTRAINT `room_details_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `room_details_ibfk_2` FOREIGN KEY (`details_id`) REFERENCES `details` (`details_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_details`
--

LOCK TABLES `room_details` WRITE;
/*!40000 ALTER TABLE `room_details` DISABLE KEYS */;
INSERT INTO `room_details` VALUES (1,1001),(2,1002),(3,1003),(4,1004),(5,1001),(6,1002),(7,1003),(8,1003),(9,1003),(10,1004),(11,1005),(12,1005),(13,1005),(14,1006),(15,1007),(16,1007),(17,1007),(18,1008),(19,1008),(20,1008),(22,1009),(22,1009),(23,1010),(24,1011),(25,1012),(26,1013),(27,1013),(28,1014),(29,1014),(30,1014),(31,1014),(32,1015),(33,1015),(34,1015),(35,1015),(36,1015),(37,1016),(38,1016),(39,1016),(40,1016),(41,1016),(42,1017),(43,1017),(44,1017),(45,1017),(46,1017),(47,1017),(48,1017),(49,1017),(50,1017),(51,1017);
/*!40000 ALTER TABLE `room_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'Hotel'
--
/*!50003 DROP PROCEDURE IF EXISTS `ArchiveBookings` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp850 */ ;
/*!50003 SET character_set_results = cp850 */ ;
/*!50003 SET collation_connection  = cp850_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ArchiveBookings`(IN cutoffDatetime DATETIME)
BEGIN
    
    
    DECLARE loop_finished INTEGER DEFAULT 0;
    DECLARE old_booking_id BIGINT DEFAULT 0;

    
    DECLARE old_booking_cursor CURSOR FOR
    (SELECT Booking.booking_id
    FROM Booking, Booking_Period, Period
    WHERE Booking.booking_id = Booking_Period.booking_id
        AND Booking_Period.period_id = Period.period_id
        AND Period.end_date < cutoffDatetime
        AND Booking.booking_id NOT IN (SELECT Booking_Archive.booking_id FROM Booking_Archive));

    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET loop_finished = 1;

    
    
    OPEN old_booking_cursor;
    move_bookings: LOOP
        FETCH old_booking_cursor INTO old_booking_id;
        IF loop_finished = 1 THEN  
            LEAVE move_bookings;
        END IF;
        
        INSERT INTO Archive VALUES (old_booking_id, NOW());
        INSERT INTO Booking_Archive VALUES (old_booking_id, old_booking_id);
        
    END LOOP move_bookings;

    
    CLOSE old_booking_cursor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-04 20:17:46
