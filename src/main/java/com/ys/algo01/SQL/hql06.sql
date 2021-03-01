-- 请用sql写出所有用户中在今年10月份第一次购买商品的金额，表ordertable字段
-- （购买用户：userid，金额：money，购买时间：paymenttime(格式：2017-10-01)，订单id：orderid）
-- 1）建表
create table ordertable
(
    userid      string,
    money       int,
    paymenttime string,
    orderid     string
)
    row format delimited fields terminated by '\t';

select userid, money, rank() over (partition by userid order by paymenttime) as rt
from ordertable
where date_format(paymenttime, "yyyy-MM") = '2017-10';

select userid, money
from (
         select userid, money, rank() over (partition by userid order by paymenttime) as rt
         from ordertable
         where date_format(paymenttime, "yyyy-MM") = '2017-10'
     )
where rt = 1;

--参考====================================
with t1 as (
    select userid,
           min(paymenttime) paymenttime --[重点]
    from ordertable
    where date_format(paymenttime, 'yyyy-MM') = '2017-10'
    group by userid
)

select t1.userid,
       t1.paymenttime,
       od.money
from t1
         join
     ordertable od
     on
             t1.userid = od.userid
             and
             t1.paymenttime = od.paymenttime;

------------------------------
select t1.userid,
       t1.paymenttime,
       od.money
from (select userid,
             min(paymenttime) paymenttime
      from ordertable
      where date_format(paymenttime, 'yyyy-MM') = '2017-10'
      group by userid) t1
         join
     ordertable od
     on
             t1.userid = od.userid
             and
             t1.paymenttime = od.paymenttime;