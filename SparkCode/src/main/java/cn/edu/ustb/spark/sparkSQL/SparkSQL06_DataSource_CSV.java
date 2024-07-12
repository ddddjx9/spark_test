package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL06_DataSource_CSV {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession.builder()
                .master("local[*]")
                .appName("data source")
                .getOrCreate();

        //这里需要设置配置参数，否则读取的时候会认为给定的列名是一行中的值
        final Dataset<Row> csv = sparkSession.read()
                //告诉spark该表是有表头的，第一行就是表头
                .option("header", "true")
                //指定csv中的分隔符
                .option("sep", "_") //=> \t隔开 .tsv文件
                .csv("datas/user.csv");
        //csv.show();
        csv.write()
                .mode("append")
                .csv("output");

        //如果输出目的地已经存在，SparkSQL会报错
        //想要在同一个路径下生成文件，就需要修改配置 - 保存模式 - append
        csv.write().csv("output");

        //输出后观察到没有表头，所以仍然需要修改配置
        csv.write()
                .option("header", true)
                .csv("output");
        sparkSession.close();
    }
}
