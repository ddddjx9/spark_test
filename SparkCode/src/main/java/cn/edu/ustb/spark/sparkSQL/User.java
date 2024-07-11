package cn.edu.ustb.spark.sparkSQL;

import java.io.Serializable;
import java.math.BigInteger;

public class User implements Serializable {
    private BigInteger id;
    private BigInteger age;
    private String name;


    public User() {
    }

    public User(BigInteger id, BigInteger age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    /**
     * 获取
     *
     * @return id
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return age
     */
    public BigInteger getAge() {
        return age;
    }

    /**
     * 设置
     *
     * @param age
     */
    public void setAge(BigInteger age) {
        this.age = age;
    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "User{id = " + id + ", age = " + age + ", name = " + name + "}";
    }
}