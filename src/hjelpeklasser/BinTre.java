package hjelpeklasser;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

public class BinTre<T> {
    private static final class Node<T> {
        private T verdi;
        private Node<T> venstre;
        private Node<T> høyre;

        private Node(T verdi, Node<T> v, Node<T> h) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
        }

        private Node(T verdi) {
            this.verdi = verdi;
        }
    } // class Node

    private Node<T> rot;
    private int antall;

    public BinTre() {
        rot = null;
        antall = 0;
    }

    public BinTre(int[] posisjon, T[] verdi) {
        if (posisjon.length > verdi.length) throw new IllegalArgumentException("verditabellen har for få elementer!");
        for (int i = 0; i < posisjon.length; i++) {
            leggInn(posisjon[i], verdi[i]);
        }
    }

    public final void leggInn(int posisjon, T verdi) {
        if (posisjon < 1) {
            throw new IllegalArgumentException("Posisjon (" + posisjon + ") < 1!");
        }

        Node<T> p = rot, q = null;
        int filter = Integer.highestOneBit(posisjon) >> 1;  // filter = 100...00

        while(p != null && filter > 0) {
            q = p;
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;
            filter >>= 1;   //bitforskyver filter
        }

        if (filter > 0) throw new IllegalArgumentException("Posisjon ("+posisjon+") mangler forelder!");
        else if (p != null) throw new IllegalArgumentException("Posisjon ("+posisjon+") finnes fra før!");

        p = new Node<>(verdi);

        if (q == null) rot = p;
        else if((posisjon & 1) == 0) {
            q.venstre = p;
        } else {
            q.høyre = p;
        }
        antall++;
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    private Node<T> finnNode(int posisjon) {
        if (posisjon < 1) return null;

        Node<T> p = rot;
        int filter = Integer.highestOneBit(posisjon >> 1);

        for (; p != null && filter > 0; filter >>= 1) {
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;
        }

        return p;
    }

    public boolean finnes(int posisjon) {
        return finnNode(posisjon) != null;
    }

    public T hent(int posisjon) {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new IllegalArgumentException("Posisjon ("+posisjon+") finnes ikke i treet!");

        return p.verdi;
    }

    public T oppdater(int posisjon, T nyverdi) {
        Node<T> p = finnNode(posisjon);

        if (p == null) throw new IllegalArgumentException("Posisjon ("+posisjon+") finnes ikke i treet!");

        T gammelverdi = p.verdi;
        p.verdi = nyverdi;

        return gammelverdi;
    }

    public T fjern(int posisjon) {
        if (posisjon < 1) throw new IllegalArgumentException("Posisjon(" + posisjon + ") < 1!");

        Node<T> p = rot, q = null;               // hjelpepekere
        int filter = Integer.highestOneBit(posisjon >> 1);   // binært siffer

        while (p != null && filter > 0)
        {
            q = p;
            p = (filter & posisjon) == 0 ? p.venstre : p.høyre;
            filter >>= 1;
        }

        if (p == null) throw new IllegalArgumentException("Posisjon(" + posisjon + ") er utenfor treet!");

        if (p.venstre != null || p.høyre != null) throw new IllegalArgumentException("Posisjon(" + posisjon + ") er ingen bladnode!");

        if (p == rot) rot = null;
        else if (p == q.venstre) q.venstre = null;
        else q.høyre = null;

        antall--;  //
        return p.verdi;
    }

    public void nivåorden(Consumer<? super T> oppgave) {
        if (tom()) return;

        Kø<Node<T>> kø = new TabellKø<>();
        kø.leggInn(rot);

        while(!kø.tom()) {
            Node<T> p = kø.taUt();
            oppgave.accept(p.verdi);

            if (p.venstre != null) kø.leggInn(p.venstre);
            if (p.høyre != null) kø.leggInn(p.høyre);
        }
    }

    private static <T> void preorden(Node<T> p, Consumer<? super T> oppgave) {
        oppgave.accept(p.verdi);
        if (p.venstre != null) preorden(p.venstre, oppgave);
        if (p.høyre != null) preorden(p.høyre, oppgave);
    }

    private static <T> void inorden(Node<T> p, Consumer<? super T> oppgave) {
        if (p.venstre != null) inorden(p.venstre, oppgave);
        oppgave.accept(p.verdi);
        if (p.høyre != null) inorden(p.høyre, oppgave);
    }

    private static <T> void postorden(Node<T> p, Consumer<? super T> oppgave) {
        if (p.venstre != null) postorden(p.venstre, oppgave);
        if (p.høyre != null) postorden(p.høyre, oppgave);
        oppgave.accept(p.verdi);
    }

    public void postorden(Consumer<? super T> oppgave) {
        postorden(rot, oppgave);
    }

    public void inorden(Consumer<? super T> oppgave) {
        inorden(rot, oppgave);
    }

    public void preorden(Consumer<? super T> oppgave) {
        preorden(rot, oppgave);
    }

    public int[] nivåer() {
        if (tom()) return new int[0];

        int[] a = new int[8];
        Kø<Node<T>> kø = new TabellKø<>();
        int nivå = 0;

        kø.leggInn(rot);

        while(!kø.tom()) {
            if (nivå == a.length) a = Arrays.copyOf(a, 2*nivå); // utvider tabell

            int k = a[nivå] = kø.antall();

            for (int i = 0; i < k; i++) {
                Node<T> p = kø.taUt();

                if (p.venstre != null) kø.leggInn(p.venstre);
                if (p.høyre != null) kø.leggInn(p.høyre);
            }
            nivå++;
        }
        return Arrays.copyOf(a, nivå);  // fjerner overflødige plasser og returnerer
    }

    public T førstInorden() {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (p.venstre != null) {
            p = p.venstre;
        }

        return p.verdi;
    }

    public T førstPostorden() {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while(true) {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p.verdi;
        }
    }

    private static <T> Node<T> trePreorden(T[] preorden, int rot, T[] inorden, int v, int h) {
        if (v > h) {
            return null;    // tomt intervall -> tomt tre
        }

        int k = v;
        T verdi = preorden[rot];

        while (!verdi.equals(inorden[k])) {
            k++;
        }

        Node<T> venstre = trePreorden(preorden, rot + 1, inorden, v, k - 1);
        Node<T> høyre = trePreorden(preorden, rot + 1 + k - v, inorden, k + 1, h);

        return new Node<>(verdi, venstre, høyre);
    }

    public static <T> BinTre<T> trePreorden(T[] preorden, T[] inorden) {
        BinTre<T> tre = new BinTre<>();
        tre.rot = trePreorden(preorden, 0, inorden, 0, inorden.length - 1);

        tre.antall = preorden.length;
        return tre;
    }

    private static <T> void preorden(Node <T> p, int k, ObjIntConsumer<? super T> oppgave) {
        oppgave.accept(p.verdi, k);
        if (p.venstre != null) preorden(p.venstre, 2*k, oppgave);
        if (p.høyre != null) preorden(p.høyre, 2*k + 1, oppgave);
    }

    public void preorden(ObjIntConsumer<? super T> oppgave) {
        if (!tom()) preorden(rot, 1, oppgave);  // roten har posisjon 1
    }

    private static <T> void nullstill(Node<? super T> p) {
        if (p.venstre != null) {
            nullstill(p.venstre);
            p.venstre = null;   // nuller peker
        }
        if (p.høyre != null) {
            nullstill(p.høyre);
            p.høyre = null;
        }
        p.verdi = null; // nuller verdi
    }

    public void nullstill() {
        nullstill(rot);
    }

    public static void main(String[] args) {
        int[] posisjon = {1,2,3,4,5,6,7,10,11,13,14,22,23,28,29};  // posisjoner og
        String[] verdi = "OGNAFIMBEHLCDJK".split("");              // verdier i nivåorden
        BinTre<String> tre = new BinTre<>(posisjon, verdi);        // en konstruktør

        StringJoiner s = new StringJoiner(", " ,"[", "]");         // StringJoiner
        tre.postorden(s::add);                         // tegn = String

        System.out.println(s);
        // Utskrift: [E, I, G, A, L, O, M, C, B, H, D, K, N, J, F]

        tre.nullstill();

        s = new StringJoiner(", " ,"[", "]");
        tre.postorden(s::add);
        System.out.println(s);
    }
} // class BinTre
