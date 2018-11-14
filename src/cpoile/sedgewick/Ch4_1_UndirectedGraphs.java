package cpoile.sedgewick;

import java.util.*;

public class Ch4_1_UndirectedGraphs {
    public static void main() {
        Graph g = new Graph(new Scanner(System.in));
        System.out.println(g);

        searchDFS(g, 0);
        searchDFS(g, 5);
        searchBFS(g, 0);
        searchBFS(g, 5);
        pathDFS(g, 0);
        pathBFS(g, 0);
        ccTest(g);
        cycleTest(g);
    }

    private static void searchDFS(Graph g, int source) {
        System.out.println("DFS search starting from " + source + ". Is connected?");
        DFS dfs = new DFS(g, source);
        for (int i = 0; i < g.V(); i++) {
            if (dfs.marked(i))
                System.out.print(i + " ");
        }
        if (dfs.count() != g.V())
            System.out.print("NOT ");
        System.out.println("connected.\n");
    }

    private static void searchBFS(Graph g, int source) {
        System.out.println("BFS search starting from " + source + ". Is connected?");
        BFS bfs = new BFS(g, source);
        for (int i = 0; i < g.V(); i++) {
            if (bfs.marked(i))
                System.out.print(i + " ");
        }
        if (bfs.count() != g.V())
            System.out.print("NOT ");
        System.out.println("connected.\n");
    }

    private static void pathDFS(Graph g, int start) {
        System.out.println("Paths from " + start + " using DFS.");
        PathDFS p = new PathDFS(g, start);
        for (int v = 0; v < g.V(); v++) {
            System.out.print(start + " to " + v + ": ");
            for (int i : p.pathTo(v)) {
                if (i == start) System.out.print(i);
                else System.out.print("-" + i);
            }
            System.out.println();
        }
    }

    private static void pathBFS(Graph g, int start) {
        System.out.println("Paths from " + start + " using BFS.");
        PathBFS p = new PathBFS(g, start);
        for (int v = 0; v < g.V(); v++) {
            System.out.print(start + " to " + v + ": ");
            for (int x : p.pathTo(v)) {
                if (x == start) System.out.print(x);
                else System.out.print("-" + x);
            }
            System.out.println();
        }
    }

    private static void ccTest(Graph g) {
        CCDFS cc = new CCDFS(g);
        int M = cc.count();
        System.out.println();
        System.out.println(M + " components");

        List<Integer>[] components = (List<Integer>[]) new List[M];
        for (int i = 0; i < M; i++) {
            components[i] = new ArrayList<>();
        }
        for (int i = 0; i < g.V(); i++) {
            components[cc.id(i)].add(i);
        }
        for (int i = 0; i < M; i++) {
            System.out.print("Component " + i + ": ");
            System.out.println(components[i].toString());
        }
    }

    private static void cycleTest(Graph g) {
        Cycle c = new Cycle(g);
        String s = c.hasCycle() ? "Yes, there is a " : "No ";
        System.out.println(s + "hasCycle.");
    }
}

class Graph {
    private int V, E;
    private Deque<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayDeque[V];
        for (int i = 0; i < V; i++)
            adj[i] = new ArrayDeque<>();
    }

    public Graph(Scanner scan) {
        this(scan.nextInt());
        int e = scan.nextInt();
        for (int i = 0; i < e; i++) {
            addEdge(scan.nextInt(), scan.nextInt());
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int e) {
        adj[v].push(e);
        adj[e].push(v);
        this.E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public String toString() {
        StringBuffer s = new StringBuffer(V + " vertices, " + E + " edges.\n");
        for (int i = 0; i < this.V; i++) {
            s.append(i).append(": ");
            for (int v : adj[i])
                s.append(v).append(" ");
            s.append("\n");
        }
        return s.toString();
    }
}

class DFS {
    private boolean[] marked;
    private int count;

    public DFS(Graph g, int start) {
        marked = new boolean[g.V()];
        dfs(g, start);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        count++;
        for (int w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
    }

    public int count() {
        return count;
    }

    public boolean marked(int i) {
        return marked[i];
    }
}

class PathDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public PathDFS(Graph g, int start) {
        s = start;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        dfs(g, start);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
        }
    }

    public Iterable<Integer> pathTo(int v) {
        Deque<Integer> d = new ArrayDeque<>();
        for (int x = v; x != s; x = edgeTo[x])
            d.push(x);
        d.push(s);
        return d;
    }
}

class BFS {
    private boolean marked[];
    private int count;

    public BFS(Graph g, int start) {
        marked = new boolean[g.V()];

        Deque<Integer> d = new ArrayDeque<>();
        d.addLast(start);
        while (!d.isEmpty()) {
            int v = d.removeFirst();
            marked[v] = true;
            count++;
            for (int w : g.adj(v)) {
                if (!marked[w])
                    d.addLast(w);
            }
        }
    }

    public int count() {
        return count;
    }

    public boolean marked(int i) {
        return marked[i];
    }
}

class PathBFS {
    private boolean[] marked;
    private int[] edgeTo;
    private int count, s;

    public PathBFS(Graph g, int start) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        s = start;
        bfs(g, s);
    }

    private void bfs(Graph g, int start) {
        Deque<Integer> q = new ArrayDeque<>();
        marked[start] = true;
        q.addLast(start);
        while (!q.isEmpty()) {
            int v = q.removeFirst();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    q.addLast(w);
                }
            }
        }
    }

    public Iterable<Integer> pathTo(int v) {
        Deque<Integer> q = new ArrayDeque<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            q.push(x);
        }
        q.push(s);
        return q;
    }
}

class CCDFS {
    private boolean[] marked;
    private int[] ccId;  // 0 indexed (between 0 and count-1
    private int count;

    public CCDFS(Graph g) {
        marked = new boolean[g.V()];
        ccId = new int[g.V()];
        for (int s = 0; s < g.V(); s++) {
            if (!marked[s]) {
                dfs(g, s);
                count++;
            }
        }
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        ccId[v] = count;
        for (int w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
    }

    public boolean connected(int v, int w) {
        return ccId[v] == ccId[w];
    }

    public int id(int v) {
        return ccId[v];
    }

    public int count() { return count; }
}

class Cycle {
    private boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph g) {
        marked = new boolean[g.V()];
        for (int s = 0; s < g.V(); s++) {
            if (!marked[s])
                dfs(g, s, s);
        }
    }

    private void dfs(Graph g, int v, int u) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w, v);
            else if (w != u) hasCycle = true;
        }
    }

    public boolean hasCycle() { return hasCycle; }
}

class TwoColor {
    private boolean[] marked;
    private boolean[] color;
    private boolean twoColor = true;

    public TwoColor(Graph g) {
        marked = new boolean[g.V()];
        color = new boolean[g.V()];
        for (int s = 0; s < g.V(); s++) {
            if (!marked[s])
                dfs(g, s);
        }
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(g, w);
            } else if (color[w] == color[v])
                twoColor = false;
        }
    }

    public boolean isBipartite() { return twoColor; }
}



















