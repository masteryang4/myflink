package com.atguigu.apitest.sink;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

public class Sinkkafka {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> ds = env.readTextFile("D:\\develop\\FlinkTutorial\\src\\main\\resources\\sensor.txt");
        SingleOutputStreamOperator<String> map = ds.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                String[] strings = s.split(",");
                return new SensorReading(strings[0], new Long(strings[1]), new Double(strings[2])).toString();
            }
        });

//        map.print();

        map.addSink(new FlinkKafkaProducer011<String>("hadoop102:9092", "sink-kafka", new SimpleStringSchema()));

        env.execute();
    }
}
