package cpoile.sedgewick;

import java.util.*;

public class Ch5_2_Tries {
    public static void main() {
        String[] ins = new String[]{"she", "sells", "sea", "shells", "by", "the", "sea", "shore"};

        Trie<Integer> trie = new Trie<>();
        for (int i = 0; i < ins.length; i++) {
            trie.put(ins[i], i);
        }

        assert(trie.get("she") == 0);
        assert(trie.get("sells") == 1);
        assert(trie.get("sea") == 6);
        assert(trie.get("shells") == 3);
        assert(trie.get("by") == 4);
        assert(trie.get("the") == 5);
        assert(trie.get("shore") == 7);

        List<String> res = new ArrayList<>();
        trie.keys().forEach(res::add);

        List<String> expected = Arrays.asList("she", "sells", "sea", "shells", "by", "the", "shore");

        assert(new HashSet<>(res).equals(new HashSet<>(expected)));

        expected = Arrays.asList("she", "shells", "shore");
        List<String> res2 = new ArrayList<>();
        trie.keysWithPrefix("sh").forEach(res2::add);
        assert(new HashSet<>(res2).equals(new HashSet<>(expected)));


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

}
