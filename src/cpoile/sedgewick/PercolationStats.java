package cpoile.sedgewick;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] means;
    private final int trials;
    private double mean, stddev;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException();

        Percolation p;
        means = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            means[i] = (double) p.numberOfOpenSites() / (n*n);
        }
        mean();
        stddev();
    }

    public double mean() {
        // sample mean of percolation threshold
        if (mean == 0) {
            mean = StdStats.mean(means);
            return mean;
        } else
            return mean;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        if (stddev == 0) {
            stddev = StdStats.stddev(means);
            return stddev;
        } else
            return stddev;
    }

    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean - 1.96*stddev/Math.sqrt(trials);
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean + 1.96*stddev/Math.sqrt(trials);
    }

    public static void main(String[] args) {
        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        System.out.println("mean                    = " + ps.mean);
        System.out.println("stddev                  = " + ps.stddev);
        System.out.println("95% confidence interval = [" + ps.confidenceLo()
                + ", " + ps.confidenceHi() + "]");
    }
}
