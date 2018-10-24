package cpoile.sedgewick;

import java.util.*;

public class Ch2_1_Sorts {
    public static void main() {
        // assume the input is coming in through standard in.
        Scanner scan = new Scanner(System.in);
        List<String> in = new ArrayList<>();
        while (scan.hasNext())
            in.add(scan.next());
        String[] words = in.toArray(new String[0]);

        Sort.show(words);
        Sort.sort(words);
        System.out.println("Sorted: " + Sort.isSorted(words));
        Sort.show(words);
    }
}

class Sort {
    static void sort(Comparable[] a) {
        //selectionSort(a);
        //insertionSort(a);
        //shellSort(a);
        //mergeSort(a);
        bottomUpMergeSort(a);
        //quickSort(a);
        //quick3WaySort(a);
    }

    public static void selectionSort(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[min]))
                    min = j;
            }
            if (min != i)
                exch(a, i, min);
        }
    }

    public static void insertionSort(Comparable[] a) {
        // i is the current elem we're inserting into its correct spot
        for (int i = 1; i < a.length; i++) {
            // j is the current position of elem as we try to find its spot
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
                show(a, j - 1, COLOUR.RED);
            }
        }
    }

    public static void shellSort2(Comparable[] a) {
        // using the initial shell width of 1/2(3^k - 1)
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;   // 1, 4, 13, 40, ...
        while (h >= 1) {
            System.out.println("h = " + h);
            for (int i = h; i < N; i++) {
                for (int j = i; j - h >= 0 && less(a[j], a[j - h]); j -= h) {
                    show(a, j, COLOUR.BLUE);
                    exch(a, j, j - h);
                    show(a, j - h, COLOUR.RED);
                }
            }
            h /= 3;
        }
    }

    private static void shellSort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1;   // 1, 4, 13, 40, ...
        while (h >= 1) {
            // h sort the array
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h; j -= h) {
                     if (less(a[j], a[j - h])) exch(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    // used for the mergeSort:
    private static Comparable[] aux;

    static void mergeSort(Comparable[] a) {
        aux = new Comparable[a.length];
        mergeSort(a, 0, a.length - 1);
    }

    private static void mergeSort(Comparable[] a, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        if (lo < hi - 1) {
            mergeSort(a, lo, mid);
            mergeSort(a, mid + 1, hi);
        }
        System.out.println("merge (" + lo + ", " + mid + ", " + hi + ")");
        merge(a, lo, mid, hi);
    }

    private static void merge(Comparable[] a, int lo, int mid, int hi) {
        //for (int i = lo; i <= hi; i++)
        //    aux[i] = a[i];
        // or:
        System.arraycopy(a, lo, aux, lo, hi-lo+1);
        int i = lo;
        int j = mid + 1;  // counter for second half
        for (int k = i; k <= hi; k++) {
            if       (i > mid)              a[k] = aux[j++];  // finished first half
            else if  (j > hi)               a[k] = aux[i++]; // finished second half
            else if  (less(aux[i], aux[j])) a[k] = aux[i++];
            else                            a[k] = aux[j++];
        }
    }

    private static void bottomUpMergeSort(Comparable[] a) {
        aux = new Comparable[a.length];
        int sz = 1;
        while (sz < a.length) {
            for (int lo = 0; lo < a.length - sz; lo += sz * 2) {
                int mid = lo + sz - 1;
                int hi = Math.min(a.length - 1, lo + sz * 2 - 1);
                merge(a, lo, mid, hi);
            }
            sz *= 2;
        }
    }


    private static void quickSort(Comparable[] a) {
        StdRandom.shuffle(a);
        quickSort(a, 0, a.length-1);
    }

    private static void quickSort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        quickSort(a, lo, j-1);
        quickSort(a, j+1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        Comparable v = a[lo];
        int i = lo;
        int j = hi +1;
        while (true) {
            while (less(v, a[--j])) if (j == lo) break;
            while (less(a[++i], v)) if (i == hi) break;
            if (j <= i) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static void quick3WaySort(Comparable[] a) {
        StdRandom.shuffle(a);
        quick3WaySort(a, 0, a.length-1);
    }
    private static void quick3WaySort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi, i = lo+1;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0)  exch(a, lt++, i++);
            else if (cmp > 0)  exch(a, i, gt--);
            else               i++;
        }
        quick3WaySort(a, lo, lt-1);
        quick3WaySort(a, gt+1, hi);
    }

    // Supporting functions
    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int x, int y) {
        Comparable tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }

    static void show(Comparable[] a) {
        for (Comparable i : a)
            System.out.print(i + " ");
        System.out.println();
    }

    enum COLOUR {RED, BLUE}

    private static void show(Comparable[] a, int j, COLOUR colour) {
        String c = (char) 27 + "[39m";
        if (colour == COLOUR.RED)
            c = (char) 27 + "[31m";
        else if (colour == COLOUR.BLUE)
            c = (char) 27 + "[34m";

        for (int i = 0; i < a.length; i++) {
            if (i == j)
                System.out.print(c + a[i] + (char) 27 + "[39m ");
            else
                System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    private static void quickSort1(Comparable[] a) {
        StdRandom.shuffle(a);
        quickSort(a, 0, a.length-1);
    }
    private static void quickSort1(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        quickSort(a, 0, j - 1);
        quickSort(a, j + 1, hi);
    }
    private static int partition1(Comparable[] a, int lo, int hi) {
        Comparable v = a[lo];
        int i = lo, j = hi+1;
        while (true) {
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }
    static boolean isSorted(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (less(a[i + 1], a[i])) return false;
        }
        return true;
    }
}
