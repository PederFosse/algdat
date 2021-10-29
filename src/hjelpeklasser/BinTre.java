package hjelpeklasser;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

public class BinTre<T> implements Iterable<T> {
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

        public String toString() {
            return verdi.toString();
        }
    } // class Node

    private Node<T> rot;
    private int antall;
    private int endringer = 0;

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

    private class OmvendtInordenIterator implements Iterator<T> {
        private Stakk<Node<T>> stakk;
        private Node<T> p = null;

        private OmvendtInordenIterator() {
            if (tom()) return;
            stakk = new TabellStakk<>();
            p = først(rot);
        }

        public T next () {
            if (!hasNext()) throw new NoSuchElementException("Ingen verdie!");

            T verdi = p.verdi;

            if (p.venstre != null) p = først(p.venstre);
            else if (stakk.tom()) p = null;
            else p = stakk.taUt();

            return verdi;
        }

        public boolean hasNext() {
            return p != null;
        }

        private Node<T> først(Node<T> q) {
            while(q.høyre != null) {
                stakk.leggInn(q);
                q = q.høyre;
            }
            return q;
        }
    }

    private class InordenIterator implements Iterator<T> {
        private Stakk<Node<T>> stakk;
        private Node<T> p = null;
        private int iteratorEndringer = endringer;

        private InordenIterator() {
            if (tom()) return;
            stakk = new TabellStakk<>();
            p = først(rot);
        }

        public T next() {
            if (iteratorEndringer != endringer) throw new ConcurrentModificationException("Treet er endret!");
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            T verdi = p.verdi;

            if (p.høyre != null) p = først(p.høyre);    // p har høyre subtre
            else if (stakk.tom()) p = null;             // stakken er tom
            else p = stakk.taUt();                      // tar fra stakken

            return verdi;
        }

        public boolean hasNext() {
            if (iteratorEndringer != endringer) throw new ConcurrentModificationException("Treet er endret!");
            return p != null;
        }

        /**
         * Hjelpemetode, finner første noden inorden (lengst til venstre) i tre med rot q
         * @param q roten
         * @return  Første noden inorden i tre med rot = q
         */
        private Node<T> først (Node<T> q) {
            while(q.venstre != null) {  // starter i q
                stakk.leggInn(q);       // legger q på stakken
                q = q.venstre;          // q er lenst ned til venstre
            }
            return q;
        }
    }

    private class PreordenInterator implements Iterator<T> {
        private final Stakk<Node<T>> s;
        private Node<T> p;

        private PreordenInterator() {
            s = new TabellStakk<>();
            p = rot;
        }

        public boolean hasNext() {
            return p != null;
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T verdi = p.verdi;

            if (p.venstre != null) {    // går til venstre
                if (p.høyre != null) s.leggInn(p.høyre);
                p = p.venstre;
            }
            else if (p.høyre != null) p = p.høyre;  // går til høyre
            else if (s.tom()) p = null; // ingen fler i treet
            else p = s.taUt();  // tar fra stakken

            return verdi;
        }
    }

    public Iterator<T> preIterator() {
        return new PreordenInterator();
    }

    public Iterator<T> iterator() {
        return new InordenIterator();
    }

    public Iterator<T> omvendtIterator() {
        return new OmvendtInordenIterator();
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
        endringer++;
        antall++;
    }

    private static int antall(Node<?> p) {
        if (p == null) return 0;
        return 1 + antall(p.venstre) + antall(p.høyre);
    }

    public int antall() {
        return antall(rot);
    }

    private static int høyde(Node<?> p) {
        if (p == null) return -1;

        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    public int høyde() {
        return høyde(rot);
    }

    private static <T> boolean inneholder(Node<T> p, T verdi) {
        if (p == null) return false;    // kan ikke ligge i et tomt tre
        return verdi.equals(p.verdi) || inneholder(p.venstre, verdi) || inneholder(p.høyre, verdi);
    }

    public boolean inneholder(T verdi) {
        return inneholder(rot, verdi);
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

        endringer++;
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

        antall--;
        endringer++;
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

    public void postorden2(Consumer<? super T> oppgave) {
        if (tom()) return;

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;

        while(true) {
            while (p != null) {
                if (p.høyre != null) stakk.leggInn(p.høyre);
                stakk.leggInn(p);
                p = p.venstre;
            }
            p = stakk.taUt();
            if (stakk.tom()) {
                oppgave.accept(p.verdi);
                break;
            }
            if (p.høyre != null && p.høyre == stakk.kikk()) {   // har høyre barn og høyre barn ligger øverst i stakk
                Node<T> temp = stakk.taUt();
                stakk.leggInn(p);
                p = temp;
            } else {
                oppgave.accept(p.verdi);
                p = null;
            }
        }
    }

    public void inorden(Consumer<? super T> oppgave) {
        inorden(rot, oppgave);
    }

    public void preorden(Consumer<? super T> oppgave) {
        preorden(rot, oppgave);
    }

    private static <T> Node<T> random(int n, Random r) {
        if (n == 0) return null;                      // et tomt tre
        else if (n == 1) return new Node<>(null);     // tre med kun en node

        int k = r.nextInt(n);    // k velges tilfeldig fra [0,n>

        Node<T> venstre = random(k,r);     // tilfeldig tre med k noder
        Node<T> høyre = random(n-k-1,r);   // tilfeldig tre med n-k-1 noder

        return new Node<>(null,venstre,høyre);
    }

    public static <T> BinTre<T> random(int n) {
        if (n < 0) throw new IllegalArgumentException("Må ha n >= 0!");

        BinTre<T> tre = new BinTre<>();
        tre.antall = n;

        tre.rot = random(n,new Random());   // kaller den private metoden

        return tre;
    }

    public void preorden2(Oppgave<? super T> oppgave) {
        if (tom()) return;

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;    // starter i roten

        while (true)
        {
            oppgave.utførOppgave(p.verdi);

            if (p.venstre != null)
            {
                if (p.høyre != null) stakk.leggInn(p.høyre);
                p = p.venstre;
            }
            else if (p.høyre != null)  // her er p.venstre lik null
            {
                p = p.høyre;
            }
            else if (!stakk.tom())     // her er p en bladnode
            {
                p = stakk.taUt();
            }
            else                       // p er en bladnode og stakken er tom
                break;                   // traverseringen er ferdig
        }
    }

    private static <T> void preorden3(Node<T> p, Oppgave<? super T> oppgave) {
        while(true) {
            oppgave.utførOppgave(p.verdi);
            if (p.venstre != null) preorden3(p.venstre, oppgave);
            if (p.høyre == null) return;
            p = p.høyre;
        }
    }

    private static <T> void inorden3(Node<T> p, Oppgave<? super T> oppgave) {
        while(true) {
            if (p.venstre != null) inorden3(p.venstre, oppgave);
            oppgave.utførOppgave(p.verdi);
            if (p.høyre == null) return;
            p = p.høyre;
        }
    }

    public void inorden2(Oppgave<? super T> oppgave) {
        if (tom()) return;

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;

        for ( ; p.venstre != null; p = p.venstre) {
            stakk.leggInn(p);
        }

        while(true) {
            oppgave.utførOppgave(p.verdi);

            if (p.høyre != null) {  // til venstre i høyre subtre
                for (p = p.høyre; p.venstre != null; p = p.venstre) {
                    stakk.leggInn(p);
                }
            } else if (!stakk.tom()) {
                p = stakk.taUt();
            } else {
                break;
            }
        }
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
        endringer++;
        nullstill(rot);
    }

    public static void main(String[] args)
    {
        int[] posisjon = {1,2,3,4,5,6,7,8,9,10};             // posisjoner og
        String[] verdi = "ABCDEFGHIJ".split("");             // verdier i nivåorden

        BinTre<String> tre = new BinTre<>(posisjon, verdi);  // konstruktør

        for (Iterator<String> i = tre.iterator(); i.hasNext(); ) {
            System.out.print(i.next() + " ");
        }

        System.out.println();

        for (Iterator<String> i = tre.omvendtIterator(); i.hasNext(); ) {
            System.out.print(i.next() + " ");
        }
    }
} // class BinTre
