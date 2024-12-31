package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class EnvConvert {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf().setAppName("Env_Convert").setMaster("local[*]");
        final SparkSession sparkSession = SparkSession
                .builder()
                .config(conf)
                .getOrCreate();

        // 环境之间的转换
        //      Core: SparkContext -> SQL: SparkSession
        SparkSession session = new SparkSession(new SparkContext(conf));
        // SQL : SparkSession -> SparkContext
        final SparkContext sc = sparkSession.sparkContext();
        // 进一步得到Java版本的环境对象
        final JavaSparkContext jsc = new JavaSparkContext(sc);

        sparkSession.close();
    }
}
