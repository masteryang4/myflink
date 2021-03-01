-- 用一条SQL语句查询出每门课都大于80分的学生姓名
-- name   kecheng   fenshu
-- 张三    语文    81
-- 张三    数学    75
-- 李四    语文    76
-- 李四    数学     90
-- 王五    语文    81
-- 王五    数学    100
-- 王五    英语    90
--A:
select distinct name
from table
where name not in (select distinct name from table where fenshu <= 80);
--B：
select name
from table
group by name
having min(fenshu) > 80;

--一个叫team的表，里面只有一个字段name,一共有4条纪录，分别是a,b,c,d,对应四个球队，现在四个球队进行比赛，用一条sql语句显示所有可能的比赛组合.
--答：
select a.name, b.name
from team a,
     team b
where a.name < b.name;

-- 怎么把这样一个
-- year   month amount
-- 1991   1     1.1
-- 1991   2     1.2
-- 1991   3     1.3
-- 1991   4     1.4
-- 1992   1     2.1
-- 1992   2     2.2
-- 1992   3     2.3
-- 1992   4     2.4
-- 查成这样一个结果
-- year m1  m2  m3   m4
-- 1991 1.1 1.2 1.3 1.4
-- 1992 2.1 2.2 2.3 2.4 
--答案
select year,
       (select amount from aaa m where month = 1 and m.year = aaa.year) as m1,
       (select amount from aaa m where month = 2 and m.year = aaa.year) as m2,
       (select amount from aaa m where month = 3 and m.year = aaa.year) as m3,
       (select amount from aaa m where month = 4 and m.year = aaa.year) as m4
from aaa
group by year;

