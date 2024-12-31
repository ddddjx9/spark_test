package cn.edu.ustb.spark.rdd.operator;

import java.io.Serializable;

/**
 * 注意在执行前需要进行序列化，否则报错：
 * java.io.NotSerializableException
 */
public class User implements Serializable, Comparable<User> {
    private final int age;
    private final int salary;

    public User(int age, int salary) {
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int compareTo(User o) {
        //this - o：升序，o - this：降序
        return this.age - o.age != 0 ? this.age - o.age : this.salary - o.salary;
    }
}