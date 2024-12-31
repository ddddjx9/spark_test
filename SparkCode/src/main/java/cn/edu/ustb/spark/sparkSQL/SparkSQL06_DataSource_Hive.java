package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.SparkSession;

public class SparkSQL06_DataSource_Hive {
    public static void main(String[] args) {
        // 默认情况下，会拿Windows环境下的用户进行登录
        //      在编码前，设定Hadoop的访问用户
        System.setProperty("HADOOP_USER_NAME", "root");

        //创建配置对象
        final SparkSession sparkSession = SparkSession.builder()
                .master("local[*]")
                .appName("Hive")
                //添加hive支持，加上这个，才会读取配置文件，访问类库
                .enableHiveSupport()
                .getOrCreate();

        sparkSession.sql("show tables").show();
        sparkSession.sql("select * from stu").show();
        sparkSession.sql("insert into table stu values(2,'yexiu')");
        sparkSession.sql("select * from stu").show();

        sparkSession.close();
    }
}
