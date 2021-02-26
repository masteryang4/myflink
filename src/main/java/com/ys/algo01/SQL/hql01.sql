-- 找出所有科目成绩都大于某一学科平均成绩的学生
-- 1001	01	90
-- 1001	02	90
-- 1001	03	90
-- 1002	01	85
-- 1002	02	85
-- 1002	03	70
-- 1003	01	70
-- 1003	02	70
-- 1003	03	85

create table score
(
    uid        string,
    subject_id string,
    score      int
)
    row format delimited fields terminated by '\t';

-- 方法一 join
select subject_id, avg(score) as avgscore
from score
group by subject_id;

with t1 as (
    select uid, score.subject_id, score as s, avgscore
    from score
             left join (select subject_id, avg(score) as avgscore
                        from score
                        group by subject_id) as a on a.subject_id = score.subject_id
)

select uid,
       if(s > avgscore, 0, 1) as flag
from t1;

select uid
from (
         select uid,
                if(s > avgscore, 0, 1) as flag
         from t1
     )
group by uid
having sum(flag) = 0;

--方法二-----------------------------------
select uid
from (select uid,
             if(score > avg_score, 0, 1) flag
      from (select uid,
                   score,
                   avg(score) over (partition by subject_id) avg_score
            from score) t1) t2
group by uid
having sum(flag) = 0;

--【注意】亮点
-- 不需要join，直接多一列-平均成绩，【灵活使用开窗函数】over！！！

select uid,
       score,
       avg(score) over (partition by subject_id) avg_score
from score
;