package cpoile.sedgewick.coursera.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    private class Node {
        private final Item item;
        private Node next, prev;

        public Node(Item val, Node next, Node prev) {
            this.item = val;
            this.next = next;
            this.prev = prev;
        }
    }

    public Deque() {
        // construct an empty deque
    }

    public boolean isEmpty() {
        // is the deque empty?
        return n == 0;
    }

    public int size() {
        // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node(item, oldFirst, null);
        if (isEmpty())
            last = first;
        else
            oldFirst.prev = first;
        n++;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node(item, null, oldLast);
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;
        n++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        if (first != null)
            first.prev = null;
        n--;
        if (isEmpty()) {
            first = null;
            last = null;
        }

        return item;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev;
        if (last != null)
            last.next = null;
        n--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
        return item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node cur = first;
        public boolean hasNext() {
            return cur != null && !isEmpty();
        }
        public Item next() {
            if (cur == null) throw new NoSuchElementException();
            Item item = cur.item;
            cur = cur.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    public static void main(String[] args) {
        // Deque<Integer> deque = new Deque<Integer>();
        //  deque.addFirst(1);
        //  deque.addLast(2);
        //  deque.addFirst(3);
        //  deque.isEmpty();
        //  deque.isEmpty();
        //  deque.removeLast();
        //  deque.addFirst(7);
        //  deque.removeLast();
        //  deque.removeFirst();
        //  deque.addLast(10);

        // unit testing (optional)
        Deque<String> d = new Deque<>();
        d.addFirst("A");
        d.removeFirst();
        d.addFirst("A");
        d.removeLast();

        d.addFirst("B");
        d.addFirst("C");
        d.addLast("D");
        d.addLast("E");

        for (String s : d)
            System.out.print(s + " ");
        System.out.println();

        assert (d.removeFirst().equals("C"));
        assert (d.removeFirst().equals("B"));
        assert (d.removeLast().equals("E"));
        assert (d.size() == 1);
        d.removeLast();
        assert (d.size() == 0);


        for (String s : d)
            System.out.print(s + " ");






    }
}
