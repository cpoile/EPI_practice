package cpoile.thinkinglikeaprogrammer;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Ex02_01 {

    public static void main(String[]... argh) throws FileNotFoundException {
        Scanner in = new Scanner(new File("inputLuhn.txt"));

        while (in.hasNextLine()) {
            int validatingIDNumberOdd = 0;
            int validatingIDNumberEven = 0;
            String inputNum = in.next();
            int[] inputDigits = Arrays.stream(inputNum.split("")).mapToInt(Integer::parseInt).toArray();
            int i = 0;
            for (int num : inputDigits) {
                i++;
                // double value of every other digit.
                // If the number ultimately has an odd number of chars, we skip the first number.
                // If the number ultimately has an even number of chars, we double the first number.
                if (i % 2 == 0) {
                    validatingIDNumberOdd += luhnDouble(num);
                    validatingIDNumberEven += num;
                } else {
                    validatingIDNumberOdd += num;
                    validatingIDNumberEven += luhnDouble(num);
                }
            }
            if (i % 2 == 0) {
                System.out.println("Even Number " + inputNum + " checksum was: " + validatingIDNumberEven
                        + " was valid? " + (validatingIDNumberEven % 10 == 0));
            } else {
                System.out.println("Odd Number " + inputNum + " checksum was: " + validatingIDNumberOdd
                        + " was valid? " + (validatingIDNumberOdd % 10 == 0));
            }
        }
    }

    private static int luhnDouble(int num) {
        int doubled = num * 2;
        int summed = doubled % 10;
        if (doubled >= 10) {
            summed += 1;
        }
        return summed;
    }
}
