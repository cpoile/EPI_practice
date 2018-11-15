package cpoile.sedgewick;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ch4_2_Digraphs {
    public static void main() throws FileNotFoundException {
        Digraph g = new Digraph(new Scanner(new File("tinyDG.txt")));

        searchDFS(g, 0);
        searchDFS(g, 8);
    }
    private static void searchDFS(Digraph g, int source) {
        DigraphDFS dfs = new DigraphDFS(g, source);
        System.out.println("DFS search starting from " + source + ". Is connected?");
        for (int i = 0; i < g.V(); i++) {
            if (dfs.marked(i))
                System.out.print(i + " ");
        }
        if (dfs.count() != g.V())
            System.out.print("NOT ");
        System.out.println("connected.\n");

    }
}

class Digraph {
    private List<Integer>[] adj;
    private final int V;
    private int E;

    public Digraph(int V) {
        adj = (List<Integer>[]) new List[V];
        this.V = V;
        for (int i = 0; i < this.V; i++)
            adj[i] = new ArrayList<>();
    }

    public Digraph(Scanner scan) {
        this(scan.nextInt());
        int e = scan.nextInt();
        for (int i = 0; i < e; i++)
            addEdge(scan.nextInt(), scan.nextInt());
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Digraph reverse() {
        Digraph d = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj[v])
                d.addEdge(w, v);
        }
        return d;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }
}

class DigraphDFS {
    boolean[] marked;
    int count;

    public DigraphDFS(Digraph g, int start) {
        marked = new boolean[g.V()];
        dfs(g, start);
    }
    private void dfs(Digraph g, int v) {
        marked[v] = true;
        count++;
        for (int w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
    }
    public boolean marked(int v) { return marked[v]; }
    public int count() { return count; }
}