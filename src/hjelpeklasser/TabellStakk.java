package hjelpeklasser;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class TabellStakk<T> implements Stakk<T> {
    private T[] a;  // en T-tabell
    private int antall; // antall verdier på stakken

    public TabellStakk() {  // konstruktør - tabellengde 8
        this(8);
    }

    @SuppressWarnings("unchecked") // pga. konverteringen: Object[] -> T[]
    public TabellStakk(int lengde) {    // valgfri lengde
        if (lengde < 0) {
            throw new IllegalArgumentException("Negativ tabellengde!");
        }

        a = (T[])new Object[lengde];   // opretter tabellen
        antall = 0;
    }


    public void leggInn(T verdi) {
        if (antall == a.length) {
            a = Arrays.copyOf(a, antall == 0 ? 1 : 2*antall);   // dobler lengde
        }

        a[antall++] = verdi;
    }

    public T kikk() {
        if (antall == 0){
            throw new NoSuchElementException("Stakken er tom!");
        }

        return  a[antall - 1];  // returnerer den øverste verdien
    }

    public T taUt() {
        if (antall == 0) {
            throw new NoSuchElementException("Stakken er tom!");
        }
        T temp = a[--antall];   // tar vare på det øverste objektet og reduserer antall med 1
        a[antall] = null;

        return temp;    // returnerer den øverste verdien
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public void nullstill() {
        for (int i = 0; i < antall; i++) {
            a[i] = null;
        }
        antall = 0;
    }

    public static <T> void snu(Stakk<T> A) {
        Stakk<T> B = new TabellStakk<T>();
        Stakk<T> C = new TabellStakk<T>();

        while (!A.tom()) B.leggInn(A.taUt());
        while (!B.tom()) C.leggInn(B.taUt());
        while (!C.tom()) A.leggInn(C.taUt());
    }

    public static <T> void kopier(Stakk<T> A, Stakk<T> B) {
        Stakk<T> C = new TabellStakk<T>();
        while (!A.tom()) C.leggInn(A.taUt());
        while (!C.tom())
        {
            T t = C.taUt();
            B.leggInn(t);
            A.leggInn(t);
        }
    }

    public String toString() {
        StringJoiner sj = new StringJoiner("[", ", ", "]");
        for (int i = antall-1; i >= 0; i--) {
            sj.add(a[i] == null ? "null" : a[i].toString());
        }
        return sj.toString();
    }
}
