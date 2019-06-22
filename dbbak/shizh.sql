/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : shizh

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-06-22 13:25:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hyxt_meetingfile
-- ----------------------------
DROP TABLE IF EXISTS `hyxt_meetingfile`;
CREATE TABLE `hyxt_meetingfile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comments` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) NOT NULL,
  `fileUrl` varchar(255) NOT NULL,
  `uploaddate` datetime DEFAULT NULL,
  `meetid` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_o0x09gwmi5mo20be3lwah966v` (`meetid`),
  KEY `FK_gc6tepnu6tmi0qbffja5e0hig` (`user_id`),
  CONSTRAINT `FK_gc6tepnu6tmi0qbffja5e0hig` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK_o0x09gwmi5mo20be3lwah966v` FOREIGN KEY (`meetid`) REFERENCES `hyxt_meetinglist` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hyxt_meetingfile
-- ----------------------------
INSERT INTO `hyxt_meetingfile` VALUES ('1', '哈哈', 'BugReport.exe', 'www.file.com', '2018-02-26 19:25:18', '1', '1');
INSERT INTO `hyxt_meetingfile` VALUES ('17', '1', 'compat.xml', '1', '2018-03-01 12:45:22', '1', '1');
INSERT INTO `hyxt_meetingfile` VALUES ('21', '1', '进1.png', '1', '2018-03-02 19:02:47', '1', '1');
INSERT INTO `hyxt_meetingfile` VALUES ('22', '1', 'version.properties', '1', '2018-03-06 14:43:28', '13', '1');
INSERT INTO `hyxt_meetingfile` VALUES ('23', 'a', 'chrome.exe.sig', 'www.a.com', '2019-06-18 14:00:19', '13', '1');

-- ----------------------------
-- Table structure for hyxt_meetinglist
-- ----------------------------
DROP TABLE IF EXISTS `hyxt_meetinglist`;
CREATE TABLE `hyxt_meetinglist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdate` datetime DEFAULT NULL,
  `finishdate` datetime DEFAULT NULL,
  `meetid` varchar(11) NOT NULL,
  `meetName` varchar(255) NOT NULL,
  `meetingRoom` varchar(255) NOT NULL,
  `startdate` datetime DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `isView` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_oxmtwootaqowx221ac0hoc6hb` (`user_id`),
  CONSTRAINT `FK_oxmtwootaqowx221ac0hoc6hb` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hyxt_meetinglist
-- ----------------------------
INSERT INTO `hyxt_meetinglist` VALUES ('1', '2018-02-01 19:23:33', '2018-02-02 19:23:44', '20121212001', '测试会议1', '第一会议室', '2018-02-07 19:24:37', '1', '', '1');
INSERT INTO `hyxt_meetinglist` VALUES ('13', '2018-03-01 21:48:52', '2018-03-01 01:00:21', '20180301001', '发生的发斯蒂芬', '第二会议室', '2018-03-01 01:02:21', '1', '', '1');
INSERT INTO `hyxt_meetinglist` VALUES ('14', '2018-03-01 21:54:01', '2018-03-01 01:00:01', '20180301002', '1212', '第一会议室', '2018-03-01 01:00:01', '1', '', '1');
INSERT INTO `hyxt_meetinglist` VALUES ('15', '2018-03-01 21:54:37', '2018-03-01 01:00:37', '20180301003', '1', '第一会议室', '2018-03-01 01:00:37', '1', null, null);
INSERT INTO `hyxt_meetinglist` VALUES ('16', '2018-03-01 21:55:45', '2018-03-01 23:00:45', '20180301004', 'a', '第一会议室', '2018-03-01 02:00:45', '1', null, null);
INSERT INTO `hyxt_meetinglist` VALUES ('17', '2018-03-01 21:56:20', '2018-03-01 01:00:20', '20180301005', '1', '第一会议室', '2018-03-01 01:00:20', '1', '', '1');
INSERT INTO `hyxt_meetinglist` VALUES ('18', '2018-03-02 12:58:24', '2018-03-19 12:58:11', '20180302001', '1', '第二会议室', '2018-03-01 12:58:08', '1', '', '0');
INSERT INTO `hyxt_meetinglist` VALUES ('19', '2018-03-02 16:16:39', '2018-03-02 16:16:23', '20180302002', 'test002', '第一会议室', '2018-03-01 16:16:21', '1', '备注', '0');
INSERT INTO `hyxt_meetinglist` VALUES ('20', '2018-03-02 16:27:45', '2018-03-14 16:27:36', '20180302003', 'test03', '第一会议室', '2018-03-01 16:27:33', '1', '1', '0');

-- ----------------------------
-- Table structure for hyxt_meetingperson
-- ----------------------------
DROP TABLE IF EXISTS `hyxt_meetingperson`;
CREATE TABLE `hyxt_meetingperson` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `meetid` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2c92ekhanh63tsodtgysnn2jm` (`meetid`),
  KEY `FK_6b2fw365rfd8qcug92ihg2ho6` (`user_id`),
  CONSTRAINT `FK_2c92ekhanh63tsodtgysnn2jm` FOREIGN KEY (`meetid`) REFERENCES `hyxt_meetinglist` (`id`),
  CONSTRAINT `FK_6b2fw365rfd8qcug92ihg2ho6` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hyxt_meetingperson
-- ----------------------------
INSERT INTO `hyxt_meetingperson` VALUES ('4', '15', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('5', '16', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('8', '19', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('12', '18', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('13', '17', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('14', '14', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('15', '1', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('17', '20', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('20', '13', '1');
INSERT INTO `hyxt_meetingperson` VALUES ('21', '13', '2');

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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', null, null, 'icon-company', '业务导航', '2', '1', '0', null, null);
INSERT INTO `sys_resource` VALUES ('2', null, null, 'icon-company', '系统菜单', '0', '8', '0', null, '1');
INSERT INTO `sys_resource` VALUES ('3', null, null, 'user_business_boss', '系统管理', '0', '50', '0', null, '2');
INSERT INTO `sys_resource` VALUES ('4', null, null, 'database', '资源管理', '0', '1', '0', '/resource/manager', '3');
INSERT INTO `sys_resource` VALUES ('5', null, null, 'group', '角色管理', '0', '2', '1', '/role/manager', '3');
INSERT INTO `sys_resource` VALUES ('6', null, null, 'user', '用户管理', '0', '3', '0', '/user/manager', '3');
INSERT INTO `sys_resource` VALUES ('7', null, null, 'application_home', '部门管理', '0', '4', '0', '/organization/manager', '3');
INSERT INTO `sys_resource` VALUES ('8', null, null, 'icon-btn', '资源添加', '1', '0', '0', '/resource/add', '4');
INSERT INTO `sys_resource` VALUES ('9', '2018-01-30 20:49:11', null, 'icon-btn', '列表', '1', '0', '0', '/resource/treeGrid', '4');
INSERT INTO `sys_resource` VALUES ('10', '2018-01-30 20:50:14', null, 'icon-btn', '编辑', '1', '0', '0', '/resource/edit', '4');
INSERT INTO `sys_resource` VALUES ('11', '2018-01-30 20:51:03', null, 'remove', '删除', '1', '0', '0', '/resource/delete', '4');
INSERT INTO `sys_resource` VALUES ('12', null, null, 'icon-btn', '角色授权', '1', '0', '0', '/role/grant', '5');
INSERT INTO `sys_resource` VALUES ('13', '2018-01-30 20:59:47', null, 'icon-btn', '列表', '1', '0', '0', '/role/dataGrid', '5');
INSERT INTO `sys_resource` VALUES ('14', '2018-01-30 21:00:32', null, 'icon-btn', '添加', '1', '0', '0', '/role/add', '5');
INSERT INTO `sys_resource` VALUES ('15', '2018-01-30 21:01:16', null, 'icon-btn', '编辑', '1', '0', '0', '/role/edit', '5');
INSERT INTO `sys_resource` VALUES ('16', '2018-01-30 21:02:31', null, 'remove', '删除', '1', '0', '0', '/role/delete', '5');
INSERT INTO `sys_resource` VALUES ('17', '2018-01-30 21:03:49', null, 'icon-btn', '列表', '1', '0', '0', '/user/dataGrid', '6');
INSERT INTO `sys_resource` VALUES ('18', '2018-01-30 21:04:43', null, 'icon-btn', '添加', '1', '0', '0', '/user/add', '6');
INSERT INTO `sys_resource` VALUES ('19', '2018-01-30 21:05:24', null, 'icon-btn', '编辑', '1', '0', '0', '/user/edit', '6');
INSERT INTO `sys_resource` VALUES ('20', '2018-01-30 21:05:58', null, 'icon-btn', '查看', '1', '0', '0', '/user/view', '6');
INSERT INTO `sys_resource` VALUES ('21', '2018-01-30 21:06:41', null, 'icon-btn', '删除', '1', '0', '0', '/user/delete', '6');
INSERT INTO `sys_resource` VALUES ('22', '2018-01-30 21:07:55', null, 'icon-btn', '列表', '1', '0', '0', '/organization/treeGrid', '7');
INSERT INTO `sys_resource` VALUES ('23', '2018-01-30 21:08:23', null, 'icon-btn', '添加', '1', '0', '0', '/organization/add', '7');
INSERT INTO `sys_resource` VALUES ('24', '2018-01-30 21:09:09', null, 'icon-btn', '编辑', '1', '0', '0', '/organization/edit', '7');
INSERT INTO `sys_resource` VALUES ('25', '2018-01-30 21:09:53', null, 'icon-btn', '删除', '1', '0', '0', '/organization/delete', '7');
INSERT INTO `sys_resource` VALUES ('26', '2018-01-30 21:10:22', null, 'icon-btn', '查看', '1', '0', '0', '/organization/view', '7');
INSERT INTO `sys_resource` VALUES ('33', '2018-02-26 10:02:26', null, 'icon-company', '业务菜单', '0', '0', '0', '', '1');
INSERT INTO `sys_resource` VALUES ('34', '2018-02-26 10:03:20', null, 'icon-company', '会议文件管理', '0', '0', '0', '', '33');
INSERT INTO `sys_resource` VALUES ('35', '2018-02-26 10:06:54', null, 'icon-company', '会议文件列表', '0', '0', '0', '/meetingfile/manager', '34');
INSERT INTO `sys_resource` VALUES ('36', '2018-02-26 10:24:17', null, 'icon-company', '会议管理', '0', '0', '0', '/meeting/manager', '34');
INSERT INTO `sys_resource` VALUES ('37', '2018-02-26 10:26:13', null, 'icon-btn', '文件预览', '1', '0', '0', '/meeting/viewfile', '36');
INSERT INTO `sys_resource` VALUES ('38', '2018-02-26 10:37:39', null, 'icon-btn', '新增', '1', '0', '0', '/meeting/add', '36');
INSERT INTO `sys_resource` VALUES ('39', '2018-02-26 10:40:37', null, 'icon-btn', '编辑', '1', '0', '0', '/meeting/edit', '36');
INSERT INTO `sys_resource` VALUES ('40', '2018-02-26 10:41:13', null, 'remove', '删除', '1', '0', '0', '/meeting/delete', '36');
INSERT INTO `sys_resource` VALUES ('41', '2018-02-26 10:42:34', null, 'icon-btn', '查看', '1', '0', '0', '/meeting/view', '36');
INSERT INTO `sys_resource` VALUES ('42', '2018-02-26 10:44:34', null, 'icon-btn', '列表', '1', '0', '0', '/meeting/dataGrid', '36');
INSERT INTO `sys_resource` VALUES ('43', '2018-02-26 10:52:02', null, 'icon-btn', '新增', '1', '0', '0', '/meetingfile/add', '35');
INSERT INTO `sys_resource` VALUES ('44', '2018-02-26 10:52:40', null, 'remove', '删除', '1', '0', '0', '/meetingfile/delete', '35');
INSERT INTO `sys_resource` VALUES ('45', '2018-02-26 10:53:15', null, 'icon-btn', '编辑', '1', '0', '0', '/meetingfile/edit', '35');
INSERT INTO `sys_resource` VALUES ('46', '2018-02-26 10:53:47', null, 'icon-btn', '列表', '1', '0', '0', '/meetingfile/dataGrid', '35');
INSERT INTO `sys_resource` VALUES ('47', '2018-02-26 10:54:56', null, 'icon-btn', '下载', '1', '0', '0', '/meetingfile/download', '35');
INSERT INTO `sys_resource` VALUES ('48', '2018-03-02 09:20:50', null, 'icon-btn', '文件预览', '1', '0', '0', '/meetingfile/viewfiles', '36');

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
INSERT INTO `sys_role_resource` VALUES ('1', '2');
INSERT INTO `sys_role_resource` VALUES ('1', '3');
INSERT INTO `sys_role_resource` VALUES ('1', '4');
INSERT INTO `sys_role_resource` VALUES ('1', '5');
INSERT INTO `sys_role_resource` VALUES ('1', '6');
INSERT INTO `sys_role_resource` VALUES ('1', '7');
INSERT INTO `sys_role_resource` VALUES ('1', '8');
INSERT INTO `sys_role_resource` VALUES ('1', '9');
INSERT INTO `sys_role_resource` VALUES ('1', '10');
INSERT INTO `sys_role_resource` VALUES ('1', '11');
INSERT INTO `sys_role_resource` VALUES ('1', '12');
INSERT INTO `sys_role_resource` VALUES ('1', '13');
INSERT INTO `sys_role_resource` VALUES ('1', '14');
INSERT INTO `sys_role_resource` VALUES ('1', '15');
INSERT INTO `sys_role_resource` VALUES ('1', '16');
INSERT INTO `sys_role_resource` VALUES ('1', '17');
INSERT INTO `sys_role_resource` VALUES ('1', '18');
INSERT INTO `sys_role_resource` VALUES ('1', '19');
INSERT INTO `sys_role_resource` VALUES ('1', '20');
INSERT INTO `sys_role_resource` VALUES ('1', '21');
INSERT INTO `sys_role_resource` VALUES ('1', '22');
INSERT INTO `sys_role_resource` VALUES ('1', '23');
INSERT INTO `sys_role_resource` VALUES ('1', '24');
INSERT INTO `sys_role_resource` VALUES ('1', '25');
INSERT INTO `sys_role_resource` VALUES ('1', '26');
INSERT INTO `sys_role_resource` VALUES ('1', '33');
INSERT INTO `sys_role_resource` VALUES ('1', '34');
INSERT INTO `sys_role_resource` VALUES ('1', '35');
INSERT INTO `sys_role_resource` VALUES ('1', '36');
INSERT INTO `sys_role_resource` VALUES ('1', '37');
INSERT INTO `sys_role_resource` VALUES ('1', '38');
INSERT INTO `sys_role_resource` VALUES ('1', '39');
INSERT INTO `sys_role_resource` VALUES ('1', '40');
INSERT INTO `sys_role_resource` VALUES ('1', '41');
INSERT INTO `sys_role_resource` VALUES ('1', '42');
INSERT INTO `sys_role_resource` VALUES ('1', '43');
INSERT INTO `sys_role_resource` VALUES ('1', '44');
INSERT INTO `sys_role_resource` VALUES ('1', '45');
INSERT INTO `sys_role_resource` VALUES ('1', '46');
INSERT INTO `sys_role_resource` VALUES ('1', '47');
INSERT INTO `sys_role_resource` VALUES ('1', '48');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '18', null, null, '0', 'admin', '超级管理员', '202cb962ac59075b964b07152d234b70', null, '0', '0', '0', '1');
INSERT INTO `sys_user` VALUES ('2', '21', '2018-03-06 15:14:48', '', '0', 'test1', 'test1name', '202cb962ac59075b964b07152d234b70', '110', '0', '0', '0', '1');

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
INSERT INTO `sys_user_role` VALUES ('2', '1');
