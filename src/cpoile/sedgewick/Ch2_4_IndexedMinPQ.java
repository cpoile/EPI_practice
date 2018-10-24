package cpoile.sedgewick;

public class Ch2_4_IndexedMinPQ {
    public static void main() {
//        List<String> lst = new ArrayList<>();
//        Scanner scan = new Scanner(System.in);
//        while (scan.hasNext())
//            lst.add(scan.next());
//
//        for (int i = 0; i < lst.size(); i++)
//            System.out.print(lst.get(i) + " ");
//        System.out.println();
//
//        IndexedMinPQ<String> pq = new IndexedMinPQ<>(lst.toArray(new String[]{}));
//        while (!pq.isEmpty()) {
//            System.out.print(pq.delMax() + " ");
//        }

        // make three ordered streams, and use the indexedPQ to combine the three into one ordered stream

        int[][] streams =
                {{14, 10, 7, 6, 4, 2},
                {19, 14, 10, 8, 5, 0},
                {10, 10, 10, 9, 3, 1, -1}};
        int[] cnt = {6, 6, 7};

        IndexedMinPQ<Integer> pq = new IndexedMinPQ<>();
        for (int i = 0; i < 3; i++) {
            pq.insert(i+1, streams[i][--cnt[i]]);
        };

        while (!pq.isEmpty()) {
            int idx = pq.minIndex();
            int min = pq.delMin();
            if (cnt[idx-1] > 0)
                pq.insert(idx, streams[idx-1][--cnt[idx-1]]);
            System.out.println("From stream " + idx + ": " + min);
        }
    }
}

class IndexedMinPQ<Key extends Comparable<Key>> {
    Key[] keys;
    int[] pq;  // position i in queue is held by this keyindex
    int[] qp;  // i (keyindex)'s position in the queue (pq)
    int N;
    public IndexedMinPQ() {
        this(32);
    }
    public IndexedMinPQ(int n) {
        keys = (Key[]) new Comparable[n];
        pq = new int[n];
        qp = new int[n];
    }
    public IndexedMinPQ(Key[] a) {
        this(a.length + 1);
        for (int i = 0; i < a.length; i++)
            insert(i+1, a[i]);
    }
    public Key minKey() {
        return keys[pq[1]];
    }
    public int minIndex() {
        return pq[1];
    }
    public boolean contains(int i) {
        return i < N && i >= 0 && qp[i] != -1;
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
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
    public void insert(int i, Key k) {
        // do checks, index is in range, we don't already have that index
        N++;
        keys[i] = k;
        pq[N] = i;
        qp[i] = N;
        swim(N);
    }
    public void change(int i, Key k) {
        // do checks, index is in range
        keys[i] = k;
        swim(qp[i]);
        sink(qp[i]);
    }
    public void delete(int i) {
        if (i < 0 || i >= N || !contains(i))
            return;    // put real exception throwing here.
        int idx = qp[i];
        exch(idx, N--);
        swim(idx);
        sink(idx);

        keys[i] = null;
        qp[i] = -1;
        pq[N+1] = 0; // not needed (will be overridden when we add more)
    }
    public int delMin() {
        // check if n == 0, if so then throw exception
        int idx = pq[1];
        exch(1, N--);
        sink(1);
        keys[idx] = null;
        qp[idx] = -1;

        return idx;
    }
    private void sink(int i) {
        int j = i*2;
        if (j > N) return;
        if (j + 1 <= N && greater(j, j+1)) j++;
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
        if (greater(i/2, i)) exch(i/2, i);
        swim(i/2);

//        // or:
//        while (i > 1 & greater(i/2, i)) {
//            exch(i/2, i);
//            i = i/2;
//        }
    }
}
