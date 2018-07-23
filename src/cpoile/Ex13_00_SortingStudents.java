package cpoile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ex13_00_SortingStudents {
    public static class Student implements Comparable<Student> {
        String name;
        double GPA;

        public Student(String n, double g) {
            this.name = n;
            this.GPA = g;
        }

        public int compareTo(Student target) {
            return -Double.compare(GPA, target.GPA);
        }

        public String toString() {
            return name + ": " + GPA;
        }
    }
    public static void main(String[] argh) {
        List<Student> students = new ArrayList<>(List.of(
                new Student("John", 3.4),
                new Student( "Adam", 3.5),
                new Student( "Ben", 3.6),
                new Student( "Aaron", 3.5),
                new Student( "Zach", 3.7)));

        print(students);

        Collections.sort(students);
        print(students);

        // this:
        Collections.sort(students, (s1, s2) -> {
            int compareRes = s1.compareTo(s2);
            return compareRes == 0 ? s1.name.compareTo(s2.name) : compareRes;
        });
        // can be replaced by this:
        Collections.sort(students, Comparator.comparing((Student s) -> s).thenComparing(s -> s.name));

        print(students);

    }
    private static void print(List<Student> students) {
        for (Student s: students)
            System.out.println(s);
        System.out.println();
    }
}
