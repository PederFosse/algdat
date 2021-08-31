package kompendieKode;

import hjelpeklasser.Tabell;

public class Program {
    public static void main(String[] args) {
        int[] a = Tabell.randPerm(20);

        for (int k : a) System.out.print(k + " ");

        int m = Tabell.maks(a, 10, 0);

        System.out.println("\nStørste verdi ligger på plass: " + m);
    } // main
} // class Program
