package com.atguigu.apitest.state;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Collections;
import java.util.List;

public class OperatorStateTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

//        env.setStateBackend(new MemoryStateBackend());
//        env.setStateBackend(new FsStateBackend(""));

        DataStreamSource<String> hadoop102 = env.socketTextStream("hadoop102", 7777);
        SingleOutputStreamOperator<Integer> map = hadoop102.map(new myMapFunc());

        map.print();
        env.execute();
    }

    public static class myMapFunc implements MapFunction<String, Integer>, ListCheckpointed<Integer> {
        Integer count = 0;

        @Override
        public Integer map(String s) throws Exception {
            return count++;
        }

        @Override
        public List<Integer> snapshotState(long checkpointId, long timestamp) throws Exception {
            return Collections.singletonList(count);
        }

        @Override
        public void restoreState(List<Integer> state) throws Exception {
            for (Integer integer : state) {
                count += integer;
            }
        }
    }
}
