package hjelpeklasser;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

public class Tabell {
    private Tabell() {} // privat standardkonstruktør - hindrer instansiering

    public static void main(String[] args) {
        int[] values = {4, 2, 3, 6, 19};
        skriv(values, 0, values.length);

    }

    public static void bytt(int[] a, int i, int j) { //programkode 1.1.8 d)
        int temp = a[i]; a[i] = a[j];a[j] = temp;
    }

    public static void bytt(char[] c, int i, int j) { // S122o3
        char temp = c[i];
        c[i] = c[j];
        c[j] = temp;
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
        fraTilKontroll(a.length, fra, til);

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
        fraTilKontroll(a.length, fra, til);

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

    /**
     * fraTilKontroll           Checks that from and to values are correct and loops will not exceed the length
     *                          of the array
     *
     * @param tabellLengde      Integer, the length of the array
     * @param fra               Integer, from-value
     * @param til               Integer, to-value
     */
    public static void fraTilKontroll(int tabellLengde, int fra, int til) {
        if (tabellLengde == 0) throw new IllegalArgumentException("lengden på tabellen er 0");
        if (fra < 0) throw new ArrayIndexOutOfBoundsException("Fra er mindre enn 0.");
        if (til > tabellLengde) throw new ArrayIndexOutOfBoundsException("til er større enn lengden på tabellen.");
        if (fra > til) throw new IllegalArgumentException("Fra er høyere enn til");
        if (fra == til) throw new NoSuchElementException("Tomt tabellintervall.");
    }

    public static void vhKontroll(int tabellLengde, int v, int h) {
        if (v < 0) throw new ArrayIndexOutOfBoundsException("v: " + v + " er mindre enn 0");
        if (h >= tabellLengde) throw new ArrayIndexOutOfBoundsException("h("+h+") >= tabellengde("+tabellLengde+")");
        if (v > h + 1) throw new IllegalArgumentException("v="+v+" h="+h);
    }

    /**
     * metode som tar inn en liste(int[]) og returnerer indeks til de to største verdiene i den listen
     * metoden benytter seg av vakspost
     * @param a liste(heltall), ikke sortert
     * @return en liste(int[]) i format [index til maksverdi, indeks til nestmaksverdi]
     */
    public static int[] nestMaks(int[] a) {
        int n = a.length;
        if (n < 2) throw new NoSuchElementException("a.length("+n+") < 2!");

        int sist = a.length - 1;
        int temp = a[sist];         // tar vare på siste verdi i tabellen

        int m = 0;      // første posisjon
        int nm = sist;     // siste posisjon

        // bytte m og nm hvis temp > a[0]
        if (temp > a[0]) {
            m = sist;
            nm = 0;
        }

        int maksverdi = a[m];                   // største verdi
        int nestmaksverdi = a[nm];              // nest største verdi

        a[sist] = 0x7fffffff;       // Bruker det største heltallet som siste verdi (vaktpost)

        for (int i=1; ; i++) {                      // i=1 fordi plass 0 allerede er sjekket
            if (a[i] > nestmaksverdi) {         // denne blir sann på slutten grunnet vaktposten
                if (i == sist) {                // sjekker om vi er ferdige
                    a[sist] = temp;             // sjekker ikke temp fordi den allerede ble sjekket i starten
                    return new int[] {m, nm};   // returnerer helstallsliste med indeks for [maks, nestmaks]
                }
                if (a[i] > maksverdi) {
                    nm = m;
                    nestmaksverdi = maksverdi;  // ny nest størst verdi

                    m = i;
                    maksverdi = a[m];           // ny maksverdi
                } else {
                    nm = i;
                    nestmaksverdi = a[nm];      // ny nest størst verdi
                }
            }
        } // for
    } // nestMaks

    public static int[] nestMaks2(int[] a) { // en turnering
        int n = a.length;   // for å forenkle notasjonen

        if (n < 2) {        // må ha minst 2 verdier
            throw new IllegalArgumentException("a.length (" + n + ") > 2!");
        }

        int[] b = new int[2 * n];                 // turneringstree
        System.arraycopy(a, 0, b, n, n); // legger a bakerst i b

        for (int k = 2 * n - 2; k > 1; k -= 2) {    // lager turneringstreet
            b[k / 2] = Math.max(b[k], b[k + 1]);
        }

        int maksverdi = b[1], nestmaksverdi = Integer.MIN_VALUE;

        for (int m = 2 * n - 1, k = 2; k < m; k *= 2) {
            int tempverdi = b[k + 1]; // ok hvis maksverdi er b[k]

            if (maksverdi != b[k]) {
                tempverdi = b[k];
                k++;
            }

            if (tempverdi > nestmaksverdi) {
                nestmaksverdi = tempverdi;
            }
        }

        return new int[]{maksverdi, nestmaksverdi}; // størst og nest størst

    } // nestMaks


    /**
     * Metode som skriver ut et array(integer) [fra:til> med mellomrom mellom elementer,
     * uten mellomrom eller linjeskift på slutten
     * @param a     et array (integer)
     * @param fra   integer for index på hvor utskriften skal starte
     * @param til   integer for index på hvor utskrift skal slutte, utskrift skriver ut elementet til venstre for til,
     *              men ikke elementet på plass "til"
     */
    public static void skriv(int[] a, int fra, int til) {
        fraTilKontroll(a.length, fra, til); // error handling

        String s = "";
        for (int i = fra; i < til; i++) {
            s += a[i] + " ";
        }
        s = s.strip(); // remove trailing space from string
        System.out.print(s);
    }

    public static void skriv(int[] a) {
        skriv(a, 0, a.length);
    }

    public static void skrivln(int[] a, int fra, int til) {
        skriv(a, fra, til);
        System.out.println();
    }

    public static void skrivln(int[] a) {
        skriv(a);
        System.out.println();
    }

    public static void sjekkTomArray(int[] a) {
        int n = a.length;
        if (n < 2) throw new NoSuchElementException("a.length(" + n + ") < 2");
    }

    /**
     * snur intervallet a[v:h] in place
     * @param a heltallsliste
     * @param v venstre grense (int)
     * @param h høyre grense (int)
     */
    public static void snu(int[] a, int v, int h) {
        while (v < h) {
            bytt(a, v++, h--);
        }
    }

    /**
     * snur en tabell fra og med v og ut tabellen
     * @param a heltallsliste
     * @param v venstre grense
     */
    public static void snu(int[] a, int v) {
        snu (a, v, a.length - 1);
    }

    /**
     * snur hele tabellen
     * @param a heltallsliste
     */
    public static void snu(int[] a) {
        snu(a, 0);
    }

    /**
     * endrer tabell(int[] a) in place til den neste leksikografiske permutasjonen av seg selv
     * @param a heltallstabell
     * @return true dersom det finnes en ny permutasjon som er lenger "bak" leksikografisk
     */
    public static boolean nestePermutasjon(int[] a) {
        int i = a.length - 2;   // i starter nest bakerst
        while (i >= 0 && a[i] > a[i+1]) i--;    // går mot venstre
        if (i < 0) return false;                // a = {n, n-1, . . . , 2, 1}

        int j = a.length - 1;                   // j starter bakerst
        while (a[j] < a[i]) j--;                // stopper når a[j] > a[i]
        bytt(a, i, j);                          // bytter og snur
        snu(a, i+1);
        return true;    // en ny permutasjon
    } // nestePermutasjon

    /**
     * Teller opp og returnerer antall inversjoner i en tabell.
     * Dersom listen ikke inneholder noen inversjoner betyr det at den er sortert.
     * @param a heltallstabell
     * @return integer som beskriver antall inversjoner i tabellen
     */
    public static int inversjoner(int[] a) {
        int antall = 0;                     // antall inversjoner
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a.length; j++) {
                if (a[i] > a[j]) antall++;  // en inversjon siden i < j
            }
        }
        return antall;
    }

    /**
     * metode som sjekker om en tabell er sortert
     * @param a heltallstabell
     * @return true dersom tabellen er sortert, false dersom ikke
     */
    public static boolean erSortert(int[] a) {

        for (int i = 1; i < a.length; i++) {    // starter med i = 1
            if (a[i-1] > a[i]) return false;    // en inversjon
        }

        return true;
    }

    public static int boble(int[] a) {
        int antall = 0;     // antall ombyttinger i tabellen

        for (int i = 1; i < a.length; i++) {    // starter med i=1
            if (a[i-1] > a[i]) {        // sammenlikner to verdier
                bytt(a, a[i-1], a[i]);  // bytter plass på verdiene om de er plassert feil
                antall++;               // inkrementerer antall hvis man byttet
            }
        }

        return antall;  // returnerer antall
    }
} // class Tabell
