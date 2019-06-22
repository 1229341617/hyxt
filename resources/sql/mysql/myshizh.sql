/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : shizh

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-30 11:38:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `createdatetime` datetime DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `isaccount` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `seq` int(11) DEFAULT NULL,
  `shortname` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7fssu67fw54bf6fbo1iwr756b` (`pid`),
  CONSTRAINT `FK_7fssu67fw54bf6fbo1iwr756b` FOREIGN KEY (`pid`) REFERENCES `sys_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1', null, '01', null, 'icon-company', '1', '市政公用集团资产管理公司', '1', 'zc', null);
INSERT INTO `sys_organization` VALUES ('12', null, '10001', null, 'icon-folder', '0', '资产公司领导', '0', null, '1');
INSERT INTO `sys_organization` VALUES ('14', null, '30001', null, 'icon-folder', '0', '工程信息部', '4', null, '1');
INSERT INTO `sys_organization` VALUES ('15', null, '40001', null, 'icon-folder', '0', '法务审计部', '5', null, '1');
INSERT INTO `sys_organization` VALUES ('16', null, '50001', null, 'icon-folder', '0', '决算部', '6', null, '1');
INSERT INTO `sys_organization` VALUES ('27', null, '70001', null, 'icon-folder', '1', '国体中心', '8', 'gt', '1');
INSERT INTO `sys_organization` VALUES ('35', null, '60001', null, 'icon-folder', '1', '驻车公司', '7', 'zv', '1');
INSERT INTO `sys_organization` VALUES ('36', null, '60010', null, 'icon-folder', '0', '驻车公司领导', '0', null, '35');
INSERT INTO `sys_organization` VALUES ('37', null, '60020', null, 'icon-folder', '0', '工程建设部', '0', null, '35');
INSERT INTO `sys_organization` VALUES ('38', null, '70010', null, 'icon-folder', '0', '国体公司领导', '0', null, '27');
INSERT INTO `sys_organization` VALUES ('39', null, '70020', null, 'icon-folder', '0', '工程项目部', '0', null, '27');

-- ----------------------------
-- Table structure for sys_organization_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_role`;
CREATE TABLE `sys_organization_role` (
  `organization_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`),
  KEY `FK_qlp6wmtt7vjskng0lmd9n7hci` (`organization_id`),
  KEY `FK_tdw57edjojh04hcfxaxm31r05` (`role_id`),
  CONSTRAINT `FK_qlp6wmtt7vjskng0lmd9n7hci` FOREIGN KEY (`organization_id`) REFERENCES `sys_organization` (`id`),
  CONSTRAINT `FK_tdw57edjojh04hcfxaxm31r05` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_organization_role
-- ----------------------------
INSERT INTO `sys_organization_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `resourcetype` int(11) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lhcrxhwf9hilx0lwhwxigxxqg` (`pid`),
  CONSTRAINT `FK_lhcrxhwf9hilx0lwhwxigxxqg` FOREIGN KEY (`pid`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', null, null, 'icon-company', '业务导航', '2', '1', '0', null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `isdefault` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `roleId` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员，拥有全部权限', '0', '超级管理员', 'admin', '0');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  PRIMARY KEY (`resource_id`,`role_id`),
  KEY `FK_p16r4t8uf973ru6yrcbb7r1ku` (`resource_id`),
  KEY `FK_10rpk2k4gddcfy7bnnmqgap3s` (`role_id`),
  CONSTRAINT `FK_10rpk2k4gddcfy7bnnmqgap3s` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FK_p16r4t8uf973ru6yrcbb7r1ku` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `CREATEDATETIME` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `isdefault` int(11) DEFAULT NULL,
  `loginname` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `usertype` int(11) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jl2srlt2cvxyiudt0fjly6m7n` (`organization_id`),
  CONSTRAINT `FK_jl2srlt2cvxyiudt0fjly6m7n` FOREIGN KEY (`organization_id`) REFERENCES `sys_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '18', null, null, '0', 'admin', '超级管理员', '202cb962ac59075b964b07152d234b70', null, '0', '0', '0', '1');

-- ----------------------------
-- Table structure for sys_user_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_resource`;
CREATE TABLE `sys_user_resource` (
  `user_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  PRIMARY KEY (`resource_id`,`user_id`),
  KEY `FK_1efpvyjkey6cuxildlfyk4dwg` (`resource_id`),
  KEY `FK_b9i1c3j45g4s7vycwx4h3esyc` (`user_id`),
  CONSTRAINT `FK_1efpvyjkey6cuxildlfyk4dwg` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`id`),
  CONSTRAINT `FK_b9i1c3j45g4s7vycwx4h3esyc` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_resource
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK_fxu3td9m5o7qov1kbdvmn0g0x` (`role_id`),
  KEY `FK_fethvr269t6stivlddbo5pxry` (`user_id`),
  CONSTRAINT `FK_fethvr269t6stivlddbo5pxry` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK_fxu3td9m5o7qov1kbdvmn0g0x` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
