package uke34;

import java.util.Arrays;
import java.util.Random;

public class S1110o1 { // class Program som beskrevet i kompendiet (programkode 1.1.9)

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

    public static int antallMaks(int[] a) { // teller opp i a
        int antall = 0;
        int maksverdi = a[0];

        for(int i = 1; i < a.length; i++){ // går gjennom tabellen a
            if(a[i] > maksverdi) { // a[i] er større enn største foran
                antall++;
                maksverdi = a[i];
            }
        }

        return antall;
    }

    public static int kostnader(int[] a) { // faste kostnader
        int m = 0;
        for (int i=1; i < a.length; i++){} // tom blokk
        return m;
    }
}
