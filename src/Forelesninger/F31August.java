package Forelesninger;

import java.util.Arrays;

public class F31August {

    // trykk ctrl + shift + T for å generere test
    public static void myFirstSort(int[] values) {
        // sorteringsmetode fra forelesning

        Arrays.sort(values); // tullemetode
    } // myFirstSort

    public static int[] findTwoMaxIndices(int[] a, int fra, int til) {

        // tester for feil input
        if (til - fra < 2) throw new ArrayIndexOutOfBoundsException("feil i grenser");
        if (fra < 0 ) throw new ArrayIndexOutOfBoundsException("fra er negativ");
        if (til < 2) throw new ArrayIndexOutOfBoundsException("til er ikke høy nok");
        if (til > a.length) throw new ArrayIndexOutOfBoundsException("til er for høy.");


        // verdier {1, 6, 3, 5, 9}
        int indexMax = fra;
        int indexNestMax = fra + 1;

        int max = a[indexMax];
        int nestMax = a[indexNestMax];

        if (max < nestMax) { // refaktorere til swap metode
            int temp = indexMax;
            indexMax = indexNestMax;
            indexNestMax = temp;

            temp = max;
            max = nestMax;
            nestMax = temp;
        }

        // status: sett på de to første elementer og definert det største og nest største av dem
        // i variabler max og nestMax

        for(int i = fra + 2; i < til; i++) {
            if (a[i] > nestMax) {   // tall er større enn nest max
                if (a[i] > max) {   // tallet er større enn max
                    nestMax = max;
                    indexNestMax = indexMax;

                    indexMax = i;
                    max = a[i];
                } else {            // tallet er mellom nestMax og max
                    indexNestMax = i;
                    nestMax = a[i];
                }
            }
        }

        int[] values =  {indexNestMax, indexMax};
        return values;
    } // nestMaks
}
