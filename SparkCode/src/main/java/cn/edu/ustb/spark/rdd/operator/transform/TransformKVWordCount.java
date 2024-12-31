package cn.edu.ustb.spark.rdd.operator.transform;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class TransformKVWordCount {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //分组聚合
        //1. 读取文件
        //2. 将文件数据进行分解 - 形成一个一个的单词
        //3. 将相同单词分到一个组中
        //4. 计算每个单词的组中的数量即可

        //当前文件进行读取的时候，是一行一行进行读取的
        final JavaRDD<String> rdd = jsc.textFile("datas\\data1.txt");

        rdd.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .groupBy(word -> word)
                .mapValues(wordGroup -> {
                    int count = 0;
                    for (String ignored : wordGroup) {
                        count++;
                    }
                    return count;
                });

        jsc.close();
    }
}
