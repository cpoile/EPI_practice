package cpoile.sedgewick;

import java.util.Iterator;

public class ExpandingStack<Item> implements Iterable {
    private Item[] stack;
    private int N;

    private class StackIterator implements Iterator<Item> {
        int cur;
        public boolean hasNext() { return cur < N; }
        public Item next() { return stack[cur++]; }
    }

    public Iterator<Item> iterator() {
        return new StackIterator();
    }

    public ExpandingStack() {
        stack = (Item[]) new Object[10];
        N = 0;
    }
    public boolean isEmpty() { return N == 0; }
    public int size() { return N; }

    private void resizeStack() {
        Item[] stack2 = (Item[]) new Object[stack.length * 2];

        //could use: System.arraycopy();
        for (int i = 0; i < N; i++) {
            stack2[i] = stack[i];
        }
        stack = stack2;
    }

    public void push(Item s) {
        if (N >= stack.length)
            resizeStack();
        stack[N++] = s;
    }
    public Item pop() {
        return stack[--N];
    }
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < N; i++) {
            s.append(stack[i]);
            s.append(" ");
        }
        s.deleteCharAt(s.length() - 1);
        return s.toString();
    }


}
