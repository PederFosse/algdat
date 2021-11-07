package hjelpeklasser;

import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SBinTre<T> {
    private static final class Node<T> {
        private T verdi;    // nodens verdi
        private Node<T> venstre, høyre;     /// venstre og høyre barn

        private Node(T verdi, Node<T> v, Node<T> h) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }

        public String toString() {
            return verdi.toString();
        }
    } // class Node

    private Node<T> rot;
    private int antall;
    private final Comparator<? super T> comp;

    public SBinTre(Comparator<? super T> c) {
        rot = null;
        antall = 0;
        comp = c;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre() {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    public static <T> SBinTre<T> sbintre(Comparator<? super T> c) {
        return new SBinTre<>(c);
    }

    public static <T> SBinTre<T> sbintre(Stream<T> s, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);
        s.forEach(tre::leggInn);
        return tre;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre(Stream<T> s) {
        return sbintre(s, Comparator.naturalOrder());
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Null-verdier ikke tillat.");

        Node<T> p = rot, q = null;  // p starter i roten
        int cmp = 0;

        while (p != null) {
            q = p;  // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);   // sammenlikner
            p = cmp < 0 ? p.venstre : p.høyre;  // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi);  // oppretter ny node

        if (q == null) rot = p; // p blir rotnode
        else if (cmp < 0) q.venstre = p;
        else q.høyre = p;

        antall++;
        return true;
    }

    private static void høyde(Node<?> p, int nivå, IntObject o) {
        if (nivå > o.get()) o.set(nivå);
        if (p.venstre != null) høyde(p.venstre, nivå + 1, o);
        if (p.høyre != null) høyde(p.høyre, nivå + 1, o);
    }

    public int høyde() {
        IntObject o = new IntObject(-1);
        if (!tom()) høyde(rot, 0, o);    // roten har nivå 0
        return o.get();     // inneholder høyden
    }

    private static <T> void inorden(Node<T> p, Consumer<? super T> oppgave) {
        if (p.venstre != null) inorden(p.venstre, oppgave);
        oppgave.accept(p.verdi);
        if (p.høyre != null) inorden(p.høyre, oppgave);
    }

    public void inorden(Consumer<? super T> oppgave) {
        inorden(rot, oppgave);
    }

    public String toString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");
        if (!tom()) inorden(x -> s.add(x != null ? x.toString() : "null"));
        return s.toString();
    }

    private static <T> Node<T> balansert(T[] a, int v, int h) {
        if (v > h) return null; // tomt intervall -> tomt tre

        int m = (v + h) / 2;
        T verdi = a[m];

        while (v < m && verdi.equals(a[m - 1])) {
            m--;    // til venstre
        }

        Node<T> p = balansert(a, v, m - 1); // venstre subtre
        Node<T> q = balansert(a, m + 1, h); // høyre subtre

        return new Node<>(verdi, p, q); // rotnoden
    }

    public static <T> SBinTre<T> balansert(T[] a, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);  // tomt tre
        tre.rot = balansert(a, 0, a.length - 1);    // bruker rekursiv metode
        tre.antall = a.length;
        return tre;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> balansert(T[] a) {
        return balansert(a, Comparator.naturalOrder());
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;    // treet har ikke null-verdier

        Node<T> p = rot;
        Node<T> q = null;

        while (p != null) {
            if (comp.compare(verdi, p.verdi) < 0) {
                p = p.venstre;
            } else {
                q = p;
                p = p.høyre;
            }
        }

        return  q != null && comp.compare(verdi, q.verdi) == 0;
    }

    public static void main(String[] args) {
        Integer[] a = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2};      // en tabell

        SBinTre<Integer> tre1 = balansert(a);    // et binært søketre

        System.out.println(tre1.antall());
        System.out.println(tre1.høyde());
        System.out.println(tre1);
    }
} // class SBinTre
