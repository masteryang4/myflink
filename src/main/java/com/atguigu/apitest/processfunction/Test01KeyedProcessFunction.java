package com.atguigu.apitest.processfunction;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class Test01KeyedProcessFunction {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> datastream = env.socketTextStream("hadoop102", 7777);
        SingleOutputStreamOperator<SensorReading> map = datastream.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] ss = s.split(",");
                return new SensorReading(ss[0], new Long(ss[1]), new Double(ss[2]));
            }
        });

        map.keyBy("id")
                .process(new MyKeyProcess())
                .print()
        ;

        env.execute();
    }

    public static class MyKeyProcess extends KeyedProcessFunction<Tuple, SensorReading, Integer> {
        ValueState<Long> timestate;

        @Override
        public void open(Configuration parameters) throws Exception {
            timestate = getRuntimeContext().getState(new ValueStateDescriptor<Long>("time", Long.class));
        }

        @Override
        public void processElement(SensorReading value, Context ctx, Collector<Integer> out) throws Exception {
            out.collect(value.getId().length());

            ctx.getCurrentKey();
            ctx.timestamp();

            timestate.update(ctx.timerService().currentProcessingTime());

            ctx.timerService().registerProcessingTimeTimer(timestate.value() + 5000L);

//            ctx.timerService().deleteProcessingTimeTimer(timestate.value() + 5000L);
        }

        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<Integer> out) throws Exception {
            System.out.println(timestamp + "定时器触发");
        }

        @Override
        public void close() throws Exception {
            timestate.clear();
        }
    }
}


