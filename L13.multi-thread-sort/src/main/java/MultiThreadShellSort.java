import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Multi-thread sort by Shell method
 *
 * @param <T>
 * @link https://en.wikipedia.org/wiki/Shellsort
 */
public class MultiThreadShellSort<T extends Number> {

    private static final int MAX_COUNT_THREADS = 4;

    private Comparator<T> comparator;

    public MultiThreadShellSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] arr) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_COUNT_THREADS);

        List<Callable<Void>> tasks = new ArrayList<>();
        int increment = arr.length / 2;
        while (increment >= 1) {
            for (int startIndex = 0; startIndex < increment; startIndex++) {
                tasks.add(new InsertionSort(arr, startIndex, increment));
            }

            try {
                executor.invokeAll(tasks);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            increment = increment / 2;
            tasks.clear();
        }

        executor.shutdown();
    }


    /**
     * @link https://en.wikipedia.org/wiki/Insertion_sort
     */
    private class InsertionSort implements Callable<Void> {

        private T[] arr;
        private int startIndex;
        private int increment;

        InsertionSort(T[] arr, int startIndex, int increment) {
            this.arr = arr;
            this.startIndex = startIndex;
            this.increment = increment;
        }

        @Override
        public Void call() {
            for (int i = startIndex; (i + increment) < arr.length; i +=  increment) {
                for (int j = (i + increment); (j - increment) >= 0; j -= increment) {
                    if (comparator.compare(arr[j - increment], arr[j]) > 0) {
                        T tmp = arr[j];
                        arr[j] = arr[j - increment];
                        arr[j - increment] = tmp;
                    } else {
                        break;
                    }
                }
            }
            return null;
        }
    }
}
