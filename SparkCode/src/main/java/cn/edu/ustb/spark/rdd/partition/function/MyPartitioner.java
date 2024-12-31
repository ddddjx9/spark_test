package cn.edu.ustb.spark.rdd.partition.function;

import org.apache.spark.Partitioner;

import java.util.Objects;

/**
 * 自定义分区器
 * <p>
 * 继承抽象类 - Partitioner
 * </p>
 * <p>
 * 重写里面的numPartitions和getPartitions方法，重写equals方法和hashCode方法
 * </p>
 */
public class MyPartitioner extends Partitioner {
    private final int numPartitions;

    public MyPartitioner(int numPartitions) {
        this.numPartitions = numPartitions;
    }

    /**
     * 指定分区数量
     *
     * @return 返回分区数量
     */
    @Override
    public int numPartitions() {
        return numPartitions;
    }

    /**
     * 返回分区的索引号
     *
     * @param key 数据的key
     * @return 返回 根据数据key来获取的分区编号（从0开始）
     */
    @Override
    public int getPartition(Object key) {
        if ("nba".equals(key)) {
            return 0;
        } else if ("wnba".equals(key)) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MyPartitioner) {
            MyPartitioner other = (MyPartitioner) o;
            return this.numPartitions == other.numPartitions;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numPartitions);
    }
}
