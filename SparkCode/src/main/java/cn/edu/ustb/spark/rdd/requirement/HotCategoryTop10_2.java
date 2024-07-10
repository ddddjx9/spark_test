package cn.edu.ustb.spark.rdd.requirement;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotCategoryTop10_2 {
    public static void main(String[] args) {
        //搭建运行环境
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("HotCategory");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //将文件作为数据源，对接RDD
        //如果当前环境是local，写本地文件的路径
        //如果是yarn环境，就写HDFS的路径 - spark on yarn
        //final JavaRDD<String> dataRDD = jsc.textFile("D:\\code\\ScalaTest\\datas\\user_visit_action.txt");

        //1. 数据格式：
        //2019-07-17 _ 95 _ 26070e87-1ad7-49a3-8fb3-cc741facaddf _ 37 _ 2019-07-17 00:00:02 _ 手机 _ -1 _ -1 _ null _ null _ null _ null _ 3

        //数据过滤
        //将过滤后的数据进行结果转变，一次性做统计
        //数据只有四种，过滤后只有三种
        //因为下单的id或者支付的id可能不止一个，所以这里出现了问题
        //将整体变为个体：flatMap，传递一个参数，返回迭代器
        jsc.textFile("D:\\code\\ScalaTest\\datas\\user_visit_action.txt")
                .filter(line -> ("null").equals(line.split("_")[5]))
                .flatMap(line -> {
                    final String[] split = line.split("_");
                    if (!"-1".equals(split[6])) {
                        //点击数据
                        return Arrays.asList(new HotCategory(split[6], 1L, 0L, 0L)).iterator();
                    } else if (!"null".equals(split[8])) {
                        //下单数据
                        final String[] id = split[8].split(",");
                        List<HotCategory> objs = new ArrayList<>();
                        for (String s : id) {
                            objs.add(new HotCategory(s, 0L, 1L, 0L));
                        }
                        return objs.iterator();
                    } else {
                        final String[] id = split[10].split(",");
                        List<HotCategory> objs = new ArrayList<>();
                        for (String s : id) {
                            objs.add(new HotCategory(s, 0L, 0L, 1L));
                        }
                        return objs.iterator();
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
                })
                //到此为止，仅仅是对品类数据做了统计。获取统计结果，还没有进行排序
                .map(kv -> kv._2)
                //在排序之前必须实现Comparable接口，并实现compareTo方法
                //sortByKey按照key进行排序，sortBy可以自己执行规则进行排序
                .sortBy(obj -> obj, false, 2)
                .take(10).forEach(System.out::println);

        jsc.close();
    }
}
