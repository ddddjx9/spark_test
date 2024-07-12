package cn.edu.ustb.spark.sparkSQL.requirement;

import org.apache.spark.sql.SparkSession;

public class SparkSQL01_DataSource {
    public static void main(String[] args) {
        //指定访问权限
        System.setProperty("HADOOP_USER_NAME", "root");

        final SparkSession sparkSession = SparkSession.builder()
                .enableHiveSupport()
                .master("local[*]")
                .appName("require")
                .getOrCreate();

        sparkSession.sql("create table `user_visit_action`(" +
                "\n `date` string," +
                "\n `user_id` bigint," +
                "\n `session_id` string," +
                "\n `page_id` bigint," +
                "\n `action_time` string," +
                "\n `search_keyword` string," +
                "\n `click_category_id` bigint," +
                "\n `click_product_id` bigint," +
                "\n `order_category_ids` string," +
                "\n `order_product_ids` string," +
                "\n `pay_category_ids` string," +
                "\n `pay_product_ids` string," +
                "\n `city_id` bigint" +
                "\n)" +
                "\nrow format delimited fields terminated by '\\t';");
        sparkSession.sql("load data local inpath 'datas/user_visit_action.txt' into table user_visit_action;");

        sparkSession.sql("create table `city_info`(" +
                "\n `city_id` bigint," +
                "\n `city_name` string," +
                "\n `area` string" +
                "\n)" +
                "\nrow format delimited fields terminated by '\\t';");
        sparkSession.sql("load data local inpath 'datas/city_info.txt' into table city_info;");

        sparkSession.sql("create table `product_info`(" +
                "\n `product_id` bigint," +
                "\n `product_name` string," +
                "\n `extend_info` string" +
                "\n)" +
                "\nrow format delimited fields terminated by '\\t';");
        sparkSession.sql("load data local inpath 'datas/product_info.txt' into table product_info;");

        sparkSession.close();
    }
}
