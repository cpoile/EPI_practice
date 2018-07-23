package cpoile.sedgewick;

public class StackOfStrings {
    private class Node {
        String val;
        Node next;

        Node(String v) {
            this.val = v;
            this.next = null;
        }
    }

    private Node first;

    public void push(String s) {
        Node n = new Node(s);
        n.next = this.first;
        this.first = n;
    }

    public String pop() {
        if (this.isEmpty())
            throw new IndexOutOfBoundsException();
        String popped = first.val;
        first = first.next;
        return popped;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        Node tmp = first;
        while (tmp != null) {
            s.append(tmp.val);
            s.append(" ");
            tmp = tmp.next;
        }
        s.deleteCharAt(s.length() - 1);
        return s.toString();
    }
}

