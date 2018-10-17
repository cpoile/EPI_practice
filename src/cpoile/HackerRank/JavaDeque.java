package cpoile.HackerRank;

import java.util.*;

public class JavaDeque {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Deque<Integer> deque = new ArrayDeque<>();
        int n = in.nextInt();
        int m = in.nextInt();

        // three ways?
        // 1: keep a map of numbers and counts and get the size after adding and removing the most recent one.
        // 2: make a new set each time we shift, count the size of the set. (easiest?)
        // 3: before adding, check if contains. if so, don't increment count. if not, increment count. after removing, check if contains removed element. if so, don't decrement count. if not, decrement count.

        Map<Integer, Integer> counts = new HashMap<>();
        Set<Integer> countSet = new HashSet<>();
        int maxUnique = 0;
        int maxUniqueFromSet = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            Integer num = in.nextInt();
            if (count == m) {
                Integer removed = deque.removeFirst();
                counts.merge(removed, 0, (x, y) -> x == 1 ? null : x--);
                count--;
            }
            deque.addLast(num);
            count++;
            counts.merge(num, 1, (x, y) -> x++);
            if (counts.size() >= maxUnique) maxUnique = counts.size();
            countSet.clear();
            countSet.addAll(deque);
            if (countSet.size() > maxUniqueFromSet) maxUniqueFromSet = countSet.size();
        }
        System.out.println(maxUnique);
        System.out.println("From set: " + maxUniqueFromSet);
    }
}

class test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Deque deque = new ArrayDeque<>();
        int n = in.nextInt();
        int m = in.nextInt();

        int maxUnique = 0;
        Map<Integer, Integer> uniqueMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            Integer num = in.nextInt();

            if (deque.size() == m) {
                Integer elem = (Integer) deque.removeFirst();
                uniqueMap.merge(elem, -1, Integer::sum);
                if (uniqueMap.get(elem) == 0)
                    uniqueMap.remove(elem);
            }
            deque.addLast(num);
            uniqueMap.merge(num, 1, Integer::sum);
            maxUnique = Math.max(maxUnique, uniqueMap.size());
        }

        System.out.println(maxUnique);
    }
}

class Add {
    public void Add(int... elems) {
        for (int i : elems) {

        }
        List<String> elemStr = new ArrayList<>();
        for (int i = 0; i < elems.length; i++)
            elemStr.add(String.valueOf(elems[i]));
        System.out.println(String.join("+", elemStr) + "=" + Arrays.stream(elems).sum());

        List<String> strings = List.of("Java", "is", "cool");
        String message = String.join(" ", strings);
    }
}