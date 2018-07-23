package cpoile.thinkinglikeaprogrammer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MostFreqWords {
    public static void main(String[]... argh) throws FileNotFoundException {
        int topXWords = 30;
        class WordEntry {
            int count;
            String word;
            int place;

            WordEntry(String word, int count, int place) {
                this.word = word;
                this.count = count;
                this.place = place;
            }
        }

        Scanner in = new Scanner(new File("inputMostFreqWords.txt")).useDelimiter("\\s*\\W\\s*");
        Map<String, WordEntry> wordCounts = new LinkedHashMap<>();

        for (int i = 0; in.hasNext(); i++) {
            WordEntry we = new WordEntry(in.next(), 1, i);
            wordCounts.merge(we.word, we, (we1, we2) -> {
                we1.count++;
                return we1;
            });
        }

        PriorityQueue<WordEntry> minHeap = new PriorityQueue<>(topXWords, (w1, w2) -> {
            if (w1.count == w2.count)
                return w1.place - w2.place;
            else
                return w1.count - w2.count;
        });

        wordCounts.forEach((w, we) -> {
            if (minHeap.size() < topXWords)
                minHeap.add(we);
            else if (minHeap.peek().count < we.count) {
                minHeap.poll();
                minHeap.add(we);
            }
        });

        /*minHeap.forEach(we -> {
            System.out.println(we.word + ": " + we.count);
        });
        System.out.println();*/

        while (!minHeap.isEmpty()) {
            WordEntry we = minHeap.poll();
            System.out.println(we.word + ": " + we.count);
        }
    }
}
