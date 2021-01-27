/**
  * 已知 一个 user_id , city 的一个实时日志，计算每个城市的pv和 uv？
  * 如果该日志数据量特别大，而且某些城市数据特别多，需要做哪些处理？
  * （考察点：spark/hive sql，计算逻辑及语法；热点处理；数据倾斜处理；）
  */

--city.log
--table: t_city

--pv----------
select city, count(1)
from t_city
group by city;

--数据倾斜版本
select split(10_city, "_")[0] as city, sum(c)
from (
         select concat(city, '_', floor(rand() * 10) + 1) as 10_city,
                count(1)                                  as c
         from t_city
         group by city
     )
group by 10_city
;


--uv-----------
select city, count(distinct userid)
from t_city
group by city;

--优化
select city, count(1)
from (
         select userid, city, count(1) as c
         from t_city
         group by userid, city --去重
     ) as a
group by city
;