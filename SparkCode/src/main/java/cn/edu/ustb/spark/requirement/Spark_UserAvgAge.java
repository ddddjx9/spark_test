package cn.edu.ustb.spark.requirement;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Spark_UserAvgAge {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("UsrAvgAge");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        //TODO 读取数据
        final JavaRDD<String> dataRDD = jsc.textFile("datas:\\user.txt");

        //TODO 获取年龄的平均值
        //      将一行数据中的年龄获取出来0

        jsc.close();
    }
}
