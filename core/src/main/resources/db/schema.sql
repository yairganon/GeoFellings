CREATE DATABASE IF NOT EXISTS `geoFeelings`;

CREATE TABLE IF NOT EXISTS `geoFeelings.questions` (
  `questionId` varchar(50) NOT NULL,
  `data` mediumblob,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`questionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;