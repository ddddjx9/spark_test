package cn.edu.ustb.spark.rdd.requirement;

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
        //      Exception in thread "main" java.lang.IllegalArgumentException: java.net.URISyntaxException: Relative path in absolute
        //      URI: datas:%5Cuser.txt
        final JavaRDD<String> dataRDD = jsc.textFile("D:\\code\\ScalaTest\\datas\\user.txt");

        //TODO 获取年龄的平均值
        //      将一行数据中的年龄获取出来
        //      然后使用reduce方法进行两两聚合，除以rdd.count()即为平均数
        //      每一行都是json格式数据，每一行都是一个对象，对象内容必须包含在大括号中
        //      对象中的多个属性必须用逗号隔开
        //      属性名和属性值之间用冒号隔开
        //      属性名必须用双引号包起来，属性值如果为字符串类型，也需要用双引号包裹

        //TODO 去除大括号，去除首尾空格
        //      { "name":"wangwu", "age":50, "id":1003}
        final JavaRDD<Integer> rdd = dataRDD.map(line -> {
            //放在算子逻辑里面，一起打包到Executor端执行
            int age = 0;
            final String[] split = line.trim().substring(1, line.length() - 1).split(",");
            for (String s : split) {
                final String[] data = s.trim().split(":");
                for (int i = 0; i < data.length; i += 2) {
                    if ("\"age\"".equals(data[i])) {
                        //验证缓存效果：System.out.println("*************************");
                        age = Integer.parseInt(data[i + 1]);
                    }
                }
            }
            return age;
        });
        rdd.cache();

        System.out.println(rdd.reduce(Integer::sum) / rdd.count());

        jsc.close();
    }
}
