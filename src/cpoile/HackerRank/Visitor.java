package cpoile.HackerRank;

import java.util.ArrayList;
import java.util.Scanner;

import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import java.util.ArrayList;
import java.util.Scanner;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    private ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis {
    public abstract int getResult();

    public abstract void visitNode(TreeNode node);

    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
    int sum = 0;

    public int getResult() {
        return sum;
    }

    public void visitNode(TreeNode node) {
    }

    public void visitLeaf(TreeLeaf leaf) {
        sum += leaf.getValue();
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    int product = 1;

    public int getResult() {
        return product;
    }

    public void visitNode(TreeNode node) {
        if (node.getColor() == Color.RED)
            computeProduct(node.getValue());
    }

    public void visitLeaf(TreeLeaf leaf) {
        if (leaf.getColor() == Color.RED)
            computeProduct(leaf.getValue());
    }

    private void computeProduct(int value) {
        product = (int) (((long) product * value) % 1000000007);
    }
}

class FancyVisitor extends TreeVis {
    int sumNonLeafEven = 0;
    int sumGreenLeaf = 0;

    public int getResult() {
        //implement this
        return Math.abs(sumNonLeafEven - sumGreenLeaf);
    }

    public void visitNode(TreeNode node) {
        if (node.getDepth() % 2 == 0)
            sumNonLeafEven += node.getValue();
    }

    public void visitLeaf(TreeLeaf leaf) {
        if (leaf.getColor() == Color.GREEN)
            sumGreenLeaf += leaf.getValue();
    }
}

class Graph {
    private List<Integer>[] adjList;

    Graph(int N) {
        adjList = new ArrayList[N];
        for (int i = 0; i < N; i++)
            adjList[i] = new ArrayList<>();

    }

    void addEdge(int u, int v) {
        adjList[u].add(v);
        adjList[v].add(u);
    }

    List<Integer> adj(int u) {
        return adjList[u];
    }

    int getN() {
        return adjList.length;
    }

}

public class Visitor {

    // dfs keeping track of depth
    static void dfs(Graph g, int curIdx, Tree curNode, boolean[] visited, int[] values, Color[] colors) {
        if (visited[curIdx])
            return;
        visited[curIdx] = true;
        // for each connected node, create the node or leaf and then dfs it.
        for (Integer i : g.adj(curIdx)) {
            if (!visited[i]) {
                Tree child;
                if (g.adj(i).size() > 1) {
                    child = new TreeNode(values[i], colors[i], curNode.getDepth() + 1);
                    ((TreeNode) curNode).addChild(child);
                } else {
                    child = new TreeLeaf(values[i], colors[i], curNode.getDepth() + 1);
                    ((TreeNode) curNode).addChild(child);
                }
                //System.out.println("Adding child #" + i + " to node #" + curIdx);
                dfs(g, i, child, visited, values, colors);
            }
        }
    }

    // bfs version
    static void bfs(Graph g, int curIdx, Tree curNode, int[] values, Color[] colors) {
        Deque<Tree> qNodes = new ArrayDeque<>();
        Deque<Integer> qIdx = new ArrayDeque<>();
        qNodes.addLast(curNode);
        qIdx.addLast(curIdx);
        boolean[] visited = new boolean[g.getN()];
        while (qNodes.peekFirst() != null) {
            Tree parent = qNodes.removeFirst();
            Integer idx = qIdx.removeFirst();
            if (!visited[idx]) {
                visited[idx] = true;
                for (int i : g.adj(idx)) {
                    if (!visited[i]) {
                        Tree child;
                        if (g.adj(i).size() > 1) {
                            child = new TreeNode(values[i], colors[i], parent.getDepth() + 1);
                        } else {
                            child = new TreeLeaf(values[i], colors[i], parent.getDepth() + 1);
                        }
                        //System.out.println("Adding to parent: " + idx + " child: " + i);
                        ((TreeNode) parent).addChild(child);
                        qNodes.addLast(child);
                        qIdx.addLast(i);
                    }
                }
            }
        }
    }

    public static Tree solve() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] values = new int[n + 1];
        Color[] colors = new Color[n + 1];
        for (int i = 1; i < n + 1; i++) {
            values[i] = scan.nextInt();
        }
        for (int i = 1; i < n + 1; i++) {
            colors[i] = Color.values()[scan.nextInt()];
        }

        // build a graph in order to construct the tree
        Graph graph = new Graph(n + 1);
        for (int i = 0; i < n - 1; i++) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            graph.addEdge(u, v);
        }
        // now traverse the graph and construct the tree
        boolean[] visited = new boolean[n + 1];


        // set the root at depth 0
        TreeNode root = new TreeNode(values[1], colors[1], 0);
        //dfs(graph, 1, root, visited, values, colors);
        bfs(graph, 1, root, values, colors);

        //System.out.println("Adding child #" + v[i] + " to node #" + u[i]);
        // debug
        /*
        for (Tree t : nodes) {
            if (t != null) {
            System.out.print(t.getClass().getName() + ", value: " + t.getValue() + ", color: " + t.getColor() + ", depth: " + t.getDepth());
            System.out.println((t instanceof TreeNode) ? " node." : " leaf.");
            }
        }
        */

        return root;
    }

    public static void main(String[] args) {
        Tree root = solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}
