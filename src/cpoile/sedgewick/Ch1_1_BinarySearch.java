package cpoile.sedgewick;

import java.util.Arrays;

public class Ch1_1_BinarySearch {
    public static void main() {
        int[] a = {1, 3, 4, 5, 6, 7};

        System.out.println("Array: " + Arrays.toString(a));
        for (int i = 0; i <= 7; i++) {
            System.out.println(i + "'s position: " + rank(a, i));
        }
    }
    private static int rank(int[] a, int i) {
        int lo = 0;
        int hi = a.length-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (i < a[mid]) hi = mid-1;
            else if (i > a[mid]) lo = mid+1;
            else return mid;
        }
        return -1;
    }
}
