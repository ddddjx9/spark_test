public class TestBreak {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 5; i++) {
                if (i == 3) {
                    //抛出异常，什么都不做，可以让程序在循环中断
                    //使用异常的捕捉，就能让循环下面的代码正常执行
                    throw new RuntimeException("退出循环");
                }
                System.out.println(i);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println("完毕！");
    }
}
