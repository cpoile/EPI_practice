package cpoile.HackerRank;

import java.util.Scanner;

public class OneDArrayPart2 {
    public static boolean canWin(int leap, int[] game) {
        return (canSolveFrom(leap, game, 1) || canSolveFrom(leap, game, leap));
    }

    private static boolean canSolveFrom(int leap, int[] game, int pos) {
        if (pos > game.length)
            return true;
        else if (pos < 1)
            return false;
        else if (game[pos - 1] == 1)
            return false;

        // record that we've been here
        game[pos-1] = 1;
        return (canSolveFrom(leap, game, pos + leap) ||
                canSolveFrom(leap, game, pos + 1) ||
                canSolveFrom(leap, game, pos - 1));
    }

    public static void main() {
        Scanner scan = new Scanner(System.in);
        int q = scan.nextInt();
        while (q-- > 0) {
            int n = scan.nextInt();
            int leap = scan.nextInt();

            int[] game = new int[n];
            for (int i = 0; i < n; i++) {
                game[i] = scan.nextInt();
            }

            System.out.println((canWin(leap, game)) ? "YES" : "NO");
        }
        scan.close();
    }
}
