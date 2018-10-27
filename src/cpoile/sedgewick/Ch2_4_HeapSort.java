package cpoile.sedgewick;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ch2_4_HeapSort {
    public static void main() {
        // assume the input is coming in through standard in.
        Scanner scan = new Scanner(System.in);
        List<String> in = new ArrayList<>();
        while (scan.hasNext())
            in.add(scan.next());
        String[] words = in.toArray(new String[0]);

        Sort.show(words);
        HeapSort.sort(words);
        System.out.println("Heap Sorted: " + Sort.isSorted(words));
        Sort.show(words);
    }
}

class HeapSort {
    static void sort(Comparable[] a) {
        int N = a.length;
        for (int k = N / 2; k >= 1; k--) {
            sink(a, k, N);
        }
        while (N > 1) {
            exch(a, 1, N--);
            sink(a, 1, N);
        }
    }
    private static void sink(Comparable[] a, int k, int N) {
        if (k * 2 > N) return;
        int j = k * 2;
        if (j < N && less(a, j, j + 1)) j++;
        if (less(a, k, j)) {
            exch(a, k, j);
            sink(a, j, N);
        }
    }
    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = swap;
    }
    private static boolean less(Comparable[] a, int i, int j) {
        return a[i-1].compareTo(a[j-1]) < 0;
    }
}
