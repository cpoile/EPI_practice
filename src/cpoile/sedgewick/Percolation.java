package cpoile.sedgewick;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // remember, convention for this is 1-based indexing.
    private WeightedQuickUnionUF uf, ufTopOnly;
    private final int top, bottom;
    private final int n;
    private final boolean[] open;
    private int numOpen;
    //private int lastOpened;

    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException();

        // create n-by-n grid, with all sites blocked
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufTopOnly = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        // connect all first row nodes to a single top node, same for bottom row
        top = n * n;
        bottom = n * n + 1;
        open = new boolean[n * n];
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        // open site (row, col) if it is not open already
        if (isOpen(row, col)) return;

        if (row == 1) {
            // connect to top virtual node
            uf.union(convert(row, col), top);
            ufTopOnly.union(convert(row, col), top);
        }
        if (row == n) {
            // connect to bottom virtual node.
            // don't connect to the bottom virtual node for the second uf
            // this allows us to test for backwash
            uf.union(convert(row, col), bottom);
        }
        safeOpenNSEW(row, col);
    }

    private void safeOpenNSEW(int row, int col) {
        // translate into 0-based single array indexing, and if valid make the connection.
        int i = convert(row, col);
        open[i] = true;
        numOpen++;
        //lastOpened = i;

        if (row > 1) {
            // can try North
            if (isOpen(row - 1, col))
                connect(row, col, row - 1, col);
        }
        if (row < n) {
            // can try South
            if (isOpen(row + 1, col))
                connect(row, col, row + 1, col);
        }
        if (col > 1) {
            // can try West
            if (isOpen(row, col - 1))
                connect(row, col, row, col - 1);
        }
        if (col < n) {
            // can try East
            if (isOpen(row, col + 1))
                connect(row, col, row, col + 1);
        }

    }

    private void connect(int rowp, int colp, int rowq, int colq) {
        uf.union(convert(rowp, colp), convert(rowq, colq));
        ufTopOnly.union(convert(rowp, colp), convert(rowq, colq));
    }

    private int convert(int row, int col) {
        // convert to 0-based 1d array
        return (row - 1) * n + (col - 1);
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        // is site (row, col) open?
        return open[convert(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        // is site (row, col) full?
        return isOpen(row, col)
                && ufTopOnly.connected(convert(row, col), top)
                && uf.connected(convert(row, col), top);
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    /*private void print() {
        String black = (char) 27 + "[39m";
        String red = (char) 27 + "[31m";

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                String s = isOpen(i, j) ? "O" : "X";
                String s2 = convert(i, j) == lastOpened ? red + s + black : s;
                System.out.print(s2);
            }
            System.out.println();
        }
    }*/

    public static void main(String[] args) {
        int n = 5;
        Percolation perc = new Percolation(n);

        while (!perc.percolates()) {
            int i = StdRandom.uniform(1, n + 1);
            int j = StdRandom.uniform(1, n + 1);
            if (perc.isFull(i, j)) {
                perc.open(i, j);
                //perc.print();
                System.out.println();
            }
        }
        System.out.println("Percolated!");
    }
}
