package cpoile.sedgewick;

public class Ch5_3_SubstringSearch {
    public static void main() {
        String text = "ABABBAFDABRA";

        assert(bfSearch("ABRA", text) == 8);
        assert(bfSearch("ABRAK", text) == text.length());
    }

    private static int bfSearch(String pat, String text) {
        int M = pat.length();
        int N = text.length();
        for (int i = 0; i <= N-M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (text.charAt(i+j) != pat.charAt(j))
                    break;
            }
            if (j == M) return i;
        }
        return N;
    }
}
