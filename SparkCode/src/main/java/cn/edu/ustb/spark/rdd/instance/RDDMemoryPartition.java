package cn.edu.ustb.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class RDDMemoryPartition {
    public static void main(String[] args) {

        final SparkConf conf = new SparkConf();

        conf.setMaster("local[*]");
        conf.setAppName("spark");
        conf.set("spark.default.parallelism", "4");
        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //TODO Kafka可以将数据进行切片，减小数据规模，也称为叫分区，这个分区操作是底层完成的
        //      local环境中：分区数量和环境中的核数相关，尝试修改环境中的线程数local[*]，但是一般不推荐
        //      分区数量需要手动设定
        //          Spark在读取集合数据时，分区设定存在3种不同的场合：
        //              1.优先使用方法参数
        //              2.使用配置参数：spark.default.parallelism
        //              3.使用环境默认值：当前环境总的虚拟核数
        final List<String> names = Arrays.asList("zhangsan", "lisi", "wangwu");
        //parallelize可以传两个参数
        //  第一个参数表示对接的数据源集合
        //  第二个参数numSlices表示切片数量，可以不需要指定，spark会采用默认值进行分区
        //      如果不指定，采用默认值，numSlices=scheduler.conf.getInt("spark.default.parallelism", totalCores)
        //          从SparkConf中获取默认配置参数：spark.default.parallelism(默认并行度)
        //              如果默认配置参数不存在，默认取值为totalCores(当前环境总的虚拟核数)
        final JavaRDD<String> rdd = jsc.parallelize(names, 2);

        //TODO 将数据模型分区后的数据保存到磁盘文件中
        rdd.saveAsTextFile("output");

        //TODO 释放资源
        jsc.close();
    }
}

