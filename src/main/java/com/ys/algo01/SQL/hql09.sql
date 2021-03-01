-- 1）有三张表分别为会员表（member）销售表（sale）退货表（regoods）
-- （1）会员表有字段memberid（会员id，主键）credits（积分）；
-- （2）销售表有字段memberid（会员id，外键）购买金额（MNAccount）；
-- （3）退货表中有字段memberid（会员id，外键）退货金额（RMNAccount）。

-- 2）业务说明
-- （1）销售表中的销售记录可以是会员购买，也可以是非会员购买。（即销售表中的memberid可以为空）；
-- （2）销售表中的一个会员可以有多条购买记录；
-- （3）退货表中的退货记录可以是会员，也可是非会员；
-- （4）一个会员可以有一条或多条退货记录。
-- 查询需求：分组查出销售表中所有会员购买金额，同时分组查出退货表中所有会员的退货金额，
-- 把会员id相同的购买金额-退款金额得到的结果更新到表会员表中对应会员的积分字段（credits）

-- 数据集
-- sale
-- 1001    50.3
-- 1002    56.5
-- 1003    235
-- 1001    23.6
-- 1005    56.2
--         25.6
--         33.5
--
-- regoods
-- 1001    20.1
-- 1002    23.6
-- 1001    10.1
--         23.5
--         10.2
-- 1005    0.8

-- 1）建表
create table member
(
    memberid string,
    credits  double
) row format delimited fields terminated by '\t';

create table sale
(
    memberid  string,
    MNAccount double
) row format delimited fields terminated by '\t';

create table regoods
(
    memberid   string,
    RMNAccount double
) row format delimited fields terminated by '\t';

--
insert into table member
select t1.memberid,
       MNAccount - RMNAccount
from (select memberid,
             sum(MNAccount) MNAccount
      from sale
      where memberid != ''
      group by memberid
     ) t1
         join
     (select memberid,
             sum(RMNAccount) RMNAccount
      from regoods
      where memberid != ''
      group by memberid
     ) t2
     on
         t1.memberid = t2.memberid;