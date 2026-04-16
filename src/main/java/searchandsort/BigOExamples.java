package searchandsort;

import searchandsort.entities.Student;

import java.util.*;

public class BigOExamples {
    public static void main(String[] args) {
        int n = 100000;
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        System.out.println("O(1) - Konstant tid:");
        // constantTime(n);

        System.out.println("\nO(log n) - Logaritmisk tid:");
        // logTime(n);

        System.out.println("\nO(n) - Lineær tid:");
        // linearTime(n);

        System.out.println("\nO(n^2) - Kvadratisk tid:");
        // quadraticTime(n);

        System.out.println("\nO(1) - Første element:");
        firstElement(array);

        System.out.println("\nO(n) - Alle elementer:");
        allElements(array);

        System.out.println("\nO(log n) - Halvering:");
        halveSize(array);

        System.out.println("\nO(log n) - Find element:");
        findElement(array, 7);
    }

    // O(1) - Konstant tid
    public static void constantTime(int n) {
        System.out.println("Jeg printer altid én gang, uanset n.");
    }

    // O(log n) - Logaritmisk tid (Binær nedtælling)
    public static void logTime(int n) {
        for (int i = n; i > 1; i /= 2) {
            System.out.println("Jeg kører log n gange, n er nu: " + i);
        }
    }

    // O(n) - Lineær tid
    public static void linearTime(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("Itererer: " + i);
        }
    }

    // O(n^2) - Kvadratisk tid
    public static void quadraticTime(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("Kvadratisk iteration: " + i + "," + j);
            }
        }

    }

    // O(1)
    public static void firstElement(int array[]) {
        System.out.println("Jeg printer altid første element af et array " + array[0]);
    }

    // O(n)
    public static void allElements(int array[]) {
        for(int i = 0; i < array.length; i++) {
            System.out.println("Jeg printer alle elementer af et arrray " + array[i]);
        }
    }

    // O(log n)
    public static void halveSize(int array[]) {
        for (int i = array.length; i > 1; i /= 2) {
            System.out.println("Jeg halverer størrelsen, den er nu: " + i);
        }
    }

    // O(log n)
    public static void findElement(int array[], int target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (array[mid] == target) {
                System.out.println("Jeg fandt elementet " + target + " ved index " + mid);
                return;
            } else if (array[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Elementet " + target + " findes ikke i arrayet");
    }

    // get — hent elementet ved givet index
    public static void getMiddleElement(List<Integer> list, int index) {
        long start = System.currentTimeMillis();
        list.get(index);
        long stop = System.currentTimeMillis();
        System.out.println(list.getClass().getSimpleName() + " get - tid: " + (stop - start) + " ms");
    }

    // add — indsæt et element ved givet index
    public static void addMiddleElement(List<Integer> list, int index, int value) {
        long start = System.currentTimeMillis();
        list.add(index, value);
        long stop = System.currentTimeMillis();
        System.out.println(list.getClass().getSimpleName() + " add - tid: " + (stop - start) + " ms");
    }

    // remove — slet elementet ved givet index
    public static void removeMiddleElement(List<Integer> list, int index) {
        long start = System.currentTimeMillis();
        list.remove(index);
        long stop = System.currentTimeMillis();
        System.out.println(list.getClass().getSimpleName() + " remove - tid: " + (stop - start) + " ms");
    }

    // contains — søg efter element
    public static void containsElement(Collection<Integer> collection, int value) {
        long start = System.currentTimeMillis();
        collection.contains(value);
        long stop = System.currentTimeMillis();
        System.out.println(collection.getClass().getSimpleName() + " contains(" + value + ") - tid: " + (stop - start) + " ms");
    }

    // add — indsæt element
    public static void addStudent(Collection<Student> collection, Student s) {
        long start = System.nanoTime();
        collection.add(s);
        long stop = System.nanoTime();

        double timeMs = (stop - start) / 1000000.0;
        System.out.println(collection.getClass().getSimpleName() + " add - tid: " + timeMs + " ms");
    }

    // remove — slet element
    public static void removeStudent(Collection<Student> collection, Student s) {
        long start = System.nanoTime();
        collection.remove(s);
        long stop = System.nanoTime();

        double timeMs = (stop - start) / 1000000.0;
        System.out.println(collection.getClass().getSimpleName() + " remove - tid: " + timeMs + " ms");
    }

    // contains — find element
    public static void containsStudent(Collection<Student> collection, Student s) {
        long start = System.nanoTime();
        collection.contains(s);
        long stop = System.nanoTime();

        double timeMs = (stop - start) / 1000000.0;
        System.out.println(collection.getClass().getSimpleName() + " contains - tid: " + timeMs + " ms");
    }


}