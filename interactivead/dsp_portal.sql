/*
Navicat MySQL Data Transfer

Source Server         : DSP-RDS
Source Server Version : 50616
Source Host           : rm-2zeq7f09ka2n496k2o.mysql.rds.aliyuncs.com:3306
Source Database       : dsp_portal

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2018-03-13 15:21:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dsp_pt_admin_auth
-- ----------------------------
DROP TABLE IF EXISTS `dsp_pt_admin_auth`;
CREATE TABLE `dsp_pt_admin_auth` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `uri` varchar(100) NOT NULL COMMENT 'http请求URI',
  `method` varchar(6) DEFAULT NULL COMMENT 'http请求method',
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员权限';

-- ----------------------------
-- Records of dsp_pt_admin_auth
-- ----------------------------

-- ----------------------------
-- Table structure for dsp_pt_admin_info
-- ----------------------------
DROP TABLE IF EXISTS `dsp_pt_admin_info`;
CREATE TABLE `dsp_pt_admin_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `mail` varchar(100) NOT NULL COMMENT '管理员登录邮箱',
  `password` varchar(50) NOT NULL COMMENT '管理员登录密码',
  `role_ids` varchar(50) NOT NULL COMMENT '角色，id逗号分隔',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='dsp管理员';

-- ----------------------------
-- Records of dsp_pt_admin_info
-- ----------------------------
INSERT INTO `dsp_pt_admin_info` VALUES ('42', '2016-11-07 18:12:52', '2016-11-07 18:12:55', 'adsmar_server@163.com', 'yTjdJq1QBIjqIlXYPre+IzEFFOQ=', '-1', '超级管理员');

-- ----------------------------
-- Table structure for dsp_pt_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `dsp_pt_admin_role`;
CREATE TABLE `dsp_pt_admin_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `auth_ids` varchar(300) NOT NULL COMMENT '权限；权限id逗号隔开',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_index_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='管理员角色';

-- ----------------------------
-- Records of dsp_pt_admin_role
-- ----------------------------

-- ----------------------------
-- Table structure for dsp_pt_advertiser_info
-- ----------------------------
DROP TABLE IF EXISTS `dsp_pt_advertiser_info`;
CREATE TABLE `dsp_pt_advertiser_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `adv_id` bigint(20) unsigned NOT NULL COMMENT '广告主id',
  `mail` varchar(100) NOT NULL COMMENT '登录邮箱',
  `nick_name` varchar(10) DEFAULT NULL COMMENT '昵称',
  `phone_number` varchar(50) NOT NULL COMMENT '手机号码',
  `password` varchar(50) NOT NULL COMMENT '登录密码',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `alipay_account` varchar(100) NOT NULL COMMENT '支付宝账号',
  `status` smallint(6) NOT NULL COMMENT '0:未审核；1：审核通过；2：审核不通过',
  `telephone` varchar(50) DEFAULT NULL COMMENT '固定电话',
  `fax` varchar(50) DEFAULT NULL COMMENT '传真',
  `qq` bigint(20) unsigned DEFAULT NULL COMMENT 'qq号码',
  `reason` varchar(50) DEFAULT NULL COMMENT '审核不通过原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='广告主信息';

-- ----------------------------
-- Records of dsp_pt_advertiser_info
-- ----------------------------
INSERT INTO `dsp_pt_advertiser_info` VALUES ('28', '2017-07-07 18:14:57', '2017-07-07 18:15:09', '270', '4HN1MAmy2+aqYWDLuiQSlFJbQIJ/Kgu7/o5Hhnw4I5g=', null, 'OdyULPOdGUk/AGvO3nshcw==', '/eF93t1WPn2nQl/rKLHyyvx30PE=', 'AESz5LxR2GP8bSou1+W1ow==', 'RMkec8YDDCnJ81mnZ3cbBw==', 'LBuDYemcFNYHBYL4y4Gxbg==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('29', '2017-07-10 17:16:55', '2017-07-10 18:02:55', '271', 'Os2/MfdQ7AoSupUDqnYA60jphVXL74gqboWO4d5H5wE=', null, 'PI/1YuWKdZn9sVKjJtazuA==', '5vRFYoAAoaFuS2U/Kp5dZPYPk+U=', '6Ofex+RNjf8ZUtyFv76LOQ==', '6yxWuToyQKJNuvzLRPSJsA==', 'pZ9F7KcMmiS1Q+BP4wAxhg==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('30', '2017-07-10 17:20:58', '2017-07-10 17:20:58', '272', 'hTSG9+OVMkgoBjma4kXIEscgsYE+ZtRFfl0prfQ4Oxs=', null, 'HGFFUENsVI9Pt7Eij30/6g==', 'aM1+CkbwhW40aSYu8oTLBOrgxa0=', 'D5NwdqDwQF1e3YDUH4N4jQ==', 'D5NwdqDwQF1e3YDUH4N4jQ==', 'N7pTcQL3xc1roBsLCCPBGw==', '0', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('31', '2017-07-10 17:26:57', '2017-07-10 17:31:44', '273', 'qyr8uoo79aM3BcvvAnMTmIhIALs/hXnm6Tl92e48z9c=', null, 'mBuQN3fILPtHvFOmrx/nkA==', 'o01lnMZf5gQEDZLMbnM8UmkQml0=', 'MFRK9gYM4+EGEicta9A+RQ==', 'zKsDxBy+kuQVC4LYl3BjgQ==', 'AJs+aFmt2Le2CPeqVOxo5w==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('32', '2017-07-10 17:35:31', '2017-07-10 18:08:58', '274', 'jJtHbpN9vldQ2wgQ7JqgbCD5p21EXQYh/Z9mT1lLnHo=', null, 'Ua0mZUqe4klWrd9wkrhLtw==', 'GIKYggwDVHxREkdfGDClvHQ22zw=', '+Sjh84XK17o/SBF8EPekMw==', 'epPm0pDi4oAFxHE7sM/XeQ==', 'NPLmPkUXPqjSNT21PT+nIg==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('33', '2017-07-10 17:59:25', '2017-07-10 18:08:52', '275', '3J7u2DAFfK94865DuQf8i4hIALs/hXnm6Tl92e48z9c=', null, 'dpa/GnJN6aQmV5TiO6Gqwg==', '54nJ11+mn/RTb7WGSUk6IZzKwQY=', 'G0MoEOEV0cPwCmYwDw4J1Q==', 'gAZcV8GA4u8HzttCtBI+YQ==', 'IK7ZomaB7nRWSWWvw70DbQ==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('34', '2017-08-07 09:54:39', '2017-08-07 09:57:00', '276', 'uFKNJCkO5TlQ+vEpNhuvqt+znXgIDCHGN6jwLaRBeEs=', null, 'lGQb297JQ9d33sKGnQyZSQ==', 'k6O44k9HTAhTy8z/eY6BHPdmaME=', 'xnOsU3m70Mjt58Rkfa1X/w==', 'xnOsU3m70Mjt58Rkfa1X/w==', 'QPy3RvFuDVjiAWDUHtjseA==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('35', '2017-08-24 16:02:47', '2017-08-24 16:03:20', '277', 'AyHqtx4qUDocw/cqWAqnbYhIALs/hXnm6Tl92e48z9c=', null, 'lw0PJQEhT+6wmgmaLgjOXw==', '6kHeqKBqcqeZy1cMK9qsmP1tU8o=', '+ibiYA5rB2Un5XAdHvX0hQ==', '+ibiYA5rB2Un5XAdHvX0hQ==', '+ibiYA5rB2Un5XAdHvX0hQ==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('36', '2017-11-07 14:21:18', '2017-11-07 14:21:30', '278', 'J1aXK3yNKM7hh9m5R7LY3quMNIEbZgf9g+xu8p38DL8=', null, 'L28zUO2mQajgz4yI0A9bbg==', '/TolgKMZXeXV8iFLV2cJRV8Ssh8=', 'BnlP0lOZw1NQPe1c3QeLHw==', 'wrQqTqdJqqdyiKxZ9cTA916hWFepXSv6X0DH1Soi8Pw=', 'ALCayHuROmIAMbQhJnN/qA==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('37', '2017-12-29 09:36:01', '2017-12-29 09:36:46', '279', 'OksubjH2J9B3rNCa8ulrtVJbQIJ/Kgu7/o5Hhnw4I5g=', null, 'B/LwOZROeci75w41HeWNTg==', 'LU0XVRqblfm9grsybJQ4kqJtD9U=', 'CwrU6S347M7Pci00UtGfXg==', 'cENnvGDap9fQ/Rl/QDDIjw==', 'HEQleIMLnyct60DEaBQAsg==', '1', null, null, null, null);
INSERT INTO `dsp_pt_advertiser_info` VALUES ('38', '2018-02-01 10:14:12', '2018-02-01 10:14:49', '280', '05fjc4VOJ9Dr4oTNlRJNb4hIALs/hXnm6Tl92e48z9c=', null, 'oHOUPkU0bQMyNkqwKvN6zA==', 'l+9M6468W2Q3MOyud/djG4WsB3o=', 'cmRSWyHx4aH0tnTwZ7NjgQ==', 'aujUIpopiw8Nh/drHVGOpDNge1dE/YiRZLs3Lrle2oM=', 'x/VSXogswN1pf7AhPpKFhQ==', '1', null, null, null, null);

-- ----------------------------
-- Table structure for dsp_pt_config
-- ----------------------------
DROP TABLE IF EXISTS `dsp_pt_config`;
CREATE TABLE `dsp_pt_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `config_key` varchar(20) NOT NULL COMMENT '配置key',
  `config_value` varchar(2000) NOT NULL COMMENT '配置value',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='配置信息';

-- ----------------------------
-- Records of dsp_pt_config
-- ----------------------------
INSERT INTO `dsp_pt_config` VALUES ('4', '2016-09-23 15:34:14', '2016-09-23 15:34:20', 'mail.smtp.host', 'nWFRxWyJdktceYDPdcNVbg==');
INSERT INTO `dsp_pt_config` VALUES ('5', '2016-09-23 15:36:09', '2016-09-23 15:36:11', 'mail.smtp.username', 'cRe6lS2XGR+j1RCx0RHDjpaMk1EPOEp4lxfrLfQdgMU=');
INSERT INTO `dsp_pt_config` VALUES ('6', '2016-09-23 15:39:58', '2016-09-23 15:40:00', 'mail.smtp.password', 'wtXoyJ1pUJvJgBkvEvL19Q==');
INSERT INTO `dsp_pt_config` VALUES ('7', '2016-09-23 15:39:58', '2016-09-23 15:39:58', 'business.name', 'hH22uJQENfJdH3BTtrwuUg==');

-- ----------------------------
-- Table structure for dsp_pt_pk_admin_id_adv_id
-- ----------------------------
DROP TABLE IF EXISTS `dsp_pt_pk_admin_id_adv_id`;
CREATE TABLE `dsp_pt_pk_admin_id_adv_id` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `admin_id` bigint(20) unsigned NOT NULL COMMENT 'dsp后台管理员id',
  `adv_id` bigint(20) unsigned NOT NULL COMMENT '推广主id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='管理员和推广主关联表';

-- ----------------------------
-- Records of dsp_pt_pk_admin_id_adv_id
-- ----------------------------
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('35', '2017-07-11 14:17:20', '2017-07-11 14:17:20', '42', '29');
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('36', '2017-07-13 11:17:46', '2017-07-13 11:17:46', '42', '32');
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('37', '2017-08-08 09:18:31', '2017-08-08 09:18:31', '42', '34');
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('38', '2017-08-25 10:52:46', '2017-08-25 10:52:46', '42', '35');
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('39', '2017-11-07 14:22:21', '2017-11-07 14:22:21', '42', '36');
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('40', '2017-11-08 13:17:56', '2017-11-08 13:17:56', '42', '33');
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('41', '2017-11-08 13:18:29', '2017-11-08 13:18:29', '42', '31');
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('42', '2017-11-08 13:18:51', '2017-11-08 13:18:51', '42', '28');
INSERT INTO `dsp_pt_pk_admin_id_adv_id` VALUES ('43', '2017-12-29 09:45:45', '2017-12-29 09:45:45', '42', '37');

-- ----------------------------
-- Table structure for dsp_pt_session
-- ----------------------------
DROP TABLE IF EXISTS `dsp_pt_session`;
CREATE TABLE `dsp_pt_session` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `session_id` varchar(50) NOT NULL COMMENT 'session ID',
  `session_key` varchar(20) NOT NULL COMMENT 'session KEY',
  `session_value` varchar(50) NOT NULL COMMENT 'session VALUE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3990 DEFAULT CHARSET=utf8 COMMENT='session统一管理';

-- ----------------------------
-- Records of dsp_pt_session
-- ----------------------------
INSERT INTO `dsp_pt_session` VALUES ('3989', '2018-03-13 00:17:48', '2018-03-13 00:17:48', '169CCF991ED9D552887CE89D0C6A6AFB', 'session.verify.code', 'ayx7s');

-- ----------------------------
-- Table structure for ssp_pt_app_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ssp_pt_app_login_log`;
CREATE TABLE `ssp_pt_app_login_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长id',
  `email` varchar(255) DEFAULT '' COMMENT '登陆账户',
  `ip` varchar(255) DEFAULT '' COMMENT '登陆ip',
  `city` varchar(255) DEFAULT '' COMMENT '登陆城市',
  `type` int(10) DEFAULT '1' COMMENT '登陆类型(1：pc端2：手机端)',
  `user_class` int(10) DEFAULT NULL COMMENT '用户类别(1.媒体主，2运营管理员)',
  `login_date` datetime DEFAULT NULL COMMENT '登陆时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ssp_pt_app_login_log
-- ----------------------------
INSERT INTO `ssp_pt_app_login_log` VALUES ('1', '1', '0:0:0:0:0:0:0:1', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('2', '1', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('3', '1', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('4', '1', '0:0:0:0:0:0:0:1', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('5', 'ssp', '0:0:0:0:0:0:0:1', '', '1', '2', '2018-01-15 10:42:05');
INSERT INTO `ssp_pt_app_login_log` VALUES ('6', 'xuepengfei@adusing.com', '0:0:0:0:0:0:0:1', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('7', '1', '0:0:0:0:0:0:0:1', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('8', '1', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('9', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('10', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('11', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('12', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('13', 'ssp', '106.2.197.29', '', '1', '2', '2018-01-15 13:26:03');
INSERT INTO `ssp_pt_app_login_log` VALUES ('14', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('15', 'ssp', '106.2.197.29', '', '1', '2', '2018-01-15 13:37:29');
INSERT INTO `ssp_pt_app_login_log` VALUES ('16', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('17', 'xuepengfei@adusing.com', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('18', '1', '192.168.2.253', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('19', 'xuepengfei@adusing.com', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('20', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('21', 'ssp', '106.2.197.29', '', '1', '2', '2018-01-15 14:59:55');
INSERT INTO `ssp_pt_app_login_log` VALUES ('22', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('23', 'dlk3', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('24', 'xuepengfei@adusing.com', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('25', '1', '106.2.197.27', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('26', '1', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('27', 'xuepengfei@adusing.com', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('28', '1', '106.2.197.29', '', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('29', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', null);
INSERT INTO `ssp_pt_app_login_log` VALUES ('30', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 09:30:51');
INSERT INTO `ssp_pt_app_login_log` VALUES ('31', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 09:37:02');
INSERT INTO `ssp_pt_app_login_log` VALUES ('32', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 09:37:07');
INSERT INTO `ssp_pt_app_login_log` VALUES ('33', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 09:45:33');
INSERT INTO `ssp_pt_app_login_log` VALUES ('34', '1', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-17 09:56:55');
INSERT INTO `ssp_pt_app_login_log` VALUES ('35', '1', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-17 12:50:37');
INSERT INTO `ssp_pt_app_login_log` VALUES ('36', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 12:58:06');
INSERT INTO `ssp_pt_app_login_log` VALUES ('37', '1', '192.168.2.253', '未分配或者内网IP0', '1', '1', '2018-01-17 13:01:20');
INSERT INTO `ssp_pt_app_login_log` VALUES ('38', '1', '192.168.2.253', '未分配或者内网IP0', '1', '1', '2018-01-17 13:02:13');
INSERT INTO `ssp_pt_app_login_log` VALUES ('39', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-17 13:11:47');
INSERT INTO `ssp_pt_app_login_log` VALUES ('40', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 13:39:30');
INSERT INTO `ssp_pt_app_login_log` VALUES ('41', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 15:01:54');
INSERT INTO `ssp_pt_app_login_log` VALUES ('42', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 15:17:49');
INSERT INTO `ssp_pt_app_login_log` VALUES ('43', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 16:18:07');
INSERT INTO `ssp_pt_app_login_log` VALUES ('44', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-17 16:40:30');
INSERT INTO `ssp_pt_app_login_log` VALUES ('45', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 17:18:32');
INSERT INTO `ssp_pt_app_login_log` VALUES ('46', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 17:50:06');
INSERT INTO `ssp_pt_app_login_log` VALUES ('47', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 18:10:46');
INSERT INTO `ssp_pt_app_login_log` VALUES ('48', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 18:11:48');
INSERT INTO `ssp_pt_app_login_log` VALUES ('49', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 18:11:53');
INSERT INTO `ssp_pt_app_login_log` VALUES ('50', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 18:12:36');
INSERT INTO `ssp_pt_app_login_log` VALUES ('51', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 18:12:40');
INSERT INTO `ssp_pt_app_login_log` VALUES ('52', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-17 18:12:49');
INSERT INTO `ssp_pt_app_login_log` VALUES ('53', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 08:58:58');
INSERT INTO `ssp_pt_app_login_log` VALUES ('54', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 09:04:09');
INSERT INTO `ssp_pt_app_login_log` VALUES ('55', 'dlk3', '192.168.2.5', '未分配或者内网IP0', '1', '1', '2018-01-18 09:16:09');
INSERT INTO `ssp_pt_app_login_log` VALUES ('56', 'dlk3', '192.168.2.5', '未分配或者内网IP0', '1', '1', '2018-01-18 09:20:15');
INSERT INTO `ssp_pt_app_login_log` VALUES ('57', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 10:05:00');
INSERT INTO `ssp_pt_app_login_log` VALUES ('58', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 11:19:51');
INSERT INTO `ssp_pt_app_login_log` VALUES ('59', 'dlk3', '192.168.2.5', '未分配或者内网IP0', '1', '1', '2018-01-18 11:24:59');
INSERT INTO `ssp_pt_app_login_log` VALUES ('60', 'ssp', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '2', '2018-01-18 11:34:52');
INSERT INTO `ssp_pt_app_login_log` VALUES ('61', 'dlk3', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '2', '2018-01-18 11:34:58');
INSERT INTO `ssp_pt_app_login_log` VALUES ('62', 'dlk3', '192.168.2.5', '未分配或者内网IP0', '1', '1', '2018-01-18 11:43:57');
INSERT INTO `ssp_pt_app_login_log` VALUES ('63', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 13:03:50');
INSERT INTO `ssp_pt_app_login_log` VALUES ('64', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 13:09:35');
INSERT INTO `ssp_pt_app_login_log` VALUES ('65', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 13:20:02');
INSERT INTO `ssp_pt_app_login_log` VALUES ('66', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 13:54:50');
INSERT INTO `ssp_pt_app_login_log` VALUES ('67', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 14:20:13');
INSERT INTO `ssp_pt_app_login_log` VALUES ('68', '1', '192.168.2.253', '未分配或者内网IP0', '1', '1', '2018-01-18 14:22:55');
INSERT INTO `ssp_pt_app_login_log` VALUES ('69', '1', '192.168.2.253', '未分配或者内网IP0', '1', '1', '2018-01-18 14:23:57');
INSERT INTO `ssp_pt_app_login_log` VALUES ('70', '1', '192.168.2.253', '未分配或者内网IP0', '1', '1', '2018-01-18 14:24:11');
INSERT INTO `ssp_pt_app_login_log` VALUES ('71', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 14:27:57');
INSERT INTO `ssp_pt_app_login_log` VALUES ('72', '1', '192.168.2.253', '未分配或者内网IP0', '1', '1', '2018-01-18 14:31:13');
INSERT INTO `ssp_pt_app_login_log` VALUES ('73', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 14:31:31');
INSERT INTO `ssp_pt_app_login_log` VALUES ('74', 'dlk3', '192.168.2.5', '未分配或者内网IP0', '1', '1', '2018-01-18 14:32:08');
INSERT INTO `ssp_pt_app_login_log` VALUES ('75', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 14:32:12');
INSERT INTO `ssp_pt_app_login_log` VALUES ('76', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 14:33:32');
INSERT INTO `ssp_pt_app_login_log` VALUES ('77', 'dlk3', '192.168.2.5', '未分配或者内网IP0', '1', '1', '2018-01-18 14:39:11');
INSERT INTO `ssp_pt_app_login_log` VALUES ('78', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 14:54:06');
INSERT INTO `ssp_pt_app_login_log` VALUES ('79', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 15:07:30');
INSERT INTO `ssp_pt_app_login_log` VALUES ('80', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 15:16:44');
INSERT INTO `ssp_pt_app_login_log` VALUES ('81', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 15:41:07');
INSERT INTO `ssp_pt_app_login_log` VALUES ('82', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 15:42:41');
INSERT INTO `ssp_pt_app_login_log` VALUES ('83', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 15:53:13');
INSERT INTO `ssp_pt_app_login_log` VALUES ('84', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 15:55:39');
INSERT INTO `ssp_pt_app_login_log` VALUES ('85', 'ssp', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '2', '2018-01-18 16:04:50');
INSERT INTO `ssp_pt_app_login_log` VALUES ('86', 'dlk3', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '2', '2018-01-18 16:04:56');
INSERT INTO `ssp_pt_app_login_log` VALUES ('87', '1', '192.168.2.253', '未分配或者内网IP0', '1', '1', '2018-01-18 16:13:25');
INSERT INTO `ssp_pt_app_login_log` VALUES ('88', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 16:19:34');
INSERT INTO `ssp_pt_app_login_log` VALUES ('89', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 16:20:30');
INSERT INTO `ssp_pt_app_login_log` VALUES ('90', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 16:29:54');
INSERT INTO `ssp_pt_app_login_log` VALUES ('91', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-18 16:39:51');
INSERT INTO `ssp_pt_app_login_log` VALUES ('92', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 16:41:21');
INSERT INTO `ssp_pt_app_login_log` VALUES ('93', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-18 17:16:54');
INSERT INTO `ssp_pt_app_login_log` VALUES ('94', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-18 18:54:45');
INSERT INTO `ssp_pt_app_login_log` VALUES ('95', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-18 18:55:05');
INSERT INTO `ssp_pt_app_login_log` VALUES ('96', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-18 18:55:35');
INSERT INTO `ssp_pt_app_login_log` VALUES ('97', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-19 11:21:15');
INSERT INTO `ssp_pt_app_login_log` VALUES ('98', '1', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-19 13:16:24');
INSERT INTO `ssp_pt_app_login_log` VALUES ('99', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-19 13:26:29');
INSERT INTO `ssp_pt_app_login_log` VALUES ('100', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-19 13:26:42');
INSERT INTO `ssp_pt_app_login_log` VALUES ('101', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-22 09:01:53');
INSERT INTO `ssp_pt_app_login_log` VALUES ('102', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-22 12:39:47');
INSERT INTO `ssp_pt_app_login_log` VALUES ('103', '1053831109@qq.com', '106.2.197.29', '中国北京市', '1', '1', '2018-01-22 12:40:03');
INSERT INTO `ssp_pt_app_login_log` VALUES ('104', '1053831109@qq.com', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-22 12:47:24');
INSERT INTO `ssp_pt_app_login_log` VALUES ('105', '1', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-22 13:07:32');
INSERT INTO `ssp_pt_app_login_log` VALUES ('106', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-22 13:15:01');
INSERT INTO `ssp_pt_app_login_log` VALUES ('107', '1053831109@qq.com', '106.2.197.29', '中国北京市', '1', '1', '2018-01-22 13:47:13');
INSERT INTO `ssp_pt_app_login_log` VALUES ('108', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-22 13:59:36');
INSERT INTO `ssp_pt_app_login_log` VALUES ('109', 'ssp', '192.168.2.253', '未分配或者内网IP0', '1', '2', '2018-01-22 14:02:24');
INSERT INTO `ssp_pt_app_login_log` VALUES ('110', '1053831109@qq.com', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-22 14:13:01');
INSERT INTO `ssp_pt_app_login_log` VALUES ('111', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-22 15:00:12');
INSERT INTO `ssp_pt_app_login_log` VALUES ('112', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-22 17:33:36');
INSERT INTO `ssp_pt_app_login_log` VALUES ('113', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-22 17:47:45');
INSERT INTO `ssp_pt_app_login_log` VALUES ('114', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-22 17:48:13');
INSERT INTO `ssp_pt_app_login_log` VALUES ('115', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-22 17:49:21');
INSERT INTO `ssp_pt_app_login_log` VALUES ('116', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-22 18:09:32');
INSERT INTO `ssp_pt_app_login_log` VALUES ('117', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-22 18:28:58');
INSERT INTO `ssp_pt_app_login_log` VALUES ('118', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-22 18:32:06');
INSERT INTO `ssp_pt_app_login_log` VALUES ('119', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-22 18:40:23');
INSERT INTO `ssp_pt_app_login_log` VALUES ('120', 'xuepengfei@adusing.com', '106.2.197.29', '中国北京市', '1', '1', '2018-01-23 14:32:21');
INSERT INTO `ssp_pt_app_login_log` VALUES ('121', '1053831109@qq.com', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-23 14:42:51');
INSERT INTO `ssp_pt_app_login_log` VALUES ('122', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-23 14:45:59');
INSERT INTO `ssp_pt_app_login_log` VALUES ('123', 'xuepengfei@adusing.com', '106.2.197.29', '中国北京市', '1', '1', '2018-01-23 15:34:40');
INSERT INTO `ssp_pt_app_login_log` VALUES ('124', 'xuepengfei@adusing.com', '106.2.197.29', '中国北京市', '1', '1', '2018-01-23 16:48:59');
INSERT INTO `ssp_pt_app_login_log` VALUES ('125', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-24 08:52:33');
INSERT INTO `ssp_pt_app_login_log` VALUES ('126', 'xuepengfei@adusing.com', '106.2.197.29', '中国北京市', '1', '1', '2018-01-24 09:52:49');
INSERT INTO `ssp_pt_app_login_log` VALUES ('127', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-24 09:55:01');
INSERT INTO `ssp_pt_app_login_log` VALUES ('128', '1053831109@qq.com', '106.2.197.29', '中国北京市', '1', '1', '2018-01-24 09:57:05');
INSERT INTO `ssp_pt_app_login_log` VALUES ('129', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-24 10:00:59');
INSERT INTO `ssp_pt_app_login_log` VALUES ('130', 'xuepengfei@adusing.com', '106.2.197.29', '中国北京市', '1', '2', '2018-01-24 10:01:05');
INSERT INTO `ssp_pt_app_login_log` VALUES ('131', '1053831109@qq.com', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '1', '2018-01-24 10:08:15');
INSERT INTO `ssp_pt_app_login_log` VALUES ('132', 'ssp', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '2', '2018-01-24 10:08:52');
INSERT INTO `ssp_pt_app_login_log` VALUES ('133', 'xuepengfei@adusing.com', '0:0:0:0:0:0:0:1', '未分配或者内网IP0', '1', '2', '2018-01-24 10:09:01');
INSERT INTO `ssp_pt_app_login_log` VALUES ('134', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-24 11:04:25');
INSERT INTO `ssp_pt_app_login_log` VALUES ('135', 'xuepengfei@adusing.com', '106.2.197.29', '中国北京市', '1', '1', '2018-01-24 11:07:04');
INSERT INTO `ssp_pt_app_login_log` VALUES ('136', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-24 16:18:13');
INSERT INTO `ssp_pt_app_login_log` VALUES ('137', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-24 17:43:50');
INSERT INTO `ssp_pt_app_login_log` VALUES ('138', '1', '106.2.197.28', '中国北京市', '1', '1', '2018-01-24 20:08:18');
INSERT INTO `ssp_pt_app_login_log` VALUES ('139', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-25 14:10:42');
INSERT INTO `ssp_pt_app_login_log` VALUES ('140', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-25 17:36:19');
INSERT INTO `ssp_pt_app_login_log` VALUES ('141', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-25 17:54:57');
INSERT INTO `ssp_pt_app_login_log` VALUES ('142', 'ssp', '106.2.197.29', '中国北京市', '1', '2', '2018-01-26 08:46:16');
INSERT INTO `ssp_pt_app_login_log` VALUES ('143', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-01-26 08:48:45');
INSERT INTO `ssp_pt_app_login_log` VALUES ('144', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-01-30 16:27:57');
INSERT INTO `ssp_pt_app_login_log` VALUES ('145', 'ssp', '127.0.0.1', '未分配或者内网IP0', '1', '2', '2018-01-30 21:31:20');
INSERT INTO `ssp_pt_app_login_log` VALUES ('146', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-01-30 21:36:59');
INSERT INTO `ssp_pt_app_login_log` VALUES ('147', '1', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-02-01 15:16:27');
INSERT INTO `ssp_pt_app_login_log` VALUES ('148', '1', '106.2.197.29', '中国北京市', '1', '1', '2018-02-01 15:18:46');
INSERT INTO `ssp_pt_app_login_log` VALUES ('149', '1053831109@qq.com', '106.2.197.29', '中国北京市', '1', '1', '2018-02-02 10:27:54');
INSERT INTO `ssp_pt_app_login_log` VALUES ('150', 'dlk3', '106.2.197.29', '中国北京市', '1', '1', '2018-02-05 11:31:41');
INSERT INTO `ssp_pt_app_login_log` VALUES ('151', 'dlk3', '127.0.0.1', '未分配或者内网IP0', '1', '1', '2018-02-05 11:45:01');
INSERT INTO `ssp_pt_app_login_log` VALUES ('152', '1053831109@qq.com', '106.2.197.29', '中国北京市', '1', '1', '2018-02-28 16:15:45');

-- ----------------------------
-- Table structure for ssp_pt_app_notice
-- ----------------------------
DROP TABLE IF EXISTS `ssp_pt_app_notice`;
CREATE TABLE `ssp_pt_app_notice` (
  `id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `content` varchar(255) DEFAULT '' COMMENT '通知内容',
  `author` varchar(255) DEFAULT '' COMMENT '作者',
  `notification_level` int(10) DEFAULT '1' COMMENT '通知级别（1-3）1：普通通知，2，紧急通知，3重要通知',
  `state` int(10) DEFAULT '1' COMMENT '发布状态(0:已经过时1:发布中，2,未发布)',
  `creation_time` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ssp_pt_app_notice
-- ----------------------------
INSERT INTO `ssp_pt_app_notice` VALUES ('0', '过年', '过年放7天', '吴贤川', '1', '1', '2018-01-18 10:12:13');
INSERT INTO `ssp_pt_app_notice` VALUES ('1', 'ssp启动', '内容', '', '1', '1', '2018-01-24 10:12:46');

-- ----------------------------
-- Table structure for ssp_pt_app_owner_bank_card_info
-- ----------------------------
DROP TABLE IF EXISTS `ssp_pt_app_owner_bank_card_info`;
CREATE TABLE `ssp_pt_app_owner_bank_card_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_owner_id` bigint(20) unsigned NOT NULL COMMENT '媒体主id',
  `type` int(1) NOT NULL COMMENT '1:个人，2：企业',
  `company_name` varchar(100) DEFAULT NULL COMMENT 'type为企业，写公司名称，开发票核对使用',
  `bank_name` varchar(100) NOT NULL COMMENT '银行名称',
  `bank_branch_name` varchar(100) DEFAULT NULL COMMENT '开户行名称',
  `bank_card_num` varchar(50) NOT NULL COMMENT '银行卡号',
  `bank_card_user_name` varchar(50) NOT NULL COMMENT '持卡人姓名',
  `bank_card_user_num` varchar(50) NOT NULL COMMENT '持卡人身份证号码',
  `company_business_license` varchar(255) DEFAULT NULL COMMENT '企业营业执照url',
  `id_card_front` varchar(255) DEFAULT NULL COMMENT '身份证正面url',
  `id_card_back` varchar(255) DEFAULT NULL COMMENT '身份证反面url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='媒体主银行卡信息,添加后不可修改';

-- ----------------------------
-- Records of ssp_pt_app_owner_bank_card_info
-- ----------------------------
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('50', '2017-12-14 17:19:46', '2017-12-14 17:19:46', '50', '1', '1', '1222', '1', '1', '1', '1', '', '', '');
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('51', '2018-01-02 13:56:13', '2018-01-02 13:56:13', '58', '1', '', '', '', '1', '2', '1', '', '', '');
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('54', '2018-01-09 09:25:08', '2018-01-09 09:25:08', '59', '2', '1234', '1234', '1324', '1234', '1234', '1234', '', '', '');
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('55', '2018-01-10 09:40:08', '2018-01-10 09:40:08', '50', '1', null, '账号', '2', '2', '2', '2', '', '', '');
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('56', '2018-01-10 11:02:58', '2018-01-10 11:02:58', '50', '2', '百度公司', '中国银行', '中国银行北京支行', '1234567891113156', '吴贤川', '1999999', '', '', '');
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('57', '2018-01-11 13:49:24', '2018-01-11 13:49:24', '50', '2', '1', '1', '1', '1', '1', '', '/ssp/imgs/2018/1_11/D33CDB649C000000C0000000001F8000.jpeg', null, null);
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('58', '2018-01-11 15:27:11', '2018-01-11 15:27:11', '65', '1', null, '北京银行', '北京大郊亭支行', '32329123234182346123', '周海明', '1212888219901923712', null, '/ssp/imgs/2018/1_11/D33E41E97C000000AF80000000000000.png', '/ssp/imgs/2018/1_11/D33E41FD00000000AF80000000000000.png');
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('59', '2018-01-11 15:51:23', '2018-01-11 15:51:23', '50', '2', null, '1', '1', '1', '1', '1', '/ssp/imgs/2018/1_11/D33E99E1CC000000AF80000000000000.png', null, null);
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('60', '2018-01-11 15:53:39', '2018-01-11 15:53:39', '50', '2', '', '1', '1', '1', '1', '1', '/ssp/imgs/2018/1_11/D33EA2F904000000AF80000000000000.jpg', null, null);
INSERT INTO `ssp_pt_app_owner_bank_card_info` VALUES ('61', '2018-01-23 15:11:50', '2018-01-23 15:11:50', '66', '2', '1234567', '北京银行', '北京银行朝阳支行', '432123456789', '薛鹏飞', '1234567890', '/ssp/imgs/2018/1_23/D4353AFD88000000D000000000178000.jpeg', null, null);

-- ----------------------------
-- Table structure for ssp_pt_app_owner_info
-- ----------------------------
DROP TABLE IF EXISTS `ssp_pt_app_owner_info`;
CREATE TABLE `ssp_pt_app_owner_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `mail` varchar(100) NOT NULL COMMENT '登录邮箱',
  `nick_name` varchar(10) DEFAULT NULL COMMENT '昵称',
  `phone_number` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `alipay_account` varchar(100) DEFAULT NULL COMMENT '支付宝账号',
  `status` smallint(6) NOT NULL COMMENT '0:未审核；1：审核通过；2：审核不通过；3：冻结',
  `telephone` varchar(50) DEFAULT NULL COMMENT '固定电话',
  `fax` varchar(50) DEFAULT NULL COMMENT '传真',
  `qq` bigint(20) unsigned DEFAULT NULL COMMENT 'qq号码',
  `reason` varchar(50) DEFAULT NULL COMMENT '审核不通过原因',
  `type` int(10) NOT NULL DEFAULT '0' COMMENT '(0:个人 ,1:公司)',
  `company_business_license` varchar(100) DEFAULT NULL COMMENT '企业营业执照url',
  `id_card_front` varchar(100) DEFAULT NULL COMMENT '身份证正面url',
  `id_card_back` varchar(100) DEFAULT NULL COMMENT '身份证反面url',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='媒体主信息';

-- ----------------------------
-- Records of ssp_pt_app_owner_info
-- ----------------------------
INSERT INTO `ssp_pt_app_owner_info` VALUES ('50', '2017-12-14 14:09:57', '2017-12-14 14:09:57', '1', '专业刷量', '13671205992', 'c4ca4238a0b923820dcc509a6f75849b', '吴贤川', '阿里', '111', '1', '999', '1', '123456', '', '0', '/ssp/imgs/2018/1_8/D2FF67E294000000C8000000001F8000.jpg', '/ssp/imgs/2018/1_8/D302798D14000000BB00000000000000.jpeg', '/ssp/imgs/2018/1_8/D2FF687B84000000C8000000001F8000.jpg');
INSERT INTO `ssp_pt_app_owner_info` VALUES ('58', '2018-01-02 10:56:57', '2018-01-02 10:56:57', '2', '测试小能手2', '13671205992', 'c4ca4238a0b923820dcc509a6f75849b', '哈哈', '嘟嘟', '没得', '1', '哈哈', '呵呵', '1', '公司业务不符合', '0', null, null, null);
INSERT INTO `ssp_pt_app_owner_info` VALUES ('59', '2018-01-03 10:03:32', '2018-01-03 10:03:32', 'dlk', '', '', 'b41b156963943db4606cf3f9ec769668', '', '', '', '1', '', '', null, '123', '0', '', '', '');
INSERT INTO `ssp_pt_app_owner_info` VALUES ('60', '2018-01-08 11:11:13', '2018-01-08 11:11:13', '123456', null, null, '827ccb0eea8a706c4c34a16891f84e7b', null, null, null, '1', null, null, null, null, '0', null, null, null);
INSERT INTO `ssp_pt_app_owner_info` VALUES ('62', '2018-01-08 14:38:25', '2018-01-08 14:38:25', 'gs', '', '', 'c4ca4238a0b923820dcc509a6f75849b', '', '', '', '1', '', '', null, '', '1', '/ssp/imgs/2018/1_8/D2FFC4EB5C000000B8000000001F8000.jpg', '', '');
INSERT INTO `ssp_pt_app_owner_info` VALUES ('63', '2018-01-10 09:20:29', '2018-01-10 09:20:29', 'dlk1', '', '', '2dd8b271ecf23cbcd735b8357b4f5432', '', '', '', '1', '', '', null, '', '0', '', '/ssp/imgs/2018/1_10/D324787FEC000000BB00000000000000.png', '/ssp/imgs/2018/1_10/D32478B530000000BB00000000000000.png');
INSERT INTO `ssp_pt_app_owner_info` VALUES ('64', '2018-01-10 09:25:51', '2018-01-10 09:25:51', 'dlk2', '', '', 'ea5aa9ece21143e5d3ca10cb969d9796', '', '', '', '1', '', '', null, '', '1', '/ssp/imgs/2018/1_10/D324804C2C000000BB00000000000000.png', '', '');
INSERT INTO `ssp_pt_app_owner_info` VALUES ('65', '2018-01-11 14:49:00', '2018-01-11 14:49:00', 'dlk3', '', '13811599502', '8d4646eb2d7067126eb08adb0672f7bb', '', '', '', '1', '', '', null, '', '1', '', '', '');
INSERT INTO `ssp_pt_app_owner_info` VALUES ('66', '2018-01-11 16:04:27', '2018-01-11 16:04:27', 'xuepengfei@adusing.com', '金龙鱼', '', 'e10adc3949ba59abbe56e057f20f883e', '1237', '薛氏集团', '123456789', '1', '12345678', '123456789', '23456789', '', '1', '', '', '');
INSERT INTO `ssp_pt_app_owner_info` VALUES ('76', '2018-01-17 12:58:02', '2018-01-17 12:58:02', '1053831109000@qq.com', null, '13671205992', 'c4ca4238a0b923820dcc509a6f75849b', null, null, null, '0', null, null, null, null, '0', null, null, null);
INSERT INTO `ssp_pt_app_owner_info` VALUES ('77', '2018-01-22 12:39:18', '2018-01-22 12:39:18', '1053831109@qq.com', '', '13671205992', '3d24b838770ee90773804e8599e549ff', '', '', '', '1', '', '', null, '', '0', '', '', '');
INSERT INTO `ssp_pt_app_owner_info` VALUES ('78', '2018-01-24 16:27:01', '2018-01-24 16:27:01', '123', null, '18686155204', '202cb962ac59075b964b07152d234b70', null, null, null, '0', null, null, null, null, '0', null, null, null);
INSERT INTO `ssp_pt_app_owner_info` VALUES ('79', '2018-03-06 22:38:59', '2018-03-06 22:38:59', '185399993@qq.com', null, '13376079411', 'e0f5d45df2ee40c206d133768802c1f8', null, null, null, '0', null, null, null, null, '0', null, null, null);

-- ----------------------------
-- Table structure for ssp_pt_pk_app_owner_id_media_id
-- ----------------------------
DROP TABLE IF EXISTS `ssp_pt_pk_app_owner_id_media_id`;
CREATE TABLE `ssp_pt_pk_app_owner_id_media_id` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_owner_id` bigint(20) unsigned NOT NULL COMMENT '媒体主ID',
  `media_id` bigint(20) unsigned NOT NULL COMMENT '媒体id,dsp_media_info.id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='媒体主和媒体关联表';

-- ----------------------------
-- Records of ssp_pt_pk_app_owner_id_media_id
-- ----------------------------
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('50', '2017-12-18 11:52:10', '2017-12-18 11:52:10', '50', '13095');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('52', '2017-12-20 09:40:10', '2017-12-20 09:40:10', '50', '13097');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('53', '2017-12-20 09:40:14', '2017-12-20 09:40:14', '50', '13098');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('54', '2017-12-20 09:40:20', '2017-12-20 09:40:20', '50', '13099');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('55', '2017-12-20 09:40:23', '2017-12-20 09:40:23', '50', '13100');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('56', '2017-12-20 09:40:25', '2017-12-20 09:40:25', '50', '13101');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('57', '2018-01-03 10:13:18', '2018-01-03 10:13:18', '58', '13104');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('58', '2018-01-03 10:14:47', '2018-01-03 10:14:47', '59', '13105');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('59', '2018-01-03 10:16:14', '2018-01-03 10:16:14', '58', '13106');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('60', '2018-01-03 13:25:10', '2018-01-03 13:25:10', '59', '13108');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('61', '2018-01-08 16:12:35', '2018-01-08 16:12:35', '50', '13109');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('62', '2018-01-08 16:12:44', '2018-01-08 16:12:44', '50', '13110');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('63', '2018-01-11 14:57:34', '2018-01-11 14:57:34', '65', '13111');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('69', '2018-01-11 16:36:11', '2018-01-11 16:36:11', '50', '13117');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('70', '2018-01-12 15:46:43', '2018-01-12 15:46:43', '65', '13118');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('71', '2018-01-22 12:41:28', '2018-01-22 12:41:28', '77', '13121');
INSERT INTO `ssp_pt_pk_app_owner_id_media_id` VALUES ('72', '2018-01-23 14:35:52', '2018-01-23 14:35:52', '66', '13122');
