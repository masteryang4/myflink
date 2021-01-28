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
--[常用方法]开启参数，两阶段MR，解决group by数据倾斜
set hive.groupby.skewindata = true;
/*
当 sql 语句使用 groupby 时数据出现倾斜时，如果该变量设置为 true，那么 Hive 会自动进行 负载均衡。
策略就是把 MR 任务拆分成两个：第一个先做预汇总，第二个再做最终汇总

在 MR 的第一个阶段中，Map 的输出结果集合会缓存到 maptaks 中，每个 Reduce 做部分聚合操作，并输出结果，
这样处理的结果是相同 Group By Key 有可能被分发到不同的 Reduce 中， 从而达到负载均衡的目的；
第二个阶段 再根据预处理的数据结果按照 Group By Key 分布到 Reduce 中
（这个过程可以保证相同的 Group By Key 被分布到同一个 Reduce 中），最后完成 最终的聚合操作。
*/

--uv-----------
select city, count(distinct userid)
from t_city
group by city;

--优化
select city, count(1)
from (
         select userid, city
         from t_city
         group by userid, city --去重
     ) as a
group by city
;