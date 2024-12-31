package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;

public class SparkSQL01_Env {
    public static void main(String[] args) {
        // 构建环境对象
        //     Spark在结构化数据的处理过程中对核心功能，环境进行了封装
        //      但是我们一般不这样构建环境对象，我们一般采用源码中的构建方式
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("SparkSQL");
        final SparkContext sc = new SparkContext(conf);
        //将之前的环境对象进行了包装
        final SparkSession sparkSession = new SparkSession(sc);

        //释放资源
        sparkSession.close();
    }
}
