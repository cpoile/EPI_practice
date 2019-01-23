package cpoile.sedgewick;

import edu.princeton.cs.algs4.UF;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ch4_3_MinimumSpanningTrees {
    public static void main() throws FileNotFoundException {
        EdgeWeightedGraph g = new EdgeWeightedGraph(new Scanner(new File("tinyEWG.txt")));
        System.out.println(g);
        LazyPrimMST lazyPrimMst = new LazyPrimMST(g);
        System.out.println("LazyPrimMST:\n" + lazyPrimMst);
        EagerPrimMST eagerPrimMst = new EagerPrimMST(g);
        System.out.println("\nEagerPrimMST:\n" + eagerPrimMst);
        KruskalMST kruskalMst = new KruskalMST(g);
        System.out.println("\nKruskalMST:\n" + kruskalMst);
    }
}

class Edge implements Comparable<Edge> {
    private int v, w;
    private double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        if (vertex == w) return v;
        throw new IllegalArgumentException("Provided vertex not in this edge.");
    }

    public int compareTo(Edge other) {
        return Double.compare(weight, other.weight);
    }

    public String toString() {
        return String.format("%d-%d (%.2f)", v, w, weight);
    }
}

class EdgeWeightedGraph {
    private List<Edge>[] adj;
    private int V, E;

    public EdgeWeightedGraph(int v) {
        adj = (List<Edge>[]) new List[v];
        V = v;
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public EdgeWeightedGraph(Scanner scan) {
        this(scan.nextInt());
        int e = scan.nextInt();
        for (int i = 0; i < e; i++) {
            addEdge(new Edge(scan.nextInt(), scan.nextInt(), scan.nextDouble()));
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Deque<Edge> d = new ArrayDeque<>();
        for (int v = 0; v < V; v++) {
            for (Edge w : adj[v]) {
                if (w.other(v) > v) d.addLast(w);
            }
        }
        return d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < V; v++) {
            sb.append(v).append(": ");
            for (Edge w : adj(v)) {
                if (w.other(v) > v)
                    sb.append(w).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1).append("\n");
        }
        return sb.toString();
    }
}

class LazyPrimMST {
    private Deque<Edge> mst;
    private double weight;
    private boolean[] marked;
    private PriorityQueue<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph g) {
        mst = new ArrayDeque<>();
        marked = new boolean[g.V()];
        pq = new PriorityQueue<>();

        visit(g, 0);
        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.addLast(e);
            weight += e.weight();
            if (!marked[v]) visit(g, v);
            if (!marked[w]) visit(g, w);
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v))
            if (!marked[e.other(v)]) pq.add(e);
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge e : mst)
            sb.append(e).append("\n");
        sb.append(String.format("Weight: %.2f", weight));
        return sb.toString();
    }
}

class EagerPrimMST {
    private boolean[] marked;
    private Edge[] edgeTo;
    private double[] distTo;
    private IndexedMinPQ<Double> pq;

    public EagerPrimMST(EdgeWeightedGraph g) {
        marked = new boolean[g.V()];
        edgeTo = new Edge[g.V()];
        pq = new IndexedMinPQ<>();
        distTo = new double[g.V()];
        for (int i = 0; i < g.V(); i++)
            distTo[i] = Double.POSITIVE_INFINITY;

        distTo[0] = 0.0;
        pq.insert(0, distTo[0]);
        while (!pq.isEmpty())
            visit(g, pq.delMin());
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (distTo[w] > e.weight()) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        //return Arrays.asList(edgeTo);
        List<Edge> ret = new ArrayList<>();
        for (int i = 0; i < edgeTo.length; i++) {
            if (edgeTo[i] != null)
                ret.add(edgeTo[i]);
        }
        return ret;
    }

    public double weight() {
        return Arrays.stream(distTo).sum();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge e : edges())
            sb.append(e).append("\n");
        sb.append(String.format("Weight: %.2f", weight()));
        return sb.toString();
    }
}

class KruskalMST {
    private Deque<Edge> mst;
    private double weight;

    public KruskalMST(EdgeWeightedGraph g) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        g.edges().forEach(pq::add);
        mst = new ArrayDeque<>();
        UF uf = new UF(g.V());

        while (!pq.isEmpty() && mst.size() < g.V() - 1) {
            Edge e = pq.remove();
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) continue;
            uf.union(v, w);
            mst.addLast(e);
            weight += e.weight();
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge e : edges())
            sb.append(e).append("\n");
        sb.append(String.format("Weight: %.2f", weight()));
        return sb.toString();
    }
}
