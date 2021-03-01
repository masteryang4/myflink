-- 有一个订单表order。已知字段有：order_id(订单ID), user_id(用户ID),amount(金额), pay_datetime(付费时间),channel_id(渠道ID),dt(分区字段)。
--
-- 1. 在Hive中创建这个表。
-- 2. 查询dt=‘2018-09-01‘里每个渠道的订单数，下单人数（去重），总金额。
-- 3. 查询dt=‘2018-09-01‘里每个渠道的金额最大3笔订单。
-- 4. 有一天发现订单数据重复，请分析原因

create external table order
(
    order_id     int,
    user_id      int,
    amount       double,
    pay_datatime timestamp,
    channel_id   int
) partitioned by (dt string)
    row format delimited fields terminated by '\t';

select count(order_id),
       count(distinct (user_id)),
       sum(amount)
from order
where dt = "2018-09-01";

select order_id,
       channel_id,
       amount
from (
         select order_id,
                channel_id,
                amount,
--                 max(amount) over (partition by channel_id),
                row_number() over ( partition by channel_id order by amount desc ) rank
         from order
         where dt = "2018-09-01"
     ) t
where t.rank < 3;
--订单属于业务数据，在关系型数据库中不会存在数据重复
-- hive建表时也不会导致数据重复，
--我推测是在数据迁移时，迁移失败导致重复迁移数据冗余了

--===================================
--     t_order订单表
--     order_id,//订单id
--     item_id, //商品id
--     create_time,//下单时间
--     amount//下单金额
--
--     t_item商品表
--     item_id,//商品id
--     item_name,//商品名称
--     category//品类
--
--     t_item商品表
--     item_id,//商品id
--     item_name,//名称
--     category_1,//一级品类
--     category_2,//二级品类
--
--     1. 最近一个月，销售数量最多的10个商品
select item_id,
       count(order_id) a
from t_order
where dataediff(create_time, current_date) <= 30
group by item_id
order by a desc;

-- 2. 最近一个月，每个种类里销售数量最多的10个商品
-- #一个订单对应一个商品 一个商品对应一个品类
with t as (
    select order_id,
           item_id,
           item_name,
           category
    from t_order
             join
         t_item
         on
             t_order.item_id = t_item.item_id
)

select order_id,
       item_id,
       item_name,
       category,
       count(item_id) over (partition by category) item_count
from t
group by category
order by item_count desc
limit 10;


-- 计算平台的每一个用户发过多少日记、获得多少点赞数
with t3 as (
    select *
    from t1
             left join t2
                       on t1.log_id = t2.log_id
)
select uid,//用户Id
       count(log_id) over (partition by uid)      log_cnt,//
       count(like_uid) over (partition by log_id) liked_cnt//获得多少点赞数
from t3;

--         处理产品版本号
--     1、需求A:找出T1表中最大的版本号
--     思路：列转行 切割版本号 一列变三列
--     主版本号  子版本号 阶段版本号
with t2 as (//转换
    select v_id v1,//版本号
           v_id v2 //主
    from t1
             lateral view explode(v2) tmp as v2
)
select //第一层 找出第一个
       v1,
       max(v2)
from t2;
--     ——————————————————————————————————————————————————————————————
--     1、需求A:找出T1表中最大的版本号
select v_id,//版本号
       max(split(v_id, ".")[0])                                  v1,//主版本不会为空
       max(if(split(v_id, ".")[1] = "", 0, split(v_id, ".")[1])) v2,//取出子版本并判断是否为空，并给默认值
       max(if(split(v_id, ".")[2] = "", 0, split(v_id, ".")[2])) v3//取出阶段版本并判断是否为空，并给默认值
from t1;

--     2、需求B：计算出如下格式的所有版本号排序，要求对于相同的版本号，顺序号并列：
select v_id,
       rank() over (partition by v_id order by v_id) seq
from t1;