-- 订单事实表

drop table if exists dwd_fact_order_info;
create external table dwd_fact_order_info
(
    `id`                    string COMMENT '订单编号',
    `order_status`          string COMMENT '订单状态',
    `user_id`               string COMMENT '用户id',
    `out_trade_no`          string COMMENT '支付流水号',
    `create_time`           string COMMENT '创建时间(未支付状态)',
    `payment_time`          string COMMENT '支付时间(已支付状态)',
    `cancel_time`           string COMMENT '取消时间(已取消状态)',
    `finish_time`           string COMMENT '完成时间(已完成状态)',
    `refund_time`           string COMMENT '退款时间(退款中状态)',
    `refund_finish_time`    string COMMENT '退款完成时间(退款完成状态)',
    `province_id`           string COMMENT '省份ID',
    `activity_id`           string COMMENT '活动ID',
    `original_total_amount` string COMMENT '原价金额',
    `benefit_reduce_amount` string COMMENT '优惠金额',
    `feight_fee`            string COMMENT '运费',
    `final_total_amount`    decimal(10, 2) COMMENT '订单金额'
)
    PARTITIONED BY (`dt` string)
    stored as parquet
    location '/warehouse/gmall/dwd/dwd_fact_order_info/'
    tblproperties ("parquet.compression" = "lzo");

set hive.exec.dynamic.partition.mode=nonstrict;
insert overwrite table dwd_fact_order_info partition (dt)
select if(new.id is null, old.id, new.id),
       if(new.order_status is null, old.order_status, new.order_status),
       if(new.user_id is null, old.user_id, new.user_id),
       if(new.out_trade_no is null, old.out_trade_no, new.out_trade_no),
       if(new.tms['1001'] is null, old.create_time, new.tms['1001']),--1001对应未支付状态
       if(new.tms['1002'] is null, old.payment_time, new.tms['1002']),
       if(new.tms['1003'] is null, old.cancel_time, new.tms['1003']),
       if(new.tms['1004'] is null, old.finish_time, new.tms['1004']),
       if(new.tms['1005'] is null, old.refund_time, new.tms['1005']),
       if(new.tms['1006'] is null, old.refund_finish_time, new.tms['1006']),
       if(new.province_id is null, old.province_id, new.province_id),
       if(new.activity_id is null, old.activity_id, new.activity_id),
       if(new.original_total_amount is null, old.original_total_amount, new.original_total_amount),
       if(new.benefit_reduce_amount is null, old.benefit_reduce_amount, new.benefit_reduce_amount),
       if(new.feight_fee is null, old.feight_fee, new.feight_fee),
       if(new.final_total_amount is null, old.final_total_amount, new.final_total_amount),
       date_format(if(new.tms['1001'] is null, old.create_time, new.tms['1001']), 'yyyy-MM-dd')
from (
         select id,
                order_status,
                user_id,
                out_trade_no,
                create_time,
                payment_time,
                cancel_time,
                finish_time,
                refund_time,
                refund_finish_time,
                province_id,
                activity_id,
                original_total_amount,
                benefit_reduce_amount,
                feight_fee,
                final_total_amount
         from dwd_fact_order_info
         where dt
                   in
               (
                   select date_format(create_time, 'yyyy-MM-dd')
                   from ods_order_info
                   where dt = '2020-03-10'
               )
     ) old
         full outer join
     (
         select info.id,
                info.order_status,
                info.user_id,
                info.out_trade_no,
                info.province_id,
                act.activity_id,
                log.tms,
                info.original_total_amount,
                info.benefit_reduce_amount,
                info.feight_fee,
                info.final_total_amount
         from (
                  select order_id,
                         str_to_map(concat_ws(',', collect_set(concat(order_status, '=', operate_time))), ',', '=') tms
                  from ods_order_status_log
                  where dt = '2020-03-10'
                  group by order_id
              ) log
                  join
              (
                  select *
                  from ods_order_info
                  where dt = '2020-03-10'
              ) info
              on log.order_id = info.id
                  left join
              (
                  select *
                  from ods_activity_order
                  where dt = '2020-03-10'
              ) act
              on log.order_id = act.order_id
     ) new
     on old.id = new.id;