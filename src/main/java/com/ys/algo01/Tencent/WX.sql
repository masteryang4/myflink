-- hive sql

-- Q1.找出最热门的 10 个公众号

-- 思路：统计每个公众号的活跃用户数量

select userid, cpid
from t_user_log tuser
         join t_wx_doc tdoc
              on tuser.docid = tdoc.docid as t1;

select userid, cpid
from t1
group by userid, cpid as t2;

select cpid, count(*) c1
from t2
group by cpid
order by c1 desc
limit 0,10;



-- Q2.给定一个100万大小用户包（userid 字符串长度 16），计算他们各自最可能喜欢的 5 个公众号。

-- 思路：统计每个用户对每个公众号的访问频次

select userid, cpid
from t_user_log tuser
         join t_wx_doc tdoc
              on tuser.docid = tdoc.docid as t1;

select userid, cpid, count(*) c1
from t1
group by userid, cpid as t2;

select userid, cpid, c1, r
from t2 rank(
)
over
(
partition
by
userid
order by c1 desc
)
r
t3;

select userid, cpid
from t3
where r <= 5
  and userid in (
    select userid
    from userid_package
);

-- 这貌似并不是面试官想看到的
-- [数据倾斜问题]不太方便调优


