/*
 Navicat Premium Dump SQL

 Source Server         : server409_docker_mysql
 Source Server Type    : MySQL
 Source Server Version : 90100 (9.1.0)
 Source Host           : 172.17.0.6:3306
 Source Schema         : ssm1

 Target Server Type    : MySQL
 Target Server Version : 90100 (9.1.0)
 File Encoding         : 65001

 Date: 20/12/2024 17:01:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'username',
  `age` int NULL DEFAULT NULL COMMENT 'age',
  `balance` decimal(10, 2) NULL DEFAULT NULL COMMENT 'balance',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (4, 'Meng Ka Keung', 92, 601.31);
INSERT INTO `account` VALUES (5, 'Loui Hok Yau', 72, 518.19);
INSERT INTO `account` VALUES (6, 'Theodore Powell', 15, 573.29);
INSERT INTO `account` VALUES (7, 'Xia Jialun', 30, 325.56);
INSERT INTO `account` VALUES (8, 'Fu Zitao', 99, 692.46);
INSERT INTO `account` VALUES (9, 'Miu Tin Wing', 92, 907.83);
INSERT INTO `account` VALUES (10, 'Liao Jialun', 46, 990.24);
INSERT INTO `account` VALUES (11, 'Nakajima Yuna', 83, 164.33);
INSERT INTO `account` VALUES (12, 'Watanabe Itsuki', 45, 676.12);
INSERT INTO `account` VALUES (13, 'Carolyn Boyd', 6, 133.79);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bookName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `stock` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (4, 'Davis Communications Inc.', 901.46, 1);
INSERT INTO `book` VALUES (5, 'Ethel Engineering LLC', 7.72, 1);
INSERT INTO `book` VALUES (6, 'Jacqueline Network Systems Inc.', 818.65, 1);
INSERT INTO `book` VALUES (7, 'Ramos LLC', 537.05, 0);
INSERT INTO `book` VALUES (8, 'Gutierrez Inc.', 878.22, 0);
INSERT INTO `book` VALUES (9, 'Christina LLC', 869.78, 0);
INSERT INTO `book` VALUES (10, 'Sheila Communications Inc.', 249.49, 1);
INSERT INTO `book` VALUES (11, 'Sylvia Toy LLC', 34.94, 1);
INSERT INTO `book` VALUES (12, 'Ellen LLC', 77.43, 0);
INSERT INTO `book` VALUES (13, 'Donna Inc.', 468.09, 1);

SET FOREIGN_KEY_CHECKS = 1;
