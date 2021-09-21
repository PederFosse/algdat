package Forelesninger.F20September;

import java.util.Arrays;

public class RecursivePermutation {
    public static void main(String[] args) {
        int[] values = {1, 2, 3};
        recursivePermutation(values, 0);
    }

    static void recursivePermutation(int[] values, int k) {
        if (k == values.length) {
            System.out.println(Arrays.toString(values));
        }

        for (int i = k; i < values.length; i++) {
            swap(values, i, k);
            recursivePermutation(values, k+1);
            swap(values, i, k);
        }
    }

    public static void swap(int[] values, int m, int n) {
        int temp = values[m];
        values[m] = values[n];
        values[n] = temp;
    }
}
