/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50712
 Source Host           : localhost:3306
 Source Schema         : dbms_ssm

 Target Server Type    : MySQL
 Target Server Version : 50712
 File Encoding         : 65001

 Date: 30/04/2019 11:30:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for connectinfo
-- ----------------------------
DROP TABLE IF EXISTS `connectinfo`;
CREATE TABLE `connectinfo`  (
  `connect_Id` int(20) NOT NULL AUTO_INCREMENT,
  `connect_Name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `host` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `port` int(11) NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isSave` tinyint(4) NOT NULL,
  PRIMARY KEY (`connect_Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of connectinfo
-- ----------------------------
INSERT INTO `connectinfo` VALUES (5, 'chenrong', 'localhost', 3306, 'root', 'root', 1);
INSERT INTO `connectinfo` VALUES (6, 'root', 'localhost', 3306, 'root', 'root', 1);

SET FOREIGN_KEY_CHECKS = 1;
