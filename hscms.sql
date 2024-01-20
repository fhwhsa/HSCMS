/*
 Navicat MySQL Data Transfer

 Source Server         : MysqlConnection
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : 127.0.0.1:3306
 Source Schema         : hscms

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 20/01/2024 15:53:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for caf
-- ----------------------------
DROP TABLE IF EXISTS `caf`;
CREATE TABLE `caf` (
  `classNo` varchar(20) NOT NULL,
  `className` varchar(50) NOT NULL,
  `creater` varchar(50) NOT NULL,
  `createTimeStamp` date NOT NULL,
  `createrEmailAddr` varchar(50) NOT NULL,
  PRIMARY KEY (`classNo`) USING BTREE,
  UNIQUE KEY `className` (`className`),
  KEY `createrEmailAddr` (`createrEmailAddr`),
  CONSTRAINT `caf_ibfk_1` FOREIGN KEY (`createrEmailAddr`) REFERENCES `user` (`emailAddr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班级审核表';

-- ----------------------------
-- Table structure for class_announcement_map
-- ----------------------------
DROP TABLE IF EXISTS `class_announcement_map`;
CREATE TABLE `class_announcement_map` (
  `id` int NOT NULL AUTO_INCREMENT,
  `classNo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `createTimeStamp` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `class_announcement_map_ibfk_1` (`classNo`),
  CONSTRAINT `class_announcement_map_ibfk_1` FOREIGN KEY (`classNo`) REFERENCES `classinfo` (`classNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班级通知表';

-- ----------------------------
-- Table structure for class_communication_records
-- ----------------------------
DROP TABLE IF EXISTS `class_communication_records`;
CREATE TABLE `class_communication_records` (
  `classNo` varchar(50) NOT NULL,
  `senderName` varchar(50) NOT NULL,
  `senderEmailAddr` varchar(50) NOT NULL,
  `context` text NOT NULL,
  `sendingDate` date NOT NULL,
  KEY `classNo` (`classNo`) USING BTREE /*!80000 INVISIBLE */,
  KEY `senderEmailAddr` (`senderEmailAddr`),
  CONSTRAINT `class_communication_records_ibfk_1` FOREIGN KEY (`classNo`) REFERENCES `classinfo` (`classNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_communication_records_ibfk_2` FOREIGN KEY (`senderEmailAddr`) REFERENCES `user` (`emailAddr`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班级聊天记录';

-- ----------------------------
-- Table structure for classinfo
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `classNo` varchar(20) NOT NULL,
  `className` varchar(50) NOT NULL,
  `creater` varchar(50) NOT NULL,
  `createTimeStamp` date NOT NULL,
  `createrEmailAddr` varchar(50) NOT NULL,
  PRIMARY KEY (`classNo`) USING BTREE,
  UNIQUE KEY `className` (`className`),
  KEY `createrEmailAddr` (`createrEmailAddr`),
  CONSTRAINT `classinfo_ibfk_1` FOREIGN KEY (`createrEmailAddr`) REFERENCES `user` (`emailAddr`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='班级信息表';

-- ----------------------------
-- Table structure for globalvar
-- ----------------------------
DROP TABLE IF EXISTS `globalvar`;
CREATE TABLE `globalvar` (
  `type` varchar(20) NOT NULL,
  `context` text COMMENT '公告内容',
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for guardian_child_map
-- ----------------------------
DROP TABLE IF EXISTS `guardian_child_map`;
CREATE TABLE `guardian_child_map` (
  `emailAddr` varchar(50) NOT NULL,
  `childName` varchar(50) NOT NULL,
  PRIMARY KEY (`emailAddr`,`childName`),
  CONSTRAINT `guardian_child_map_ibfk_1` FOREIGN KEY (`emailAddr`) REFERENCES `user` (`emailAddr`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='家长-孩子映射表';

-- ----------------------------
-- Table structure for jcaf
-- ----------------------------
DROP TABLE IF EXISTS `jcaf`;
CREATE TABLE `jcaf` (
  `emailAddr` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `classNo` varchar(50) NOT NULL,
  KEY `jcaf_ibfk_1` (`emailAddr`),
  KEY `jcaf_ibfk_2` (`classNo`),
  CONSTRAINT `jcaf_ibfk_1` FOREIGN KEY (`emailAddr`) REFERENCES `user` (`emailAddr`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `jcaf_ibfk_2` FOREIGN KEY (`classNo`) REFERENCES `classinfo` (`classNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='加入班级申请表';

-- ----------------------------
-- Table structure for raf
-- ----------------------------
DROP TABLE IF EXISTS `raf`;
CREATE TABLE `raf` (
  `name` varchar(20) NOT NULL,
  `passWord` varchar(30) NOT NULL,
  `emailAddr` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='注册申请表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(50) NOT NULL,
  `passWord` varchar(50) NOT NULL,
  `emailAddr` varchar(50) NOT NULL,
  `userType` varchar(20) NOT NULL,
  PRIMARY KEY (`emailAddr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Table structure for user_class_map
-- ----------------------------
DROP TABLE IF EXISTS `user_class_map`;
CREATE TABLE `user_class_map` (
  `emailAddr` varchar(50) NOT NULL,
  `classNo` varchar(20) NOT NULL,
  PRIMARY KEY (`emailAddr`,`classNo`),
  KEY `user_class_map_ibfk_2` (`classNo`),
  CONSTRAINT `user_class_map_ibfk_1` FOREIGN KEY (`emailAddr`) REFERENCES `user` (`emailAddr`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_class_map_ibfk_2` FOREIGN KEY (`classNo`) REFERENCES `classinfo` (`classNo`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-班级映射表';

SET FOREIGN_KEY_CHECKS = 1;
