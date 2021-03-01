-- 有一个账号表如下，请写出SQL语句，查询各自区组的money排名前十的账号（分组取前10）
-- 1）建表（MySQL）
CREATE TABLE `account`
(
    `dist_id` int(11)      DEFAULT NULL COMMENT '区组id',
    `account` varchar(100) DEFAULT NULL COMMENT '账号',
    `gold`    int(11)      DEFAULT 0 COMMENT '金币'
);

-- 2）最终SQL
select *
from account as a
where (select count(distinct (a1.gold))
       from account as a1
       where a1.dist_id = a.dist_id
         and a1.gold > a.gold) < 3;

--
select dist_id,
       gold,
       account
from (
         select dist_id,
                gold,
                account,
                rank() over (partition by dist_id order by gold desc) as rt
         from account
     )
where rt <= 10;
