package cpoile.sedgewick;

import java.util.Arrays;

public class Ch2_4_IndexedMinPQ {
    public static void main() {
        // make three ordered streams, and use the indexedPQ to combine the three into one ordered stream
        int[][] streams =
                {{14, 13, 7, 6, 4, 2},
                {19, 15, 11, 8, 5, 0},
                {16, 12, 10, 9, 3, 1, -1}};
        int[] cnt = {6, 6, 7};

        IndexedMinPQ<Integer> pq = new IndexedMinPQ<>();
        for (int i = 0; i < 3; i++) {
            pq.insert(i+1, streams[i][--cnt[i]]);
        };
        pq.delete(2);
        pq.insert(2, streams[1][--cnt[1]]);

        int checkIdx = 0;
        String[] expected = {"3: -1", "3: 1", "1: 2", "3: 3", "1: 4", "2: 5",
        "1: 6", "1: 7", "2: 8", "3: 9", "3: 10", "2: 11", "3: 12", "1: 13", "1: 14",
        "2: 15", "3: 16", "2: 19"};
        String[] actual = new String[18];
        while (!pq.isEmpty()) {
            int min = pq.min();
            int idx = pq.delMin();
            if (cnt[idx-1] > 0) {
                pq.insert(idx, streams[idx - 1][--cnt[idx - 1]]);
            }
            String res =  + idx + ": " + min;
            System.out.println("From stream " + res);
            actual[checkIdx++] = res;
        }
        String check = (Arrays.equals(expected, actual) ? "Matches" : "Does not match");
        System.out.println("Output " + check);
    }
}

class IndexedMinPQ<Item extends Comparable<Item>> {
    Item[] keys;
    int[] pq;  // position i in queue is held by this keyindex
    int[] qp;  // i (keyindex)'s position in the queue (pq)
    int N, maxN;

    public IndexedMinPQ() {
        this(32);
    }
    public IndexedMinPQ(int maxN) {
        this.maxN = maxN;
        keys = (Item[]) new Comparable[maxN];
        pq = new int[maxN];
        qp = new int[maxN];
        for (int i = 0; i < maxN; i++)
            qp[i] = -1;
    }
    public IndexedMinPQ(Item[] a) {
        this(a.length + 1);
        for (int i = 0; i < a.length; i++)
            insert(i+1, a[i]);
    }
    public Item min() {
        return keys[pq[1]];
    }
    public int minIndex() {
        return pq[1];
    }
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IllegalArgumentException();
        return qp[i] != -1;
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    public void insert(int k, Item item) {
        // do checks, index is in range, we don't already have that index
        N++;
        keys[k] = item;
        pq[N] = k;
        qp[k] = N;
        swim(N);
    }
    public void change(int k, Item item) {
        // do checks, index is in range
        keys[k] = item;
        swim(qp[k]);
        sink(qp[k]);
    }
    public void delete(int k) {
        if (k < 0 || k >= maxN || !contains(k))
            return;    // put real exception throwing here.
        int idx = qp[k];
        exch(idx, N--);
        swim(idx);
        sink(idx);

        keys[k] = null;
        qp[k] = -1;
        pq[N+1] = 0; // not needed (will be overridden when we add more)
    }
    public int delMin() {
        // check if n == 0, if so then throw exception
        int idx = pq[1];
        delete(idx);
        return idx;
    }
    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }
    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;

        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
    private void sink(int i) {
        if (i*2 > N) return;
        int j = i*2;
        if (j < N && greater(j, j+1)) j++;
        if (greater(i, j)) {
            exch(i, j);
            sink(j);
        }

//        // or:
//        while (i*2 <= N) {
//            int j = i*2;
//            if (j < N && greater(j, j+1)) j++;
//            if (greater(i, j)) exch(i, j);
//            i = j;
//        }
    }
    private void swim(int i) {
        if (i <= 1) return;
        if (greater(i/2, i)) {
            exch(i/2, i);
            swim(i/2);
        }

//        // or:
//        while (i > 1 & greater(i/2, i)) {
//            exch(i/2, i);
//            i = i/2;
//        }
    }
}
