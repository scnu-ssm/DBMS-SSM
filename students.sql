/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50712
 Source Host           : localhost:3306
 Source Schema         : study

 Target Server Type    : MySQL
 Target Server Version : 50712
 File Encoding         : 65001

 Date: 03/03/2019 14:53:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` int(11) NOT NULL,
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES (2, '刘备', '女', 19, '234');
INSERT INTO `students` VALUES (3, '小宇', '男', 19, '345');
INSERT INTO `students` VALUES (6, '貂蝉', '女', 25, '58938939');
INSERT INTO `students` VALUES (7, '黄桂纯', '女', 20, '1320295');
INSERT INTO `students` VALUES (8, '陈荣', '男', 20, '188198');
INSERT INTO `students` VALUES (9, '貂蝉', '女', 25, '58938939');

SET FOREIGN_KEY_CHECKS = 1;
