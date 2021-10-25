package hjelpeklasser;

import hjelpeklasser.Liste;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EnkeltLenketListe<T> implements Liste<T> {
    private Node<T> hode;   // referanse til den første noden i listen
    private Node<T> hale;   // referanse til siste node i listen
    private int antall;     // amtall noder i listen
    private int endringer;  // endringer i listen


    /**
     * Hjelpeklasse for EnkeltLenketListe, derfor private
     * Static fordi dens instanser ikke vil være avhengig av å ha kontakt med listeklassens variabler
     * det opprettes da ingen bildinger til den ytre klassen -> enklere kompilert kode
     * Heller ikke aktuelt med subklasser av Node, dermed er den final -> vil også gi fordeler under kompilering
     * @param <T> generisk verdi
     */
    private static final class Node<T> {    // nested class
        private T verdi;
        private Node<T> neste;

        private Node(T verdi, Node<T> neste) {   // konstruktør
            this.verdi = verdi;
            this.neste = neste;
        }
    } // Node

    private class EnkeltLenketListeIterator implements Iterator<T> {
        private Node<T> p = hode;           // p starter på den første i listen
        private boolean fjernOK = false;    // blir sann når next() kalles
        private int iteratorEndringer = endringer;  // startverdi

        public boolean hasNext() {
            return p != null;   // p er ute av listen hvis den har blitt null
        }

        public T next() {
            if (endringer != iteratorEndringer) {
                throw new ConcurrentModificationException("Listen er endret");
            }
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier");

            fjernOK = true; // må kan remove kalles
            T denneVerdi = p.verdi; // tar vare på verdien i p
            p = p.neste;    // flytter å til den neste verdien

            return denneVerdi;      // returnerer verdien
        }

        public void remove() {
            if (endringer != iteratorEndringer) {
                throw new ConcurrentModificationException("Listen er endret");
            }
            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;    // remove() kan ikke kalles på nytt
            Node<T> q = hode;   // hjelpevariabel

            if (hode.neste == p) {  // skal den første fjernes?
                hode = hode.neste;  // den første fjernes
                if (p == null) hale = null; // dette var den eneste noden
            } else {
                Node<T> r = hode;   // må finne forgjengeren til p

                while(r.neste.neste != p) {
                    r = r.neste;    // flytter r
                }

                q = r.neste;    // det er q som skal fjernes
                r.neste = p;    // hopper over q
                if (p == null) hale = r;    // q var den siste
            }

            q.verdi = null;
            q.neste = null;

            antall--;   // en node mindre i listen
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action, "null-consumer");
            while(p != null) {
                action.accept(p.verdi);
                p = p.neste;
            }
        }
    }


    public EnkeltLenketListe() {    // standardkonstruktør
        hode = hale = null;    // hode og hale er null
        antall = 0;     // ingen verdier - listen er tom
        endringer = 0;  // ingen endringer når vi starter
    }

    public EnkeltLenketListe(T[] a) {
        this(); // alle variabler er nullet

        // Finner første element i a som ikke er null
        int i = 0;
        for(; i < a.length && a[i] == null; i++);

        if (i < a.length) {
            Node<T> p = hode = new Node<>(a[i], null);  // den første noden
            antall = 1; // vi har minst en node

            for (i++; i < a.length; i++) {
                if(a[i] != null) {
                    p = p.neste = new Node<>(a[i], null);   // en ny node
                    antall++;
                }
            }

            hale = p;
        }
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier");

        if (antall == 0) {
            hode = hale = new Node<>(verdi, null);  // tom liste
        } else {
            hale = hale.neste = new Node<>(verdi, null);    // legges bakerst
        }

        antall++;       // en mer i listen
        endringer++;
        return true;    // vellykket innlegging
    }

    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier");

        indeksKontroll(indeks, true);

        if (indeks == 0) {  // ny verdi skal ligge først
            hode = new Node<>(verdi, hode); // legges først
            if (antall == 0) {
                hale = hode;    // hode og hale går til samme node
            }
        } else if (indeks == antall) {  // ny verdi skal ligge bakerst
            hale = hale.neste = new Node<>(verdi, null);    // legges bakerst
        } else {
            Node<T> p = hode;   // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++) {
                p = p.neste;
            }

            p.neste = new Node<>(verdi, p.neste);   // verdien setttes inn i listen
        }

        antall++;   // listen har fått en ny verdi
        endringer++;
    }

    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    public int indeksTil(T verdi) {
        if (verdi == null) return -1;

        Node<T> temp = hode;

        for (int i = 0; i < antall; i++) {
            if (temp.verdi.equals(verdi)) return i; // returnerer indeksen til temp om temp.verdi er lik verdi
            temp = temp.neste;  // oppdaterer temp
        }
        return -1;  // ikke funnet
    }

    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier");
        indeksKontroll(indeks, false);

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;
        p.verdi = verdi;

        endringer++;

        return gammelVerdi;
    }

    public boolean fjern(T verdi) {
        if (verdi == null) return false;

        Node<T> q = hode, p = null;

        while (q != null) {
            if (q.verdi.equals(verdi)) break;   // leter etter verdi
            p = q;  // p er forgjengeren til q
            q = q.neste;
        }

        if (q == null) return false;    // verdi finnes ikke
        else if (q == hode) hode = hode.neste;  // hvis q er først, flyttes hode til sin neste
        else p.neste = q.neste; // hopper over q

        if (q == hale) hale = p;    // q var siste node i listen


        q.verdi = null; // nuller verdien til q
        q.neste = null; // nuller nestepeker

        antall--;
        endringer++;

        return true;
    }

    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        T temp;

        if (indeks == 0) {  // skal første verdi fjernes?
            temp = hode.verdi;  // ta vare på verdien som skal fjernes
            hode = hode.neste;  // hode flyttes til neste node
            if (antall == 1) hale = null;   // kun 1 verdi i listen
        } else {
            Node <T> p = finnNode(indeks - 1);  // p er noden foran den som skal fjernes
            Node<T> q = p.neste;    // q skal fjernes
            temp = q.verdi;         // tar vare på verdien som skal fjernes

            if (q == hale) {
                hale = p;   // hvis q er siste node
            }
            p.neste = q.neste;  // "hopper over" q
        }

        antall--;       // reduserer antall
        endringer++;
        return temp;    // returnerer fjernet verdi
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public void nullstill() {
        hode = hale = null;
        antall = 0;
        endringer++;
    }

    public Iterator<T> iterator() {
        return new EnkeltLenketListeIterator();
    }

    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (Node<T> p = hode; p != null; p = p.neste) {
            sj.add(p.verdi.toString());
        }
        return sj.toString();
    }

    private Node<T> finnNode(int indeks) {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++) {
            p = p.neste;
        }
        return p;
    }

    public boolean fjernHvis(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate,"null-predikat!");

        Node<T> p = hode, q = null;
        int antallFjernet = 0;

        while (p != null) {
            if (predicate.test(p.verdi)) {
                antallFjernet++;
                endringer++;

                if (p == hode) {
                    if (p == hale) {
                        hale = null;
                        hode = hode.neste;
                    }
                } else if (p == hale) {
                    q.neste = null;
                } else {
                    q.neste = p.neste;
                }
            }
            q = p;
            p = p.neste;
        }
        antall -= antallFjernet;
        return antallFjernet > 0;
    }

    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action, "null-consumer");
        Node<T> p = hode;
        while (p != null) {
            action.accept(p.verdi);
            p = p.neste;
        }
    }

    public static void main(String[] args) {
        Liste<Integer> liste = new EnkeltLenketListe<>();
        for (int i = 1; i <= 10; i++) liste.leggInn(i);
        System.out.println(liste);

        // fjerner partallene
        liste.fjernHvis(x -> x % 2 == 0);

        System.out.println(liste);
        // skriver ut
        liste.forEach(x -> System.out.print(x + " "));
    }
}
