package cpoile.sedgewick;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ch4_1_SymbolGraph {
    public static void main() throws FileNotFoundException {
        SymbolGraph sg = new SymbolGraph("movies.txt", "/");
        Graph g = sg.G();

        // Tests:
        String source = "Tin Men (1987)";
        System.out.println(source);
        for (int w : g.adj(sg.index(source)))
            System.out.println("    " + sg.name(w));
        source = "Jackson, Samuel L.";
        System.out.println(source);
        for (int w : g.adj(sg.index(source)))
            System.out.println("    " + sg.name(w));

        System.out.println("\nPlaying six degrees of Kevin Bacon.");
        DegreesOfSeparation dos = new DegreesOfSeparation(sg, "Bacon, Kevin");
        String dest = "Kidman, Nicole";
        System.out.println(dest);
        for (String s : dos.pathTo(dest)) {
            System.out.println("    " + s);
        }
        dest = "Grant, Cary";
        System.out.println(dest);
        for (String s : dos.pathTo(dest)) {
            System.out.println("    " + s);
        }
    }
}

class SymbolGraph {
    private Map<String, Integer> st;
    private String[] keys;
    private Graph g;

    public SymbolGraph(String filename, String delim) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        st = new HashMap<>();
        while (scan.hasNextLine()) {
            for (String s : scan.nextLine().split(delim)) {
                if (!st.containsKey(s))
                    st.put(s, st.size());
            }
        }

        keys = new String[st.size()];
        for (String s : st.keySet())
            keys[st.get(s)] = s;
        g = new Graph(st.size());

        scan = new Scanner(new File(filename));
        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(delim);
            int v = st.get(line[0]);
            for (int i = 1; i < line.length; i++) {
                g.addEdge(v, st.get(line[i]));
            }
        }
    }

    public boolean contains(String s) {
        return st.containsKey(s);
    }

    public int index(String key) {
        return st.get(key);
    }

    public String name(int v) {
        return keys[v];
    }

    public Graph G() {
        return g;
    }
}

class DegreesOfSeparation {
    SymbolGraph sg;
    private boolean[] marked;
    private int[] edgeTo;
    int s;

    public DegreesOfSeparation(SymbolGraph symbolGraph, String start) {
        // do a BFS to find every node's connection to 'start' (if there is a connection
        sg = symbolGraph;
        Graph g = sg.G();
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        Deque<Integer> d = new ArrayDeque<>();
        s = sg.index(start);
        d.addLast(s);
        edgeTo[s] = s;
        while (!d.isEmpty()) {
            int v = d.removeFirst();
            marked[v] = true;
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    d.addLast(w);
                }
            }
        }
    }
    public Iterable<String> pathTo(String dest) {
        Deque<String> d = new ArrayDeque<>();
        int v = sg.index(dest);
        for (int x = v; x != s; x = edgeTo[x]) {
            d.push(sg.name(x));
        }
        d.push(sg.name(s));
        return d;
    }
}