package cpoile.sedgewick;

import java.util.ArrayDeque;
import java.util.Deque;

public class Ch1_3_ConvertToBase {
    public static void main() {
        System.out.println(convert(5, 2));
        System.out.println(convert(6, 2));
        System.out.println(convert(7, 2));
    }
    private static String convert(int num, int base) {
        Deque<Integer> d = new ArrayDeque<>();
        while(num > 0) {
            d.push(num % base);
            num /= base;
        }
        StringBuilder s = new StringBuilder();
        for (int i : d) s.append(i);
        return s.toString();
    }
}
