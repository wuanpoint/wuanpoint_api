/*
Navicat MySQL Data Transfer

Source Server         : mysql @ localhost
Source Server Version : 50560
Source Host           : localhost:3306
Source Database       : wuanpoint

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2019-02-18 18:34:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for actors
-- ----------------------------
DROP TABLE IF EXISTS `actors`;
CREATE TABLE `actors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '演员id',
  `name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '演员名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='演员表';

-- ----------------------------
-- Records of actors
-- ----------------------------

-- ----------------------------
-- Table structure for auth_detail
-- ----------------------------
DROP TABLE IF EXISTS `auth_detail`;
CREATE TABLE `auth_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `identity` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '权限类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限对应关系表';

-- ----------------------------
-- Records of auth_detail
-- ----------------------------
INSERT INTO `auth_detail` VALUES ('1', '管理员');
INSERT INTO `auth_detail` VALUES ('2', '最高管理员');

-- ----------------------------
-- Table structure for directors
-- ----------------------------
DROP TABLE IF EXISTS `directors`;
CREATE TABLE `directors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '导演id',
  `name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '导演名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='导演表';

-- ----------------------------
-- Records of directors
-- ----------------------------

-- ----------------------------
-- Table structure for movies_actors
-- ----------------------------
DROP TABLE IF EXISTS `movies_actors`;
CREATE TABLE `movies_actors` (
  `movie_id` int(10) unsigned NOT NULL COMMENT '影片id',
  `actor_id` int(10) unsigned NOT NULL COMMENT '演员id',
  PRIMARY KEY (`movie_id`,`actor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片演员表';

-- ----------------------------
-- Records of movies_actors
-- ----------------------------

-- ----------------------------
-- Table structure for movies_base
-- ----------------------------
DROP TABLE IF EXISTS `movies_base`;
CREATE TABLE `movies_base` (
  `id` int(10) unsigned NOT NULL COMMENT '影片id',
  `type` tinyint(3) unsigned NOT NULL COMMENT '影片分类(影片首页分类)',
  `title` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '影片标题',
  `digest` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '摘要',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '资源添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片基础信息表';

-- ----------------------------
-- Records of movies_base
-- ----------------------------
INSERT INTO `movies_base` VALUES ('1', '2', '1', '1', '2019-02-12 17:39:18');

-- ----------------------------
-- Table structure for movies_details
-- ----------------------------
DROP TABLE IF EXISTS `movies_details`;
CREATE TABLE `movies_details` (
  `id` int(10) unsigned NOT NULL COMMENT '影片id',
  `title` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '影片标题',
  `original_title` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '影片原名',
  `countries` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '制片国家/地区',
  `year` char(4) COLLATE utf8_bin NOT NULL COMMENT '年代',
  `aka` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '影片别名',
  `url_douban` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '豆瓣链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片详情表';

-- ----------------------------
-- Records of movies_details
-- ----------------------------
INSERT INTO `movies_details` VALUES ('1', '1', '阿萨德', '中国', '12', 'asd', 'www.doubi.com');

-- ----------------------------
-- Table structure for movies_directors
-- ----------------------------
DROP TABLE IF EXISTS `movies_directors`;
CREATE TABLE `movies_directors` (
  `movie_id` int(10) unsigned NOT NULL COMMENT '影片id',
  `director_id` int(10) unsigned NOT NULL COMMENT '导演id',
  PRIMARY KEY (`movie_id`,`director_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片导演表';

-- ----------------------------
-- Records of movies_directors
-- ----------------------------

-- ----------------------------
-- Table structure for movies_genres
-- ----------------------------
DROP TABLE IF EXISTS `movies_genres`;
CREATE TABLE `movies_genres` (
  `movies_id` int(10) unsigned NOT NULL COMMENT '影片id',
  `genres_id` tinyint(3) unsigned NOT NULL COMMENT '影片类型id',
  PRIMARY KEY (`movies_id`,`genres_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片类型表(影片详情页面显示)';

-- ----------------------------
-- Records of movies_genres
-- ----------------------------

-- ----------------------------
-- Table structure for movies_genres_details
-- ----------------------------
DROP TABLE IF EXISTS `movies_genres_details`;
CREATE TABLE `movies_genres_details` (
  `genres_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `genres_name` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '类型名',
  PRIMARY KEY (`genres_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片类型表(影片详情页面显示)';

-- ----------------------------
-- Records of movies_genres_details
-- ----------------------------

-- ----------------------------
-- Table structure for movies_poster
-- ----------------------------
DROP TABLE IF EXISTS `movies_poster`;
CREATE TABLE `movies_poster` (
  `id` int(10) unsigned NOT NULL COMMENT '影片id',
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '海报链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片海报表';

-- ----------------------------
-- Records of movies_poster
-- ----------------------------
INSERT INTO `movies_poster` VALUES ('1', 'www.popopo.com');

-- ----------------------------
-- Table structure for movies_rating
-- ----------------------------
DROP TABLE IF EXISTS `movies_rating`;
CREATE TABLE `movies_rating` (
  `id` int(10) unsigned NOT NULL COMMENT '影片id',
  `rating` float(2,1) unsigned NOT NULL COMMENT '影片评分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片评分表';

-- ----------------------------
-- Records of movies_rating
-- ----------------------------

-- ----------------------------
-- Table structure for movies_summary
-- ----------------------------
DROP TABLE IF EXISTS `movies_summary`;
CREATE TABLE `movies_summary` (
  `id` int(10) unsigned NOT NULL COMMENT '影片id',
  `summary` varchar(500) COLLATE utf8_bin NOT NULL COMMENT '影片简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='剧情简介表';

-- ----------------------------
-- Records of movies_summary
-- ----------------------------

-- ----------------------------
-- Table structure for movies_type
-- ----------------------------
DROP TABLE IF EXISTS `movies_type`;
CREATE TABLE `movies_type` (
  `movies_id` int(10) unsigned NOT NULL COMMENT '影片id',
  `type_id` tinyint(3) unsigned NOT NULL COMMENT '分类id',
  PRIMARY KEY (`movies_id`,`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='影片分类表';

-- ----------------------------
-- Records of movies_type
-- ----------------------------

-- ----------------------------
-- Table structure for movies_type_details
-- ----------------------------
DROP TABLE IF EXISTS `movies_type_details`;
CREATE TABLE `movies_type_details` (
  `type_id` tinyint(3) unsigned NOT NULL COMMENT '分类id',
  `type_name` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '分类名',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分类字典表';

-- ----------------------------
-- Records of movies_type_details
-- ----------------------------
INSERT INTO `movies_type_details` VALUES ('1', '电影');
INSERT INTO `movies_type_details` VALUES ('2', '国产/港台剧');
INSERT INTO `movies_type_details` VALUES ('3', '欧美剧');
INSERT INTO `movies_type_details` VALUES ('4', '日韩剧');
INSERT INTO `movies_type_details` VALUES ('5', '综艺');

-- ----------------------------
-- Table structure for points
-- ----------------------------
DROP TABLE IF EXISTS `points`;
CREATE TABLE `points` (
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `points` int(10) unsigned NOT NULL COMMENT '午安影视积分',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户积分表';

-- ----------------------------
-- Records of points
-- ----------------------------

-- ----------------------------
-- Table structure for points_order
-- ----------------------------
DROP TABLE IF EXISTS `points_order`;
CREATE TABLE `points_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `points_alert` int(11) NOT NULL COMMENT '午安影视积分',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of points_order
-- ----------------------------

-- ----------------------------
-- Table structure for resources_type_details
-- ----------------------------
DROP TABLE IF EXISTS `resources_type_details`;
CREATE TABLE `resources_type_details` (
  `type_id` tinyint(3) unsigned NOT NULL COMMENT '资源种类id',
  `type_name` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '资源种类名',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源种类字典表';

-- ----------------------------
-- Records of resources_type_details
-- ----------------------------
INSERT INTO `resources_type_details` VALUES ('1', '在线');
INSERT INTO `resources_type_details` VALUES ('2', '磁力');
INSERT INTO `resources_type_details` VALUES ('3', '电驴');
INSERT INTO `resources_type_details` VALUES ('4', '网盘');

-- ----------------------------
-- Table structure for unreviewed_resources
-- ----------------------------
DROP TABLE IF EXISTS `unreviewed_resources`;
CREATE TABLE `unreviewed_resources` (
  `resource_id` int(10) unsigned NOT NULL COMMENT '资源id',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='待审核资源表';

-- ----------------------------
-- Records of unreviewed_resources
-- ----------------------------

-- ----------------------------
-- Table structure for users_auth
-- ----------------------------
DROP TABLE IF EXISTS `users_auth`;
CREATE TABLE `users_auth` (
  `id` int(10) unsigned NOT NULL COMMENT '用户id',
  `auth` int(10) unsigned NOT NULL COMMENT '用户权限',
  PRIMARY KEY (`id`,`auth`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户权限表';

-- ----------------------------
-- Records of users_auth
-- ----------------------------
