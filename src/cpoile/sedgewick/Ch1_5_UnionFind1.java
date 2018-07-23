package cpoile.sedgewick;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Ch1_5_UnionFind1 {
    private int[] grid, treeSize;
    private int count;
    Ch1_5_UnionFind1(int N) {
        this.count = N;
        grid = new int[N];
        treeSize = new int[N];
        for (int i = 0; i < N; i++) {
            grid[i] = i;
            treeSize[i] = 1;
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
    public int count() {
        return count;
    }

//    // quick-find implementation:
//    public boolean union(int p, int q) {
//        int pid = find(p);
//        int qid = find(q);
//        if (pid == qid)
//            return false;
//
//        for (int i = 0; i < grid.length; i++) {
//            if (grid[i] == qid)
//                grid[i] = pid;
//        }
//        count--;
//        return true;
//    }
//    public int find(int p) {
//        return grid[p];
//    }

//    // quick-union implementation:
//    public boolean union(int p, int q) {
//        int pParent = find(p);
//        int qParent = find(q);
//        if (pParent == qParent)
//            return false;
//
//        grid[qParent] = pParent;
//        count--;
//        return true;
//    }
//
//    public int find(int p) {
//        int cur = grid[p];
//        while (grid[cur] != cur)
//            cur = grid[cur];
//        return cur;
//    }

    // weighted-union implementation:
    public boolean union(int p, int q) {
        int pParent = find(p);
        int qParent = find(q);
        if (pParent == qParent)
            return false;

        if (treeSize[qParent] < treeSize[pParent]) {
            grid[qParent] = grid[pParent];
            treeSize[pParent] += treeSize[qParent];
        } else {
            grid[pParent] = grid[qParent];
            treeSize[qParent] += treeSize[pParent];
        }
//        int smaller = treeSize[qParent] < treeSize[pParent] ? qParent : pParent;
//        int larger = treeSize[qParent] < treeSize[pParent] ? pParent : qParent;
//        grid[smaller] = larger;
//
//        treeSize[larger] = treeSize[larger] > treeSize[smaller] + 1 ? treeSize[larger] : treeSize[smaller] + 1;
        count--;
        return true;
    }

    public int find(int p) {
        int cur = grid[p];
        while (grid[cur] != cur)
            cur = grid[cur];
        return cur;
    }



    public static void main(String[] arghs) throws FileNotFoundException {
        Scanner in = new Scanner(new File(arghs[0]));

        long start = System.currentTimeMillis();

        Ch1_5_UnionFind1 UF = new Ch1_5_UnionFind1(in.nextInt());
        while (in.hasNext()) {
            int p = in.nextInt();
            int q = in.nextInt();
//            if (UF.union(p, q))
//                System.out.println(p + " " + q);
            UF.union(p, q);
        }

        long end = System.currentTimeMillis();
        System.out.printf("Time: %.3f seconds\n", (end - start)/1000.0);
    }
}
