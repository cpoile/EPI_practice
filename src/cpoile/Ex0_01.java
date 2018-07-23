package cpoile;

import java.util.Arrays;

public class Ex0_01 {
    public static void main(String[]... arghs) {
        int[] stockPrices = {5, 6, 5, 7, 9, 5, 6, 4, 5, 7, 8, 9, 9, 8, 10, 9, 11, 10, 7, 10, 10, 11, 12, 9, 10, 6, 5, 4, 3, 10, 12};

        // naive version
        int maxDiff = 0;
        for (int i = 0; i < stockPrices.length; i++) {
            for (int j = i; j < stockPrices.length; j++) {
                int diff = stockPrices[j] - stockPrices[i];
                if (diff > maxDiff)
                    maxDiff = diff;
            }
        }
        System.out.println("Max diff is: " + maxDiff);

        // better version
        int maxLoc = 0, maxVal = 0;
        for (int i = 0; i < stockPrices.length; i++) {
            if (stockPrices[i] >= maxVal) {
                maxVal = stockPrices[i];
                maxLoc = i;
            }
        }
        int minVal = Integer.MAX_VALUE;
        for (int i = 0; i < maxLoc; i++) {
            if (stockPrices[i] <= minVal)
                minVal = stockPrices[i];
        }
        System.out.println("Max diff2 is: " + (maxVal-minVal));

        // even better version
        int low = Integer.MAX_VALUE, maxDiff2 = Integer.MIN_VALUE;
        for (int price : stockPrices) {
            if (price < low)
                low = price;
            if (price - low > maxDiff2)
                maxDiff2 = price - low;
        }
        System.out.println("MaxDiff3 is: " + maxDiff2);
    }
}
