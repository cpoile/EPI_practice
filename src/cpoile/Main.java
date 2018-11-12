package cpoile;

import cpoile.sedgewick.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        //Ch1_1_BinarySearch.main();
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
        //Ch2_4_IndexedMinPQ.main();

        Ch3_1_SymbolTables.main();

        //Ch1_5_UnionFind2.main("tinyUF.txt");
        //Ch1_3_ConvertToBase.main();
    }
}