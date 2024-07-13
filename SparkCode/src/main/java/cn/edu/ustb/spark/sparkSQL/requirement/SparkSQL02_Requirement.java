package cn.edu.ustb.spark.sparkSQL.requirement;

import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

public class SparkSQL02_Requirement {
    public static void main(String[] args) {
        //统计热门商品的Top3
        System.setProperty("HADOOP_USER_NAME", "root");

        final SparkSession sparkSession = SparkSession.builder()
                .master("local[*]")
                .appName("requirement")
                .enableHiveSupport()
                .getOrCreate();

        //需求分析：
        //  如果需求中有【各个xxx】的描述。表述的基本含义是将相同的数据分到一个组中。
        //  热门：从点击量统计 => （商品id，点击数量）
        //  组内排序后取前三名
        //  区域  商品  点击数量    排序号
        //  华北  鞋     5000        1
        //  华北  衣服   3500        2
        //  华南  帽子   6000        1
        //  2019-07-17	95	26070e87-1ad7-49a3-8fb3-cc741facaddf	37	2019-07-17 00:00:02	手机	-1	-1	\N	\N	\N	\N	3
        //  1	北京	华北
        //  1	商品_1	自营

        //当前只考虑点击行为，将不是点击行为的过滤掉
        //这种sql的写法肯定效率不高，因为直接连，没有先投影
        //select查询子句中如果包含聚合函数，那么对查询字段有要求
        //  常量
        //  查询字段在聚合函数内使用
        //  查询字段必须参与分组
        //group by分组字段关系
        //  如果分组字段存在上下级，或者从属关系，那么统计结果和下级字段有关，与上级字段无关
        sparkSession.udf().register("cityRemark", functions.udaf(new MyCityRemarkUDAF(), Encoders.STRING()));
        sparkSession.sql("SELECT ci.area, pi.product_name, COUNT(*) AS click_count, cityRemark(city_name)\n" +
                        "FROM user_visit_action AS user\n" +
                        "JOIN product_info AS pi ON user.click_product_id = pi.product_id\n" +
                        "JOIN city_info AS ci ON user.city_id = ci.city_id\n" +
                        "WHERE user.click_product_id != '-1'\n" +
                        "GROUP BY ci.area, pi.product_id, pi.product_name\n" +
                        "LIMIT 10;")
                .show();

        sparkSession.close();
    }
}
