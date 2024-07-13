package cn.edu.ustb.spark.sparkSQL.requirement;

import java.io.Serializable;
import java.util.Map;

public class MyCityRemarkBuffer implements Serializable {
    //总点击数
    private Long count;
    //各个市的点击数
    private Map<String,Long> cityMap;

    public MyCityRemarkBuffer() {
    }

    public MyCityRemarkBuffer(Long count, Map<String, Long> cityMap) {
        this.count = count;
        this.cityMap = cityMap;
    }

    /**
     * 获取总点击数
     * @return 返回点击数count
     */
    public Long getCount() {
        return count;
    }

    /**
     * 设置点击数
     * @param count 点击数
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * 获取城市对应的点击数的集合
     * @return 返回集合cityMap
     */
    public Map<String, Long> getCityMap() {
        return cityMap;
    }

    /**
     * 设置城市对应的点击数的集合
     * @param cityMap 城市对应的点击数的集合
     */
    public void setCityMap(Map<String, Long> cityMap) {
        this.cityMap = cityMap;
    }

    public String toString() {
        return "MyCityRemarkBuffer{count = " + count + ", cityMap = " + cityMap + "}";
    }
}
