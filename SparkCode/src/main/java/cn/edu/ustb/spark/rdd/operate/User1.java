package cn.edu.ustb.spark.rdd.operate;

public class User1 {
    private int age;
    private String name;

    public User1() {
    }

    public User1(int age, String name) {
        this.age = age;
        this.name = name;
    }

    /**
     * 获取
     *
     * @return age 返回age
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置age
     *
     * @param age 传入具体的age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 获取name
     *
     * @return name 返回name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name
     *
     * @param name 传入具体的name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "User{age = " + age + ", name = " + name + "}";
    }
}