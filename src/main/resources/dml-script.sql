/* DB Name : log_parser
* Create bellow tables using this sql
* Find IP's threshold` count by time 
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


/*==================Question Queries===========
* 
(1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.
*/

SELECT COUNT(*) AS threshold, ip FROM log_records WHERE date_time BETWEEN '2017-01-01 13:00:00' AND '2017-01-01 14:00:00' GROUP BY ip HAVING threshold > 100;

/*
* 
*(2) Write MySQL query to find requests made by a given IP.
* NOT clear if you are asking to see all request made by a ip then bellow sql 
*/
SELECT * FROM log_records WHERE ip='192.168.228.188';

/*
* 
*(2) Write MySQL query to find requests made by a given IP.
* if you are asking to see total threshold made by a ip then bellow sql 
* If you want to see threshold count by all ip
*/
SELECT COUNT(id),ip FROM log_records WHERE ip='192.168.228.188';
SELECT COUNT(id),ip FROM log_records  GROUP BY ip;


