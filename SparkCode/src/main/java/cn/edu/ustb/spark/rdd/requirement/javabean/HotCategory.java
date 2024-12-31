package cn.edu.ustb.spark.rdd.requirement.javabean;

import java.io.Serializable;

/**
 * 自定义数据对象
 * <p>
 * &nbsp&nbsp 实现可序列化接口
 * </p>
 * <p>
 * &nbsp&nbsp 定义私有属性和公共的get和set方法
 * </p>
 * <p>
 * &nbsp&nbsp 提供无参和全参的构造方法
 * </p>
 * <p>
 * &nbsp&nbsp 重写toString方法
 * </p>
 */
public class HotCategory implements Serializable, Comparable<HotCategory> {
    private String id;
    private Long clickCount;
    private Long orderCount;
    private Long payCount;

    public HotCategory() {
    }

    public HotCategory(String id, Long clickCount, Long orderCount, Long payCount) {
        this.id = id;
        this.clickCount = clickCount;
        this.orderCount = orderCount;
        this.payCount = payCount;
    }

    /**
     * 获取id
     *
     * @return id 返回id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id 传入具体id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取点击数
     *
     * @return clickCount 返回点击数
     */
    public Long getClickCount() {
        return clickCount;
    }

    /**
     * 设置点击数
     *
     * @param clickCount 传入点击数
     */
    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    /**
     * 获取下单数
     *
     * @return orderCount 返回下单数
     */
    public Long getOrderCount() {
        return orderCount;
    }

    /**
     * 设置下单数
     *
     * @param orderCount 返回下单数
     */
    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    /**
     * 获取支付数
     *
     * @return payCount 返回支付数
     */
    public Long getPayCount() {
        return payCount;
    }

    /**
     * 设置支付数
     *
     * @param payCount 传入支付数
     */
    public void setPayCount(Long payCount) {
        this.payCount = payCount;
    }

    public String toString() {
        return "HotCategory{id = " + id + ", clickCount = " + clickCount + ", orderCount = " + orderCount + ", payCount = " + payCount + "}";
    }

    @Override
    public int compareTo(HotCategory other) {
        final long clickCountCompare = this.clickCount - other.clickCount;
        final long orderCountCompare = this.orderCount - other.orderCount;
        final long payCountCompare = this.payCount - other.payCount;
        return (int) (clickCountCompare != 0 ? clickCountCompare : orderCountCompare != 0 ? orderCountCompare : payCountCompare);
    }
}
