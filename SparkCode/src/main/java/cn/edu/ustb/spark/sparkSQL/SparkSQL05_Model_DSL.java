package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL05_Model_DSL {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession.builder()
                .appName("DSL")
                .master("local[*]")
                .getOrCreate();

        final Dataset<Row> ds = sparkSession.read().json("datas\\user.json");

        ds.select("name").show();

        sparkSession.close();
    }
}
