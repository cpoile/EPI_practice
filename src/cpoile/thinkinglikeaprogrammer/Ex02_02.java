package cpoile.thinkinglikeaprogrammer;

import java.util.Scanner;

public class Ex02_02 {
    public static void main(String[]... argh) {
        MsgDecoder md = new MsgDecoder();

        int[] msg = {18,12312,171,763,98423,1208,216,11,500,18,241,0,32,20620,27,10};

        for (int i : msg) {
            Character res = md.process(i);
            if (res != null)
                System.out.print(res);
        }

        Scanner in = new Scanner("inputMostFreqWords.txt");
    }
}
