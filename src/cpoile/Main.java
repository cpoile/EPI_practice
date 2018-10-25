package cpoile;

import cpoile.sedgewick.Ch2_1_Sorts;
import cpoile.sedgewick.Ch2_4_HeapSort;
import cpoile.sedgewick.Ch2_4_IndexedMinPQ;
import cpoile.sedgewick.Ch2_4_PriorityQueues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        /*System.out.println("Hello World");
        System.out.println(2.0 - 1.1);
        System.out.println(1 / 10.0);

        Scanner in = new Scanner(new File("inputTest.txt"));

        for (int count = 1; in.hasNext(); count++)
            System.out.println(Integer.parseInt(in.nextLine()) * count);*/

//        Ex0_01.main();
        //Ex02_01.main();
        //Ch1_3_Stack.main();
//        Ex02_02.main();
        //MostFreqWords.main();
        //Ch1_3_Stack.main();
//        Ch1_5_UnionFind1.main(new String[] {"tinyUF.txt"});
//        Ch1_5_UnionFind1.main(new String[] {"mediumUF.txt"});
//        Ch1_5_UnionFind1.main(new String[] {"largeUF.txt"});
        //Ex13_00_SortingStudents.main(new String[]{});
        //FileInputStream is = new FileInputStream(new File("input-oned.txt"));
        //System.setIn(is);
        //OneDArrayPart2.main();
//        FileInputStream is = new FileInputStream(new File("input08.txt"));
//        System.setIn(is);
//        JavaDeque.main(new String[]{});

//        FileInputStream is = new FileInputStream(new File("visitor-input01.txt"));
//        //FileInputStream is = new FileInputStream(new File("visitor-test1.txt"));
//        System.setIn(is);
//        Visitor.main(new String[]{});

//        FileInputStream is = new FileInputStream(new File("tinyG.txt"));
//        System.setIn(is);
//        Ch4_1_Graphs.main(new String[]{});

        FileInputStream is = new FileInputStream(new File("tinyChar.txt"));
        System.setIn(is);
        //Ch2_1_Sorts.main();
        //Ch2_4_HeapSort.main();
        /*is = new FileInputStream(new File("tinyTale.txt"));
        System.setIn(is);
        Ch2_1_Sorts.main();*/

        //Ch2_4_PriorityQueues.sort();
        Ch2_4_IndexedMinPQ.main();

    }
}