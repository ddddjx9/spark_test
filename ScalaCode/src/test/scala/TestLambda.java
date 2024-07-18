import java.util.ArrayList;
import java.util.Collections;

public class TestLambda {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        list.forEach(System.out::println);

        list.forEach(num -> System.out.println(num * 2));
    }
}
