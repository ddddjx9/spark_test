package cn.edu.ustb.spark.requirement;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class HotCategoryTop10 {
    public static void main(String[] args) {
        //搭建运行环境
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("HotCategory");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //将文件作为数据源，对接RDD
        final JavaRDD<String> dataRDD = jsc.textFile("D:\\code\\ScalaTest\\datas\\user_visit_action.txt");

        //TODO 需求分析
        //      热门（点击，下单，支付）品类Top10
        //      1. 对同一个品类的不同行为进行统计，然后排序 - wordCount
        //          对同一个品类的数据进行分组
        //          （品类，点击数量）
        //          （品类，下单数量）
        //          （品类，支付数量）
        //      2. 对统计结果进行排序 - sortByKey
        //      3. 对排序后的结果取前10条 - take

        //数据格式：
        //2019-07-17 _ 95 _ 26070e87-1ad7-49a3-8fb3-cc741facaddf _ 37 _ 2019-07-17 00:00:02 _ 手机 _ -1 _ -1 _ null _ null _ null _ null _ 3

        //数据过滤
        final JavaRDD<String> filterRDD = dataRDD.filter(line -> {
            //判断数据是否为搜索数据，如果是，就不留；如果不是，就留下
            final String[] split = line.split("_");
            //数出上述数据的搜索关键字所在的索引
            return ("null").equals(split[5]);
        });
        System.out.println(filterRDD.count());

        jsc.close();
    }
}
