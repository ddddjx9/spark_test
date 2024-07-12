package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.SparkSession;

public class Test {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession.builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        sparkSession.read().option("header", true)
                .option("sep", "_")
                .csv("datas\\user.csv")
                .write().option("header", true)
                .mode("overwrite")
                .csv("output");
    }
}
