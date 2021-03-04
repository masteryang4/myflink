
drop table if exists ads_continuity_wk_count;
create external table ads_continuity_wk_count
(
    `dt`               string COMMENT '统计日期,一般用结束周周日日期,如果每天计算一次,可用当天日期',
    `wk_dt`            string COMMENT '持续时间',
    `continuity_count` bigint COMMENT '活跃次数'
)
    row format delimited fields terminated by '\t';


insert into table ads_continuity_wk_count
select '2020-03-15',
       concat(date_add(next_day('2020-03-15', 'MO'), -7 * 3), '_', date_add(next_day('2020-03-15', 'MO'), -1)),
       count(*)
from (
         select mid_id
         from (
                  select mid_id
                  from dws_uv_detail_daycount
                  where dt >= date_add(next_day('2020-03-10', 'monday'), -7)
                    and dt <= date_add(next_day('2020-03-10', 'monday'), -1)
                  group by mid_id

                  union all

                  select mid_id
                  from dws_uv_detail_daycount
                  where dt >= date_add(next_day('2020-03-10', 'monday'), -7 * 2)
                    and dt <= date_add(next_day('2020-03-10', 'monday'), -7 - 1)
                  group by mid_id

                  union all

                  select mid_id
                  from dws_uv_detail_daycount
                  where dt >= date_add(next_day('2020-03-10', 'monday'), -7 * 3)
                    and dt <= date_add(next_day('2020-03-10', 'monday'), -7 * 2 - 1)
                  group by mid_id
              ) t1
         group by mid_id
         having count(*) = 3
     ) t2