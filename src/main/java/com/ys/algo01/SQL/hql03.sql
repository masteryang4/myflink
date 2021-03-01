-- 有50W个京东店铺，每个顾客访客访问任何一个店铺的任何一个商品时都会产生一条访问日志，
-- 访问日志存储的表名为Visit，访客的用户id为user_id，被访问的店铺名称为shop，请统计：

-- 1）每个店铺的UV（访客数）
-- 2）每个店铺访问次数top3的访客信息。输出店铺名称、访客id、访问次数

-- u1	a
-- u2	b
-- u1	b
-- u1	a
-- u3	c
-- u4	b
-- u1	a
-- u2	c
-- u5	b
-- u4	b
-- u6	c
-- u2	c
-- u1	b
-- u2	a
-- u2	a
-- u3	a
-- u5	a
-- u5	a
-- u5	a

create table visit
(
    user_id string,
    shop    string
) row format delimited fields terminated by '\t';

--1
select count(distinct user_id), shop
from visit
group by shop;

select count(1)
from (
         select shop
         from visit
         group by user_id, shop
     )
group by shop
;

--2  每个店铺访问次数top3的访客信息。输出店铺名称、访客id、访问次数

select shop,
       user_id,
       ct
from (select shop,
             user_id,
             ct,
             rank() over (partition by shop order by ct) rk
      from (select shop,
                   user_id,
                   count(*) ct
            from visit
            group by shop,
                     user_id) t1
     ) t2
where rk <= 3;