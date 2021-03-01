-- 已知一个表STG.ORDER，有如下字段:Date，Order_id，User_id，amount。
-- 请给出sql进行统计:数据样例:2017-01-01,10029028,1000003251,33.57

-- 建表
create table order_tab
(
    dt       string,
    order_id string,
    user_id  string,
    amount   decimal(10, 2)
) row format delimited fields terminated by '\t';

-- 1）给出 2017年每个月的订单数、用户数、总成交金额
select date_format(dt, 'yyyy-MM'),
       count(order_id),
       count(distinct user_id),
       sum(amount)
from order_tab
where date_format(dt, 'yyyy') = '2017'
group by date_format(dt, 'yyyy-MM');

-- 2）给出2017年11月的新客数(指在11月才有第一笔订单)
--【重点】
select count(user_id)
from order_tab
group by user_id
having date_format(min(dt), 'yyyy-MM') = '2017-11';

--【注意】where后里面不能嵌套min
-- select count(user_id)
-- from order_tab
-- group by user_id
-- where date_format(min(dt), 'yyyy-MM') = '2017-11';
-- where 会报错
