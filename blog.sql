CREATE TABLE `user` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `role` int(2) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  KEY `password` (`password`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=366 DEFAULT CHARSET=utf8mb4;



CREATE TABLE `menu` (
  `id` varchar(37) NOT NULL COMMENT 'UUID',
  `sn` int(11) NOT NULL COMMENT '菜单列表编号',
  `name` varchar(64) NOT NULL COMMENT '菜单列表名称',
  `subject` varchar(64) DEFAULT NULL COMMENT '菜单功能简介',
  `type` varchar(32) DEFAULT NULL COMMENT '功能类型',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sn` (`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `user_menu` (
  `id` varchar(37) NOT NULL COMMENT 'UUID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `menu_sn` int(11) NOT NULL COMMENT '菜单列表编号',
  `name` varchar(64) NOT NULL COMMENT '菜单列表名称',
  `subject` varchar(64) DEFAULT NULL COMMENT '菜单功能简介',
  `type` varchar(32) DEFAULT NULL COMMENT '功能类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


