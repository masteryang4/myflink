package com.atguigu.apitest.tableapi;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

public class Example_Test {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> ds = env.readTextFile("D:\\develop\\FlinkTutorial\\src\\main\\resources\\sensor.txt");

        SingleOutputStreamOperator<SensorReading> map = ds.map(new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                String[] strings = s.split(",");
                return new SensorReading(strings[0], new Long(strings[1]), new Double(strings[2]));
            }
        });

        //tableapi
        StreamTableEnvironment streamTableEnvironment = StreamTableEnvironment.create(env);//【核心】

        Table table = streamTableEnvironment.fromDataStream(map);
        Table tableapi_result = table.select("id,temperature").where("id='sensor_1'");
        streamTableEnvironment.toAppendStream(tableapi_result, Row.class).print("tableapi");

        //sql
        streamTableEnvironment.createTemporaryView("sensor", map);
        String sqlstring = "select id,temperature from sensor where id='sensor_1'";
        Table table1 = streamTableEnvironment.sqlQuery(sqlstring);
        streamTableEnvironment.toAppendStream(table1, Row.class).print("sql");

        env.execute();
    }
}
