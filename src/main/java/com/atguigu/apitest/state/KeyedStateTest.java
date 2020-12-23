package com.atguigu.apitest.state;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KeyedStateTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<String> hadoop102 = env.socketTextStream("hadoop102", 7777);

        SingleOutputStreamOperator<SensorReading> datastream = hadoop102.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] strings = s.split(",");
                return new SensorReading(strings[0], new Long(strings[1]), new Double(strings[2]));
            }
        });

        SingleOutputStreamOperator<Integer> id = datastream.keyBy("id")
                .map(new MyKeyFunction());

        id.print();

        env.execute();
    }

    public static class MyKeyFunction extends RichMapFunction<SensorReading, Integer> {
        private ValueState<Integer> count;

        @Override
        public void open(Configuration parameters) throws Exception {  //【注意】
            count = getRuntimeContext().getState(new ValueStateDescriptor<Integer>("key-count", Integer.class, 0));
        }

        @Override
        public Integer map(SensorReading sensorReading) throws Exception {
            Integer value = count.value();
            value++;
            count.update(value);
            return value;
        }
    }
}
