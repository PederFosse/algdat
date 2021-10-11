package kompendieKode;

import java.util.StringJoiner;

public class Rekursjon {

    /**
     * Kode fra kompendiet - Programkode 1.5.1 a)
     */
    public static int a(int n)           // n må være et ikke-negativt tall
    {
        if (n == 0) return 1;              // a0 = 1
        else if (n == 1) return 2;         // a1 = 2
        else return 2*a(n-1) + 3*a(n-2);   // to rekursive kall
    }

    /**
     * Iterativ versjon av programkode 1.5.1 a)
     */
    public static int iterativA(int n) {    // n må være et ikke-negativt tall
        if (n == 0) return 1;
        else if (n == 1) return 2;

        int x = 0, y = 1, z = 1;

        for (int i = 0; i < n; i++) {
            z = 2 * y + 3 * x;
            x = y;
            y = z;
        }

        return z;

    }

    /**
     * Finner summen av kvadrattallene fra 1 til n, dvs 1^2 + 2^2 + 3^2 + ... + n^2
     * @param n må være større enn 0
     * @return
     */
    public static int sumKvadrattall(int n) {
        if (n == 1) return 1;
        return n*n + sumKvadrattall(n - 1);
    }

    /**
     * Finner summen av heltallene mellom k og n ved bruk av en "splitt og hersk" fremgangsmåte
     * @param   k heltall, mindre eller lik n
     * @param   n heltall, større eller lik k
     * @return  int, summen av heltallene i intervallet [k:n]
     */
    public static int sum(int k, int n) {
        if (k==n) return k;
        int m = (k + n) / 2;    // finner midten
        return sum(k, m) + sum(m + 1, n);
    }

    /**
     * Finner posisjonen til den største verdien i en heltallstabell,
     * ved bruk av "splitt og hersk" i intervallet [v:h]
     * @param a heltallstabell
     * @param v venstre
     * @param h høyre
     * @return indeks (int) til største verdi i tabellen
     */
    public static int oppgave9(int[] a, int v, int h) {
        if (v == h) return v;
        int m = (v + h) / 2;    // midten

        int mv = oppgave9(a, v, m);         // maks verdi til venstre for midten
        int mh = oppgave9(a, m + 1, h); // maks verdi til høyre for midten

        return a[mv] >= a[mh] ? mv : mh;   // sammenlikner og returner største verdi
    }

    public static void sjekkProgramkode151a() {
        StringJoiner sj = new StringJoiner(", ", "[", "]"); // stringjoiner object created

        for (int i = 0; i < 10; i++) {          // adding the values of a(i) to the stringjoiner object
            sj.add(String.valueOf(a(i)));
        }
        String withRecursion = sj.toString();   // saving the string created with recursive a(i)

        sj = new StringJoiner(", ", "[", "]");  // reset stringjoiner

        for (int i = 0; i < 10; i++) {          // add values of iterativA(i) to new stringjoiner object
            sj.add(String.valueOf(iterativA(i)));
        }
        String iterative = sj.toString();       // save string created with iterative iterativA(i)

        // Printing out the results
        System.out.println("Recursive: " + withRecursion);
        System.out.println("Iterative: " + iterative);
    }

    public static int tverrsum(int n)   {
        System.out.println("tverrsum(" + n + ") starter!");
        int sum = (n < 10) ? n : tverrsum(n / 10) + (n % 10);
        System.out.println("tverrsum(" + n + ") er ferdig!");
        return sum;
    }

    public static int euklid(int a, int b) {
        System.out.println("euklid(" + a + "," + b + ") ble kalt!");
        if (b == 0) return a;
        int r = a % b;  // r er resten
        return euklid(b, r);
    }

}
