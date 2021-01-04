package com.atguigu.UserBehaviorAnalysis.HotItemsAnalysis;

import com.atguigu.UserBehaviorAnalysis.HotItemsAnalysis.beans.UserBehavior;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;


public class HotItemsSQL {
    public static void main(String[] args) throws Exception {
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

        EnvironmentSettings environmentSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();

        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env, environmentSettings);

        tableEnvironment.createTemporaryView("datatable", dataStream, "itemId, behavior, timestamp.rowtime as ts");

        Table table = tableEnvironment.sqlQuery("select * from " +
                "(" +
                "select *,ROW_NUMBER() over(partition by windowEnd order by cnt desc) as row_num from " +
                "(" +
                "select itemId,count(itemId) as cnt,HOP_END(ts,interval '5' minute,interval '1' hour) as windowEnd" +
                " from datatable" +
                " where behavior='pv' group by itemId,HOP(ts,interval '5' minute,interval '1' hour)" +
                ")" +
                ")" +
                "where row_num<=5");

        tableEnvironment.toRetractStream(table, Row.class).print();

        env.execute("flinkSQLforHotItems");
    }
}
