package com.atguigu.UserBehaviorAnalysis.HotItemsAnalysis;

import com.atguigu.UserBehaviorAnalysis.HotItemsAnalysis.beans.UserBehavior;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

public class HotItems {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStreamSource<String> dataStreamSource = env.readTextFile("D:\\develop\\FlinkTutorial\\src\\main\\resources\\UserBehavior.csv");

        SingleOutputStreamOperator<UserBehavior> dataStream = dataStreamSource.map(new MapFunction<String, UserBehavior>() {
            @Override
            public UserBehavior map(String s) throws Exception {
                String[] split = s.split(",");
                return new UserBehavior(new Long(split[0]), new Long(split[1]), new Integer(split[2]), split[3], new Long(split[4]));
            }
        }).assignTimestampsAndWatermarks(new AscendingTimestampExtractor<UserBehavior>() {
            @Override
            public long extractAscendingTimestamp(UserBehavior element) {
                return element.getTimestamp() * 1000L;
            }
        });

        dataStream.filter(new FilterFunction<UserBehavior>() {
            @Override
            public boolean filter(UserBehavior userBehavior) throws Exception {
                return userBehavior.getBehavior() == "pv" ? true : false;
            }
        }).keyBy("itemId")
                .timeWindow(Time.hours(1),Time.minutes(5))
                .aggregate()
    }
}
