/* DB Name : log_parser
* Create bellow tables using this sql 
*/

CREATE TABLE `log_records` (
  `id` bigint(20) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ip` varchar(20) DEFAULT NULL,
  `request` varchar(96) DEFAULT NULL,
  `status` int(4) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8




CREATE TABLE `blocked_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) DEFAULT NULL,
  `start_date_time` timestamp NULL DEFAULT NULL,
  `end_date_time` timestamp NULL DEFAULT NULL,
  `threshold` int(4) DEFAULT NULL,
  `blocked_reason` varchar(255) DEFAULT NULL,
  `creation_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8




CREATE TABLE `parser_usage` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `log_file_path` varchar(232) DEFAULT NULL,
  `status` enum('read_success','read_failed') DEFAULT NULL,
  `creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `time_taken` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `sequence` (
  `next_val` bigint(20) NOT NULL,
  PRIMARY KEY (`next_val`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

INSERT INTO sequence (next_val) VALUES(1);

