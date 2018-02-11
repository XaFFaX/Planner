create database if not exists planner;
use planner;

CREATE TABLE IF NOT EXISTS `planner` (
  `plan_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL DEFAULT '',
  `room` smallint unsigned NOT NULL,
  `fromDate` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `toDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;