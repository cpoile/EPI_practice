package cpoile.sedgewick;

public class FixedStackStringsArray {
    private String[] stack;
    private int N;
    public FixedStackStringsArray() {
        stack = new String[10];
    }
    public boolean isEmpty() { return N == 0; }
    public int size() { return N; }
    public void push(String s) {
        stack[N++] = s;
    }
    public String pop() {
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
