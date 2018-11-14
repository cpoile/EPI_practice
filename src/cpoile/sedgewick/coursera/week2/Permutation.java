package cpoile.sedgewick.coursera.week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int n = 0;

        RandomizedQueue<String> q = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            n++;
            if (q.size() == k) {
                if (StdRandom.uniform(0.0, 1.0) < (double) k / n) {
                    q.dequeue();
                    q.enqueue(s);
                }
            }
            else {
                q.enqueue(s);
            }
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
