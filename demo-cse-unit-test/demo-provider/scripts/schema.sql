-- 创建数据库
create database `demo` default character set utf8 collate utf8_general_ci;

use demo;

-- 建表
CREATE TABLE `demo_person` (
  `pid` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 插入数据
INSERT INTO `demo_person` (`pid`, `name`, `password`, `gender`, `age`)
VALUES ('0','Java','123456','male',23);
INSERT INTO `demo_person` (`pid`, `name`, `password`, `gender`, `age`)
VALUES ('1','Python','123456','male',27);
INSERT INTO `demo_person` (`pid`, `name`, `password`, `gender`, `age`)
VALUES ('3','Go','123456','male',10);
INSERT INTO `demo_person` (`pid`, `name`, `password`, `gender`, `age`)
VALUES ('4','Ruby','123456','female',24);