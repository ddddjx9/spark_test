package cn.edu.ustb.spark.rdd.operator.funtion;


import org.apache.spark.api.java.JavaRDD;

/**
 * <p>
 * 感觉过滤逻辑每次都改很不方便，所以想做一个封装
 * </p>
 * <p>
 * 将整体逻辑封装好，只需要传递一个参数，用于查询即可
 * </p>
 * <p>
 * private final String query
 * </p>
 * <p>
 * public Search(String query) {
 * *         this.query = query;
 * *     }
 * </p>
 * <p>
 * 这两段代码都是在Driver端执行的
 * </p>
 * <p>
 * 这两段下面的代码都是在Executor端执行的
 * </p>
 * <p>
 * 执行Executor端的代码时，用到了query，这是在Driver执行的，所以需要从Driver拉取到Executor
 * </p>
 * <p>
 * 因为query是成员属性，所以需要将类进行加载，才能获取成员属性
 * </p>
 */
public class Search {
    private final String query;

    public Search(String query) {
        this.query = query;
    }

    public void match(JavaRDD<String> rdd) {
        String q = this.query;
        rdd.filter(s -> !s.startsWith(q)).collect().forEach(System.out::println);
    }
}