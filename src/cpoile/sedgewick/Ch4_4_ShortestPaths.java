package cpoile.sedgewick;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Ch4_4_ShortestPaths {
    public static void main() throws FileNotFoundException {
        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(new Scanner(new File("tinyEWD.txt")));
        System.out.println(ewd);
        int s = 0;
        DijkstraSP sp = new DijkstraSP(ewd, s);
        for (int v = 0; v < ewd.V(); v++) {
            System.out.print(String.format("%d to %d: (%.2f) ", s, v, sp.distTo(v)));
            for (DirectedEdge e : sp.pathTo(v))
                System.out.print(e + " ");
            System.out.println();
        }

        System.out.println("\nShortest path of a DAG:");
        ewd = new EdgeWeightedDigraph(new Scanner(new File("tinyEWDAG.txt")));
        s = 5;
        AcyclicSP asp = new AcyclicSP(ewd, s);
        for (int v = 0; v < ewd.V(); v++) {
            System.out.print(String.format("%d to %d: (%.2f) ", s, v, asp.distTo(v)));
            for (DirectedEdge e : asp.pathTo(v))
                System.out.print(e + " ");
            System.out.println();
        }

    }
}

class DirectedEdge {
    private int v, w;
    private double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return String.format("%d->%d (%.2f)", v, w, weight);
    }
}

class EdgeWeightedDigraph {
    private List<DirectedEdge>[] adj;
    private int V, E;

    public EdgeWeightedDigraph(int v) {
        V = v;
        adj = (List<DirectedEdge>[]) new List[v];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public EdgeWeightedDigraph(Scanner scan) {
        this(scan.nextInt());
        int e = scan.nextInt();
        for (int i = 0; i < e; i++)
            addEdge(new DirectedEdge(scan.nextInt(), scan.nextInt(), scan.nextDouble()));
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        Deque<DirectedEdge> d = new ArrayDeque<>();
        for (int i = 0; i < V; i++) {
            for (DirectedEdge w : adj(i))
                d.addLast(w);
        }
        return d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V).append(" vertices, ").append(E).append(" edges:\n");
        for (int v = 0; v < V; v++) {
            sb.append(v).append(": ");
            String sep = "";
            for (DirectedEdge e : adj(v)) {
                sb.append(sep).append(e);
                sep = ", ";
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

class DijkstraSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private int s;
    private IndexedMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph g, int s) {
        this.s = s;
        distTo = new double[g.V()];
        for (int i = 0; i < g.V(); i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        edgeTo = new DirectedEdge[g.V()];
        pq = new IndexedMinPQ<>();

        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        // or use a deque: d.addLast(s);
        while (!pq.isEmpty())
            relax(g, pq.delMin());  // or d.remove()
    }

    private void relax(EdgeWeightedDigraph g, int v) {
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.change(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
                // or, if using a queue: d.addLast(w);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPath(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPath(v)) return null;
        Deque<DirectedEdge> d = new ArrayDeque<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            d.push(e);
        return d;
    }
}

class AcyclicSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph g, int start) {
        edgeTo = new DirectedEdge[g.V()];
        distTo = new double[g.V()];
        for (int i = 0; i < g.V(); i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        distTo[start] = 0.0;

        Iterable<Integer> order = new DepthFirstOrderEWD(g).reversePost();
        for (int v : order)
            relax(g, v);
    }

    private void relax(EdgeWeightedDigraph g, int v) {
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }

    public double distTo(int v) { return distTo[v]; }

    public boolean hasPath(int v) { return distTo[v] < Double.POSITIVE_INFINITY; }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPath(v)) return null;
        Deque<DirectedEdge> d = new ArrayDeque<>();
        for (DirectedEdge x = edgeTo[v]; x != null; x = edgeTo[x.from()])
            d.push(x);
        return d;
    }
}