--要求使用SQL统计出每个用户的累积访问次数，如下表所示：

-- u01     2017/1/21       5
-- u02     2017/1/23       6
-- u03     2017/1/22       8
-- u04     2017/1/20       3
-- u01     2017/1/23       6
-- u01     2017/2/21       8
-- u02     2017/1/23       6
-- u01     2017/2/22       4

-- u01	2017-01	11	11
-- u01	2017-02	12	23
-- u02	2017-01	12	12
-- u03	2017-01	8	8
-- u04	2017-01	3	3

create table action
(
    userId     string,
    visitDate  string,
    visitCount int
)
    row format delimited fields terminated by "\t";

select userId, visitCount, substr(replace(visitDate, '/', '-'), 1, 7) as vdate
from action
;

select userId,
       vdate,
       sum(visitCount) as xiaoji
from (
         select userId, visitCount, substr(replace(visitDate, '/', '-'), 1, 7) as vdate
         from action
     )
group by userId, vdate
;

select userId,
       vdate,
       xiaoji,
       sum(xiaoji) over (partition by userId order by vdate)
from (
         select userId,
                vdate,
                sum(visitCount) as xiaoji
         from (
                  select userId, visitCount, substr(replace(visitDate, '/', '-'), 1, 7) as vdate
                  from action
              )
         group by userId, vdate
     ) as aa
;

-- 方法二
select userId,
       mn,
       mn_count,
       sum(mn_count) over (partition by userId order by mn)
from (select userId,
             mn,
             sum(visitCount) mn_count
      from (select userId,
                   date_format(regexp_replace(visitDate, '/', '-'), 'yyyy-MM') mn, --【注意】
                   visitCount
            from action) t1
      group by userId, mn) t2;
