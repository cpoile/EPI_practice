package cpoile.sedgewick;

public class Ch5_3_SubstringSearch {
    public static void main() {
        String text = "ABABBAFDABRA";

        assert (bfSearch("ABRA", text) == 8);
        assert (bfSearch("ABRAK", text) == text.length());

        text = "ZACAAACADABRAACAADABRA";
        String pat = "AACAA";
        System.out.println("Text:    " + text);
        int loc = KMP(pat, text);
        System.out.println("pattern: " + new String(new char[loc]).replace("\0", " ") + pat);

        loc = BoyerMoore(pat, text);
        System.out.println("pattern: " + new String(new char[loc]).replace("\0", " ") + pat);
    }

    private static int bfSearch(String pat, String text) {
        int M = pat.length();
        int N = text.length();
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (text.charAt(i + j) != pat.charAt(j))
                    break;
            }
            if (j == M) return i;
        }
        return N;
    }

    private static int KMP(String pat, String text) {
        int R = 128;
        int M = pat.length(), N = text.length();
        int[][] dfa = new int[R][M];

        // build dfa
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (char c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];       // copy mismatched cases
            }
            dfa[pat.charAt(j)][j] = j + 1;   // overwrite matched case

            X = dfa[pat.charAt(j)][X];       // set lookback/restart state
        }

        // search the text
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[text.charAt(i)][j];
        }
        if (j == M) return i - M;          // found (hit end of pattern)
        return i;                          // not found (hit end of text)
    }

    private static int BoyerMoore(String pat, String text) {
        int N = text.length(), M = pat.length(), R = 128;
        int[] right = new int[R];

        // compute skip table: -1 for chars not in pattern
        for (int r = 0; r < R; r++) {
            right[r] = -1;
        }
        // rightmost position for characters in pattern
        for (int j = 0; j < M; j++) {
            right[pat.charAt(j)] = j;
        }

        int skip = 0;
        // does the text match the pattern at pos i ?
        for (int i = 0; i <= N-M; i += skip) {
            int j;
            for (j = M-1; j >= 0; j--) {
                if (text.charAt(i+j) != pat.charAt(j)) {
                    skip = Math.max(1, j - right[text.charAt(i+j)]);
                    break;
                }
            }
            if (j == -1) return i;  // found
            // or set skip = 0 in the loop and test against it
        }
        return N;
    }
}
