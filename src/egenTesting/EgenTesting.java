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
        int posisjon = 45;                                  // 101101
        int filter = Integer.highestOneBit(posisjon) >> 0;  // 100000
        System.out.println(posisjon & filter);  // tror: 32

        filter = Integer.highestOneBit(posisjon) >> 1;  //  10000
        System.out.println(posisjon & filter);  // tror: 0

        filter = Integer.highestOneBit(posisjon) >> 2;  //   1000
        System.out.println(posisjon & filter);  // tror: 8

        filter = Integer.highestOneBit(posisjon) >> 3;  //    100
        System.out.println(posisjon & filter);  // tror: 4

        filter = Integer.highestOneBit(posisjon) >> 4;  //     10
        System.out.println(posisjon & filter);  // tror: 0

        filter = Integer.highestOneBit(posisjon) >> 5;  //      1
        System.out.println(posisjon & filter);  // tror: 1
    }
}
