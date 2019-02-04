package cpoile.sedgewick;

public class Ch5_3_SubstringSearch {
    public static void main() {
        String text = "ABABBAFDABRA";

        assert (bfSearch("ABRA", text) == 8);
        assert (bfSearch("ABRAK", text) == text.length());

        text = "AABRAACADABRAACAADABRA";
        String pat = "AACAA";
        System.out.println("Text:    " + text);
        int loc = KMP(pat, text);
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
}
