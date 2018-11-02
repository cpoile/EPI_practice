package cpoile.sedgewick;

import java.util.*;

public class Ch3_1_SymbolTables {
    public static void main() {
        //String[] testInput = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
        String[] testInput = "SEARCHEXAMPLE".split("");

        System.out.println(Arrays.toString(testInput));

        //OrderedSequentialST<String, Integer> st = new OrderedSequentialST<>();
        //OrderedBinaryST<String, Integer> st = new OrderedBinaryST<>();
        BinarySearchTree<String, Integer> st = new BinarySearchTree<>();
        for (int i = 0; i < testInput.length; i++) {
            st.put(testInput[i], i);
        }

        for (String s : st.keys()) {
            System.out.println(s + ": " + st.get(s));
        }

        System.out.println("contains B: " + st.contains("B"));
        System.out.println("floor B: " + st.floor("B") + ", ceiling B: " + st.ceiling("B"));
        System.out.println("contains Y: " + st.contains("Y"));
        System.out.println("floor Y: " + st.floor("Y") + ", ceiling Y: " + st.ceiling("Y"));
        System.out.println("contains A: " + st.contains("A"));
        System.out.println("floor A: " + st.floor("A") + ", ceiling A: " + st.ceiling("A"));
        System.out.println("contains X: " + st.contains("X"));
        System.out.println("floor M: " + st.floor("M") + ", ceiling M: " + st.ceiling("M"));
        System.out.println("floor N: " + st.floor("N") + ", ceiling N: " + st.ceiling("N"));

        System.out.println("min: " + st.min() + ", max: " + st.max());
        st.delete("C");
        st.delete("A");
        st.delete("X");
        for (String s : st.keys()) {
            System.out.println(s + ": " + st.get(s));
        }
        System.out.println("floor A: " + st.floor("A") + ", ceiling A: " + st.ceiling("A"));
    }
}

class OrderedSequentialST<Key extends Comparable<Key>, Value> {
    class Node {
        Key key;
        Value value;
        Node next;

        Node(Key k, Value v, Node n) {
            key = k;
            value = v;
            next = n;
        }
    }

    Node first = new Node(null, null, null);

    public void put(Key key, Value val) {
        Node cur = first;
        while (true) {
            if (cur.next == null) {
                cur.next = new Node(key, val, null);
                return;
            }
            int cmp = key.compareTo(cur.next.key);
            if (cmp < 0) {
                cur.next = new Node(key, val, cur.next);
                return;
            } else if (cmp > 0) {
                cur = cur.next;
            } else {
                cur.next.value = val;
                return;
            }
        }
    }

    public Value get(Key key) {
        Node cur = first;
        while (cur.next != null) {
            cur = cur.next;
            if (cur.key.equals(key))
                return cur.value;
        }
        return null;
    }

    public Iterable<Key> keys() {
        Deque<Key> q = new ArrayDeque<>();
        Node cur = first;
        while (cur.next != null) {
            cur = cur.next;
            q.addLast(cur.key);
        }
        return q;
    }
}

class OrderedBinaryST<Key extends Comparable<Key>, Value> {
    Key[] keys;
    Value[] values;
    int N;

    public OrderedBinaryST() {
        keys = (Key[]) new Comparable[32];
        values = (Value[]) new Object[32];
    }

    public void put(Key key, Value val) {
        if (N >= keys.length)
            resize();
        int pos = rank(key);
        if (pos < N && keys[pos].equals(key)) {
            values[pos] = val;
            return;
        }
        for (int j = N; j > pos; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        keys[pos] = key;
        values[pos] = val;
        N++;
    }

    public Value get(Key key) {
        int pos = rank(key);
        if (keys[pos].equals(key))
            return values[pos];
        else {
            return null;
        }
    }

    private int rank(Key key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    public void delete(Key key) {
        if (!contains(key))
            return;
        int pos = rank(key);
        for (int i = pos + 1; i < N; i++) {
            keys[i - 1] = keys[i];
            values[i - 1] = values[i];
        }
        N--;
    }

    public boolean contains(Key key) {
        // checks for null
        int pos = rank(key);
        return (pos < N && key.equals(keys[pos]));
    }

    public Key min() {
        return keys[0];
    }

    public Key max() {
        return keys[N - 1];
    }

    public Key select(int k) {
        return keys[k];
    }

    public Key ceiling(Key key) {
        // ckeck null
        int pos = rank(key);
        if (pos == N)
            return null;
        else
            return keys[pos];
    }

    public Key floor(Key key) {
        // check null
        int pos = rank(key);
        if (pos < N && key.equals(keys[pos]))
            return keys[pos];
        else if (pos == 0)
            return null;
        else
            return keys[pos - 1];
    }

    public void deleteMin() {
        delete(min());
    }

    public void deleteMax() {
        delete(max());
    }

    public int size(Key lo, Key hi) {
        int loPos = rank(lo);
        int hiPos = rank(hi);
        if (hiPos < loPos)
            return 0;
        else if (contains(hi))
            return hiPos - loPos + 1;
        else
            return hiPos - loPos;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Deque<Key> q = new ArrayDeque<>();
        int loPos = rank(lo);
        for (int i = loPos; i < loPos + size(lo, hi); i++) {
            q.addLast(keys[i]);
        }
        return q;
    }

    private void resize() {
        Key[] swapK = (Key[]) new Object[keys.length * 2];
        System.arraycopy(keys, 0, swapK, 0, keys.length);
        keys = swapK;
        Value[] swapV = (Value[]) new Object[values.length * 2];
        System.arraycopy(values, 0, swapV, 0, values.length);
        values = swapV;
    }

}

class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private class Node {
        Key key;
        Value val;
        Node left, right;
        int N;

        public Node(Key key, Value value, int N) {
            this.key = key;
            this.val = value;
            this.N = N;
        }
    }

    Node root;

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t == null)
            return x;
        else
            return t;
    }

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t == null)
            return x;
        else
            return t;
    }

    public Key min() {
        if (root == null) return null;
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    public Key select(int rank) {
        return select(root, rank).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);
    }

    public void deleteMin() {
        // check and throw underflow if root == null
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.right) + size(x.left) + 1;
        return x;
    }

    public void deleteMax() {
        // check and throw underflow if root == null
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.right) + size(x.left) + 1;
        return x;
    }

    public void delete(Key key) {
        // check and throw underflow if root == null
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            // replace with the min of x.right
            Node t = min(x.right);
            t.right = deleteMin(x.right);
            t.left = x.left;
            x = t;
        }
        x.N = size(x.right) + size(x.left) + 1;
        return x;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Deque<Key> q = new ArrayDeque<>();
        keys(root, q, lo, hi);
        return q;
    }
    public void keys(Node x, Deque q, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, q, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) q.addLast(x.key);
        if (cmphi > 0) keys(x.right, q, lo, hi);
    }
}