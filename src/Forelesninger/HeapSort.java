package Forelesninger;

import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int[] values = {-99, 5, 9, 8, 3, 1};
        heapsort(values);
    }

    private static void bytt(int[] a, int i1, int i2) {
        int temp = a[i1];
        a[i1] = a[i2];
        a[i2] = temp;
    }

    private static void heapsort(int[] values) {
        System.out.println(Arrays.toString(values));
        heapify(values);

        for (int i = 1; i < values.length; i++) {
            int first = 1;
            int last = values.length - i;

            System.out.println("Bytter " + first + " med " + last);
            bytt(values, first, last);

            int current = first;
            int left_child = current * 2;
            int right_child = current * 2 + 1;

            while(true) {
                int left_value = values[left_child];
                int right_value = values[right_child];

                if (left_child < last
                        && left_value < right_value
                        && left_value < values[current]) {
                    // venstre barn er minst og mindre enn parent
                    System.out.println("Bytter " + current + " med " + left_child);

                    bytt(values, left_child, current);
                    current = left_child;
                } else if (right_child < last
                        && right_value < left_value
                        && right_value < values[current]) {
                    // høyre barn er minst og mindre enn parent
                    System.out.println("Bytter " + current + " med " + right_child);

                    bytt(values, right_child, current);
                    current = right_child;
                } else break;   // vi har funnet riktig plass
                left_child = current * 2;
                right_child = current * 2 + 1;
            }
        }
        System.out.println(Arrays.toString(values));
    }

    private static void heapify(int[] values) {
        for (int i = 1; i < values.length; i++) {
            int current = i;
            int parent = i / 2;

            while(parent > 0 && values[parent] > values[current]) {
                System.out.println("Bytter " + current + " med " + parent);
                // bytter verdier i array
                bytt(values, parent, current);

                current = parent;
                parent = current / 2;
            }
        }
    }
}
