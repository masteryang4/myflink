package com.atguigu.apitest.transform;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Transform_Base {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);
        DataStreamSource<String> dataStreamSource = env.readTextFile("D:\\develop\\FlinkTutorial\\src\\main\\resources\\sensor.txt");

        DataStream<SensorReading> dataStream = dataStreamSource.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] strings = s.split(",");
                return new SensorReading(strings[0], new Long(strings[1]), new Double(strings[2]));
            }
        });
        KeyedStream<SensorReading, Tuple> id = dataStream.keyBy("id"); //【注意返回类型】

        SingleOutputStreamOperator<SensorReading> temperature = id.maxBy("temperature");

        temperature.print();

//        id.print();
        env.execute();
    }
}
