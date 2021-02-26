package com.ys.algo01.Tencent

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 可能出现的【数据倾斜】的问题的处理
  */

object WX01 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("tencent")

    val sc: SparkContext = new SparkContext(sparkConf)

    val hv: RDD[String] = sc.textFile("hdfs://ip:port/user/hive/warehouse/default.db/t_user_log")
    val t_user_log: RDD[(String, String)] = hv.map(_.split("\t")).map(e => (e(3), e(2))) //(docid,userid)

    val hv1: RDD[String] = sc.textFile("hdfs://ip:port/user/hive/warehouse/default.db/t_wx_doc")
    val t_wx_doc: RDD[(String, String)] = hv1.map(_.split("\t")).map(e => (e(2), e(0))) //(docid,cpid)

    /**
      * 会产生数据倾斜，尤其是(docid,userid)里面的 docid
      */
    //【抽样10%，看哪个docid对应的数字大的离谱，就是可能会导致数据倾斜的key】也就是某篇文章对应的用户数目特别多
    //【一般来说，应该是极少数的 docid 会有这种问题】
    val samplerdd: RDD[(String, String)] = t_user_log.sample(false, 0.1)
    val stringToLong: collection.Map[String, Long] = samplerdd.countByKey()

    val tuples: Array[(String, Long)] = stringToLong.toArray.sortBy(_._2).take(50)
    val strings: Array[String] = tuples.map(_._1)

    val strings111: List[String] = stringToLong.toList.sortBy(_._2).take(10).map(_._1)


    val rddjoin: RDD[(String, (String, String))] = t_user_log.join(t_wx_doc) //(docid,(userid,cpid))

    rddjoin.map(_._2)

  }
}

