-- 有日志如下，请写出代码求得所有用户和活跃用户的总数及平均年龄。（活跃用户指连续两天都有访问记录的用户）日期 用户 年龄
-- 数据集
-- 2019-02-11,test_1,23
-- 2019-02-11,test_2,19
-- 2019-02-11,test_3,39
-- 2019-02-11,test_1,23
-- 2019-02-11,test_3,39
-- 2019-02-11,test_1,23
-- 2019-02-12,test_2,19
-- 2019-02-13,test_1,23
-- 2019-02-15,test_2,19
-- 2019-02-16,test_2,19

-- 1）建表
create table user_age
(
    dt      string,
    user_id string,
    age     int
) row format delimited fields terminated by ',';

--用户
select count(user_id), avg(age)
from user_age
group by user_id, age;

--活跃用户（连续两天都有访问记录的用户）
select user_id, age, dt
from user_age
group by user_id, age, dt;

select user_id, age, dt, rank() over (partition by user_id order by dt) as rt
from (
         select user_id, age, dt
         from user_age
         group by user_id, age, dt
     );

select user_id, age, date_sub(dt, rt) as flag
from (
         select user_id, age, dt, rank() over (partition by user_id order by dt) as rt
         from (
                  select user_id, age, dt
                  from user_age
                  group by user_id, age, dt
              )
     );

select user_id,
       min(age)  --配合group by
from (
         select user_id, age, flag, count(flag) as c
         from (
                  select user_id, age, date_sub(dt, rt) as flag
                  from (
                           select user_id, age, dt, rank() over (partition by user_id order by dt) as rt
                           from (
                                    select user_id, age, dt
                                    from user_age
                                    group by user_id, age, dt
                                )
                       )
              )
         group by user_id, age, flag
         having c >= 2
     )
group by user_id; --去重
;


-- 答案

--注：活跃用户要两次去重，第一次一开始按用户和日期；第二次按用户和flag（可能有多次连续2次登录，只留一个就行）
select sum(user_total_count),
       sum(user_total_avg_age),
       sum(twice_count),
       sum(twice_count_avg_age)
from (select 0                                           user_total_count,
             0                                           user_total_avg_age,
             count(*)                                    twice_count,
             cast(sum(age) / count(*) as decimal(10, 2)) twice_count_avg_age
      from (
               select user_id,
                      min(age) age
               from (select user_id,
                            min(age) age
                     from (
                              select user_id,
                                     age,
                                     date_sub(dt, rk) flag
                              from (
                                       select dt,
                                              user_id,
                                              min(age)                                       age,
                                              rank() over (partition by user_id order by dt) rk
                                       from user_age
                                       group by dt, user_id
                                   ) t1
                          ) t2
                     group by user_id, flag
                     having count(*) >= 2) t3
               group by user_id
           ) t4

      union all

      select count(*) user_total_count,
             cast((sum(age) / count(*)) as decimal(10, 1)),
             0        twice_count,
             0        twice_count_avg_age
      from (
               select user_id,
                      min(age) age
               from user_age
               group by user_id
           ) t5) t6;

