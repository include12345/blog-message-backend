
create schema if not exists `blog` default character set utf8mb4 collate utf8mb4_bin;


CREATE TABLE `blog`.`menu` (
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


INSERT INTO `menu` (`id`, `sn`, `name`, `subject`, `type`, `deleted`, `version`)
VALUES
  ('19b054c7-b027-428c-8f4f-81886ec1584c',2,'updateBlog','更新文章','管理面板',0,1),
  ('44265f1f-c892-495e-8606-1fd2ebf461c5',1,'articleList','文章管理','管理面板',0,1),
  ('5eeade99-b0d8-4252-bf1e-a8ce6dc1f8ae',3,'createBlog','新建文章','管理面板',0,1),
  ('c8b7960d-76c1-43bd-953d-7e00a73da1db',4,'deleteBlog','删除文章','管理面板',0,1);


CREATE TABLE `blog`.`user` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `user` (`id`, `username`, `password`, `role`, `name`, `deleted`, `version`)
VALUES
  (366,'18301900685','63b891d3324fef21bfcc8914a723acef',0,'lihebin',0,1),
  (367,'test','aa9b7d97dac95962e4c7345d7ac0e25e',0,'test',0,1);





CREATE TABLE `blog`.`user_menu` (
  `id` varchar(37) NOT NULL COMMENT 'UUID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `menu_sn` int(11) NOT NULL COMMENT '菜单列表编号',
  `name` varchar(64) NOT NULL COMMENT '菜单列表名称',
  `subject` varchar(64) DEFAULT NULL COMMENT '菜单功能简介',
  `type` varchar(32) DEFAULT NULL COMMENT '功能类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `user_menu` (`id`, `username`, `menu_sn`, `name`, `subject`, `type`)
VALUES
  ('0729f999-c80a-4031-823a-0f50ef3d4a99','18301900685',4,'deleteBlog','删除文章','管理面板'),
  ('17262561-3658-4ca7-acb1-9c95b18e5b19','18301900685',3,'createBlog','新建文章','管理面板'),
  ('241114ef-a28f-4066-bc1e-d47fecba4bd3','18301900685',1,'articleList','文章管理','管理面板'),
  ('66cd7201-89a4-4d39-8286-2081aa115e7e','18301900685',2,'updateBlog','更新文章','管理面板');




CREATE TABLE `blog`.`article` (
  `id` varchar(37) COLLATE utf8mb4_bin NOT NULL COMMENT 'UUID',
  `title` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `classify` int(3) NOT NULL COMMENT '分类',
  `content` text COLLATE utf8mb4_bin,
  `create_author_id` varchar(37) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '创建者编号',
  `update_author_id` varchar(37) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最新一次的修改者编号',
  `ctime` bigint(20) DEFAULT NULL,
  `mtime` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `version` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `ctime` (`ctime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='博客详情';

CREATE TABLE `blog`.`classify` (
  `sn` bigint(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='博客标签';
