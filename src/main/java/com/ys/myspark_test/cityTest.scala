package com.ys.myspark_test

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import scala.util.Random

/**
  * 已知 一个 user_id , city 的一个实时日志，计算每个城市的pv和 uv？
  * 如果该日志数据量特别大，而且某些城市数据特别多，需要做哪些处理？
  * （考察点：spark/have sql，计算逻辑及语法；热点处理；数据倾斜处理；）
  */
object cityTest {
  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("city_uv_pv")

    val sc = new SparkContext(conf)

    val citylog: RDD[String] = sc.textFile("city.log").cache()

    val city_pv: RDD[(String, Int)] = citylog.map(
      data => {
        val datas = data.split(",")
        (datas(1), 1)
      }
    )

    //city数据倾斜
    //打散热点key，进行两阶段聚合
    city_pv.map(
      data => {
        (new Random().nextInt(10) + "_" + data._1, 1)
      }
    ).reduceByKey(_ + _)
      .map(
        data => {
          (data._1.split("_")(1), data._2)
        }
      ).reduceByKey(_ + _).foreach(println)

    println("pv============>>>")
    //无热点的情况
    city_pv.reduceByKey(_ + _).foreach(println)

    //还可以进行抽样，选出数据量最大的几个城市
    /*
    val sampleRDD: RDD[(String, Int)] = city_pv.sample(false, 0.1)

    sampleRDD.countByKey().take(10).foreach(println)

     */

    println("uv============>>>")

    //防止数据倾斜的解法
    citylog.map(
      data => {
        val datas: Array[String] = data.split(",")
        ((datas(0), datas(1)), 1)
      }
    ).reduceByKey(_ + _)
      .map(
        data => {
          (new Random().nextInt(10) + "_" + data._1._2, 1)
        }
      ).reduceByKey(_ + _)
      .map(
        data => {
          (data._1.split("_")(1), data._2)
        }
      )
      .reduceByKey(_ + _)
      .foreach(println)

  }
}
