package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL06_DataSource_JSON {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession.builder()
                .master("local[*]")
                .appName("JSON")
                .getOrCreate();
        //一定要按照格式要求解析文件，否则会出现下述错误：
        //Exception in thread "main" org.apache.spark.sql.AnalysisException:
        //      Since Spark 2.3, the queries from raw JSON/CSV files are disallowed when the
        //      referenced columns only include the internal corrupt record column
        //          (named _corrupt_record by default).
        final Dataset<Row> json = sparkSession.read().json("datas\\user.json");

        //json.write().json("output");
        json.write().csv("output");
        json.show();

        sparkSession.close();
    }
}
