package cn.edu.ustb.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class Spark01_Env {
    public static void main(String[] args) {
        //TODO 构建Spark的运行环境

        //TODO 创建Spark的配置对象
        SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("spark");

        //A master URL must be set in your configuration
        //An application name must be set in your configuration
        //监控当前执行情况
        //采纳数可以直接传递，将master和appName直接传递给JavaSparkContext
        final JavaSparkContext jsc = new JavaSparkContext("local", "spark");

        //TODO 用完之后释放资源
        jsc.close();
    }
}
