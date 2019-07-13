import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //init
        final int size = 100;
        Random random = new Random();
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 10);
        }

        System.out.println("Before sort");
        System.out.println(Arrays.asList(arr));

        //sort
        MultiThreadShellSort<Integer> sortService = new MultiThreadShellSort<>(Integer::compare);
        sortService.sort(arr);

        System.out.println("After sort");
        System.out.println(Arrays.asList(arr));
    }
}
