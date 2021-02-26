package com.ys.algo01.Tencent

/**
  * HIVE 表 t_user_log 里有 40 亿条数据，包含了未经过滤的用户在公众号中的行为。
  * HIVE 表 t_wx_doc 里有 2000w 条数据，记录了不同公众号发表的文章。
  * // 相关表定义如下：
  * CREATE TABLE default.t_user_log (
  * ip string COMMENT ‘IP’,
  * ts string COMMENT ‘时间戳’,
  * userid string COMMENT ‘用户标识’,
  * docid string COMMENT ‘文章 ID’,
  * action int COMMENT ‘行为类型 1阅读 2 点赞 3 分享 4 打赏 5 评论’,
  * entry string COMMENT ‘渠道’,
  * ua string COMMENT ‘ua信息’)
  * PARTITIONED BY (
  * ds bigint)
  * * * * * * * * * * *
  * CREATE TABLE default.t_wx_doc (
  * cpid string COMMENT ‘公众号 ID’,
  * ts string COMMENT ‘发文时间戳’,
  * docid string COMMENT ‘文章ID’,
  * text string COMMENT ‘文章内容’,
  * category int COMMENT ‘文章类别’
  * )
  * 在机器资源有限（Vcore<=100 && Mem <400G）的情况下，请编程解决以下两个问题：
  * （计算框架&算法不限）
  * Q1. 找出最热门的 10 个公众号。
  * Q2. 给定一个100万大小用户包（userid 字符串长度 16），计算他们各自最可能喜欢的 5 个公众号。
  */

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object WX {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")

    val sc: SparkContext = new SparkContext(sparkConf)

    val hv: RDD[String] = sc.textFile("hdfs://ip:port/user/hive/warehouse/default.db/t_user_log")
    val t_user_log: RDD[(String, String)] = hv.map(_.split("\t")).map(e => (e(3), e(2))) //(docid,userid)
    //可能数据倾斜，（爆款文章）一个 docid 对应特别多的 userid

    val hv1: RDD[String] = sc.textFile("hdfs://ip:port/user/hive/warehouse/default.db/t_wx_doc")
    val t_wx_doc: RDD[(String, String)] = hv1.map(_.split("\t")).map(e => (e(2), e(0))) //(docid,cpid)


    val rddjoin: RDD[(String, (String, String))] = t_user_log.join(t_wx_doc).cache() //(docid,(userid,cpid))

    //Q1.找出最热门的 10 个公众号
    //思路：统计每个公众号的活跃用户数量
    rddjoin.distinct().map(_._2).distinct()
      .groupBy(_._2)
      .map(e => (e._1, e._2.size))
      .sortBy(_._2, false)
      .take(10)
      .foreach(println)


    //Q2.给定一个100万大小用户包（userid 字符串长度 16），计算他们各自最可能喜欢的 5 个公众号
    //思路：统计每个用户对每个公众号的操作频次，操作越多越喜欢这个公众号

    //用户id包不会超过100M，所以当作广播变量广播出去
    val broadcastIds: Broadcast[List[String]] = sc.broadcast(List("1", "2", "3")) //list里面是用户id

    rddjoin.filter(
      rdd => {
        broadcastIds.value.contains(rdd._2._1)
      }
    )

    //join导致的数据倾斜
    //聚合等算子导致的数据倾斜
    rddjoin.map(e => (e._2, 1))
      .reduceByKey(_ + _)
      .map(e => (e._1._1, (e._1._2, e._2))) //(userid,(cpid,sum))
      .groupByKey()
      .mapValues(
        iter => {
          iter.toList.sortWith(
            (left, right) => {
              left._2 > right._2
            }
          ).take(5)
        }
      ).foreach(println)


  }
}

