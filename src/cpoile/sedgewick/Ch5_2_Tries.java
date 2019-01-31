package cpoile.sedgewick;

import java.util.*;

public class Ch5_2_Tries {
    public static void main() {
        String[] ins = new String[]{"she", "sells", "sea", "shells", "by", "the", "sea", "shore", "surely"};

        Trie<Integer> trie = new Trie<>();
        for (int i = 0; i < ins.length; i++) {
            trie.put(ins[i], i);
        }

        assert (trie.get("she") == 0);
        assert (trie.get("sells") == 1);
        assert (trie.get("sea") == 6);
        assert (trie.get("shells") == 3);
        assert (trie.get("by") == 4);
        assert (trie.get("the") == 5);
        assert (trie.get("shore") == 7);

        List<String> res = new ArrayList<>();
        trie.keys().forEach(res::add);

        List<String> expected = Arrays.asList("she", "sells", "sea", "shells", "by", "the", "shore", "surely");

        assert (new HashSet<>(res).equals(new HashSet<>(expected)));

        expected = Arrays.asList("she", "shells", "shore");
        List<String> res2 = new ArrayList<>();
        trie.keysWithPrefix("sh").forEach(res2::add);
        assert (new HashSet<>(res2).equals(new HashSet<>(expected)));

        expected = Arrays.asList("she", "the");
        List<String> res3 = new ArrayList<>();
        trie.keysThatMatch(".he").forEach(res3::add);
        assert (new HashSet<>(res3).equals(new HashSet<>(expected)));

        expected = Arrays.asList("she", "sea");
        List<String> res4 = new ArrayList<>();
        trie.keysThatMatch("s..").forEach(res4::add);
        assert (new HashSet<>(res4).equals(new HashSet<>(expected)));

        assert (trie.longestPrefixOf("shell").equals("she"));
        assert (trie.longestPrefixOf("sh").equals(""));
        assert (trie.longestPrefixOf("shells").equals("shells"));
        assert (trie.longestPrefixOf("sea").equals("sea"));

        assert (trie.get("she") == 0);
        trie.delete("she");
        assert (trie.get("she") == null);
        assert (trie.get("shells") == 3);
        trie.delete("shell");
        assert (trie.get("shells") == 3);
        trie.delete("shells");
        assert (trie.get("shells") == null);
        assert (trie.get("shore") == 7);

        TST<Integer> tst = new TST<>();
        for (int i = 0; i < ins.length; i++) {
            tst.put(ins[i], i);
        }

        assert (tst.get("she") == 0);
        assert (tst.get("sells") == 1);
        assert (tst.get("sea") == 6);
        assert (tst.get("shells") == 3);
        assert (tst.get("by") == 4);
        assert (tst.get("the") == 5);
        assert (tst.get("shore") == 7);
        assert (tst.get("surely") == 8);
    }
}

class Trie<Value> {
    private static final int R = 256;
    Node root;

    private static class Node {
        Object val;
        Node[] next = new Node[R];
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        int c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        int c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Deque<String> q = new ArrayDeque<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Deque<String> q) {
        if (x == null) return;
        if (x.val != null) q.addLast(pre);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre + c, q);
        }
    }

    public Iterable<String> keysThatMatch(String pat) {
        Deque<String> q = new ArrayDeque<>();
        collect(root, "", pat, q);
        return q;
    }

    private void collect(Node x, String pre, String pat, Deque<String> q) {
        if (x == null) return;
        int d = pre.length();
        if (d == pat.length() && x.val != null) q.addLast(pre);
        if (d == pat.length()) return;
        char next = pat.charAt(d);
        for (char c = 0; c < R; c++) {
            if (next == '.' || next == c)
                collect(x.next[c], pre + c, pat, q);
        }
    }

    public String longestPrefixOf(String s) {
        int len = searchForKeys(root, s, 0, 0);
        return s.substring(0, len);
    }

    private int searchForKeys(Node x, String s, int d, int idx) {
        if (x == null) return idx;
        if (x.val != null) idx = d;
        if (d == s.length()) return idx;
        char c = s.charAt(d);
        return searchForKeys(x.next[c], s, d + 1, idx);
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        if (x.val != null) return x;

        for (char c = 0; c < R; c++) {
            if (x.next[c] != null)
                return x;
        }
        return null;
    }
}

class TST<Value> {
    private Node root;

    private class Node {
        private Node left, right, mid;
        private char c;
        private Value val;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length() - 1)
            return get(x.mid, key, d+1);
        else return x;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) { x = new Node(); x.c = c; }
        if      (c < x.c) x.left  = put(x.left, key, val, d);
        else if (c > x.c) x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)
                          x.mid   = put(x.mid, key, val, d+1);
        else x.val = val;
        return x;
    }
}