drop table if exists dwd_fact_coupon_use;
create external table dwd_fact_coupon_use
(
    `id`            string COMMENT '编号',
    `coupon_id`     string COMMENT '优惠券ID',
    `user_id`       string COMMENT 'userid',
    `order_id`      string COMMENT '订单id',
    `coupon_status` string COMMENT '优惠券状态',
    `get_time`      string COMMENT '领取时间',
    `using_time`    string COMMENT '使用时间(下单)',
    `used_time`     string COMMENT '使用时间(支付)'
) COMMENT '优惠券领用事实表'
    PARTITIONED BY (`dt` string)
    row format delimited fields terminated by '\t'
    location '/warehouse/gmall/dwd/dwd_fact_coupon_use/';
--注意：dt是按照优惠卷领用时间get_time做为分区。


set hive.exec.dynamic.partition.mode=nonstrict;

insert overwrite table dwd_fact_coupon_use partition (dt)
select if(new.id is null, old.id, new.id),
       if(new.coupon_id is null, old.coupon_id, new.coupon_id),
       if(new.user_id is null, old.user_id, new.user_id),
       if(new.order_id is null, old.order_id, new.order_id),
       if(new.coupon_status is null, old.coupon_status, new.coupon_status),
       if(new.get_time is null, old.get_time, new.get_time),
       if(new.using_time is null, old.using_time, new.using_time),
       if(new.used_time is null, old.used_time, new.used_time),
       date_format(if(new.get_time is null, old.get_time, new.get_time), 'yyyy-MM-dd')
from (
         select id,
                coupon_id,
                user_id,
                order_id,
                coupon_status,
                get_time,
                using_time,
                used_time
         from dwd_fact_coupon_use
         where dt in
               (
                   select date_format(get_time, 'yyyy-MM-dd')
                   from ods_coupon_use
                   where dt = '2020-03-10'
               )
     ) old
         full outer join
     (
         select id,
                coupon_id,
                user_id,
                order_id,
                coupon_status,
                get_time,
                using_time,
                used_time
         from ods_coupon_use
         where dt = '2020-03-10'
     ) new
     on old.id = new.id;

--think：和拉链表的区别：既然都是【修改和新增的数据】
    --本表是new的表，原字段为空，修改后有了新值
    --拉链表是在原值上修改。