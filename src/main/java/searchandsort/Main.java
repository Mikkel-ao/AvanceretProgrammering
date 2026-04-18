package searchandsort;

import searchandsort.entities.Student;
import searchandsort.util.Factory;

import java.util.*;

public class Main {

    public static void main(String[] args) {
       //  testComplexity();
       // testLinearAndBinarySearch();
       // testBubble(10, true, true);
        testMerge(10, true, true);
       // testListOperations();
       // testSearchOperations();
       // testSetOperations();
    }

    private static void testComplexity() {
        int n = 10000; // justér for at demonstrere tydeligt

        // O(1)
        long start = System.currentTimeMillis();
        BigOExamples.constantTime(n);
        long stop = System.currentTimeMillis();
        System.out.println("Time for O(1): " + (stop - start) + " ms\n");

        // O(log n)
        start = System.currentTimeMillis();
        BigOExamples.logTime(n);
        stop = System.currentTimeMillis();
        System.out.println("Time for O(log n): " + (stop - start) + " ms\n");

        // O(n)
        start = System.currentTimeMillis();
        BigOExamples.linearTime(n);
        stop = System.currentTimeMillis();
        System.out.println("Time for O(n): " + (stop - start) + " ms\n");

        // O(n²) - pas på med at vælge et for stort n!
       /* n = 2000; // mindre n for kvadratisk!
        start = System.currentTimeMillis();
        BigOExamples.quadraticTime(n);
        stop = System.currentTimeMillis();
        System.out.println("Time for O(n²): " + (stop - start) + " ms\n");*/
    }

    private static void testLinearAndBinarySearch() {
        List<Student> students = new ArrayList<>();
        Factory.fillWithStudents(students, 100000);

        int targetId = 99999; // sidst i listen, så forskellen er tydelig!

        // Lineær søgning
        long start = System.currentTimeMillis();
        Student studentLinear = SearchExamples.linearSearch(students, targetId);
        long stop = System.currentTimeMillis();
        System.out.println("Lineær søgning fandt: " + studentLinear);
        System.out.println("Tid: " + (stop - start) + " ms");

        // Sorter listen efter ID (nødvendigt for binær søgning)
        // Hvad sorteres de studerende efter? Hvordan finder vi ud af det?
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        // Binær søgning
        start = System.currentTimeMillis();
        Student studentBinary = SearchExamples.binarySearch(students, targetId);
        stop = System.currentTimeMillis();
        System.out.println("Binær søgning fandt: " + studentBinary);
        System.out.println("Tid: " + (stop - start) + " ms");
    }

    private static void testBubble(int size, boolean time, boolean print) {
        List<Student> list = new ArrayList<>();
        Factory.fillWithStudents(list, size);
        Collections.shuffle(list);
        if (print) printList("Bubble Sort - før", list);
        long start = System.currentTimeMillis();
        SortExamples.bubbleSort(list);
        long stop = System.currentTimeMillis();
        if (print) printList("Bubble Sort - efter", list);
        if (time) System.out.println("Bubble Sort - tid: " + (stop - start) + " ms");
    }

    private static void testHeap(int size, boolean time, boolean print) {
        List<Student> list = new ArrayList<>();
        Factory.fillWithStudents(list, size);
        Collections.shuffle(list);
        if (print) printList("Heap Sort - før", list);
        long start = System.currentTimeMillis();
        SortExamples.heapSort(list);
        long stop = System.currentTimeMillis();
        if (print) printList("Heap Sort - efter", list);
        if (time) System.out.println("Heap Sort - tid: " + (stop - start) + " ms");
    }

    private static void testQuick(int size, boolean time, boolean print) {
        List<Student> list = new ArrayList<>();
        Factory.fillWithStudents(list, size);
        Collections.shuffle(list);
        if (print) printList("Quick Sort - før", list);
        long start = System.currentTimeMillis();
        SortExamples.quickSort(list, 0, list.size() - 1);
        long stop = System.currentTimeMillis();
        if (print) printList("Quick Sort - efter", list);
        if (time) System.out.println("Quick Sort - tid: " + (stop - start) + " ms");
    }

    private static void testMerge(int size, boolean time, boolean print) {
        List<Student> original = new ArrayList<>();
        Factory.fillWithStudents(original, size);
        Collections.shuffle(original);
        Student[] mergearray = original.toArray(new Student[0]);
        if (print) printArray("Merge Sort - før", mergearray);
        long start = System.currentTimeMillis();
        SortExamples.mergeSort(mergearray);
        long stop = System.currentTimeMillis();
        if (print) printArray("Merge Sort - efter", mergearray);
        if (time) System.out.println("Merge Sort - tid: " + (stop - start) + " ms");
    }

    private static void testListOperations() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();
        int size = 500000;
        int mid = size / 2;

        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        System.out.println("\n*** getMiddleElement ***");
        BigOExamples.getMiddleElement(arrayList, mid);
        BigOExamples.getMiddleElement(linkedList, mid);

        System.out.println("\n*** addMiddleElement ***");
        BigOExamples.addMiddleElement(arrayList, mid, 99);
        BigOExamples.addMiddleElement(linkedList, mid, 99);

        System.out.println("\n*** removeMiddleElement ***");
        BigOExamples.removeMiddleElement(arrayList, mid);
        BigOExamples.removeMiddleElement(linkedList, mid);
    }

    private static void testSearchOperations() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        HashSet<Integer> hashSet = new HashSet<>();
        int size = 500000;

        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            hashSet.add(i);
        }

        int existingElement = 499999;
        int missingElement = 500001;

        System.out.println("\n*** contains - element der findes ***");
        BigOExamples.containsElement(arrayList, existingElement);
        BigOExamples.containsElement(hashSet, existingElement);

        System.out.println("\n*** contains - element der ikke findes ***");
        BigOExamples.containsElement(arrayList, missingElement);
        BigOExamples.containsElement(hashSet, missingElement);
    }

    private static void testSetOperations() {
        TreeSet<Student> treeSet = new TreeSet<>();
        HashSet<Student> hashSet = new HashSet<>();

        int size = 500000;

        for (int i = 0; i < size; i++) {
            Student s = new Student("S" + i, i);
            treeSet.add(s);
            hashSet.add(s);
        }

        Student target = new Student("S250000", 250000);

        System.out.println("\n*** contains ***");
        BigOExamples.containsStudent(treeSet, target); // O(log n)
        BigOExamples.containsStudent(hashSet, target); // O(1)

        System.out.println("\n*** add ***");
        BigOExamples.addStudent(treeSet, new Student("NEW", 999999)); // O(log n)
        BigOExamples.addStudent(hashSet, new Student("NEW", 999999)); // O(1)

        System.out.println("\n*** remove ***");
        BigOExamples.removeStudent(treeSet, target); // O(log n)
        BigOExamples.removeStudent(hashSet, target); // O(1)
    }

    // Hjælpemetoder til udskrift
    private static void printList(String label, List<Student> list) {
        System.out.println(label + ": ");
        for(Student s:list){
            System.out.println(s);
        }
    }

    private static void printArray(String label, Student[] arr) {
        System.out.println(label + ": ");
        for(Student s:arr){
            System.out.println(s);
        }
    }



}
