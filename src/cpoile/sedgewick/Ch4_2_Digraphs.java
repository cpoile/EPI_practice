package cpoile.sedgewick;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ch4_2_Digraphs {
    public static void main() throws FileNotFoundException {
        Digraph g = new Digraph(new Scanner(new File("tinyDG.txt")));

        searchDFS(g, 0);
        searchDFS(g, 8);

        SymbolDigraph sg = new SymbolDigraph("jobs.txt", "/");
        Topological top = new Topological(sg.G());
        System.out.println("Is DAG? " + top.isDAG() + ". If so, here is the topological ordering: ");
        if (top.isDAG()) {
            for (int v : top.order())
                System.out.println(v + ": " + sg.name(v));
        }

        kosarajuSCC(g);
    }
    private static void searchDFS(Digraph g, int source) {
        DirectedDFS dfs = new DirectedDFS(g, source);
        System.out.println("DFS search starting from " + source + ". Is connected?");
        for (int i = 0; i < g.V(); i++) {
            if (dfs.marked(i))
                System.out.print(i + " ");
        }
        if (dfs.count() != g.V())
            System.out.print("NOT ");
        System.out.println("connected.\n");

    }
    private static void kosarajuSCC(Digraph g) {
        KosarajuSCC cc = new KosarajuSCC(g);
        System.out.println();
        System.out.println(cc.count() + " strongly connected components.");
        List<Integer>[] components = (List<Integer>[]) new ArrayList[cc.count()];
        for (int i = 0; i < cc.count(); i++)
            components[i] = new ArrayList<>();
        for (int v = 0; v < g.V(); v++)
            components[cc.ccid(v)].add(v);
        for (int i = 0; i < cc.count(); i++) {
            System.out.print(components[i]);
            System.out.println();
        }
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

class DirectedDFS {
    private boolean[] marked;
    private int count;

    public DirectedDFS(Digraph g, int start) {
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

class DirectedCycle {
    private boolean[] marked;
    private boolean hasCycle;
    private int[] edgeTo;
    private boolean[] onStack;
    private Deque<Integer> cycle;

    public DirectedCycle(Digraph g) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        onStack = new boolean[g.V()];

        for (int s = 0; s < g.V(); s++)
            if (!marked[s]) dfs(g, s);
    }
    private void dfs(Digraph g, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (int w : g.adj(v)) {
            if (hasCycle) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            } else if (onStack[w]) {
                hasCycle = true;
                cycle = new ArrayDeque<>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }
    public boolean hasCycle() { return hasCycle; }
    public Iterable<Integer> cycle() { return cycle; }
}

class DepthFirstOrder {
    private boolean[] marked;
    private Deque<Integer> preOrder, postOrder, revPostOrder;
    public DepthFirstOrder(Digraph g) {
        marked = new boolean[g.V()];
        preOrder = new ArrayDeque<>();
        postOrder = new ArrayDeque<>();
        revPostOrder = new ArrayDeque<>();
        for (int s = 0; s < g.V(); s++) {
            if (!marked[s]) dfs(g, s);
        }
    }
    private void dfs(Digraph g, int v) {
        preOrder.addLast(v);
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) dfs(g, w);
        }
        postOrder.addLast(v);
        revPostOrder.push(v);
    }
    public Iterable<Integer> pre() { return preOrder; }
    public Iterable<Integer> post() { return postOrder; }
    public Iterable<Integer> reversePost() { return revPostOrder; }
}

class DepthFirstOrderEWD {
    private boolean[] marked;
    private Deque<Integer> preOrder, postOrder, revPostOrder;

    public DepthFirstOrderEWD(EdgeWeightedDigraph g) {
        preOrder = new ArrayDeque<>();
        postOrder = new ArrayDeque<>();
        revPostOrder = new ArrayDeque<>();
        marked = new boolean[g.V()];

        for (int s = 0; s < g.V(); s++)
            if (!marked[s]) dfs(g, s);
    }

    private void dfs(EdgeWeightedDigraph g, int v) {
        marked[v] = true;
        preOrder.addLast(v);
        for (DirectedEdge w : g.adj(v)) {
            if (!marked[w.to()])
                dfs(g, w.to());
        }
        postOrder.addLast(v);
        revPostOrder.push(v);
    }
    public Iterable<Integer> pre() { return preOrder; }
    public Iterable<Integer> post() { return postOrder; }
    public Iterable<Integer> reversePost() { return revPostOrder; }
}

class Topological {
    Iterable<Integer> order;
    public Topological(Digraph g) {
        DirectedCycle cycleFinder = new DirectedCycle(g);

        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder dfo = new DepthFirstOrder(g);
            order = dfo.reversePost();
        }
    }
    public boolean isDAG() { return order != null; }
    public Iterable<Integer> order() { return order; }
}

class KosarajuSCC {
    private boolean[] marked;
    private int[] ccid;
    private int count;

    public KosarajuSCC(Digraph g) {
        marked = new boolean[g.V()];
        ccid = new int[g.V()];
        DepthFirstOrder order = new DepthFirstOrder(g.reverse());
        for (int s : order.reversePost())
            if (!marked[s]) {
                dfs(g, s);
                count++;
            }
    }
    private void dfs(Digraph g, int v) {
        marked[v] = true;
        ccid[v] = count;
        for (int w : g.adj(v))
            if (!marked[w])
                dfs(g, w);
    }
    public boolean isStronglyConnected(int v, int w) {
        return ccid[v] == ccid[w];
    }
    public int ccid(int v) { return ccid[v]; }
    public int count() { return count; }
}