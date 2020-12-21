package com.atguigu.apitest.window;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;

import javax.annotation.Nullable;

public class WaterMarkTest01 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStreamSource<String> hadoop102 = env.socketTextStream("hadoop102", 7777);

        SingleOutputStreamOperator<SensorReading> map = hadoop102.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] ss = s.split(",");
                return new SensorReading(ss[0], new Long(ss[1]), new Double(ss[2]));
            }
        });

        //assignTimestampsAndWatermarks 分配时间戳和水位线，有两种：（1）周期型分配水位线（2）断点式分配水位线
        //默认是周期性生成水位线
        SingleOutputStreamOperator<SensorReading> m1 = map.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<SensorReading>(Time.seconds(1)) {
            @Override
            public long extractTimestamp(SensorReading sensorReading) {
                return sensorReading.getTimestamp() * 1000L;
            }
        });

        SingleOutputStreamOperator<SensorReading> m2 = map.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<SensorReading>() {
            @Override
            public long extractAscendingTimestamp(SensorReading sensorReading) {
                return sensorReading.getTimestamp() * 1000L;
            }
        });


        map.print();

        env.execute();
    }
}
