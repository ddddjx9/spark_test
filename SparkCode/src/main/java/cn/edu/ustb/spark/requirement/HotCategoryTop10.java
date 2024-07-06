package cn.edu.ustb.spark.requirement;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class HotCategoryTop10 {
    public static void main(String[] args) {
        //搭建运行环境
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("HotCategory");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //对接文件

        jsc.close();
    }
}
