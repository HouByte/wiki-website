/*
 Navicat Premium Data Transfer

 Source Server         : bugio_wiki
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : sql.bugio.cn:28266
 Source Schema         : bugio_wiki

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 09/06/2022 00:36:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `parent` bigint(20) NOT NULL DEFAULT 0 COMMENT '父id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT ' 顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (100, 0, '前端开发', 100);
INSERT INTO `category` VALUES (101, 100, 'Vue', 101);
INSERT INTO `category` VALUES (102, 100, 'HTML & CSS', 102);
INSERT INTO `category` VALUES (200, 0, 'Java', 200);
INSERT INTO `category` VALUES (201, 200, ' 基础应用', 201);
INSERT INTO `category` VALUES (202, 200, ' 框架应用', 202);
INSERT INTO `category` VALUES (300, 0, ' Python', 300);
INSERT INTO `category` VALUES (301, 300, ' 基础应用', 301);
INSERT INTO `category` VALUES (302, 300, ' 进阶方向应用', 302);
INSERT INTO `category` VALUES (400, 0, ' 数据库', 400);
INSERT INTO `category` VALUES (401, 400, 'MySQL', 401);
INSERT INTO `category` VALUES (500, 0, '运维', 500);
INSERT INTO `category` VALUES (501, 500, ' 服务器', 501);
INSERT INTO `category` VALUES (502, 2149536126603264, '开发工具', 601);
INSERT INTO `category` VALUES (2149536126603264, 0, '其他', 600);

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`  (
  `id` bigint(20) NOT NULL COMMENT '文档id',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文档内容' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content
-- ----------------------------
INSERT INTO `content` VALUES (1, '<p><img src=\"http://s.bugio.cn/img/qq.jpg\" style=\"max-width:100%;\" width=\"219.75\" height=\"219.75\"><br></p><h1 id=\"f190q\">你好呀</h1><p><img src=\"https://pic.3gbizhi.com/2020/1214/20201214113101309.jpg\" style=\"max-width:100%;\"><br></p>');
INSERT INTO `content` VALUES (2, '<blockquote><p>文档测试1</p></blockquote><table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><th>1</th><th>2</th><th></th><th></th><th></th></tr><tr><td>为</td><td></td><td></td><td></td><td></td></tr><tr><td></td><td></td><td></td><td></td><td></td></tr><tr><td></td><td></td><td></td><td></td><td></td></tr><tr><td></td><td></td><td></td><td></td><td></td></tr></tbody></table>');
INSERT INTO `content` VALUES (3, NULL);
INSERT INTO `content` VALUES (5, NULL);
INSERT INTO `content` VALUES (6, NULL);
INSERT INTO `content` VALUES (5955442463346688, '<blockquote><h1 id=\"w07mm\">1111</h1></blockquote><p>vue</p>');
INSERT INTO `content` VALUES (7853792032854016, '<p>测试</p>');

-- ----------------------------
-- Table structure for doc
-- ----------------------------
DROP TABLE IF EXISTS `doc`;
CREATE TABLE `doc`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `ebook_id` bigint(20) NOT NULL DEFAULT 0 COMMENT ' 电子书id',
  `parent` bigint(20) NOT NULL DEFAULT 0 COMMENT ' 父id ',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT ' 顺序',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '阅读数',
  `vote_count` int(11) NULL DEFAULT 0 COMMENT ' 点赞数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文档' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doc
-- ----------------------------
INSERT INTO `doc` VALUES (1, 1, 0, '文档1', 1, 62, 8);
INSERT INTO `doc` VALUES (2, 1, 1, '文档1.1', 1, 24, 3);
INSERT INTO `doc` VALUES (3, 1, 0, '文档2', 2, 0, 0);
INSERT INTO `doc` VALUES (5, 1, 3, '文档2.2', 2, 0, 0);
INSERT INTO `doc` VALUES (6, 1, 5, '文档2.2.1', 1, 0, 7);
INSERT INTO `doc` VALUES (5955442463346688, 2, 0, 'vue1', 1, 23, 5);
INSERT INTO `doc` VALUES (7853792032854016, 1, 1, '测试', 103, 2, 0);

-- ----------------------------
-- Table structure for ebook
-- ----------------------------
DROP TABLE IF EXISTS `ebook`;
CREATE TABLE `ebook`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `category_ids` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类列表，逗号隔开',
  `desc` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `cover` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
  `doc_count` int(11) NULL DEFAULT 0 COMMENT '文档数',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '页面数',
  `vote_count` int(11) NULL DEFAULT 0 COMMENT '点赞数',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ebook
-- ----------------------------
INSERT INTO `ebook` VALUES (1, 'Spring Boot 入门教程', '[200,202]', 'spring', 'http://s.bugio.cn/img/logo.jpg', 6, 88, 18, '2021-07-30 15:00:00', '2021-07-07 12:04:27');
INSERT INTO `ebook` VALUES (2, 'Vue 入门手册', '[100,101]', 'Vue 快速入门', 'http://s.bugio.cn/img/logo.jpg', 1, 23, 5, '2021-08-14 15:00:00', '2021-07-07 12:08:36');
INSERT INTO `ebook` VALUES (3, 'Netty 网络编程', '[200,201]', 'Nio 框架 Netty ', 'http://s.bugio.cn/img/logo.jpg', 0, 0, 0, '2021-07-17 13:26:31', '2021-07-07 12:08:47');
INSERT INTO `ebook` VALUES (4, 'Dubbo', '[200,202]', '基于RPC的框架', 'http://s.bugio.cn/img/logo.jpg', 0, 0, 0, '2021-07-17 13:26:30', '2021-07-07 11:08:57');

-- ----------------------------
-- Table structure for ebook_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `ebook_snapshot`;
CREATE TABLE `ebook_snapshot`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ebook_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '电子书id',
  `date` date NOT NULL COMMENT '快照日期',
  `view_count` int(11) NOT NULL DEFAULT 0 COMMENT '阅读数',
  `vote_count` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `view_increase` int(11) NOT NULL DEFAULT 0 COMMENT '阅读增长',
  `vote_increase` int(11) NOT NULL DEFAULT 0 COMMENT '点赞增长',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ebook_id_date_unique`(`ebook_id`, `date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 353 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电子书快照表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ebook_snapshot
-- ----------------------------
INSERT INTO `ebook_snapshot` VALUES (19, 1, '2021-07-15', 12, 3, 12, 3);
INSERT INTO `ebook_snapshot` VALUES (20, 2, '2021-07-15', 1, 0, 1, 0);
INSERT INTO `ebook_snapshot` VALUES (21, 3, '2021-07-15', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (22, 4, '2021-07-15', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (23, 1, '2021-07-16', 43, 5, 31, 2);
INSERT INTO `ebook_snapshot` VALUES (24, 2, '2021-07-16', 3, 1, 2, 1);
INSERT INTO `ebook_snapshot` VALUES (25, 3, '2021-07-16', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (26, 4, '2021-07-16', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (27, 1, '2021-07-17', 73, 18, 30, 13);
INSERT INTO `ebook_snapshot` VALUES (28, 2, '2021-07-17', 5, 1, 2, 0);
INSERT INTO `ebook_snapshot` VALUES (29, 3, '2021-07-17', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (30, 4, '2021-07-17', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (34, 1, '2021-07-18', 75, 18, 2, 0);
INSERT INTO `ebook_snapshot` VALUES (35, 2, '2021-07-18', 11, 2, 6, 1);
INSERT INTO `ebook_snapshot` VALUES (36, 3, '2021-07-18', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (37, 4, '2021-07-18', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (41, 1, '2021-07-19', 75, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (42, 2, '2021-07-19', 11, 2, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (43, 3, '2021-07-19', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (44, 4, '2021-07-19', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (48, 1, '2021-07-20', 75, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (49, 2, '2021-07-20', 11, 2, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (50, 3, '2021-07-20', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (51, 4, '2021-07-20', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (55, 1, '2021-07-21', 75, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (56, 2, '2021-07-21', 11, 2, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (57, 3, '2021-07-21', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (58, 4, '2021-07-21', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (62, 1, '2021-07-22', 84, 18, 9, 0);
INSERT INTO `ebook_snapshot` VALUES (63, 2, '2021-07-22', 18, 4, 7, 2);
INSERT INTO `ebook_snapshot` VALUES (64, 3, '2021-07-22', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (65, 4, '2021-07-22', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (69, 1, '2021-07-23', 84, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (70, 2, '2021-07-23', 18, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (71, 3, '2021-07-23', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (72, 4, '2021-07-23', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (76, 1, '2021-07-24', 84, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (77, 2, '2021-07-24', 18, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (78, 3, '2021-07-24', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (79, 4, '2021-07-24', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (83, 1, '2021-07-25', 84, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (84, 2, '2021-07-25', 18, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (85, 3, '2021-07-25', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (86, 4, '2021-07-25', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (90, 1, '2021-07-26', 84, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (91, 2, '2021-07-26', 18, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (92, 3, '2021-07-26', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (93, 4, '2021-07-26', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (97, 1, '2021-07-27', 84, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (98, 2, '2021-07-27', 18, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (99, 3, '2021-07-27', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (100, 4, '2021-07-27', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (104, 1, '2021-07-28', 84, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (105, 2, '2021-07-28', 18, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (106, 3, '2021-07-28', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (107, 4, '2021-07-28', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (111, 1, '2021-07-29', 84, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (112, 2, '2021-07-29', 18, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (113, 3, '2021-07-29', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (114, 4, '2021-07-29', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (118, 1, '2021-07-30', 88, 18, 4, 0);
INSERT INTO `ebook_snapshot` VALUES (119, 2, '2021-07-30', 21, 4, 3, 0);
INSERT INTO `ebook_snapshot` VALUES (120, 3, '2021-07-30', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (121, 4, '2021-07-30', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (125, 1, '2021-07-31', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (126, 2, '2021-07-31', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (127, 3, '2021-07-31', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (128, 4, '2021-07-31', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (132, 1, '2021-08-01', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (133, 2, '2021-08-01', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (134, 3, '2021-08-01', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (135, 4, '2021-08-01', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (139, 1, '2021-08-02', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (140, 2, '2021-08-02', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (141, 3, '2021-08-02', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (142, 4, '2021-08-02', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (146, 1, '2021-08-03', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (147, 2, '2021-08-03', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (148, 3, '2021-08-03', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (149, 4, '2021-08-03', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (153, 1, '2021-08-04', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (154, 2, '2021-08-04', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (155, 3, '2021-08-04', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (156, 4, '2021-08-04', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (160, 1, '2021-08-05', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (161, 2, '2021-08-05', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (162, 3, '2021-08-05', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (163, 4, '2021-08-05', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (167, 1, '2021-08-06', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (168, 2, '2021-08-06', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (169, 3, '2021-08-06', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (170, 4, '2021-08-06', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (174, 1, '2021-08-07', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (175, 2, '2021-08-07', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (176, 3, '2021-08-07', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (177, 4, '2021-08-07', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (181, 1, '2021-08-08', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (182, 2, '2021-08-08', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (183, 3, '2021-08-08', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (184, 4, '2021-08-08', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (188, 1, '2021-08-09', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (189, 2, '2021-08-09', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (190, 3, '2021-08-09', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (191, 4, '2021-08-09', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (195, 1, '2021-08-10', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (196, 2, '2021-08-10', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (197, 3, '2021-08-10', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (198, 4, '2021-08-10', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (202, 1, '2021-08-11', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (203, 2, '2021-08-11', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (204, 3, '2021-08-11', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (205, 4, '2021-08-11', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (209, 1, '2021-08-12', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (210, 2, '2021-08-12', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (211, 3, '2021-08-12', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (212, 4, '2021-08-12', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (216, 1, '2021-08-13', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (217, 2, '2021-08-13', 21, 4, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (218, 3, '2021-08-13', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (219, 4, '2021-08-13', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (223, 1, '2021-08-14', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (224, 2, '2021-08-14', 23, 5, 2, 1);
INSERT INTO `ebook_snapshot` VALUES (225, 3, '2021-08-14', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (226, 4, '2021-08-14', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (230, 1, '2021-08-15', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (231, 2, '2021-08-15', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (232, 3, '2021-08-15', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (233, 4, '2021-08-15', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (237, 1, '2021-08-16', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (238, 2, '2021-08-16', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (239, 3, '2021-08-16', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (240, 4, '2021-08-16', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (244, 1, '2021-08-17', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (245, 2, '2021-08-17', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (246, 3, '2021-08-17', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (247, 4, '2021-08-17', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (251, 1, '2021-08-18', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (252, 2, '2021-08-18', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (253, 3, '2021-08-18', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (254, 4, '2021-08-18', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (258, 1, '2021-08-19', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (259, 2, '2021-08-19', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (260, 3, '2021-08-19', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (261, 4, '2021-08-19', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (265, 1, '2021-08-20', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (266, 2, '2021-08-20', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (267, 3, '2021-08-20', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (268, 4, '2021-08-20', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (272, 1, '2021-08-21', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (273, 2, '2021-08-21', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (274, 3, '2021-08-21', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (275, 4, '2021-08-21', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (279, 1, '2021-08-22', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (280, 2, '2021-08-22', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (281, 3, '2021-08-22', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (282, 4, '2021-08-22', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (286, 1, '2021-08-23', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (287, 2, '2021-08-23', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (288, 3, '2021-08-23', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (289, 4, '2021-08-23', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (293, 1, '2021-08-24', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (294, 2, '2021-08-24', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (295, 3, '2021-08-24', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (296, 4, '2021-08-24', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (300, 1, '2021-08-25', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (301, 2, '2021-08-25', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (302, 3, '2021-08-25', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (303, 4, '2021-08-25', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (307, 1, '2021-08-26', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (308, 2, '2021-08-26', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (309, 3, '2021-08-26', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (310, 4, '2021-08-26', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (311, 1, '2021-08-27', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (312, 2, '2021-08-27', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (313, 3, '2021-08-27', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (314, 4, '2021-08-27', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (318, 1, '2021-11-25', 88, 18, 88, 18);
INSERT INTO `ebook_snapshot` VALUES (319, 2, '2021-11-25', 23, 5, 23, 5);
INSERT INTO `ebook_snapshot` VALUES (320, 3, '2021-11-25', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (321, 4, '2021-11-25', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (325, 1, '2021-11-26', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (326, 2, '2021-11-26', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (327, 3, '2021-11-26', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (328, 4, '2021-11-26', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (332, 1, '2021-11-27', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (333, 2, '2021-11-27', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (334, 3, '2021-11-27', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (335, 4, '2021-11-27', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (339, 1, '2021-11-28', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (340, 2, '2021-11-28', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (341, 3, '2021-11-28', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (342, 4, '2021-11-28', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (346, 1, '2021-11-29', 88, 18, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (347, 2, '2021-11-29', 23, 5, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (348, 3, '2021-11-29', 0, 0, 0, 0);
INSERT INTO `ebook_snapshot` VALUES (349, 4, '2021-11-29', 0, 0, 0, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT ' ID',
  `login_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登陆名',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 昵称',
  `password` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ' 密码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否可用，0 不可用 1可用 默认0',
  `roles` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name_unique`(`login_name`) USING BTREE COMMENT '登入名',
  UNIQUE INDEX `email_unique`(`email`) USING BTREE COMMENT '邮箱'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', 'Bugio', '5d259c238a4d888b1c4456435ec2020b', 'v@timeapi.cn', 'http://s.bugio.cn/img/bugio.jpg', 1, NULL, '2021-07-18 18:11:46', '2021-07-18 17:10:01');
INSERT INTO `user` VALUES (7828584148373504, 'admin', '管理员', '031c66c07aab29af29836babc913f005', 'vcoding@dingtalk.com', 'http://s.bugio.cn/img/bugio.jpg', 1, NULL, '2021-07-22 15:21:54', '2021-07-22 14:28:00');

SET FOREIGN_KEY_CHECKS = 1;
