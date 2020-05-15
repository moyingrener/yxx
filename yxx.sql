/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 50554
 Source Host           : 127.0.0.1:3306
 Source Schema         : yxx

 Target Server Type    : MySQL
 Target Server Version : 50554
 File Encoding         : 65001

 Date: 25/07/2019 14:00:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` tinyint(15) NOT NULL COMMENT '类别ID',
  `category` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '类别',
  `class_name` varchar(30) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '图型名称',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (0, '二手手机', 'icon-icon--');
INSERT INTO `category` VALUES (1, '数码', 'icon-shuma');
INSERT INTO `category` VALUES (2, '二手图书', 'icon-book');
INSERT INTO `category` VALUES (3, '游戏交易', 'icon-youxi');
INSERT INTO `category` VALUES (4, '服装鞋包', 'icon-Txu-');
INSERT INTO `category` VALUES (5, '美妆闲置', 'icon-meizhuang');
INSERT INTO `category` VALUES (6, '运动户外', 'icon-yundong');
INSERT INTO `category` VALUES (7, '乐器', 'icon-gangqin');
INSERT INTO `category` VALUES (8, '跑腿代办', 'icon-icon6');
INSERT INTO `category` VALUES (9, '其他闲置', 'icon-qita-');

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat`  (
  `chat_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '聊天ID',
  `A_openID` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '我的openID',
  `B_openID` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '对方openID',
  `content` text CHARACTER SET gbk COLLATE gbk_chinese_ci NULL COMMENT '一条消息',
  PRIMARY KEY (`chat_id`) USING BTREE,
  INDEX `ch_myID`(`A_openID`) USING BTREE,
  INDEX `ch_otherID`(`B_openID`) USING BTREE,
  CONSTRAINT `ch_myID` FOREIGN KEY (`A_openID`) REFERENCES `user` (`openID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_otherID` FOREIGN KEY (`B_openID`) REFERENCES `user` (`openID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection`  (
  `collection_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `goods_id` int(255) NULL DEFAULT NULL COMMENT '物品ID',
  `openID` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '用户openID',
  PRIMARY KEY (`collection_id`) USING BTREE,
  INDEX `c_openID`(`openID`) USING BTREE,
  INDEX `c_goodsID`(`goods_id`) USING BTREE,
  CONSTRAINT `c_goodsID` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `c_openID` FOREIGN KEY (`openID`) REFERENCES `user` (`openID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES (1, 87, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (2, 86, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (3, 85, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (4, 84, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (5, 83, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (6, 82, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (7, 81, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (8, 80, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (9, 74, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (10, 73, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (11, 72, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (12, 71, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (13, 70, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (14, 69, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `collection` VALUES (15, 68, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '物品ID',
  `goods_name` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '物品名称',
  `goods_describe` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '物品描述',
  `goods_price` decimal(20, 2) NULL DEFAULT NULL COMMENT '物品价格',
  `goods_image` text CHARACTER SET gbk COLLATE gbk_chinese_ci NULL COMMENT '物品图片地址',
  `category_id` tinyint(15) NULL DEFAULT NULL COMMENT '物品类别ID',
  `status` tinyint(5) NULL DEFAULT NULL COMMENT '物品状态: 0：未售出 1：售出',
  `create_time` datetime NULL DEFAULT NULL COMMENT '物品发布时间',
  `sale_time` datetime NULL DEFAULT NULL COMMENT '售出时间',
  `openID` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '卖家openID',
  PRIMARY KEY (`goods_id`) USING BTREE,
  INDEX `g_openID`(`openID`) USING BTREE,
  INDEX `g_categoryID`(`category_id`) USING BTREE,
  CONSTRAINT `g_categoryID` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `g_openID` FOREIGN KEY (`openID`) REFERENCES `user` (`openID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '凳子', '板凳', 1.00, 't4.jpg', 9, 1, '2019-07-04 11:58:06', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (2, '华为手机', '手机', 2.00, 't3.jpg,t3.jpg,t3.jpg,t3.jpg', 0, 1, '2019-07-24 12:16:06', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (3, '三星手机', '手机', 3.00, 't1.jpg,t1.jpg,t1.jpg', 0, 1, '2019-07-24 12:16:24', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (4, '惠普笔记本', '惠普笔记本', 4.00, 't1.jpg', 1, 1, '2019-07-26 14:39:02', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (5, '铁轮椅', '轮椅', 5.00, 't4.jpg', 6, 1, '2019-07-07 20:34:14', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (6, '木凳子', '板凳', 5.00, 't3.jpg,t3.jpgt3.jpg,t3.jpg', 9, 1, '2019-07-10 22:21:41', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (7, '木桌子', '书桌', 5.00, 't1.jpg', 9, 1, '2019-07-23 22:23:21', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (8, '耐克鞋', '破鞋', 3.00, 't1.jpg', 6, 1, '2019-07-24 22:24:30', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (9, '钢铁侠手办', '钢铁侠手办', 2.00, 't5.jpg', 9, 1, '2019-07-03 22:25:02', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (10, '联想笔记本', '联想笔记本', 2.00, 't5.jpg', 1, 1, '2019-06-24 22:25:16', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (11, 'X洗面奶', 'X洗面奶', 2.00, 't5.jpg', 5, 1, '2019-07-11 22:25:34', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (12, 'ipad', 'ipad', 22.00, 't5.jpg', 1, 1, '2019-07-02 22:26:47', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (13, '《斗破苍穹》实体书', '斗破苍穹', 22.00, 't3.jpg,t3.jpg,t3.jpg,t3.jpg', 2, 1, '2019-07-03 11:56:22', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (14, '《斗罗大陆》实体书', '斗罗大陆', 21.00, 't4.jpg', 2, 1, '2019-07-17 12:35:08', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (15, '《诛仙》实体书', '诛仙', 23.00, 't3.jpg,t3.jpg,t3.jpg,t3.jpg', 2, 1, '2019-07-23 13:36:22', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (16, '《仙逆》实体书', '仙逆', 32.00, 't4.jpg', 2, 1, '2019-07-10 14:20:57', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (17, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (18, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (19, '《仙逆》实体书', '诛仙', 23.00, 't3.jpg,t3.jpg,t3.jpg,t3.jpg', 2, 1, '2019-07-10 14:20:57', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (20, '《仙逆》实体书', '诛仙', 23.00, 't3.jpg,t3.jpg,t3.jpg,t3.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (21, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (22, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (23, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (24, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (25, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (26, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (27, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (28, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (29, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (30, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 1, '2019-07-24 15:33:38', '2019-07-25 13:48:15', 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (52, '凳子', '板凳', 1.00, 't4.jpg', 9, 0, '2019-07-04 11:58:06', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (53, '华为手机', '手机', 2.00, 't3.jpg,t3.jpg,t3.jpg,t3.jpg', 0, 0, '2019-07-24 12:16:06', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (54, '三星手机', '手机', 3.00, 't1.jpg,t1.jpg,t1.jpg', 0, 0, '2019-07-24 12:16:24', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (55, '惠普笔记本', '惠普笔记本', 4.00, 't1.jpg', 1, 0, '2019-07-26 14:39:02', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (56, '铁轮椅', '轮椅', 5.00, 't4.jpg', 6, 0, '2019-07-07 20:34:14', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (57, '木凳子', '板凳', 5.00, 't3.jpg,t3.jpgt3.jpg,t3.jpg', 9, 0, '2019-07-10 22:21:41', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (58, '木桌子', '书桌', 5.00, 't1.jpg', 9, 0, '2019-07-23 22:23:21', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (59, '耐克鞋', '破鞋', 3.00, 't1.jpg', 6, 0, '2019-07-24 22:24:30', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (60, '钢铁侠手办', '钢铁侠手办', 2.00, 't5.jpg', 9, 0, '2019-07-03 22:25:02', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (61, '联想笔记本', '联想笔记本', 2.00, 't5.jpg', 1, 0, '2019-06-24 22:25:16', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (62, 'X洗面奶', 'X洗面奶', 2.00, 't5.jpg', 5, 0, '2019-07-11 22:25:34', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (63, 'ipad', 'ipad', 22.00, 't5.jpg', 1, 0, '2019-07-02 22:26:47', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (64, '华为手机', '手机', 2.00, 't3.jpg,t3.jpg,t3.jpg,t3.jpg', 0, 0, '2019-07-24 12:16:06', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (65, '三星手机', '手机', 3.00, 't1.jpg,t1.jpg,t1.jpg', 0, 0, '2019-07-24 12:16:24', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (66, '惠普笔记本', '惠普笔记本', 4.00, 't1.jpg', 1, 0, '2019-07-26 14:39:02', NULL, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4');
INSERT INTO `goods` VALUES (67, '铁轮椅', '轮椅', 5.00, 't4.jpg', 6, 0, '2019-07-07 20:34:14', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (68, '木凳子', '板凳', 5.00, 't3.jpg,t3.jpgt3.jpg,t3.jpg', 9, 0, '2019-07-10 22:21:41', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (69, '木桌子', '书桌', 5.00, 't1.jpg', 9, 0, '2019-07-23 22:23:21', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (70, '《龙族5悼亡者的归来》', '龙族五', 40.00, 't5.jpg', 2, 0, '2019-07-24 15:33:38', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (71, '三星手机', '手机', 3.00, 't1.jpg,t1.jpg,t1.jpg', 0, 0, '2019-07-24 12:16:24', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (72, '惠普笔记本', '惠普笔记本', 4.00, 't1.jpg', 1, 0, '2019-07-26 14:39:02', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (73, '铁轮椅', '轮椅', 5.00, 't4.jpg', 6, 0, '2019-07-07 20:34:14', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (74, '木凳子', '板凳', 5.00, 't3.jpg,t3.jpgt3.jpg,t3.jpg', 9, 0, '2019-07-10 22:21:41', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (80, '木桌子', '书桌', 5.00, 't1.jpg', 9, 0, '2019-07-23 22:23:21', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (81, '耐克鞋', '破鞋', 3.00, 't1.jpg', 6, 0, '2019-07-24 22:24:30', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (82, '钢铁侠手办', '钢铁侠手办', 2.00, 't5.jpg', 9, 0, '2019-07-03 22:25:02', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (83, '联想笔记本', '联想笔记本', 2.00, 't5.jpg', 1, 0, '2019-06-24 22:25:16', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (84, 'X洗面奶', 'X洗面奶', 2.00, 't5.jpg', 5, 0, '2019-07-11 22:25:34', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (85, 'ipad', 'ipad', 22.00, 't5.jpg', 1, 0, '2019-07-02 22:26:47', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (86, '华为手机', '手机', 2.00, 't3.jpg,t3.jpg,t3.jpg,t3.jpg', 0, 0, '2019-07-24 12:16:06', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');
INSERT INTO `goods` VALUES (87, '三星手机', '手机', 3.00, 't1.jpg,t1.jpg,t1.jpg', 0, 0, '2019-07-24 12:16:24', NULL, 'o028s5Lixb3AnRmrWB8cswyjECog');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '留言ID',
  `goods_id` int(255) NULL DEFAULT NULL COMMENT '物品ID',
  `message` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '留言信息',
  `openID` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '留言用户openID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '留言时间',
  `message_number` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '回复框编号',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `m_openID`(`openID`) USING BTREE,
  INDEX `m_goodsID`(`goods_id`) USING BTREE,
  CONSTRAINT `m_goodsID` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `m_openID` FOREIGN KEY (`openID`) REFERENCES `user` (`openID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (9, 1, '保熟嘛？', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-23 20:58:58', 'c75c3331-4fa2-4d37-b090-2339ad5f0f3d1563886738452');
INSERT INTO `message` VALUES (10, 1, '保熟嘛？', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-23 20:58:58', 'c75c3331-4fa2-4d37-b090-2339ad5f0f3d1563886738452');
INSERT INTO `message` VALUES (11, 3, '便宜卖了吧!', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-24 13:46:48', '27acdec2-d885-4eb0-940a-ddecedfae3301563947208873');
INSERT INTO `message` VALUES (12, 3, '便宜卖了吧!', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-24 13:46:48', '27acdec2-d885-4eb0-940a-ddecedfae3301563947208873');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orders_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `goods_id` int(255) NULL DEFAULT NULL COMMENT '物品ID',
  `status` tinyint(5) NULL DEFAULT NULL COMMENT '订单状态',
  `buyer` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '买家openID',
  `seller` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '卖家openID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '订单创建时间',
  `buyer_status` tinyint(5) NULL DEFAULT NULL COMMENT '买家是否删除了订单 0：否 1：是',
  `seller_status` tinyint(5) NULL DEFAULT NULL COMMENT '卖家是否删除了订单 0：否 1：是',
  PRIMARY KEY (`orders_id`) USING BTREE,
  INDEX `o_goodsID`(`goods_id`) USING BTREE,
  INDEX `o_buyer`(`buyer`) USING BTREE,
  INDEX `o_seller`(`seller`) USING BTREE,
  CONSTRAINT `o_buyer` FOREIGN KEY (`buyer`) REFERENCES `user` (`openID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `o_goodsID` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `o_seller` FOREIGN KEY (`seller`) REFERENCES `user` (`openID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (2, 2, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (4, 4, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (5, 5, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (6, 6, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (7, 7, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (8, 8, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (9, 9, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (10, 10, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (11, 11, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (12, 12, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (13, 13, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (14, 14, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (15, 15, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (16, 16, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (17, 17, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (18, 18, 0, 'o028s5Lixb3AnRmrWB8cswyjECog', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (19, 19, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (20, 20, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (21, 21, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (22, 22, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (23, 23, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (24, 24, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (25, 25, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (26, 26, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (27, 27, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (28, 28, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (29, 29, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);
INSERT INTO `orders` VALUES (30, 30, 0, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-25 13:45:18', 0, 0);

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `reply_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复ID',
  `speaker` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '买家name',
  `listener` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '卖家name',
  `seller` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '卖家openID',
  `buyer` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '买家openID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `message` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '回复信息',
  `message_number` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT '' COMMENT '回复框编号',
  `speaker_image` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '回复者头像',
  `goods_id` int(255) NULL DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`reply_id`) USING BTREE,
  INDEX `goods_id_key`(`goods_id`) USING BTREE,
  CONSTRAINT `goods_id_key` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES (8, '无处安身', 'Hello World', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-23 20:58:58', '保熟嘛？', 'c75c3331-4fa2-4d37-b090-2339ad5f0f3d1563886738452', 'https://wx.qlogo.cn/mmopen/vi_32/y7myPTmc5sia014QMflJxGDzJtTJrEWicT9GmOQGwibecbuZBSnomicUk5bzo276nEJKUsDOZgfInkfXynEbBW4NdQ/132', 1);
INSERT INTO `reply` VALUES (9, '无处安身', 'Hello World', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-23 20:59:33', '到底保熟嘛？', 'c75c3331-4fa2-4d37-b090-2339ad5f0f3d1563886738452', 'https://wx.qlogo.cn/mmopen/vi_32/y7myPTmc5sia014QMflJxGDzJtTJrEWicT9GmOQGwibecbuZBSnomicUk5bzo276nEJKUsDOZgfInkfXynEbBW4NdQ/132', 1);
INSERT INTO `reply` VALUES (10, 'Hello World', '无处安身', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-23 21:05:33', '保熟!', 'c75c3331-4fa2-4d37-b090-2339ad5f0f3d1563886738452', 'https://wx.qlogo.cn/mmopen/vi_32/NtzewX2ULcdJYf7k72z7dicicXxjcssYL8hgv4jgbupJLjduxXqLmF2PMcJ4HjohlAHxPvAiaiaciccYZlNrVTonEog/132', 1);
INSERT INTO `reply` VALUES (11, '无处安身', 'Hello World', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-24 13:46:48', '便宜卖了吧!', '27acdec2-d885-4eb0-940a-ddecedfae3301563947208873', 'https://wx.qlogo.cn/mmopen/vi_32/y7myPTmc5sia014QMflJxGDzJtTJrEWicT9GmOQGwibecbuZBSnomicUk5bzo276nEJKUsDOZgfInkfXynEbBW4NdQ/132', 3);
INSERT INTO `reply` VALUES (12, 'Hello World', '无处安身', 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'o028s5Lixb3AnRmrWB8cswyjECog', '2019-07-24 13:48:26', '你想的到美！', '27acdec2-d885-4eb0-940a-ddecedfae3301563947208873', 'https://wx.qlogo.cn/mmopen/vi_32/NtzewX2ULcdJYf7k72z7dicicXxjcssYL8hgv4jgbupJLjduxXqLmF2PMcJ4HjohlAHxPvAiaiaciccYZlNrVTonEog/132', 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openID` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT 'openID',
  `user_name` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_image` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '用户头像地址',
  `blacklist` tinyint(255) NULL DEFAULT NULL COMMENT '用户被拉黑次数',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `openID`(`openID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2229 CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1111, '1', 'aaa', 'https://wx.qlogo.cn/mmopen/vi_32/y7myPTmc5sia014QMflJxGDzJtTJrEWicT9GmOQGwibecbuZBSnomicUk5bzo276nEJKUsDOZgfInkfXynEbBW4NdQ/132', 1);
INSERT INTO `user` VALUES (2222, '2', 'bbb', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLO490QGiciaiax7Xl1vfmE5wjqFVnaStibmh8toiaQjhffsFlxib4kWr64ichEYbNxmGbH4UFZBcOCiajy1Q/132', 2);
INSERT INTO `user` VALUES (2223, 'o028s5Cn_V5Y3ymqRpafs4C5Z4T4', 'Hello World', 'https://wx.qlogo.cn/mmopen/vi_32/NtzewX2ULcdJYf7k72z7dicicXxjcssYL8hgv4jgbupJLjduxXqLmF2PMcJ4HjohlAHxPvAiaiaciccYZlNrVTonEog/132', 0);
INSERT INTO `user` VALUES (2225, 'o028s5Lixb3AnRmrWB8cswyjECog', '无处安身', 'https://wx.qlogo.cn/mmopen/vi_32/y7myPTmc5sia014QMflJxGDzJtTJrEWicT9GmOQGwibecbuZBSnomicUk5bzo276nEJKUsDOZgfInkfXynEbBW4NdQ/132', 0);
INSERT INTO `user` VALUES (2226, 'o028s5Iww32AxwXRy948__S8FFbc', 'D', 'https://wx.qlogo.cn/mmopen/vi_32/C2oLWYznZ4SsHB3P1UFORk2Hj8X5u5qkdBRaEuMyicFr14Fa1x2n58eNqcvzG3VpXG1xSIyyfibcc8Va1prCWsTg/132', 0);
INSERT INTO `user` VALUES (2227, 'o028s5FK4rYpsXk4Zp3psrjMxteg', 'qwe', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLO490QGiciaiax7Xl1vfmE5wjqFVnaStibmh8toiaQjhffsFlxib4kWr64ichEYbNxmGbH4UFZBcOCiajy1Q/132', 0);
INSERT INTO `user` VALUES (2228, 'o028s5FK4rYpsXk4Zp3psrjMxteg-detele', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
