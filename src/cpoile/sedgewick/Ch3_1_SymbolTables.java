package cpoile.sedgewick;

import java.util.*;

public class Ch3_1_SymbolTables {
    public static void main() {
        //String[] testInput = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
        String[] testInput = "SEARCHEXAMPLE".split("");

        System.out.println(Arrays.toString(testInput));

        //OrderedSequentialST<String, Integer> st = new OrderedSequentialST<>();
        //OrderedBinaryST<String, Integer> st = new OrderedBinaryST<>();
        //BinarySearchTree<String, Integer> st = new BinarySearchTree<>();
        RedBlackBST<String, Integer> st = new RedBlackBST<>();
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

        System.out.println("Diagram:");
        st.draw();
        st.runChecks();

        System.out.println("min: " + st.min() + ", max: " + st.max());
//        st.delete("C");
//        st.delete("A");
//        st.delete("X");
//        for (String s : st.keys()) {
//            System.out.println(s + ": " + st.get(s));
//        }
//        System.out.println("floor A: " + st.floor("A") + ", ceiling A: " + st.ceiling("A"));
//
//        System.out.println("Diagram:");
//        st.draw();

        System.out.println("\nNow inserting in sorted order:");
        Arrays.sort(testInput);
        st = new RedBlackBST<>();
        for (int i = 0; i < testInput.length; i++) {
            st.put(testInput[i], i);
        }
        st.draw();
        st.runChecks();

        System.out.println("Now deleting min. Size: " + st.size());
        st.deleteMin();
        st.draw();
        st.runChecks();

        System.out.println("Now deleting min. Size: " + st.size());
        st.deleteMin();
        st.draw();
        st.runChecks();

        System.out.println("Now deleting min. Size: " + st.size());
        st.deleteMin();
        st.draw();
        st.runChecks();

        System.out.println("Now deleting min. Size: " + st.size());
        st.deleteMin();
        st.draw();
        st.runChecks();

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

    // Node has color for the RedBlackBST subclass.
    class Node {
        Key key;
        Value val;
        int N;
        Node left, right;
        boolean color;
        int depth, rank;

        public Node(Key k, Value val, int n) {
            this.key = k;
            this.val = val;
            this.N = n;
        }

        public Node(Key k, Value val, int n, boolean color) {
            this.key = k;
            this.val = val;
            this.N = n;
            this.color = color;
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

    public boolean isEmpty() {
        return root == null;
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
        if (root == null) return null;
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
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

    // for drawing
    class Width {
        int k = 0, v = 0;
    }

    public void draw() {
        System.out.println();

        PriorityQueue<Node> q = new PriorityQueue<>((n1, n2) -> {
            if (n1.depth < n2.depth) return -1;
            if (n1.depth > n2.depth) return 1;
            return Integer.compare(n1.rank, n2.rank);
        });


        Width width = new Width();
        populateDrawingCoords(root, 1, q, width);
        int space = width.v + width.v;

        int curLine = 1;
        int curRank = 0;
        while (q.peek() != null) {
            Node x = q.poll();
            if (x.depth != curLine) {
                System.out.println();
                curLine++;
                curRank = 0;
            }
            printWhiteSpaces((x.rank - curRank) * space);
            printSpaced(x.key + "-" + x.val, space, x.color);
            curRank = x.rank + 1;
        }
        System.out.println();
        System.out.println();
    }

    private void populateDrawingCoords(Node x, int depth, PriorityQueue<Node> q, Width width) {

        if (x == null) return;
        x.depth = depth;
        x.rank = rank(x.key);
        q.add(x);
        populateDrawingCoords(x.left, depth + 1, q, width);
        populateDrawingCoords(x.right, depth + 1, q, width);
        width.k = Math.max(width.k, x.key.toString().length());
        width.v = Math.max(width.v, x.val.toString().length());
    }

    private void printWhiteSpaces(int x) {
        for (int i = 0; i < x; i++)
            System.out.print(" ");
    }

    protected void printSpaced(String s, int space, boolean printRed) {
        System.out.print(s);
        for (int i = space - s.length(); i > 0; i--) {
            System.out.print(" ");
        }
    }
}

class RedBlackBST<Key extends Comparable<Key>, Value> extends BinarySearchTree<Key, Value> {
    public static final boolean BLACK = false;
    public static final boolean RED = true;

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, 1, RED);
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public void deleteMin() {
        if (root == null) return;  // or throw exception

        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    protected void printSpaced(String s, int space, boolean printRed) {
        String c = "";
        if (printRed)
            c = (char) 27 + "[31m";

        System.out.print(c + s + (char)27 + "[39m");
        for (int i = space - s.length(); i > 0; i--) {
            System.out.print(" ");
        }
    }

    // Checks
    public void runChecks() {
        assert(isBST());
        assert(isSizeConsistent());
        assert(isRankConsistent());
        assert(is23());
        assert(isBalanced());
    }
    public boolean isBST() {
        return isBST(root, null, null);
    }
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }
    public boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (size(x) != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }
    public boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) return false;
        }
        for (Key k : keys()) {
            if (k.compareTo(select(rank(k))) != 0) return false;
        }
        return true;
    }
    public boolean is23() {
        return is23(root);
    }
    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left)) return false;
        return is23(x.left) && is23(x.right);
    }
    public boolean isBalanced() {
        int black = 0;
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }
    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }
}