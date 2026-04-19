package searchandsort;

import searchandsort.entities.Student;

import java.util.*;

public class SortExamples {

    public static void bubbleSort(List<Student> students) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).getId() > students.get(j + 1).getId()) {
                    Collections.swap(students, j, j + 1);
                }
            }
        }
    }

    public static void arrayBubbleSort(int[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) { // // Kør n-1 gange
            boolean swapped = false;

            // Sammenlign naboelementer, ignorer de allerede sorterede til sidst
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1); // Byt om hvis forkert rækkefølge
                    swapped = true;
                }
            }
            if (!swapped) break; // Hvis array allerede er sorteret, break.
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void heapSort(List<Student> students) {
        PriorityQueue<Student> heap = new PriorityQueue<>(Comparator.comparingInt(Student::getId));
        heap.addAll(students);

        students.clear();
        while (!heap.isEmpty()) {
            students.add(heap.poll());
        }
    }

    // rekursiv metode
    public static void quickSort(List<Student> students, int low, int high) {
        // base case - vi hopper ud af rekursion hvis low er >=  high
        if (low < high) {
            int pivot = partition(students, low, high);
            quickSort(students, low, pivot - 1);
            quickSort(students, pivot + 1, high);
        }
    }

    private static int partition(List<Student> students, int low, int high) {
        int pivotValue = students.get(high).getId();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (students.get(j).getId() <= pivotValue) {
                i++;
                Collections.swap(students, i, j);
            }
        }
        Collections.swap(students, i + 1, high);
        return i + 1;
    }

    // Rekursiv quicksort - sorterer array[low..high] ved at partitionere og sortere hver halvdel
    public static void arrayQuickSort(int[] array, int low, int high) {
        // Basecasen - stop rekursionen når der kun er et element tilbage
        if (low < high) {
            // Find pivotens endelige placering
            int pivot = arrayPartition(array, low, high);
            // Sorter elementerne til venstre for pivot
            arrayQuickSort(array, low, pivot - 1);
            // Sorter elementerne til højre for pivot
            arrayQuickSort(array, pivot + 1, high);
        }
    }

    // Vælger det sidste element som pivot, placerer mindre elementer til venstre & større til højre.
    private static int arrayPartition(int[] array, int low, int high) {
        int pivotValue = array[high]; // Brug det sidste element som pivot
        int i = low - 1; // i peger på det sidste mindre element

        for (int j = low; j < high; j++) {
            // Hvis current element er mindre end eller lig med pivot, byt det ind i venstre side
            if (array[j] <= pivotValue) {
                i++;
                swap(array, i, j);
            }
        }
        // Placér pivot på sin korrekte position mellem de to halvdele
        swap(array, i + 1, high);
        return i + 1; // Return pivotens endelige index
    }

    public static void mergeSort(Student[] students){
        // Hvis der er 1 element tilbage kan vi ikke sortere mere
        // Dette er metoden base case
        if (students.length < 2)
            return;
        // Vi finder midten af students arrayet
        int middle = students.length / 2;
        // Vi laver to sub-arrays som hver er halvdelen af student-arrayet langt
        Student[] lefthalf = new Student[middle];
        Student[] righthalf = new Student[students.length - middle];

        // Vi kopierer første halvdel af students arrayet ind i lefthalf
        for(int i = 0; i < middle; i++){
            lefthalf[i] = students[i];
        }

        // Og anden halvdel ind i righthalf
        for(int i = middle; i < students.length; i++){
            righthalf[i - middle] = students[i];
        }

        /*
        Vi kunne erstatte forloops med
        System.arraycopy(students, 0, lefthalf, 0, middle);
        System.arraycopy(students, middle, righthalf, 0, students.length - middle);
         */

        // Vi kalder metoden rekursivt med de to arrays
        mergeSort(lefthalf);
        mergeSort(righthalf);

        // Vi merger de to sorterede halvdele
        merge(students, lefthalf, righthalf);
    }

    private static void merge(Student[] input, Student[] left, Student[] right){
        // Tre hjælpevariable der skal bruges som pointere i hver deres array
        int i = 0, l = 0, r = 0;
        // Så længe der er elementer i både venstre og højre array, som ikke er
        // kopieret til input-array, så fortsætter vi sortering
        while (l < left.length && r < right.length){
            if (left[l].getId() <= right[r].getId()){
                input[i] = left[l];
                l++;
                i++;
            } else {
                input[i] = right[r];
                r++;
                i++;
            }
        }
        // Når der ikke er flere elementer i enten left eller right, lægges de
        // resterende elementer i input
        while(l < left.length){
            input[i] = left[l];
            l++;
            i++;
        }
        while(r < right.length){
            input[i] = right[r];
            r++;
            i++;
        }
    }

    public static void mergeSortList(List<Student> students) {
        if (students.size() > 1) {
            int mid = students.size() / 2;
            List<Student> left = new ArrayList<>(students.subList(0, mid));
            List<Student> right = new ArrayList<>(students.subList(mid, students.size()));

            mergeSortList(left);
            mergeSortList(right);

            mergeList(students, left, right);
        }
    }

    private static void mergeList(List<Student> students, List<Student> left, List<Student> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getId() <= right.get(j).getId()) {
                students.set(k++, left.get(i++));
            } else {
                students.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            students.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            students.set(k++, right.get(j++));
        }
    }

    private static void arrayMergeSort(int[] array) {
        if (array.length < 2)
            return;

        int middle = array.length / 2;

        // Del arrayet i to halvdele
        int[] left = new int[middle];
        int[] right = new int[array.length - middle];

        // Kopier data ind i hver halvdel
        for (int i = 0; i < middle; i++)
            left[i] = array[i];
        for (int i = middle; i < array.length; i++)
            right[i - middle] = array[i];

        // Sorter hver halvdel rekursivt
        arrayMergeSort(left);
        arrayMergeSort(right);

        // Flet de sorterede halvdele tilbage i det originale array
        arrayMerge(array, left, right);
    }

    private static void arrayMerge(int[] input, int[] left, int[] right) {
        int i = 0, l = 0, r = 0;

        // Sammenlign elementer fra left og right og læg det mindste i input
        while (l < left.length && r < right.length) {
            if (left[l] <= right[r])
                input[i++] = left[l++];
            else
                input[i++] = right[r++];
        }

        // Kopier resterende elementer fra left og right
        while (l < left.length)
            input[i++] = left[l++];

        while (r < right.length)
            input[i++] = right[r++];
    }





}
