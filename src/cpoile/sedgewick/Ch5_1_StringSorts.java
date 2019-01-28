package cpoile.sedgewick;

import java.util.Arrays;

public class Ch5_1_StringSorts {
    public static void main() {
        String[] input = {"4PGC938", "2IYE230", "3CIO720", "1ICK750", "1OHV845", "4JZY524", "1ICK750", "3CIO720", "1OHV845", "1OHV845", "2RLA629", "2RLA629", "3ATW723"};
        String[] expected = {"1ICK750", "1ICK750", "1OHV845", "1OHV845", "1OHV845", "2IYE230", "2RLA629", "2RLA629", "3ATW723", "3CIO720", "3CIO720", "4JZY524", "4PGC938"};

        String[] copy = Arrays.copyOf(input, input.length);
        LSDSort(copy, copy[0].length());

        assert (Arrays.equals(copy, expected));

        copy = Arrays.copyOf(input, input.length);
        MSDSort.sort(copy);

        assert (Arrays.equals(copy, expected));
    }

    public static void LSDSort(String[] a, int W) {
        int N = a.length;
        int R = 128;
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            // Compute frequency counts
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }
            // Transform counts to indices
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }
            // Distribute
            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            // Copy back
            System.arraycopy(aux, 0, a, 0, N);
        }
    }
}

class MSDSort {
    private static int M = 10;   // change to invoke the insertion sort
    private static int R = 256;
    private static int N;
    private static String[] aux;

    private static int charAt(String a, int d) {
        if (d < a.length()) return a.charAt(d);
        else return -1;
    }

    public static void sort(String[] a) {
        N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo + M) {
            insertionSort(a, lo, hi, d);
            return;
        }
        int[] count = new int[R + 2];
        // Compute counts
        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }
        // Translate counts into indices
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }
        // Distribute
        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }
        // Copy back
        System.arraycopy(aux, 0, a, lo, hi - lo + 1);

        // recurse
        for (int r = 0; r < R; r++) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }

    private static void insertionSort(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    private static boolean less(String a, String b, int d) {
        return a.substring(d).compareTo(b.substring(d)) < 0;
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
