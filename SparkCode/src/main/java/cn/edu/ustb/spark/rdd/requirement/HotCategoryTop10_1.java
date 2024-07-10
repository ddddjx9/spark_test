package cn.edu.ustb.spark.rdd.requirement;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

/**
 * version2：因为下单的id或者支付的id可能不止一个，所以这里出现了问题
 */
public class HotCategoryTop10_1 {
    public static void main(String[] args) {
        //搭建运行环境
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("HotCategory");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //将文件作为数据源，对接RDD
        final JavaRDD<String> dataRDD = jsc.textFile("D:\\code\\ScalaTest\\datas\\user_visit_action.txt");

        //1. 数据格式：
        //2019-07-17 _ 95 _ 26070e87-1ad7-49a3-8fb3-cc741facaddf _ 37 _ 2019-07-17 00:00:02 _ 手机 _ -1 _ -1 _ null _ null _ null _ null _ 3

        //数据过滤
        //将过滤后的数据进行结果转变，一次性做统计
        //数据只有四种，过滤后只有三种
        //因为下单的id或者支付的id可能不止一个，所以这里出现了问题
        final JavaPairRDD<String, HotCategory> objCountRDD = dataRDD.filter(line -> ("null").equals(line.split("_")[5]))
                .map(line -> {
                    final String[] split = line.split("_");
                    if (!"-1".equals(split[6])) {
                        //点击数据
                        return new HotCategory(split[6], 1L, 0L, 0L);
                    } else if (!"null".equals(split[8])) {
                        //下单数据
                        return new HotCategory(split[8], 0L, 1L, 0L);
                    } else {
                        return new HotCategory(split[10], 0L, 0L, 1L);
                    }
                }).mapToPair(obj -> new Tuple2<>(obj.getId(), obj))
                /*.reduceByKey(new Function2<HotCategory, HotCategory, HotCategory>() {
                    @Override
                    public HotCategory call(HotCategory v1, HotCategory v2) throws Exception {
                        return null;
                    }
                })*/
                .reduceByKey((obj1, obj2) -> {
                    //两两聚合
                    obj1.setClickCount(obj1.getClickCount() + obj2.getClickCount());
                    obj1.setOrderCount(obj1.getOrderCount() + obj2.getOrderCount());
                    obj1.setPayCount(obj1.getPayCount() + obj2.getPayCount());

                    return obj1;
                });
        objCountRDD.take(5).forEach(System.out::println);

        jsc.close();
    }
}
