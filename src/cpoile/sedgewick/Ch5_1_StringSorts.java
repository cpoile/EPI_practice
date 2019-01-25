package cpoile.sedgewick;

import java.util.Arrays;

public class Ch5_1_StringSorts {
    public static void main() {
        String[] input = {"4PGC938", "2IYE230", "3CIO720", "1ICK750", "1OHV845", "4JZY524", "1ICK750", "3CIO720", "1OHV845", "1OHV845", "2RLA629", "2RLA629", "3ATW723"};
        String[] expected = {"1ICK750", "1ICK750", "1OHV845", "1OHV845", "1OHV845", "2IYE230", "2RLA629", "2RLA629", "3ATW723", "3CIO720", "3CIO720", "4JZY524", "4PGC938"};

        String[] copy = Arrays.copyOf(input, input.length);
        LSDSort(copy, copy[0].length());

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
