package Forelesninger.F7September;

import static egenTesting.EgenTesting.print;
import static hjelpeklasser.Tabell.*;

public class BubbleSort {
    public static void main(String[] args) {
        int[] values = {5, 6, 9, 2, 8, 3, 1, 10, 4, 7, 14, 17, 32, 18, 13};

        bubblesort(values);

        print(values);
    } // main

    public static void bubblesort(int[] values) {
        // Two for loops:
        // outer loop: sorts one number per iteration
        for (int i = 0; i < values.length - 1; i++) { // i < values.length - 1 fordi siste tallet blir sortert av seg selv
            // inner loop: Bubbles up number to right position
            for (int j = values.length - 1; j > i; j--) {
                // sjekk om tall på posisjon j og j - 1 er sortert riktig
                if (values[j] < values[j-1]) {
                    bytt(values, j, j-1);
                }
            } // for
        } // for
    }
} // BubbleSort
