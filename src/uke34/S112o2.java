package uke34;

public class S112o2 { // lag en min-metode på samme måte som programkode 1.1.2 sin maks-metode
    public static void main(String[] args) {
        // tester oppgave med en enkel liste
        int[] testArray = {34, 4, 13, 33, 8, 4, 900};

        int index = min(testArray);

        System.out.println("Det minste tallet ligger på index " + index + " og har verdien " + testArray[index]);
    }

    private static int min(int[] a) {
        if (a.length < 1) throw new java.util.NoSuchElementException("Tabellen a er tom!");

        int m = 0; // index til foreløpig største verdi

        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[m]) { // bytter til <= hvis jeg skal ha index på det siste elementet i listen med lavest verdi
                m = i; // oppdaterer index på minste verdi
            }
        }

        return m;
    }
}
