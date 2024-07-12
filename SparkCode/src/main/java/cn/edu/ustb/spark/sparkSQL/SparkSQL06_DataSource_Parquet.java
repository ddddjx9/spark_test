package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.SparkSession;

public class SparkSQL06_DataSource_Parquet {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession.builder()
                .master("local[*]")
                .appName("Parquet")
                .getOrCreate();

        sparkSession.read().parquet("datas\\users.parquet").show();
        //将行式存储转换为列式存储
        sparkSession.read().json("datas\\user.json").write().parquet("output");
        sparkSession.close();
    }
}
