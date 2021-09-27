package egenTesting;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class EgenTesting {
    public static void print(Object o) {
        try{
            try {
                int[] l = (int[]) o;
                System.out.println(Arrays.toString(l));
                return;
            } catch (Exception e) {
            }
            try {
                String[] l = (String[]) o;
                System.out.println(Arrays.toString(l));
                return;
            } catch (Exception e) {
            }
            try {
                char[] l = (char[]) o;
                System.out.println(Arrays.toString(l));
                return;
            } catch (Exception e) {
            }
            double[] l = (double[]) o;
            System.out.println(Arrays.toString(l));
            return;
        } catch (Exception e) {
            System.out.println(o);
        }
    }

//    public static void print(Object[] o) {
//        String str = "[";
//        for (int i = 0; i < o.length; i++) {
//            str += o[i];
//            str += ", ";
//        }
//        str = str.replaceAll(", $", "]");
//        System.out.println(str);
//        System.arraycopy();
//    }

    public static void main(String[] args) {
        int value = 5;
        String aString = "hello";
        char aChar = 'f';
        double aDouble = 0.2;
        int[] values = {1, 2, 3, 4, 5};
        String[] strings = {"hello", "world"};
        char[] chars = {'a', 'b', 'c'};
        double[] doubles = {0.2, 0.3, 0.5};

        print(value);
        print(aString);
        print(aChar);
        print(aDouble);
        print(values);
        print(strings);
        print(chars);
        print(doubles);
    }
}
