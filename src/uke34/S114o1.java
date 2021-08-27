package uke34;

public class S114o1 {

    public static void main(String[] args) {
        int[] deloppgave1 = {10, 5, 7, 2, 9, 1, 3, 8, 4, 6};
        int[] deloppgave2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // hvor mange grunnleggende operasjoner for deloppgave1?
        // her er x = 0 og n = 10, totale ops = 4*10 + 3*0 + 1 = 41

        // hvor mange grunnleggende operasjoner for deloppgave2?
        // her er  x = 9 og n = 10, totale ops = 4*10 + 3*9 + 1 = 68

        // test mot eksempel i figur 1.1.2
        // programkode 1.1.2 brukte 77 operasjoner
        // x = 2 og n = 15, totale ops = 4*15 + 3*2 + 1 = 67
    }

    public static int maks(int[] a) { // programkode 1.1.4
        int m = 0; // hjelpevariabel m med startverdi 0 (1 operasjon)
        int maksverdi = a[0]; // hjelpevariable maksverdi med starverdi tilsvarende første element i a (2 ops)
        for (int i = 1; i < a.length; i++) // løkkevariabel i (1 operasjon) i < a.length utføres n ganger i++ utføres n-1 ganger (totalt 2*n ops)
            if (a[i] > maksverdi) { // utføres n-1 ganger og består av en tabelloperasjon og en sammenlikning (2(n-1) operasjoner = 2n - 2)
                maksverdi = a[i]; // tilordning og tabelloperasjon skjer x ganger (2x ops)
                m = i; // tilordning skjer x ganger
            }
        return m; // verdi returneres (1 operasjon)
        // totale operasjoner: 3 + 2n + 2n - 2 + 2x + x = 4n + 3x + 1
    }
}
