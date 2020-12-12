-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: gamercenter_dev
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `announce_date`  varchar(255) DEFAULT NULL,
    `branch`         varchar(255) DEFAULT NULL,
    `description`    varchar(255) DEFAULT NULL,
    `developer_id`   bigint NOT NULL,
    `discount_end`   varchar(255) DEFAULT NULL,
    `discount_rate`  double NOT NULL,
    `discount_start` varchar(255) DEFAULT NULL,
    `front_image`    varchar(255) DEFAULT NULL,
    `name`           varchar(255) DEFAULT NULL,
    `price`          double NOT NULL,
    `release_date`   varchar(255) DEFAULT NULL,
    `score`          double NOT NULL,
    `tag`            varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game`
    DISABLE KEYS */;
INSERT INTO `game`
VALUES (1, '2020-11-25', '1', 'my game', 15732, '2020-11-28', 0.6, '2020-11-27', '1606391330056极光.jpg', 'my', 50,
        '2020-11-26', 0, 'Racing'),
       (2, '2020-11-25', '2', '123', 15732, '2020-11-28', 0.6, '2020-11-27', '1606228882669your computer is on.jpg',
        'dd', 123, '2020-11-22', 0, 'Adventure'),
       (3, '2020-11-25', '1', 'jjj', 15734, '2020-11-29', 0.8, '2020-11-29', '1606228959363微信图片_20200903153200.jpg',
        'abf', 222, '2020-11-21', 0, 'Action'),
       (4, '2020-11-26', '3', '123456', 15736, '2020-12-02', 0.8, '2020-11-28', '1606293871521小船.jpg', 'aaa', 102,
        '2020-11-27', 0, 'Adventure'),
       (5, '2020-11-29', '1', '123', 15734, '2020-11-30', 0.5, '2020-11-30',
        '1606294921258f6b0e7cf7bbd6819841faa7f42965163.jpg', 'aaadd', 22, '2020-11-30', 0, 'Action'),
       (6, '2020-11-26', '1', 'alll', 15734, '2020-11-30', 0.6, '2020-11-27', NULL, 'abddd', 123, '2020-11-26', 0,
        'Sports'),
       (7, '2020-11-28', '0', 'abdabdabd', 15734, '2020-12-01', 0.6, '2020-11-30', '1606456076396img105.jpg', 'mmmm',
        200, '2020-11-29', 0, 'Adventure');
/*!40000 ALTER TABLE `game`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_comment`
--

DROP TABLE IF EXISTS `game_comment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_comment`
(
    `id`         bigint    NOT NULL,
    `content`    varchar(255)   DEFAULT NULL,
    `game_id`    bigint    NOT NULL,
    `grade`      double    NOT NULL,
    `user_id`    bigint    NOT NULL,
    `visible`    bit(1)         DEFAULT b'1',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_comment`
--

LOCK TABLES `game_comment` WRITE;
/*!40000 ALTER TABLE `game_comment`
    DISABLE KEYS */;
INSERT INTO `game_comment`
VALUES (1, 'this game rocks', 1, 5, 15734, _binary '', '2020-12-08 06:38:34'),
       (2, 'shit game', 2, 1, 15734, _binary '', '2020-12-08 06:38:34');
/*!40000 ALTER TABLE `game_comment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_content`
--

DROP TABLE IF EXISTS `game_content`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_content`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `game_id` bigint NOT NULL,
    `name`    varchar(255) DEFAULT NULL,
    `path`    varchar(255) DEFAULT NULL,
    `type`    varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_content`
--

LOCK TABLES `game_content` WRITE;
/*!40000 ALTER TABLE `game_content`
    DISABLE KEYS */;
INSERT INTO `game_content`
VALUES (1, 1, '1606228402138Snipaste.exe', '\\src\\main\\resources\\static\\game\\installation', 'installation'),
       (2, 1, '1606228423434微信图片_20200903153204.jpg', '\\src\\main\\resources\\static\\game\\image', 'image'),
       (3, 1, '1606228557023cs309-observer-task1.mp4', '\\src\\main\\resources\\static\\game\\video', 'video'),
       (4, 2, '1606228874805Snipaste.exe', '\\src\\main\\resources\\static\\game\\installation', 'installation'),
       (5, 2, '1606228882669your computer is on.jpg', '\\src\\main\\resources\\static\\game\\image', 'image'),
       (6, 3, '1606228948280Snipaste.exe', '\\src\\main\\resources\\static\\game\\installation', 'installation'),
       (7, 3, '1606228959363微信图片_20200903153200.jpg', '\\src\\main\\resources\\static\\game\\image', 'image'),
       (8, 4, '1606293871521小船.jpg', '\\src\\main\\resources\\static\\game\\image', 'image'),
       (9, 5, '1606294921258f6b0e7cf7bbd6819841faa7f42965163.jpg', '\\src\\main\\resources\\static\\game\\image',
        'image'),
       (10, 1, '1606314692175极光.jpg', '\\src\\main\\resources\\static\\game\\image', 'image'),
       (11, 6, '1606314744710Snipaste.exe', '\\src\\main\\resources\\static\\game\\installation', 'installation'),
       (12, 1, '1606388540062微信图片_20200903153155.jpg', '\\src\\main\\resources\\static\\game\\image', 'image'),
       (13, 1, '1606391322569极光.jpg', '\\src\\main\\resources\\static\\game\\image', 'image'),
       (14, 1, '1606391330056极光.jpg', '\\src\\main\\resources\\static\\game\\image', 'image'),
       (15, 7, '1606456076396img105.jpg', '\\src\\main\\resources\\static\\game\\image', 'image');
/*!40000 ALTER TABLE `game_content`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_game_contents`
--

DROP TABLE IF EXISTS `game_game_contents`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_game_contents`
(
    `game_id`          bigint NOT NULL auto_increment,
    `game_contents_id` bigint NOT NULL,
    UNIQUE KEY `UK_67au2hxsmdlsxdsphjbgqhos8` (`game_contents_id`),
    KEY `FKm2e5jggj74x9hbnsa70dftojy` (`game_id`),
    CONSTRAINT `FK631soouy7g08y126kabiijt0r` FOREIGN KEY (`game_contents_id`) REFERENCES `game_content` (`id`),
    CONSTRAINT `FKm2e5jggj74x9hbnsa70dftojy` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_game_contents`
--

LOCK TABLES `game_game_contents` WRITE;
/*!40000 ALTER TABLE `game_game_contents`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `game_game_contents`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence`
    DISABLE KEYS */;
INSERT INTO `hibernate_sequence`
VALUES (15769),
       (15769);
/*!40000 ALTER TABLE `hibernate_sequence`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `name`        varchar(45)  NOT NULL,
    `email`       varchar(45)  NOT NULL,
    `password`    varchar(255) NOT NULL,
    `balance`     decimal(9, 2)         DEFAULT '0.00',
    `role`        varchar(10)           DEFAULT 'p' COMMENT 'a/p/d/t combinations, denoting admin/player/develop/developer',
    `avatar`      varchar(255)          DEFAULT NULL,
    `bio`         varchar(2048)         DEFAULT '',
    `online`      bit(1)                DEFAULT b'0',
    `locked`      bit(1)       NOT NULL DEFAULT b'0',
    `created_at`  timestamp    NULL     DEFAULT CURRENT_TIMESTAMP,
    `last_online` timestamp    NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `email_UNIQUE` (`email`),
    UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100001
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES (0, 'system', 'system@gmail.com', 'no login', 0.00, 'apdt', NULL, 'SYSTEM INFO', _binary '', _binary '',
        '2020-11-25 06:22:40', '2020-11-25 06:22:37'),
       (15732, 'calvin', 'calvin@gmail.com', '$2a$10$nl27adlgdtp2p6Jr2r8WXOw9FoSF5W.1PUwjLDDFmti26HMkogIFm', 0.00,
        'adpt', NULL, '', _binary '\0', _binary '\0', '2020-11-24 13:44:03', NULL),
       (15734, 'alice', 'alice@gmail.com', '$2a$10$p7RqPgTC4B/1o5Da2/hu5OZPvAyd2f5.Ntu./cEBc9EmT0SGO7u52', 12263.40,
        'adp', NULL, 'alicealice', _binary '\0', _binary '\0', '2020-11-24 13:47:05', NULL),
       (15735, 'bob vance', 'bobvance@gmail.com', '$2a$10$Xi80/qCvMMZ6rLrm.4DKyuMfI3xvUgJIeeJIA3wFAjJWcqWHRXPaS', 0.00,
        'dp', NULL, '', _binary '', _binary '\0', '2020-11-24 14:19:12', NULL),
       (15736, 'mike', 'mike@qq.com', '$2a$10$8.zIxq31334doVWmR6sZMek/8tqta0YVse4p9QiB18o48N6EN4VMe', 102.00, 'p', NULL,
        '', _binary '\0', _binary '\0', '2020-11-24 14:19:33', NULL),
       (15738, 'charlie', 'charlie@foxmail.com', '$2a$10$fg/wtv3rhCUz4hRFxqpsBOC/k1nu/J9zHa/K.HTEhdzg5CvhCryve', 0.00,
        'd', NULL, '', _binary '\0', _binary '\0', '2020-11-24 15:15:51', NULL),
       (15739, 'david', 'david@foxmail.com', '$2a$10$We1lGXcV03eJ.WuC.oqn0uAOGMnISgZM.i5AySKSwzjnCnASeoxEu', 100000.00,
        'p', NULL, '', _binary '\0', _binary '\0', '2020-11-24 15:16:20', NULL),
       (15742, 'tom', 'tom@gmail.com', '$2a$10$486jsyn2gpyxwK2tsubpoujbIx8Ufv8EQWOmqfJURe6Et4lONZfYK', 0.00, 'p', NULL,
        '', _binary '\0', _binary '\0', '2020-11-25 01:42:04', NULL),
       (15743, 'shui', 'shui@gmail.com', '$2a$10$U/5hwZajmDM38qk4zfmOnugzRCuYanIA5js0trMiwWwiRkG4mH3Ka', 0.00, 'p',
        NULL, '', _binary '\0', _binary '\0', '2020-11-25 11:28:44', NULL),
       (15744, 'aha', 'aha@gmail.com', '$2a$10$/vqzsmYxWlb4Iaap0xBfXerFEP7mi1mf3421VXBzEWXQCVMT6CXZG', 0.00, 'p', NULL,
        '', _binary '\0', _binary '\0', '2020-11-25 13:26:35', NULL),
       (15745, 'haha', 'haha@gmail.com', '$2a$10$qZ5oEJjaMWCm7xMABSzyUe2wD1sipfIJXFl6idWtsB5iDblfyNJ5q', 0.00, 'p',
        NULL, '', _binary '\0', _binary '\0', '2020-11-25 13:27:44', NULL),
       (15746, 'frank', 'fank@163.com', '$2a$10$72bC.X3UToCIs/WriW9IVOCJzgLMrruwRhZ52w9NEMtbVxNv8NwTC', 0.00, 'p', NULL,
        '', _binary '\0', _binary '\0', '2020-11-25 14:02:20', NULL),
       (15747, 'guy', 'guy@sustech.com', '$2a$10$ZRdz.glz2QReMtdNkO/1IelAkh8safcZXYTmBeEtJBWgQFCdQ37bS', 0.00, 'p',
        NULL, '', _binary '\0', _binary '\0', '2020-11-25 14:05:40', NULL),
       (15748, 'who', 'who@gmail.com', '$2a$10$CrzBF6O3hfcZal3Mv3dOPeDeg.9.3UA36kxzQ3Lyg3MGUT9oekxMW', 0.00, 'p', NULL,
        '', _binary '\0', _binary '\0', '2020-11-25 14:44:41', NULL),
       (15751, 'hahahah', 'hahahah@gmail.com', '$2a$10$N5xICBRRLMExjs/y9OhxoO1l4lBXfPn3wcPtNiSuMcOlX1Zz58juG', 0.00,
        'a', NULL, '', _binary '\0', _binary '\0', '2020-11-25 14:50:03', NULL),
       (15752, 'karen', 'karen@qq.com', '$2a$10$ExRoeTVT19f6QZeyjeu7k.prZmAf/rKthA1PJgo3eevAeY4omXUYS', 10384.13, 'p',
        NULL, '', _binary '\0', _binary '\0', '2020-11-26 11:24:58', NULL),
       (15753, 'zxy', 'zxy@gmail.com', '$2a$10$Ck5gwWqZ9jU74xVCM6vEA.duqRBxyiUF.zfpj5rtVykiWZCG4cC9G', 0.00, 'p', '',
        '', _binary '\0', _binary '\0', '2020-11-27 05:10:06', NULL),
       (15754, 'zm', 'zm@gmail.com', '$2a$10$H/Xs0sk93voGb5Sg/O5NPOXNa9xzt..Eu7aLLnmdZRhzd061rjlB2', 0.00, 'p', '', '',
        _binary '\0', _binary '\0', '2020-11-27 05:15:52', NULL),
       (15755, 'zm1', 'zm1@gmail.com', '$2a$10$hz.PZiRLora.xh2HScx7F.oerZd0rVtxuIK1iuB7agSaHVDUhmeci', 0.00, 'a', '',
        '', _binary '\0', _binary '\0', '2020-11-27 05:20:05', NULL),
       (15756, 'lht', 'lht@gmail.com', '$2a$10$XlnQcLN/jhPmEaeq00Y07u6U9onwuECuMZxWdfIncHq4M1PsLbydC', 0.00, 'p', '',
        '', _binary '\0', _binary '\0', '2020-11-27 05:22:02', NULL),
       (15758, 'lht1', 'lht1@gmail.com', '$2a$10$noXXjxpibIu82SAmK3e4xegUVM9yXePClCFc9Skx.P.mHbXRLq70m', 0.00, 'a', '',
        '', _binary '\0', _binary '\0', '2020-11-27 05:23:20', NULL),
       (15759, 'ymz', 'ymz@gmail.com', '$2a$10$hlNVU897IgEMe.9ogtoDkeMGNNFomd3QyEF/AHBdpqzzYbZ31Irlq', 0.00, 'p', '',
        '', _binary '\0', _binary '\0', '2020-11-27 05:24:36', NULL),
       (15760, 'ymz1', 'yma1@gmail.com', '$2a$10$fl3rCSwz9cKuWrROf0pY.uoEY7gLjtR5wg/hAfr5u.CLyTUpxo.KC', 0.00, 'a', '',
        '', _binary '\0', _binary '\0', '2020-11-27 05:25:33', NULL),
       (15766, 'dsxl', '11811713@mail.sustech.edu.cn', '$2a$10$trQqG6xahBGZzbJB187cYOEMyzoELmUwhXHyCOZkMKLLkGSu.EgMi',
        0.00, 'p', '', '', _binary '\0', _binary '\0', '2020-12-06 02:14:13', NULL),
       (15767, 'zhouyq', '11812636@mail.sustech.edu.cn', '$2a$10$zbf1402SbF3Cdldx4rWfQuY/qXBu7rnAgO0RvzAAuEUE9g4OaTnR2',
        0.00, 'p', '', '', _binary '\0', _binary '', '2020-12-09 08:10:43', NULL);
/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_friends`
--

DROP TABLE IF EXISTS `users_friends`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_friends`
(
    `from_user_id` bigint NOT NULL,
    `to_user_id`   bigint NOT NULL,
    `status`       varchar(45) DEFAULT 'pending',
    PRIMARY KEY (`from_user_id`, `to_user_id`),
    KEY `idx_users_friends_users1` (`from_user_id`),
    KEY `idx_users_friends_users2` (`to_user_id`),
    CONSTRAINT `fk_users_friends_id1_in_users` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_users_friends_id2_in_users` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `different_user_ids` CHECK ((`from_user_id` <> `to_user_id`))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_friends`
--

LOCK TABLES `users_friends` WRITE;
/*!40000 ALTER TABLE `users_friends`
    DISABLE KEYS */;
INSERT INTO `users_friends`
VALUES (15732, 15734, 'established'),
       (15732, 15739, 'pending'),
       (15732, 15743, 'pending'),
       (15734, 15732, 'established'),
       (15746, 15734, 'established');
/*!40000 ALTER TABLE `users_friends`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_games`
--

DROP TABLE IF EXISTS `users_games`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_games`
(
    `user_id`     bigint  NOT NULL,
    `game_id`     bigint  NOT NULL,
    `purchase_id` bigint  NOT NULL,
    `valid`       tinyint NOT NULL DEFAULT '1',
    `user_tag`    varchar(45)      DEFAULT NULL,
    PRIMARY KEY (`user_id`, `game_id`, `valid`),
    KEY `fk_users_games_games1_idx` (`game_id`),
    KEY `fk_users_games_user_history1_idx` (`purchase_id`),
    KEY `fk_users_games_users1_idx` (`user_id`),
    CONSTRAINT `fk_users_games_games` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
    CONSTRAINT `fk_users_games_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `users_games_users_purchases_id_fk` FOREIGN KEY (`purchase_id`) REFERENCES `users_purchases` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_games`
--

LOCK TABLES `users_games` WRITE;
/*!40000 ALTER TABLE `users_games`
    DISABLE KEYS */;
INSERT INTO `users_games`
VALUES (15734, 1, 1, 1, 'Racing'),
       (15734, 2, 628, 1, 'Adventure'),
       (15734, 3, 624, 1, 'Action'),
       (15734, 4, 630, 1, 'Adventure');
/*!40000 ALTER TABLE `users_games`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_games_tokens`
--

DROP TABLE IF EXISTS `users_games_tokens`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_games_tokens`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `user_id`      bigint       NOT NULL,
    `developer_id` bigint       NOT NULL,
    `game_id`      bigint       NOT NULL,
    `token`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `users_games_token_id_uindex` (`id`),
    KEY `users_games_token_game_id_fk` (`game_id`),
    KEY `users_games_token_users_id_fk` (`user_id`),
    KEY `users_games_token_users_id_fk_2` (`developer_id`),
    CONSTRAINT `users_games_token_game_id_fk` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
    CONSTRAINT `users_games_token_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `users_games_token_users_id_fk_2` FOREIGN KEY (`developer_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='third-party-authorization';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_games_tokens`
--

LOCK TABLES `users_games_tokens` WRITE;
/*!40000 ALTER TABLE `users_games_tokens`
    DISABLE KEYS */;
INSERT INTO `users_games_tokens`
VALUES (1, 15734, 15732, 1, 'sdfsdf'),
       (2, 15766, 15732, 2, 'ffc92b51-7b90-4215-b06b-81ccec35db3a');
/*!40000 ALTER TABLE `users_games_tokens`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_messages`
--

DROP TABLE IF EXISTS `users_messages`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_messages`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `source`     bigint                DEFAULT '0',
    `user_id`    bigint       NOT NULL,
    `type`       varchar(45)           DEFAULT 'default',
    `message`    varchar(255) NOT NULL,
    `unread`     bit(1)       NOT NULL DEFAULT b'1',
    `created_at` timestamp    NULL     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `users_messages_id_uindex` (`id`),
    KEY `users_messages_users_id_fk` (`user_id`),
    CONSTRAINT `users_messages_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='unread_messages';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_messages`
--

LOCK TABLES `users_messages` WRITE;
/*!40000 ALTER TABLE `users_messages`
    DISABLE KEYS */;
INSERT INTO `users_messages`
VALUES (1, 15732, 15734, 'friend request', 'hi', _binary '\0', '2020-11-25 06:30:38'),
       (4, 0, 15734, 'system', 'hello', _binary '\0', '2020-11-25 06:30:38'),
       (5, 15732, 15739, 'friend request', 'Please add me as a friend', _binary '', '2020-11-25 11:18:49'),
       (6, 15732, 15743, 'friend request', 'Please add me as a friend', _binary '', '2020-11-25 11:45:55'),
       (7, 15734, 15732, 'friend request', 'Please add me as a friend', _binary '', '2020-11-25 13:30:05'),
       (9, 15746, 15734, 'friend request', 'Please add me as a friend', _binary '', '2020-11-25 14:04:16'),
       (12, 0, 15734, 'system', 'welcome to gamer center', _binary '', '2020-11-25 14:20:01'),
       (13, 15732, 15734, 'chat', 'Wanna play games together?', _binary '', '2020-11-25 14:21:14'),
       (14, 0, 15734, 'system', 'Consider top up your account for game purchasing!', _binary '',
        '2020-11-25 14:21:59'),
       (15, 15734, 15732, 'reply', 'I\'ve accepted your friend request', _binary '', '2020-11-25 14:30:24'),
       (16, 15734, 15746, 'reply', 'I\'ve accepted your friend request', _binary '', '2020-11-25 14:30:29'),
       (17, 15734, 15732, 'reply', 'I\'ve accepted your friend request', _binary '', '2020-11-27 05:26:08'),
       (18, 15734, 15732, 'reply', 'I\'ve accepted your friend request', _binary '', '2020-11-27 05:46:41');
/*!40000 ALTER TABLE `users_messages`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_purchases`
--

DROP TABLE IF EXISTS `users_purchases`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_purchases`
(
    `id`             bigint        NOT NULL AUTO_INCREMENT,
    `user_id`        bigint        NOT NULL,
    `balance_change` decimal(9, 2)      DEFAULT NULL,
    `created_at`     timestamp     NULL DEFAULT CURRENT_TIMESTAMP,
    `rest_balance`   decimal(9, 2) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `users_purchases_id_uindex` (`id`),
    KEY `idx_users_purchases` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 632
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_purchases`
--

LOCK TABLES `users_purchases` WRITE;
/*!40000 ALTER TABLE `users_purchases`
    DISABLE KEYS */;
INSERT INTO `users_purchases`
VALUES (1, 15734, 15.60, '2020-11-24 15:01:29', 135.00),
       (3, 15734, -123.00, '2020-12-05 14:30:58', 365.40),
       (4, 15734, -123.00, '2020-12-06 01:53:21', 365.40),
       (5, 15734, 123.00, '2020-12-06 01:53:21', 365.40),
       (622, 15734, -222.00, '2020-12-06 02:21:59', 365.40),
       (623, 15734, 222.00, '2020-12-06 02:21:59', 365.40),
       (624, 15734, -222.00, '2020-12-06 02:21:59', 365.40),
       (625, 15734, -222.00, '2020-12-06 02:22:17', 365.40),
       (626, 15734, 222.00, '2020-12-06 02:22:17', 365.40),
       (627, 15734, -222.00, '2020-12-06 02:22:18', 365.40),
       (628, 15734, -123.00, '2020-12-06 02:32:44', 12365.40),
       (629, 15734, 123.00, '2020-12-06 02:32:44', 12365.40),
       (630, 15734, -102.00, '2020-12-06 02:34:30', 12263.40),
       (631, 15736, 102.00, '2020-12-06 02:34:30', 102.00);
/*!40000 ALTER TABLE `users_purchases`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-12-09 16:50:34