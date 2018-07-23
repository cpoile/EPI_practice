package cpoile.sedgewick;

public class Ch1_3_Stack {
    private static String in1 = "to be or - not - - to then this will fail if we get too big";

    public static void main(String[]... argh) {
        validate(in1, "to to");
    }

    private static void validate(String input, String expected) {
        //StackOfStrings ms = new StackOfStrings();
        //FixedStackStringsArray ms = new FixedStackStringsArray();
        ExpandingStack ms = new ExpandingStack();

        for (String s : input.split("\\s")) {
            if (s.equals("-")) ms.pop();
            else ms.push(s);
        }

        System.out.println("Stack size is: " + ms.size());
        System.out.println("Validating: " + expected.equals(ms.toString()));
        System.out.println("Stack has: " + ms + "; expected: " + expected);
        System.out.println("Stack size is: " + ms.size());
    }
}
