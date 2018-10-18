package cpoile.sedgewick;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Graph {
    List<Integer>[] adjLists;
    int V, E;
    public Graph(InputStream in) {
        Scanner scan = new Scanner(in);
        this.V = scan.nextInt();
        adjLists = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adjLists[i] = new ArrayList<Integer>();
        }
        int e = scan.nextInt();
        for (int i = 0; i < e; i++) {
            addEdge(scan.nextInt(), scan.nextInt());
        }
    }
    public void addEdge(int u, int v) {
        adjLists[u].add(v);
        adjLists[v].add(u);
        this.E++;
    }
    public List<Integer> adj(int u) {
        return adjLists[u];
    }
    public int V() {
        return this.V;
    }
    public int E() {
        return this.E;
    }
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(this.V()).append(" vertices, ").append(this.E()).append(" edges\n");
        for (int i = 0; i < adjLists.length; i++) {
            ret.append(i).append(": ");
            for (int j : adjLists[i])
                ret.append(j).append(" ");
            ret.append("\n");
        }
        return ret.toString();
    }
}

class DFSSearch {
    private boolean[] marked;
    private int count;
    public DFSSearch(Graph g, int u) {
        marked = new boolean[g.V()];
        dfs(g, u);
    }
    private void dfs(Graph g, int u) {
        if (!marked[u]) {
            marked[u] = true;
            count++;
            for (int i : g.adj(u))
                dfs(g, i);
        }
    }
    public void PrintConnected(Graph g, int u) {
        System.out.println("DFS seach on vertex " + u);
        for (int i = 0; i < g.V(); i++) {
            if (marked[i]) System.out.print(i + " ");
        }
        System.out.println();
        if (count < g.V())
            System.out.print("NOT ");
        System.out.println("connected.");
    }
}

public class Ch4_1_Graphs {
    static public void main(String[]... argh) {
        Graph g = new Graph(System.in);
        System.out.println(g);

        DFSSearch search = new DFSSearch(g, 0);
        search.PrintConnected(g, 0);
        search = new DFSSearch(g, 9);
        search.PrintConnected(g, 9);

    }
}
