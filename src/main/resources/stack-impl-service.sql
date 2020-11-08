create database if not exists db_stack;
use db_stack;
CREATE TABLE if not exists `stack` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
