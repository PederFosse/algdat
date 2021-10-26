package hjelpeklasser;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class TabellKø<T> implements Kø<T> {
    private T[] a;  // en tabell
    private int fra;    // pososjonen til den første i køen
    private int til;    // posisjonen til den første ledige plass

    @SuppressWarnings("unchecked")
    public TabellKø(int lengde) {
        if (lengde < 1) throw new IllegalArgumentException("Må ha positiv lengde");

        a = (T[]) new Object[lengde];
        fra = til = 0;  // a[fra:til> er tom
    }

    public TabellKø() { // standardkonstruktør
        this(8);
    }

    public boolean leggInn(T verdi) {
        a[til] = verdi; // ny verdi bakerst
        til++;          // øker til med 1
        if (til == a.length) {
            til = 0;
        }
        if (fra == til) {
            a = utvidTabell(2*a.length);    // sjekker og dobler
        }
        return true;
    }

    private T[] utvidTabell(int lengde) {
        @SuppressWarnings("unchecked")
        T[] b = (T[]) new Object[lengde];

        System.arraycopy(a, fra, b, 0, a.length - fra);
        System.arraycopy(a, 0, b, a.length - fra, fra);

        fra = 0;
        til = a.length;
        return b;
    }

    public T kikk() {
        return a[fra];
    }

    public T taUt() {
        if (fra == til) throw new NoSuchElementException("Køen er tom!");

        T temp = a[fra];    // tar vare på den første i køen
        a[fra] = null;
        fra++;
        if (fra == a.length) fra = 0;
        return temp;
    }

    public int antall() {
        return fra <= til ? til - fra : a.length + til - fra;
    }

    public boolean tom() {
        return fra == til;
    }

    public boolean nullstill() {
        while (fra != til) {
            a[fra++] = null;
            if (fra == a.length) fra = 0;
        }
        return true;
    }

    public int indeksTil(T t) {
        int k = fra;

        while (k != til) {
            if (t.equals(a[k])) {
                return fra <= k ? k - fra : a.length + k - fra;
            }
            k++;
            if (k == a.length) {
                k = 0;
            }
        }
        return -1;  // ikke funnet
    }

    public static <T> void snu(Stakk<T> a) {
        Kø<T> b = new TabellKø<T>();
        while(!a.tom()) {
            b.leggInn(a.taUt());
        }
        while(!b.tom()) {
            a.leggInn(b.taUt());
        }
    }

    public static <T> void snu(Kø<T> a) {
        Stakk<T> b = new TabellStakk<T>();
        while(!a.tom()) {
            b.leggInn(a.taUt());
        }
        while (!b.tom()) {
            a.leggInn(b.taUt());
        }
    }

    public String toString() {
        if (tom()) return "[]";
        StringJoiner sj = new StringJoiner("[", ", ", "]");
        while (fra != til) {
            sj.add(a[fra++].toString());
            if (fra == a.length) fra = 0;
        }
        return sj.toString();
    }
} // class TabellKø
