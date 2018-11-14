package cpoile.sedgewick.coursera.week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] q;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }

    private void resize(int cap) {
        Item[] newQ = (Item[]) new Object[cap];
        int numToCopy = cap < n ? cap : n;
        System.arraycopy(q, 0, newQ, 0, numToCopy);
        q = newQ;
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return n == 0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return n;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) throw new IllegalArgumentException();

        if (n == q.length)
            resize(q.length*2);
        q[n] = item;
        n++;
    }

    public Item dequeue() {
        // remove and return a random item
        if (n == 0) throw new NoSuchElementException();

        int rnd = StdRandom.uniform(0, n);
        Item item = q[rnd];
        q[rnd] = q[n-1];
        q[n-1] = null;
        n--;

        if (n > 0 && n == q.length/4)
             resize(q.length/2);
        return item;
    }

    public Item sample() {
        // return a random item (but do not remove it)
        if (n == 0) throw new NoSuchElementException();

        return q[StdRandom.uniform(0, n)];
    }

    private class RandomIterator implements Iterator<Item> {
        private final int[] order;
        private int cur;
        public RandomIterator() {
            order = new int[n];
            for (int i = 0; i < n; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }
        public boolean hasNext() {
            return cur < n;
        }
        public Item next() {
            if (cur >= n) throw new NoSuchElementException();
            return q[order[cur++]];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        q.enqueue("A");
        q.dequeue();
        q.enqueue("B");
        q.dequeue();
        q.enqueue("C");
        q.enqueue("D");
        q.enqueue("D");
        q.enqueue("D");
        q.enqueue("D");

        for (String s : q)
            System.out.print(s + " ");
    }
}
