package cpoile.sedgewick;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Ch1_5_UnionFind2 {
    int[] id;
    int[] sz;
    int[] max;
    int N;

    public static void main(String file) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(file));
        int n = scan.nextInt();
        Ch1_5_UnionFind2 uf = new Ch1_5_UnionFind2(n);

        while (scan.hasNext()) {
            int p = scan.nextInt();
            int q = scan.nextInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                System.out.println(p + " " + q);
            }
        }

        System.out.println(Arrays.toString(uf.id));
        System.out.println(Arrays.toString(uf.sz));

        System.out.println("Now doing the deletion example problem.");
        uf = new Ch1_5_UnionFind2(10);
        int[] tests = {3, 6, 5, 7, 4, 2, 0, 1};
        System.out.println("Should print:\n4 7 7 8 8 8 1 8");
        for (int t : tests) {
            System.out.print(uf.delete(t) + " ");
        }
    }

    private Ch1_5_UnionFind2(int n) {
        N = n;
        id = new int[n];
        sz = new int[n];
        max = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
            max[i] = i;
        }
    }

    private void union(int p, int q) {
        // weighted quick union
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        int m = Math.max(max[i], max[j]);
        max[i] = m;
        max[j] = m;
    }
    private void unionV2(int p, int q) {
        // quick union without weighting
        id[root(p)] = root(q);
    }

    private void unionV1(int p, int q) {
        // union, slowly (V1)
        int old = id[p];
        for (int i = 0; i < N; i++)
            if (id[i] == old)
                id[i] = id[q];
    }

    private int root(int i) {
        // path compression.
        // Two pass: could go through a second loop and set all children's parent to the root.
        // or, One pass: for every id who's parent isn't root, set it's parent to it's parent's parent.
        while (id[i] != i) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    private int rootV1(int i) {
        // root V1 (without path compression
        while (i != id[i])
            i = id[i];
        return i;
    }

    private boolean connected(int p, int q) {
        // for quick find (V2)
        return root(p) == root(q);
    }

    private boolean connectedV1(int p, int q) {
        // connected for slow union (V1)
        return id[p] == id[q];
    }

    private int find(int i) {
        // return the largest element in the connected component containing i
        return max[root(i)];
    }

    private int count() {
        return N;
    }

    public int delete(int i) {
        // not sure how to handle deleting last element.
        if (i >= N) return -1;

        union(i, i+1);
        return find(i);
    }
}

class SuccessorWithDelete {
    // this is needlessly complicated.
    // use delete above
    int[] next;
    int[] prev;
    public SuccessorWithDelete(int N) {
        next = new int[N];
        prev = new int[N];
        for (int i = 0; i < N; i++) {
            next[i] = i+1;
            prev[i] = i-1;
        }
        // -1 indicates nothing bigger
        next[N-1] = -1;
    }
    public int deleteAndReturnSuccessor(int x) {
        if (prev[x] != -1) {
            next[prev[x]] = next[x];
        }
        if (next[x] != -1) {
            prev[next[x]] = prev[x];
        }
        int successor = next[x];
        next[x] = -1;
        prev[x] = -1;
        return successor;
    }
}
