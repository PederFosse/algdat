package hjelpeklasser;

import java.util.Arrays;
import java.util.Random;

public class Tabell {
    private Tabell() {}; // privat standardkonstruktør - hindrer instansiering

    public static void main(String[] args) {

    }

    public static void bytt(int[] a, int i, int j) { //programkode 1.1.8 d)
        int temp = a[i]; a[i] = a[j];a[j] = temp;
    }

    public static int[] randPerm(int n) { // programkode 1.1.8 f som gir en helt tilfeldig heltallsliste uten tilbakelegging med n elementer
        Random r = new Random(); // randomgenerator
        int[] a = new int[n]; // en tabell med plass til n tall

        Arrays.setAll(a, i -> i+1); // legger inn tallene 1, 2, ... , n

        for (int k = n-1; k > 0; k--){
            int i = r.nextInt(k+1);
            bytt(a, k, i);
        }

        return a;
    }

    public static int kostnader(int[] a) { // faste kostnader
        int m = 0;
        for (int i=1; i < a.length; i++){} // tom blokk
        return m;
    }

    public static int maks(int[] a, int fra, int til) { // programkode 1.2.1 c)
        if (fra < 0 || til > a.length || fra >= til) throw new IllegalArgumentException("Illegalt intervall");

        int m = fra;                // indeks til største verdi i a[fra:til>
        int maksverdi = a[fra];     // største verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++) {
            if (a[i] > maksverdi) {
                m = i;              // indeks til største verdi oppdateres
                maksverdi = a[m];   // største verdi oppdateres
            }
        }

        return m;
    }

    public static int maks(int[] a) {
        return maks(a, 0, a.length);
    }

    public static int min(int[] a, int fra, int til) { // oppgave S121o1
        if (fra < 0 || til > a.length || fra >= til) throw new IllegalArgumentException("Illegalt intervall");

        int m = fra;                // indeks til minste verdi i a[fra:til>
        int minverdi = a[fra];      // minste verdi i a[fra:til>

        for (int i = fra + 1; i < til; i++) {
            if (a[i] < minverdi) {
                m = i;              // indeks til minste verdi oppdateres
                minverdi = a[i];    // minste verdi oppdateres
            }
        }

        return m;
    }

    public static int min(int[] a) {
        return min(a, 0, a.length);
    }
}
