package hjelpeklasser;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class LenketKø<T> implements Kø<T> {

    private static final class Node<T> {
        private T verdi;    // nodens verdi
        private Node<T> neste;  // peker til neste node

        Node(Node<T> neste) {
            verdi = null;
            this.neste = neste;
        }
    } // class Node

    private Node<T> fra, til;   // fra: først i køen, til: etter den siste
    private int antall;         // antall i køen

    private static final int START_STØRRELSE = 8;

    public LenketKø(int størrelse) {
        til = fra = new Node<>(null);   // lager den første noden

        Node<T> p = fra;    // hjelpevariabel
        for (int i = 1; i < størrelse; i++) {
            p = new Node<>(p);  // lager resten av nodene
        }
        fra.neste = p;  // for å få en sirkel
        antall = 0;     // ingen verdier foreløpig
    }

    public LenketKø() {
        this(START_STØRRELSE);
    }

    public boolean leggInn(T verdi) {
        til.verdi = verdi;      // legger inn bakerst
        if (til.neste == fra) { // køen vil bli full, må utvides
            til.neste = new Node<>(fra);    // ny node mellom til og fra
        }

        til = til.neste;    // flytter til
        antall++;           // øker antallet

        return true;        // vellykket innlegging
    }

    public T kikk() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return fra.verdi;   // returnerer verdien
    }

    public T taUt() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");

        T tempverdi = fra.verdi;    // tar vare på verdien i fra
        fra.verdi = null;           // nuller innholdet i fra

        fra = fra.neste;    // flytter fra
        antall--;           // reduserer antallet

        return tempverdi;   // returnerer verdien
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean nullstill() {
        return false;
    }

    public static <T> void sorter(Kø<T> kø, Stakk<T> stakk, Comparator<? super T> c) {
        int n = kø.antall();

        while (n > 0) {
            stakk.leggInn(kø.taUt());   // legger inn første element i køen

            for (int i = 1; i < n; i++) {
                T p = kø.taUt();    // tar ut første element i køen
                if (c.compare(p, stakk.kikk()) > 0) {   // sammenlikner p og øverste element i stakk
                    kø.leggInn(stakk.taUt());   // flytter stakk sitt øverste element til køen
                    stakk.leggInn(p);   // legger p inn i stakk
                } else {
                    kø.leggInn(p);  // legger p bakerst i køen
                }
            }
            n--;
        }
        while (!stakk.tom()) {
            kø.leggInn(stakk.taUt());   // legger inn stakk i køen
        }
    }

    public String toString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = fra;

        for (int i = 0; i < antall; i++, p = p.neste)
        {
            s.add(p.verdi.toString());
        }

        return s.toString();
    }

    public static void main(String[] args) {
        Integer[] a = Tabell.randPermInteger(10);

        Kø<Integer> kø = new LenketKø<>();
        for (Integer i : a) kø.leggInn(i);

        System.out.println(kø);    // usortert

        Stakk<Integer> stakk = new TabellStakk<>();

        sorter(kø, stakk, Comparator.naturalOrder());

        System.out.println(kø);    // sortert
    }
} // class LenketKø
