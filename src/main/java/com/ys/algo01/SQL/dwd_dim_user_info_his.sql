drop table if exists dwd_dim_user_info_his;
create external table dwd_dim_user_info_his
(
    `id`           string COMMENT '用户id',
    `name`         string COMMENT '姓名',
    `birthday`     string COMMENT '生日',
    `gender`       string COMMENT '性别',
    `email`        string COMMENT '邮箱',
    `user_level`   string COMMENT '用户等级',
    `create_time`  string COMMENT '创建时间',
    `operate_time` string COMMENT '操作时间',
    `start_date`   string COMMENT '有效开始日期',
    `end_date`     string COMMENT '有效结束日期'
) COMMENT '订单拉链表'
    stored as parquet
    location '/warehouse/gmall/dwd/dwd_dim_user_info_his/'
    tblproperties ("parquet.compression" = "lzo");

--初始化拉链表
insert overwrite table dwd_dim_user_info_his
select id,
       name,
       birthday,
       gender,
       email,
       user_level,
       create_time,
       operate_time,
       '2020-03-10',
       '9999-99-99'
from ods_user_info oi
where oi.dt = '2020-03-10';

drop table if exists dwd_dim_user_info_his_tmp;
create external table dwd_dim_user_info_his_tmp
(
    `id`           string COMMENT '用户id',
    `name`         string COMMENT '姓名',
    `birthday`     string COMMENT '生日',
    `gender`       string COMMENT '性别',
    `email`        string COMMENT '邮箱',
    `user_level`   string COMMENT '用户等级',
    `create_time`  string COMMENT '创建时间',
    `operate_time` string COMMENT '操作时间',
    `start_date`   string COMMENT '有效开始日期',
    `end_date`     string COMMENT '有效结束日期'
) COMMENT '订单拉链临时表'
    stored as parquet
    location '/warehouse/gmall/dwd/dwd_dim_user_info_his_tmp/'
    tblproperties ("parquet.compression" = "lzo");

insert overwrite table dwd_dim_user_info_his_tmp
select *
from (
         select id,
                name,
                birthday,
                gender,
                email,
                user_level,
                create_time,
                operate_time,
                '2020-03-11' start_date,
                '9999-99-99' end_date
         from ods_user_info
         where dt = '2020-03-11'

         union all
         select uh.id,
                uh.name,
                uh.birthday,
                uh.gender,
                uh.email,
                uh.user_level,
                uh.create_time,
                uh.operate_time,
                uh.start_date,
                if(ui.id is not null and uh.end_date = '9999-99-99', date_add(ui.dt, -1), uh.end_date) end_date
         from dwd_dim_user_info_his uh
                  left join
              (
                  select *
                  from ods_user_info
                  where dt = '2020-03-11'
              ) ui on uh.id = ui.id
     ) his
order by his.id, start_date;

insert overwrite table dwd_dim_user_info_his
select *
from dwd_dim_user_info_his_tmp;


