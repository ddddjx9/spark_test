package cn.edu.ustb.spark.sparkSQL;


import java.io.Serializable;

/**
 * 自定义缓冲区中的数据类型
 */
public class AvgAgeBuffer implements Serializable {
    private Long total;
    private Long count;

    public AvgAgeBuffer() {
    }

    public AvgAgeBuffer(Long total, Long count) {
        this.total = total;
        this.count = count;
    }

    /**
     * 获取
     *
     * @return total 返回计算之后的总和
     */
    public Long getTotal() {
        return total;
    }

    /**
     * 设置
     *
     * @param total 设置计算之后的总和
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * 获取
     *
     * @return count 返回计数值
     */
    public Long getCount() {
        return count;
    }

    /**
     * 设置
     *
     * @param count 设置计数值
     */
    public void setCount(Long count) {
        this.count = count;
    }

    public String toString() {
        return "AvgAgeBuffer{total = " + total + ", count = " + count + "}";
    }
}
