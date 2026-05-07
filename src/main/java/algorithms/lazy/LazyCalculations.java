package algorithms.lazy;

import java.util.HashMap;
import java.util.Map;

public class LazyCalculations {

    static Map<Integer, Long> memo = new HashMap<>();
    static Map<Integer, Long> eagerMemo = new HashMap<>();

    static int lazyCounter = 0;
    static int eagerInitCounter = 0;
    static int eagerCounter = 0;

    public static long lazyFactorial(int n) {

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        lazyCounter++;

        long result;

        if (n == 0 || n == 1) {
            result = 1;
        } else {
            result = n * lazyFactorial(n - 1);
        }

        memo.put(n, result);
        return result;
    }

    public static long lazySum(int n) {
        long result = 0;

        for (int i = 1; i <= n; i++) {
            result += lazyFactorial(i);
        }

        return result;
    }

    public static void init(int max) {

        long result = 1;

        eagerMemo.put(0, 1L);

        for (int i = 1; i <= max; i++) {

            eagerInitCounter++;

            result *= i;

            eagerMemo.put(i, result);
        }
    }

    public static long eagerFactorial(int n) {
        return eagerMemo.get(n);
    }

    public static long eagerSum(int n) {
        long result = 0;

        for (int i = 1; i <= n; i++) {
            result += eagerFactorial(i);
            eagerCounter++;
        }
        return result;
    }

    public static void main(String[] args) {

        int n = 5;

        System.out.println("---- LAZY ----");

        long lazyStart = System.nanoTime();

        System.out.println(lazyFactorial(n));
        System.out.println(lazyFactorial(n - 1));
        System.out.println(lazySum(n));

        long lazyEnd = System.nanoTime();

        System.out.println("Lazy calculations: " + lazyCounter);
        System.out.println("Lazy time: " + (lazyEnd - lazyStart) + " ns");

        System.out.println("-------------------------");

        System.out.println("---- EAGER ----");

        long eagerStart = System.nanoTime();

        init(100); // Forberedelse

        System.out.println(eagerFactorial(n));
        System.out.println(eagerSum(n));

        long eagerEnd = System.nanoTime();

        System.out.println("Eager calculations: " + eagerCounter);
        System.out.println("Eager calculations (init only): " + eagerInitCounter);
        System.out.println("Eager time: " + (eagerEnd - eagerStart) + " ns");
    }
}