package cpoile.sedgewick;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ch2_4_PriorityQueues {
    public static void sort() {
        List<String> lst = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext())
            lst.add(scan.next());

        for (int i = 0; i < lst.size(); i++)
            System.out.print(i + ": " + lst.get(i) + " ");
        System.out.println();

        MaxPQ<String> pq = new MaxPQ<>(lst.toArray(new String[]{}));
        while (!pq.isEmpty()) {
            System.out.print(pq.delMax() + " ");
        }

        //IndexMinPQ<String> impq = new IndexMinPQ<>(lst.toArray(new String[]{}));
    }
}

class MaxPQ<Key extends Comparable<Key>> {
    Key[] pq;
    int N;

    MaxPQ() {
        this(32);
    }

    MaxPQ(int max) {
        pq = (Key[]) new Comparable[max + 1];
    }

    MaxPQ(Key[] a) {
        this(a.length + 1);
        for (int i = 0; i < a.length; i++)
            insert(a[i]);
    }

    void insert(Key v) {
        pq[++N] = v;
        swim(N);
    }

    Key max() {
        if (!isEmpty())
            return pq[1];
        else
            return null;
    }

    Key delMax() {
        Key ret = pq[1];
        exch(1, N--);
        pq[N+1] = null;
        sink(1);
        return ret;
    }

    boolean isEmpty() {
        return N == 0;
    }

    int size() {
        return N;
    }

    void swim(int k) {
        if (k/2 >= 1 && less(k/2, k)) {
            exch(k, k/2);
            swim(k/2);
        }
//        while (k > 1 && less(k / 2, k)) {
//            exch(k / 2, k);
//            k = k / 2;
//        }
    }

    void sink(int k) {
        if (k*2 <= N) {
            int j = k*2;
            if (j < N && less(j, j+1)) j++;
            if (less(k, j)) {
                exch(k, j);
                sink(j);
            }
        }
//        while (2 * k <= N) {
//            int j = 2 * k;
//            if (j < N && less(j, j + 1)) j++;
//            if (!less(k, j)) break;
//            exch(k, j);
//            k = j;
//        }
    }

    boolean less(int a, int b) {
        return pq[a].compareTo(pq[b]) < 0;
    }

    void exch(int a, int b) {
        Key aux = pq[a];
        pq[a] = pq[b];
        pq[b] = aux;
    }
}

class IndexMinPQ<Key extends Comparable<Key>> {

}