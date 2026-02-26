/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.32-MariaDB : Database - pozoriste
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pozoriste` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `pozoriste`;

/*Table structure for table `kartapredstave` */

DROP TABLE IF EXISTS `kartapredstave`;

CREATE TABLE `kartapredstave` (
  `idKarta` bigint(20) NOT NULL AUTO_INCREMENT,
  `nazivPredstave` varchar(50) NOT NULL,
  `zanr` varchar(50) NOT NULL,
  `reditelj` varchar(50) NOT NULL,
  `datumOdrzavanja` date NOT NULL,
  `cena` double NOT NULL,
  PRIMARY KEY (`idKarta`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `kartapredstave` */

insert  into `kartapredstave`(`idKarta`,`nazivPredstave`,`zanr`,`reditelj`,`datumOdrzavanja`,`cena`) values 
(1,'Koštana','Drama','Dejan Mijač','2026-03-15',1200),
(2,'Balkanski špijun','Komedija','Dušan Kovačević','2026-03-20',1500),
(3,'Zločin i kazna','Drama','Egon Savin','2026-04-02',1800),
(9,'Romeo i Julija','Drama','Timoti Skot','2026-02-20',2000),
(10,'Senke iza zavese','Psiholoski triler','Marko Petrovic','2026-03-13',1200),
(11,'Poslednji aplauz','Tragedija','Stefan Jovanovic','2026-04-20',1300);

/*Table structure for table `korisnik` */

DROP TABLE IF EXISTS `korisnik`;

CREATE TABLE `korisnik` (
  `idKorisnik` bigint(20) NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `tipKorisnika` varchar(20) NOT NULL,
  `mesto` bigint(20) NOT NULL,
  PRIMARY KEY (`idKorisnik`),
  KEY `mesto` (`mesto`),
  CONSTRAINT `korisnik_ibfk_1` FOREIGN KEY (`mesto`) REFERENCES `mesto` (`idMesto`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `korisnik` */

insert  into `korisnik`(`idKorisnik`,`ime`,`prezime`,`email`,`tipKorisnika`,`mesto`) values 
(1,'Ognjen','Burdzic','ognjen1@gmail.com','clan',1),
(3,'Zorica','Milosevic','zorica@gmail.com','penzioner',1),
(12,'Jovana','Pavicevic','jovana@gmail.com','clan',2),
(13,'Ksenija','Nikic','ksenija1@gmail.com','student',1),
(14,'Marija','Milosavljevic','marija@gmail.com','student',1),
(15,'Ana','Stefanovic','ana@gmail.com','student',1),
(17,'Elena','Zivkovic','elena@gmail.com','student',3);

/*Table structure for table `mesto` */

DROP TABLE IF EXISTS `mesto`;

CREATE TABLE `mesto` (
  `idMesto` bigint(20) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) NOT NULL,
  PRIMARY KEY (`idMesto`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `mesto` */

insert  into `mesto`(`idMesto`,`naziv`) values 
(1,'Beograd'),
(2,'Novi Sad'),
(3,'Jagodina');

/*Table structure for table `prodavac` */

DROP TABLE IF EXISTS `prodavac`;

CREATE TABLE `prodavac` (
  `idProdavac` bigint(20) NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `kontaktTelefon` varchar(50) NOT NULL,
  `plata` double NOT NULL,
  `email` varchar(50) NOT NULL,
  `lozinka` varchar(50) NOT NULL,
  PRIMARY KEY (`idProdavac`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `prodavac` */

insert  into `prodavac`(`idProdavac`,`ime`,`prezime`,`kontaktTelefon`,`plata`,`email`,`lozinka`) values 
(1,'Marko','Petrovic','+381641112223',80000,'marko.petrovic@pozoriste.rs','marko123'),
(2,'Jelena','Ilic','+381635554441',0,'jelena.ilic@pozoriste.rs','jelena123');

/*Table structure for table `prodavacsmena` */

DROP TABLE IF EXISTS `prodavacsmena`;

CREATE TABLE `prodavacsmena` (
  `prodavac` bigint(20) NOT NULL,
  `smena` bigint(20) NOT NULL,
  `datumPS` date NOT NULL,
  PRIMARY KEY (`prodavac`,`smena`),
  KEY `smena` (`smena`),
  CONSTRAINT `prodavacsmena_ibfk_1` FOREIGN KEY (`prodavac`) REFERENCES `prodavac` (`idProdavac`),
  CONSTRAINT `prodavacsmena_ibfk_2` FOREIGN KEY (`smena`) REFERENCES `smena` (`idSmena`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `prodavacsmena` */

insert  into `prodavacsmena`(`prodavac`,`smena`,`datumPS`) values 
(1,1,'2026-01-10'),
(2,2,'2026-01-10');

/*Table structure for table `racun` */

DROP TABLE IF EXISTS `racun`;

CREATE TABLE `racun` (
  `idRacun` bigint(20) NOT NULL AUTO_INCREMENT,
  `datumTransakcije` date NOT NULL,
  `ukupanIznos` double NOT NULL,
  `prodavac` bigint(20) NOT NULL,
  `korisnik` bigint(20) NOT NULL,
  `popust` double NOT NULL,
  PRIMARY KEY (`idRacun`),
  KEY `prodavac` (`prodavac`),
  KEY `korisnik` (`korisnik`),
  CONSTRAINT `racun_ibfk_1` FOREIGN KEY (`prodavac`) REFERENCES `prodavac` (`idProdavac`),
  CONSTRAINT `racun_ibfk_2` FOREIGN KEY (`korisnik`) REFERENCES `korisnik` (`idKorisnik`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `racun` */

insert  into `racun`(`idRacun`,`datumTransakcije`,`ukupanIznos`,`prodavac`,`korisnik`,`popust`) values 
(1,'2026-01-20',4000,1,12,0),
(2,'2026-02-05',4845,1,15,255),
(3,'2026-02-18',4560,1,13,240),
(4,'2026-02-18',1140,1,14,60),
(5,'2026-02-23',3800,2,14,200),
(6,'2026-02-23',3040,1,17,160);

/*Table structure for table `smena` */

DROP TABLE IF EXISTS `smena`;

CREATE TABLE `smena` (
  `idSmena` bigint(20) NOT NULL AUTO_INCREMENT,
  `nazivSmene` varchar(50) NOT NULL,
  `brojSati` int(11) NOT NULL,
  `satnica` double NOT NULL,
  `vremePocetka` time NOT NULL,
  `vremeZavrsetka` time NOT NULL,
  PRIMARY KEY (`idSmena`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `smena` */

insert  into `smena`(`idSmena`,`nazivSmene`,`brojSati`,`satnica`,`vremePocetka`,`vremeZavrsetka`) values 
(1,'Jutarnja smena',8,520,'08:00:00','16:00:00'),
(2,'Popodnevna smena',6,580,'16:00:00','22:00:00'),
(8,'Nocna smena',4,600,'20:00:00','00:00:00');

/*Table structure for table `stavkaracuna` */

DROP TABLE IF EXISTS `stavkaracuna`;

CREATE TABLE `stavkaracuna` (
  `rb` bigint(20) NOT NULL,
  `racun` bigint(20) NOT NULL,
  `iznos` double NOT NULL,
  `cenaKarte` double NOT NULL,
  `brojKarata` bigint(20) NOT NULL,
  `kartaPredstave` bigint(20) NOT NULL,
  PRIMARY KEY (`rb`,`racun`),
  KEY `racun` (`racun`),
  KEY `kartaPredstave` (`kartaPredstave`),
  CONSTRAINT `stavkaracuna_ibfk_1` FOREIGN KEY (`racun`) REFERENCES `racun` (`idRacun`),
  CONSTRAINT `stavkaracuna_ibfk_2` FOREIGN KEY (`kartaPredstave`) REFERENCES `kartapredstave` (`idKarta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `stavkaracuna` */

insert  into `stavkaracuna`(`rb`,`racun`,`iznos`,`cenaKarte`,`brojKarata`,`kartaPredstave`) values 
(1,1,4000,2000,2,9),
(1,2,3600,1800,2,3),
(1,3,3600,1800,2,3),
(1,4,1200,1200,1,1),
(1,5,4000,2000,2,9),
(2,2,1500,1500,1,2),
(2,3,1200,1200,1,1),
(2,6,1200,1200,1,10),
(3,6,2000,2000,1,9);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
