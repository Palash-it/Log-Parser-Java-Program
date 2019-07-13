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


